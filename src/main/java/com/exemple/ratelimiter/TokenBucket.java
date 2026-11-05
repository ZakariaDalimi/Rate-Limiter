package main.java.com.exemple.ratelimiter;

import java.sql.Timestamp;
import java.util.Objects;

public class TokenBucket {

    private int capacity;
    private int tokens;
    private Timestamp lastRefillTimestamp;
    private int timeWindowInMinute;
    private final Object lock = new Object();

    public TokenBucket(int capacity, int tokens, int timeWindowInMinute)
    {
        setCapacity(capacity);
        setTokens(tokens);
        settimeWindowInMinute(timeWindowInMinute);
        this.lastRefillTimestamp = new Timestamp(System.currentTimeMillis());
    }



    public boolean tryConsume()
    {
        synchronized (lock) {
            long now = System.currentTimeMillis();
            long timeDiff = now - lastRefillTimestamp.getTime();
            long windowMs = timeWindowInMinute * 60 * 1000L;


            if (timeDiff >= windowMs ) {
                setTokens(getCapacity());
                setLastRefillTimestamp(new Timestamp (System.currentTimeMillis()));
            }

            if(getTokens() >= 1) {
                setTokens(getTokens() - 1);
                return true;
            }else{
                return false;
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TokenBucket that = (TokenBucket) o;
        return capacity == that.capacity && tokens == that.tokens && timeWindowInMinute == that.timeWindowInMinute && Objects.equals(lastRefillTimestamp, that.lastRefillTimestamp) && Objects.equals(lock, that.lock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, tokens, lastRefillTimestamp, timeWindowInMinute, lock);
    }

    @Override
    public String toString() {
        return "TokenBucket{" +
                "capacity=" + capacity +
                ", tokens=" + tokens +
                ", lastRefillTimestamp=" + lastRefillTimestamp +
                ", timeWindowInMinute=" + timeWindowInMinute +
                '}';
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public Timestamp getLastRefillTimestamp() {
        return lastRefillTimestamp;
    }

    public void setLastRefillTimestamp(Timestamp lastRefillTimestamp) {
        this.lastRefillTimestamp = lastRefillTimestamp;
    }

    public long gettimeWindowInMinute() {
        return timeWindowInMinute;
    }

    public void settimeWindowInMinute(int timeWindowInMinute) {
        this.timeWindowInMinute = timeWindowInMinute;
    }
}
