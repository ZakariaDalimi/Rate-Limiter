package test.java.com.exemple.ratelimiter;

import main.java.com.exemple.ratelimiter.RateLimiterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RateLimiterServiceTest {

    private static RateLimiterService testRateLimiter;

    @BeforeEach
    public void setUp()
    {
        testRateLimiter = new RateLimiterService(5, 1);

    }


    @Test
    void testBlockRequestsWhenCapacityExceeded(){

        testRateLimiter.allowRequest("user_1");
        for(int i = 0; i < 5; i++){
            testRateLimiter.allowRequest("user_1");
        }

        assertFalse(testRateLimiter.allowRequest("user_1"), "the 6th request should be blocked");

    }

    @Test
    void testGettingActiveBlockedUsers() {
        testRateLimiter.allowRequest("user_1");
        testRateLimiter.allowRequest("user_2");

        for(int i = 0; i < 7; i++){
            testRateLimiter.allowRequest("user_1");
        }

        assertEquals(2, testRateLimiter.getActiveUsersCount());
        assertEquals(1, testRateLimiter.getBlockedUsersCount());
    }

}
