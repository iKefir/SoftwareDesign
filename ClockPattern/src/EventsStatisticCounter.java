import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class EventsStatisticCounter implements EventsStatistic {

    private Clock clock;
    private Map<String, Queue<Instant>> eventsTimes;

    EventsStatisticCounter(Clock clock) {
        this.clock = clock;
        eventsTimes = new HashMap<>();
    }

    private void deleteOldEvents(Queue<Instant> queue, Instant now) {
        while (queue.peek() != null && queue.peek().isBefore(now.minusSeconds(60 * 60))) {
            queue.remove();
        }
    }

    public void incEvent(String name) {
        Instant now = clock.now();
        if (eventsTimes.containsKey(name)) {
            Queue<Instant> queue = eventsTimes.get(name);
            queue.add(now);
            deleteOldEvents(queue, now);
        } else {
            Queue<Instant> queue = new ArrayDeque<>();
            queue.add(now);
            eventsTimes.put(name, queue);
        }
    }

    private int getEventCountByName(String name, Instant now) {
        if (eventsTimes.containsKey(name)) {
            Queue<Instant> queue = eventsTimes.get(name);
            deleteOldEvents(queue, now);
            return queue.size();
        } else {
            return 0;
        }
    }

    public double getEventStatisticByName(String name) {
        Instant now = clock.now();
        return ((double) getEventCountByName(name, now)) / 60;
    }

    public double getAllEventStatistic() {
        Instant now = clock.now();
        int counter = 0;
        for (String key : eventsTimes.keySet()) {
            counter += getEventCountByName(key, now);
        }
        return ((double) counter) / 60;
    }

    public void printStatistic() {
        System.out.println(getAllEventStatistic());
    }
}
