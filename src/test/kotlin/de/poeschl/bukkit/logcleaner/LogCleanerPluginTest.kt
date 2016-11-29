package de.poeschl.bukkit.logcleaner

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import de.poeschl.bukkit.logcleaner.helper.FileHelper
import de.poeschl.bukkit.logcleaner.managers.SettingManager
import de.poeschl.bukkit.logcleaner.threads.LogCleanerThread
import de.poeschl.bukkit.logcleaner.utils.InstanceFactory
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import java.io.File
import java.util.*
import java.util.logging.Logger

class LogCleanerPluginTest {

    @Test
    fun onEnable() {
        //WHEN
        val mockedInstances: InstanceFactory = mock()
        val mockedConfig: FileConfiguration = mock()
        val dummyKeys = setOf("dummy")
        `when`(mockedInstances.createSettingsManager(ArgumentMatchers.any(FileConfiguration::class.java), ArgumentMatchers.any(Logger::class.java))).thenReturn(mock())
        `when`(mockedInstances.getLogger(ArgumentMatchers.any(JavaPlugin::class.java))).thenReturn(mock())
        `when`(mockedInstances.createFileHelper(ArgumentMatchers.any(Logger::class.java))).thenReturn(mock())
        `when`(mockedInstances.createLogCleanerThread(ArgumentMatchers.any(FileHelper::class.java), ArgumentMatchers.any(SettingManager::class.java), ArgumentMatchers.any(File::class.java)))
                .thenReturn(mock())

        val pluginToTest: LogCleanerPlugin = mock()
        `when`(pluginToTest.config).thenReturn(mockedConfig)
        `when`(mockedConfig.getKeys(ArgumentMatchers.anyBoolean())).thenReturn(dummyKeys)
        `when`(pluginToTest.instanceFactory).thenReturn(mockedInstances)
        `when`(pluginToTest.info).thenReturn(PluginDescriptionFile("", "", ""))
        `when`(pluginToTest.onEnable()).thenCallRealMethod()

        //THEN
        pluginToTest.onEnable()

        //VERIFY
        verify(pluginToTest).activateLogCleaner()
        verify(pluginToTest, never()).saveDefaultConfig()
    }

    /**
     * This test test the first time initialization, when noe key is available from the Settingsmanager
     */
    @Test
    fun onEnableFirstTime() {
        //WHEN
        val mockedInstances: InstanceFactory = mock()
        val mockedConfig: FileConfiguration = mock()
        `when`(mockedInstances.createSettingsManager(ArgumentMatchers.any(FileConfiguration::class.java), ArgumentMatchers.any(Logger::class.java))).thenReturn(mock())
        `when`(mockedInstances.getLogger(ArgumentMatchers.any(JavaPlugin::class.java))).thenReturn(mock())
        `when`(mockedInstances.createFileHelper(ArgumentMatchers.any(Logger::class.java))).thenReturn(mock())
        `when`(mockedInstances.createLogCleanerThread(ArgumentMatchers.any(FileHelper::class.java), ArgumentMatchers.any(SettingManager::class.java), ArgumentMatchers.any(File::class.java)))
                .thenReturn(mock())

        val pluginToTest: LogCleanerPlugin = mock()
        `when`(pluginToTest.config).thenReturn(mockedConfig)
        `when`(pluginToTest.instanceFactory).thenReturn(mockedInstances)
        `when`(pluginToTest.info).thenReturn(PluginDescriptionFile("", "", ""))
        `when`(pluginToTest.onEnable()).thenCallRealMethod()

        //THEN
        pluginToTest.onEnable()

        //VERIFY
        verify(pluginToTest).activateLogCleaner()
        verify(pluginToTest).saveDefaultConfig()
    }

    @Test
    fun activateLogCleaner() {
        //WHEN
        val mockLog: Logger = mock()
        val mockedThread: LogCleanerThread = mock()
        val pluginToTest: LogCleanerPlugin = mock()
        pluginToTest.logger = mockLog
        pluginToTest.logCleanerThread = mockedThread

        `when`(pluginToTest.activateLogCleaner()).thenCallRealMethod()

        //THEN
        pluginToTest.activateLogCleaner()

        //VERIFY
        verify(mockedThread).setNow(ArgumentMatchers.any(Date::class.java))
        verify(mockedThread).start()
    }
}