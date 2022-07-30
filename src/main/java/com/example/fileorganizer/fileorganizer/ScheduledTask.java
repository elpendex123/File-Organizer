package com.example.fileorganizer.fileorganizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

//    touch file.txt file.mp3 file.mp4 file.jpg file.png file.rtf file.wav file.mov

    @Value("${PICTURES_DIRECTORY}")
    private String picturesDirectory;

    @Value("${VIDEOS_DIRECTORY}")
    private String videosDirectory;

    @Value("${DOCUMENTS_DIRECTORY}")
    private String documentsDirectory;

    @Value("${DOWNLOADS_DIRECTORY}")
    private String downloadsDirectory;

    @Value("${MUSIC_DIRECTORY}")
    private String musicDirectory;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", simpleDateFormat.format(new Date()));

        // Check Downloads directory for files
        log.info("Downloads directory: {}", downloadsDirectory);
        log.info("Music directory: {}", musicDirectory);
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
        File[] files = new File(downloadsDirectory).listFiles();

        for (File file : files) {
            log.info("file name: {}", file.getName());

            if (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav")) {
                log.info("music file, moved to the music directory: {}", musicDirectory);
                file.renameTo(new File(musicDirectory + File.separator + file.getName()));
            }

            if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
                log.info("image file, moved to the music directory: {}", picturesDirectory);
                file.renameTo(new File(picturesDirectory + File.separator + file.getName()));
            }

            if (file.getName().endsWith(".rtf") || file.getName().endsWith(".txt")) {
                log.info("text file, moved to the music directory: {}", documentsDirectory);
                file.renameTo(new File(documentsDirectory + File.separator + file.getName()));
            }

            if (file.getName().endsWith(".mov") || file.getName().endsWith(".mp4")) {
                log.info("videos file, moved to the music directory: {}", videosDirectory);
                file.renameTo(new File(videosDirectory + File.separator + file.getName()));
            }
        }
    }
}
