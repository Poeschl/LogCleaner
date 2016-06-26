package de.poeschl.bukkit.logcleaner.utils;

import de.poeschl.bukkit.logcleaner.helper.FileHelper;
import de.poeschl.bukkit.logcleaner.managers.SettingManager;
import de.poeschl.bukkit.logcleaner.threads.LogCleanerThread;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

/**
 * Project: logcleaner
 * Created by Markus on 26.06.2016.
 */
public class InstanceFactory {
    public SettingManager createSettingsManager(FileConfiguration config, Logger logger) {
        return new SettingManager(config, logger);
    }

    public Logger getLogger(JavaPlugin javaPlugin) {
        return javaPlugin.getLogger();
    }

    public FileHelper createFileHelper(Logger logger) {
        return new FileHelper(logger);
    }

    public LogCleanerThread createLogCleanerThread(FileHelper fileHelper, SettingManager settingManager, File logFolder) {
        return new LogCleanerThread(fileHelper, settingManager.getKeepDays(), logFolder);
    }
}
