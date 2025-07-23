package com.lemon.taskmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
@CrossOrigin(origins = "http://localhost:8080")
public class FrontLoggingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrontLoggingController.class);

    @PostMapping
    public ResponseEntity<Void> log(@RequestBody LoggingRequest loggingRequest) {
        String msg = "[FRONT] " + loggingRequest.message();

        switch (loggingRequest.logLevel()) {
            case "INFO" -> LOGGER.info(msg);
            case "WARN" -> LOGGER.warn(msg);
            case "ERROR" -> LOGGER.error(msg);
            default -> LOGGER.debug("[FRONT][UNRECOGNIZED LEVEL] " + msg);
        }

        return ResponseEntity.ok().build();
    }

    public record LoggingRequest(String logLevel, String message) {}
}
//TODO parece que no están llegando, parece un tema de cors. Arreglar