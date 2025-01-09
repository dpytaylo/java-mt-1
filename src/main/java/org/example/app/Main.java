package org.example.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.app.service.FormatStringService;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String[] strings;
        try {
            var content = new String(Files.readAllBytes(Paths.get("input.txt")));
            strings = content.split("\\r?\\n");
        } catch (IOException e) {
            logger.error(e);
            return;
        }

        var formatStringService = new RateLimiter<>(FormatStringService.INSTANCE, 2);

        logger.info("Start");
        var start = Instant.now();
        for (var id : strings) {
            new Thread(() -> {
                var output = formatStringService.run(id);
                var finish = Instant.now();
                var timeElapsed = Duration.between(start, finish).toMillis();
                logger.info("Input: \"{}\"; Output: \"{}\"; Elapsed: {}", id, output, timeElapsed);
            }, id).start();
        }
    }
}