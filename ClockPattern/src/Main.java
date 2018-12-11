import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Clock clock = new NormalClock();
        EventsStatistic eventsStatistic = new EventsStatisticCounter(clock);

        eventsStatistic.incEvent("HAHA");
        eventsStatistic.incEvent("HAHA");
        eventsStatistic.printStatistic();
    }
}
