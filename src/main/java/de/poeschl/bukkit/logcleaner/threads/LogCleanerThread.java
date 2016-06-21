package de.poeschl.bukkit.logcleaner.threads;


import de.poeschl.bukkit.logcleaner.helper.FileHelper;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LogCleanerThread extends Thread {

    private final FileHelper fileHelper;
    private final int daysToKeep;
    private File logFolder;

    public LogCleanerThread(FileHelper fileHelper, int daysToKeep, File logFolder) {
        this.fileHelper = fileHelper;
        this.daysToKeep = daysToKeep;
        this.logFolder = logFolder;
    }

    @Override
    public void run() {
        List<File> archiveList = fileHelper.getLogArchives(logFolder);
        Date currentDate = new Date();
        for (File archive : archiveList) {
            long timeDiff = currentDate.getTime() - fileHelper.getLogArchiveDate(archive).getTime();
            if (TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS) >= daysToKeep) {
                fileHelper.delete(archive);
            }
        }
    }
}
