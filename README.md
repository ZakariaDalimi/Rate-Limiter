# Java Rate Limiter

A self-programmed project implementing rate limiting to control API request frequency.

An in-memory, thread-safe rate limiter built manually from scratch in Java using the Token Bucket algorithm. It dynamically controls request rates per user to protect backend APIs and login endpoints from overload and spam.

## Key Features

* **Lazy Refill Strategy:** Refills tokens dynamically on incoming requests without background timer threads.
* **Thread-Safe Architecture:** Uses `ConcurrentHashMap` with `computeIfAbsent()` and `synchronized` blocks for race-condition-free execution.
* **Telemetry & Metrics:** Leverages Java Streams to track active and blocked users in real time.
* **Concurrency Tested:** Includes a JUnit 5 test suite verifying multi-threaded safety under heavy load.

## 💻 Quick Start

```java
// Limit: 5 requests per 1-minute window
RateLimiterService rateLimiter = new RateLimiterService(5, 1);

if (rateLimiter.allowRequest("user_101")) {
    // Request allowed (HTTP 200 OK)
} else {
    // Request blocked (HTTP 429 Too Many Requests)
}