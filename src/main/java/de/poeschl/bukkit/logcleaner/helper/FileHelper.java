package de.poeschl.bukkit.logcleaner.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class FileHelper {

    private static final String LOGS_FOLDER_NAME = "logs";

    private final Logger logger;
    private final File logFolder;

    public FileHelper(Logger logger) {
        this.logger = logger;
        this.logFolder = new File("./" + LOGS_FOLDER_NAME);
    }

    public List<File> getLogArchives() {
        FilenameFilter archiveFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".gz");
            }
        };

        return Arrays.asList(logFolder.listFiles(archiveFilter));
    }

    public Date getLogArchiveDate(File file) {
        String[] splitedName = file.getName().split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(splitedName[0]), Integer.parseInt(splitedName[1]) - 1, Integer.parseInt(splitedName[2]));
        return cal.getTime();
    }

    public boolean delete(File file) {
        logger.info("Deleted log:" + file.getName());
        return file.delete();
    }
}
