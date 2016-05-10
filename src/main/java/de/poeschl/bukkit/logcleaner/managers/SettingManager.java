package de.poeschl.bukkit.logcleaner.managers;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;


public class SettingManager {

    private static final String KEEP_DAYS_KEY = "keepLogsFromLastDays";

    private FileConfiguration config;
    private Logger logger;

    public SettingManager(FileConfiguration config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    public void updateConfig(FileConfiguration config) {
        this.config = config;
    }

    public int getKeepDays() {
        return config.getInt(KEEP_DAYS_KEY);
    }
}
