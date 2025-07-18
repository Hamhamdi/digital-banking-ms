package net.hamdi.beneficiaryservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class LogsTest {

    @GetMapping("/logs")
    public String generateLogs() {
        log.debug("Debug log message");
        log.info("Info log message - generating test logs");
        log.warn("Warning log message");

        try {
            // Simulate some work
            Thread.sleep(100);
            log.info("Operation completed successfully");
        } catch (InterruptedException e) {
            log.error("Error occurred during operation", e);
            Thread.currentThread().interrupt();
        }

        return "Logs generated successfully!";
    }

    @GetMapping("/error")
    public String generateError() {
        log.error("This is a test error log for demonstration");
        throw new RuntimeException("Test exception for logging");
    }

    @GetMapping("/beneficiary/{id}")
    public String getBeneficiary(@PathVariable String id) {
        log.info("Fetching beneficiary with ID: {}", id);

        if ("invalid".equals(id)) {
            log.warn("Invalid beneficiary ID provided: {}", id);
            return "Invalid ID";
        }

        log.debug("Beneficiary data retrieved successfully for ID: {}", id);
        return "Beneficiary: " + id;
    }
}