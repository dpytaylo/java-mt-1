package org.example.app.service;

import java.util.concurrent.TimeUnit;

public enum FormatStringService implements Service {
    INSTANCE;

    @Override
    public String executeService(String input) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "---- " + input + " ----";
    }
}
