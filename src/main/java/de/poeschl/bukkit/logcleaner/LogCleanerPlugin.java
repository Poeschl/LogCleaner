package de.poeschl.bukkit.logcleaner;

import de.poeschl.bukkit.logcleaner.helper.FileHelper;
import de.poeschl.bukkit.logcleaner.managers.SettingManager;
import de.poeschl.bukkit.logcleaner.threads.LogCleanerThread;
import de.poeschl.bukkit.logcleaner.utils.InstanceFactory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

public class LogCleanerPlugin extends JavaPlugin {

    private static final File LOG_FOLDER = new File("./" + FileHelper.LOGS_FOLDER_NAME);

    private InstanceFactory instanceFactory;
    Logger logger;
    SettingManager settingManager;
    FileHelper fileHelper;
    LogCleanerThread logCleanerThread;

    @Override
    public void onEnable() {
        super.onEnable();
        PluginDescriptionFile pdfFile = getInfo();

        if (getConfig().getKeys(false).size() == 0) {
            getConfig();
            saveDefaultConfig();
        }

        instanceFactory = getInstanceFactory();
        logger = instanceFactory.getLogger(this);
        settingManager = instanceFactory.createSettingsManager(getConfig(), logger);
        fileHelper = instanceFactory.createFileHelper(logger);
        logCleanerThread = instanceFactory.createLogCleanerThread(fileHelper, settingManager, LOG_FOLDER);

        logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");

        activateLogCleaner();
    }

    protected PluginDescriptionFile getInfo() {
        return getDescription();
    }

    protected InstanceFactory getInstanceFactory() {
        return new InstanceFactory();
    }

    protected void activateLogCleaner() {
        logger.info("Starting LogCleanerPlugin Thread");
        logCleanerThread.setNow(new Date());
        logCleanerThread.start();
    }
}
