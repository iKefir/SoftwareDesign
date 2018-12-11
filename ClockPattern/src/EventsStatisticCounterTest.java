import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Random;

class EventsStatisticCounterTest {
    SetableClock clock;
    Instant now;
    EventsStatistic eventsStatistic;

    @BeforeEach
    void onStartup() {
        now = Instant.now();
        clock = new SetableClock(now);
        eventsStatistic = new EventsStatisticCounter(clock);
    }

    @Test
    void testGetEventStatisticByName() {
        eventsStatistic.incEvent("First");
        eventsStatistic.incEvent("Second");
        eventsStatistic.incEvent("First");

        Assertions.assertEquals(2.0d/60, eventsStatistic.getEventStatisticByName("First"));
        Assertions.assertEquals(1.0d/60, eventsStatistic.getEventStatisticByName("Second"));
    }

    @Test
    void testGetEventStatisticsForHour() {
        for (int i = 0; i < 3000; ++i) {
            eventsStatistic.incEvent("Event");
            now = now.plusSeconds(60);
            clock.setNow(now);
        }
        Assertions.assertEquals(1.0d, eventsStatistic.getEventStatisticByName("Event"));
    }

    @Test
    void testGetAllEventStatistic() {
        eventsStatistic.incEvent("First");
        eventsStatistic.incEvent("Second");
        eventsStatistic.incEvent("First");

        Assertions.assertEquals(3.0d/60, eventsStatistic.getAllEventStatistic());
    }

    @Test
    void testGetAllEventStatisticForHour() {
        Random random = new Random();
        String[] events = {"Event", "Another event", "Third Event"};
        for (int i = 0; i < 3000; ++i) {
            eventsStatistic.incEvent(events[random.nextInt(3)]);
            now = now.plusSeconds(60);
            clock.setNow(now);
        }
        Assertions.assertEquals(1.0d, eventsStatistic.getAllEventStatistic());
    }
}