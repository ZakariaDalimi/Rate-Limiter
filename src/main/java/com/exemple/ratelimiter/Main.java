package main.java.com.exemple.ratelimiter;

import java.util.ArrayList;
import java.util.List;

public class Main {

    void main() {

        RateLimiterService loginRateLimiter = new RateLimiterService(5, 1);

        ArrayList<String> users = new ArrayList<>(List.of("user_1", "user_2"));

        System.out.println("=== RATE LIMITER DEMO ===");
        System.out.printf("Limit: %d requests per %d minute(s).\n",
                loginRateLimiter.getCapacity(), loginRateLimiter.getTimeWindowInMinute());

        System.out.println("\n--- SIMULATING REQUESTS ---");

        for (int i = 0; i < 7; i++) {
            System.out.printf("Request #%d\n", i + 1);
            for (String user : users) {
                if (loginRateLimiter.allowRequest(user)) {
                    System.out.println("  " + user + ": \u001B[32mALLOWED (HTTP 200 OK)\u001B[0m" + " | Tokens left " + loginRateLimiter.getTokens(user));
                } else {
                    System.out.println("  " + user + ": \u001B[31mBLOCKED (HTTP 429 Too Many Requests)\u001B[0m" + " | Tokens left " + loginRateLimiter.getTokens(user));
                }
            }
        }

        System.out.println("\n--- TELEMETRY METRICS ---");
        System.out.printf("Active users: %d\n", loginRateLimiter.getActiveUsersCount());
        System.out.printf("Blocked users: %d\n", loginRateLimiter.getBlockedUsersCount());
    }
}