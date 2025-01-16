package org.example.app.service;

import java.util.concurrent.Semaphore;

public class RateLimiter<T extends Service> implements Service {
    private final T service;
    private final Semaphore semaphore;

    public RateLimiter(T service, int rateLimit) {
        this.service = service;
        this.semaphore = new Semaphore(rateLimit);
    }

    @Override
    public String executeService(String input) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            semaphore.release();
            throw new RuntimeException(e);
        }

        var result = service.executeService(input);
        semaphore.release();
        return result;
    }
}
