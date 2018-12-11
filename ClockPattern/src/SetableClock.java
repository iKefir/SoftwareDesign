import java.time.Instant;

public class SetableClock implements Clock {
    private Instant now;

    public SetableClock(Instant now) {
        this.now = now;
    }

    void setNow(Instant now) {
        this.now = now;
    }

    public Instant now() {
        return now;
    }
}
