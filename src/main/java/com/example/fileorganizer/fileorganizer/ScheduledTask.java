package com.example.fileorganizer.fileorganizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${DOWNLOADS_DIRECTORY}")
    private String downloadsDirectory;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", simpleDateFormat.format(new Date()));

        // Check Downloads directory for files
        log.info("Downloads directory: {}", downloadsDirectory);
        checkDirectories();
    }

    public void checkDirectories() {
        Path path = FileSystems.getDefault().getPath(downloadsDirectory);

        if (Files.isDirectory(path)) {
            log.info("Directory exists");
            checkFiles();
        } else {
            log.info("Directory {} does not exist", downloadsDirectory);
        }
    }

    public void checkFiles() {
        // TODO: check files in directory
    }
}
