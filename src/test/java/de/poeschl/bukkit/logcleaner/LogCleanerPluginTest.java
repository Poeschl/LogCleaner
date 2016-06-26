package de.poeschl.bukkit.logcleaner;

import de.poeschl.bukkit.logcleaner.helper.FileHelper;
import de.poeschl.bukkit.logcleaner.managers.SettingManager;
import de.poeschl.bukkit.logcleaner.threads.LogCleanerThread;
import de.poeschl.bukkit.logcleaner.utils.InstanceFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.*;

/**
 * Project: logcleaner
 * Created by Markus on 26.06.2016.
 */
public class LogCleanerPluginTest {

    @Test
    public void onEnable() {
        //WHEN
        InstanceFactory mockedInstances = Mockito.mock(InstanceFactory.class);
        LogCleanerPlugin pluginToTest = Mockito.mock(LogCleanerPlugin.class);
        FileConfiguration mockedConfig = Mockito.mock(FileConfiguration.class);
        Set<String> dummyKeys = new HashSet<>();
        dummyKeys.add("dummy");

        when(mockedInstances.createSettingsManager(any(FileConfiguration.class), any(Logger.class))).thenReturn(Mockito.mock(SettingManager.class));
        when(mockedInstances.getLogger(any(JavaPlugin.class))).thenReturn(Mockito.mock(Logger.class));
        when(mockedInstances.createFileHelper(any(Logger.class))).thenReturn(Mockito.mock(FileHelper.class));
        when(mockedInstances.createLogCleanerThread(any(FileHelper.class), any(SettingManager.class), any(File.class)))
                .thenReturn(Mockito.mock(LogCleanerThread.class));
        when(pluginToTest.getConfig()).thenReturn(mockedConfig);
        when(mockedConfig.getKeys(anyBoolean())).thenReturn(dummyKeys);
        when(pluginToTest.getInstanceFactory()).thenReturn(mockedInstances);
        when(pluginToTest.getInfo()).thenReturn(new PluginDescriptionFile("", "", ""));

        doCallRealMethod().when(pluginToTest).onEnable();

        //THEN
        pluginToTest.onEnable();

        //VERIFY
        verify(pluginToTest).activateLogCleaner();
        verify(pluginToTest, never()).saveDefaultConfig();
    }

    @Test
    public void onEnableFirstTime() {
        //WHEN
        InstanceFactory mockedInstanceFactory = Mockito.mock(InstanceFactory.class);
        LogCleanerPlugin pluginToTest = Mockito.mock(LogCleanerPlugin.class);

        when(pluginToTest.getInstanceFactory()).thenReturn(mockedInstanceFactory);
        when(mockedInstanceFactory.createSettingsManager(any(FileConfiguration.class), any(Logger.class))).thenReturn(Mockito.mock(SettingManager.class));
        when(mockedInstanceFactory.getLogger(any(JavaPlugin.class))).thenReturn(Mockito.mock(Logger.class));
        when(mockedInstanceFactory.createFileHelper(any(Logger.class))).thenReturn(Mockito.mock(FileHelper.class));
        when(mockedInstanceFactory.createLogCleanerThread(any(FileHelper.class), any(SettingManager.class), any(File.class)))
                .thenReturn(Mockito.mock(LogCleanerThread.class));
        when(pluginToTest.getConfig()).thenReturn(Mockito.mock(FileConfiguration.class));
        when(pluginToTest.getInfo()).thenReturn(new PluginDescriptionFile("", "", ""));

        doCallRealMethod().when(pluginToTest).onEnable();

        //THEN
        pluginToTest.onEnable();

        //VERIFY
        verify(pluginToTest).activateLogCleaner();
        verify(pluginToTest).saveDefaultConfig();
    }

    @Test
    public void activateLogCleaner() {
        //WHEN
        Logger mockLog = Mockito.mock(Logger.class);
        LogCleanerThread mockedThread = Mockito.mock(LogCleanerThread.class);
        LogCleanerPlugin pluginToTest = Mockito.mock(LogCleanerPlugin.class);
        pluginToTest.logger = mockLog;
        pluginToTest.logCleanerThread = mockedThread;

        doCallRealMethod().when(pluginToTest).activateLogCleaner();

        //THEN
        pluginToTest.activateLogCleaner();

        //VERIFY
        verify(mockedThread).setNow(any(Date.class));
        verify(mockedThread).start();
    }
}