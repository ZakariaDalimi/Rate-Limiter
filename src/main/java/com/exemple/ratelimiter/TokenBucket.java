package main.java.com.exemple.ratelimiter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class TokenBucket {

    private int capacity;
    private int tokens;
    private Timestamp lastRefillTimestamp;
    private long timeWindowInMinute;

    public TokenBucket(int capacity, int tokens, Timestamp lastRefillTimestamp, long timeWindowInMinute)
    {
        setCapacity(capacity);
        setTokens(tokens);
        setLastRefillTimestamp(lastRefillTimestamp);
        settimeWindowInMinute(timeWindowInMinute);
    }

    public boolean tryConsume()
    {
        long now = System.currentTimeMillis();

        long timeDiff = now - lastRefillTimestamp.getTime();

        long windowMs = timeWindowInMinute * 60 * 1000L;

        if (timeDiff >= windowMs ) {
            setTokens(getCapacity());
            setLastRefillTimestamp(new Timestamp (System.currentTimeMillis()));
        }

        if(getTokens() >= 1)
        {
            setTokens(getTokens() - 1);
            return true;
        }

        return true;
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

    public void settimeWindowInMinute(long timeWindowInMinute) {
        this.timeWindowInMinute = timeWindowInMinute;
    }
}
