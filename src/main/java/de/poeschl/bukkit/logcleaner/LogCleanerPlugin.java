package de.poeschl.bukkit.logcleaner;

import de.poeschl.bukkit.logcleaner.helper.FileHelper;
import de.poeschl.bukkit.logcleaner.managers.SettingManager;
import de.poeschl.bukkit.logcleaner.threads.LogCleanerThread;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class LogCleanerPlugin extends JavaPlugin {

    private SettingManager settingManager;
    private FileHelper fileHelper;

    @Override
    public void onEnable() {
        super.onEnable();
        PluginDescriptionFile pdfFile = this.getDescription();

        if (getConfig().getKeys(false).size() == 0) {
            getConfig();
            saveDefaultConfig();
        }
        settingManager = new SettingManager(getConfig(), getLogger());
        fileHelper = new FileHelper(getLogger());

        getLogger().info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");

        activateLogCleaner();
    }

    private void activateLogCleaner() {
        getLogger().info("Starting LogCleanerPlugin Thread");
       new LogCleanerThread(fileHelper,settingManager.getKeepDays()).start();
    }
}
