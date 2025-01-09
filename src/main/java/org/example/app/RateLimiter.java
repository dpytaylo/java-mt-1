package org.example.app;

import org.example.app.service.FormatStringService;

import java.util.concurrent.Semaphore;

public class RateLimiter<T extends FormatStringService> {
    private final T service;
    private final Semaphore semaphore;

    public RateLimiter(T service, int rateLimit) {
        this.service = service;
        this.semaphore = new Semaphore(rateLimit);
    }

    public String run(String input) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var result = service.run(input);
        semaphore.release();
        return result;
    }
}
