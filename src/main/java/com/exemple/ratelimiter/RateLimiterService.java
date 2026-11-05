package main.java.com.exemple.ratelimiter;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class RateLimiterService {

    private final Map<String, TokenBucket> userBuckets;

     private int capacity = 5;
     private int timeWindowInMinute = 5;


    public RateLimiterService( int capacity, int timeWindowInMinute)
    {
        setCapacity(capacity);
        setTimeWindowInMinute(timeWindowInMinute);

        this.userBuckets = new ConcurrentHashMap<>();
    }

    @Override
    public String toString() {
        return "RateLimiterService{" +
                "userBuckets=" + userBuckets +
                ", capacity=" + capacity +
                ", timeWindowInMinute=" + timeWindowInMinute +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RateLimiterService that = (RateLimiterService) o;
        return capacity == that.capacity && timeWindowInMinute == that.timeWindowInMinute && Objects.equals(userBuckets, that.userBuckets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userBuckets, capacity, timeWindowInMinute);
    }


    public boolean allowRequest(String user){
        TokenBucket bucket = getUserBuckets().computeIfAbsent(user, key -> new TokenBucket(capacity, capacity, timeWindowInMinute));
        return bucket.tryConsume();
    }


    public int getActiveUsersCount(){
        return getUserBuckets().size();
    }

    public int getBlockedUsersCount()
    {
        return Math.toIntExact(getUserBuckets().values().stream()
                .filter(bucket -> bucket.getTokens() == 0)
                .count());
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTimeWindowInMinute() {
        return timeWindowInMinute;
    }

    public void setTimeWindowInMinute(int timeWindowInMinute) {
        this.timeWindowInMinute = timeWindowInMinute;
    }


    public Map<String, TokenBucket> getUserBuckets() {
        return userBuckets;
    }


    public int getTokens(String user) {
        return userBuckets.get(user).getTokens();
    }
}
