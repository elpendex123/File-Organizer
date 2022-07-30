package com.example.fileorganizer.fileorganizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    @Value("${DOWNLOADS_DIRECTORY}")
    private String downloadsDirectory;

    @Value("${PICTURES_DIRECTORY}")
    private String picturesDirectory;

    @Value("${VIDEOS_DIRECTORY}")
    private String videosDirectory;

    @Value("${DOCUMENTS_DIRECTORY}")
    private String documentsDirectory;

    @Value("${MUSIC_DIRECTORY}")
    private String musicDirectory;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", simpleDateFormat.format(new Date()));

        log.info("Downloads directory: {}", downloadsDirectory);
        log.info("Music directory: {}", musicDirectory);
        log.info("Documents directory: {} ", documentsDirectory);
        log.info("Pictures directory: {} ", picturesDirectory);
        log.info("Videos directory: {}", videosDirectory);

        checkDirectories();
    }

    public void checkDirectories() {
        Path downloadsPath = FileSystems.getDefault().getPath(downloadsDirectory);
        if (Files.isDirectory(downloadsPath)) {
            log.info("The {} directory exists", downloadsDirectory);
        } else {
            log.info("The {} directory does not exist, creating it", downloadsDirectory);
            File newDirectory = new File(downloadsDirectory);
            newDirectory.mkdir();
        }

        Path documentsPath = FileSystems.getDefault().getPath(documentsDirectory);
        if (Files.isDirectory(documentsPath)) {
            log.info("The {} directory exists", documentsDirectory);
        } else {
            log.info("The {} directory does not exist, creating it", documentsDirectory);
            File newDirectory = new File(documentsDirectory);
            newDirectory.mkdir();
        }

        Path picturesPath = FileSystems.getDefault().getPath(picturesDirectory);
        if (Files.isDirectory(picturesPath)) {
            log.info("The {} directory exists", picturesDirectory);
        } else {
            log.info("The {} directory does not exist, creating it", picturesDirectory);
            File newDirectory = new File(picturesDirectory);
            newDirectory.mkdir();
        }

        Path videosPath = FileSystems.getDefault().getPath(videosDirectory);
        if (Files.isDirectory(videosPath)) {
            log.info("The {} directory exists", videosDirectory);
        } else {
            log.info("The {} directory does not exist, creating it", videosDirectory);
            File newDirectory = new File(videosDirectory);
            newDirectory.mkdir();
        }

        Path musicPath = FileSystems.getDefault().getPath(musicDirectory);
        if (Files.isDirectory(musicPath)) {
            log.info("The {} directory exists", musicDirectory);
        } else {
            log.info("The {} directory does not exist, creating it", musicDirectory);
            File newDirectory = new File(musicDirectory);
            newDirectory.mkdir();
        }

        checkFiles();
    }

    public void checkFiles() {
        File[] files = new File(downloadsDirectory).listFiles();

        for (File file : files) {
            log.info("Filename: {}", file.getName());

            if (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav")) {
                if (Files.exists(Paths.get(musicDirectory + File.separator + file.getName()))) {
                    log.info("The file {} exists in the destination directory {}", file.getName(), musicDirectory);
                } else {
                    log.info("The file {} does not exist in the destination directory {}", file.getName(), musicDirectory);
                    file.renameTo(new File(musicDirectory + File.separator + file.getName()));
                    log.info("The file {} was moved to the directory: {}", file.getName(), musicDirectory);
                }
            }

            if (file.getName().endsWith(".mov") || file.getName().endsWith(".mp4")) {
                if (Files.exists(Paths.get(videosDirectory + File.separator + file.getName()))) {
                    log.info("The file {} exists in the destination directory {}", file.getName(), videosDirectory);
                } else {
                    log.info("The file {} does not exist in the destination directory {}", file.getName(), videosDirectory);
                    file.renameTo(new File(videosDirectory + File.separator + file.getName()));
                    log.info("The file {} was moved to the directory: {}", file.getName(), videosDirectory);
                }
            }

            if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
                if (Files.exists(Paths.get(picturesDirectory + File.separator + file.getName()))) {
                    log.info("The file {} exists in the destination directory {}", file.getName(), picturesDirectory);
                } else {
                    log.info("The file {} does not exist in the destination directory {}", file.getName(), picturesDirectory);
                    file.renameTo(new File(picturesDirectory + File.separator + file.getName()));
                    log.info("The file {} was moved to the directory: {}", file.getName(), picturesDirectory);
                }
            }

            if (file.getName().endsWith(".rtf") || file.getName().endsWith(".txt")) {
                if (Files.exists(Paths.get(documentsDirectory + File.separator + file.getName()))) {
                    log.info("The file {} exists in the destination directory {}", file.getName(), documentsDirectory);
                } else {
                    log.info("The file {} does not exist in the destination directory {}", file.getName(), documentsDirectory);
                    file.renameTo(new File(documentsDirectory + File.separator + file.getName()));
                    log.info("The file {} was moved to the directory: {}", file.getName(), documentsDirectory);
                }
            }
        }
    }
}
