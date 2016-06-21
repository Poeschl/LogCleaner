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
    private Date now;

    public LogCleanerThread(FileHelper fileHelper, int daysToKeep, File logFolder) {
        this.fileHelper = fileHelper;
        this.daysToKeep = daysToKeep;
        this.logFolder = logFolder;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    @Override
    public void run() {
        List<File> archiveList = fileHelper.getLogArchives(logFolder);
        for (File archive : archiveList) {
            long timeDiff = now.getTime() - fileHelper.getLogArchiveDate(archive).getTime();
            if (TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS) >= daysToKeep) {
                fileHelper.delete(archive);
            }
        }
    }
}
