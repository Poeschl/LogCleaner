package de.poeschl.bukkit.logcleaner

import com.nhaarman.mockito_kotlin.*
import de.poeschl.bukkit.logcleaner.helper.FileHelper
import de.poeschl.bukkit.logcleaner.managers.SettingsManager
import de.poeschl.bukkit.logcleaner.threads.LogCleanerRunnable
import de.poeschl.bukkit.logcleaner.utils.InstanceFactory
import org.assertj.core.api.Assertions
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.PluginDescriptionFile
import org.junit.Test
import java.util.*
import java.util.logging.Logger


class LogCleanerPluginTest {

    @Test
    fun onEnable() {
        //WHEN
        val mockedConfig: FileConfiguration = mock {
            on { getKeys(any()) } doReturn setOf("dummy")
        }

        val pluginToTest: LogCleanerPlugin = mock {
            on { config } doReturn mockedConfig
            on { info } doReturn PluginDescriptionFile("", "", "")
            on { onEnable() }.thenCallRealMethod()
        }
        pluginToTest.pluginLogger = mock()

        //THEN
        pluginToTest.onEnable()

        //VERIFY
        verify(pluginToTest).initFields()
        verify(pluginToTest).activateLogCleaner()
        verify(pluginToTest, never()).saveDefaultConfig()
    }

    /**
     * This test test the first time initialization, when noe key is available from the Settingsmanager
     */
    @Test
    fun onEnableFirstTime() {
        //WHEN
        val mockedConfig: FileConfiguration = mock()

        val pluginToTest: LogCleanerPlugin = mock {
            on { config } doReturn mockedConfig
            on { info } doReturn PluginDescriptionFile("", "", "")
            on { onEnable() }.thenCallRealMethod()
        }
        pluginToTest.pluginLogger = mock()

        //THEN
        pluginToTest.onEnable()

        //VERIFY
        verify(pluginToTest).initFields()
        verify(pluginToTest).activateLogCleaner()
        verify(pluginToTest).saveDefaultConfig()
    }

    @Test
    fun activateLogCleaner() {
        //WHEN
        val mockLog: Logger = mock()
        val mockedRunnable: LogCleanerRunnable = mock()
        val pluginToTest: LogCleanerPlugin = mock {
            on { activateLogCleaner() }.thenCallRealMethod()
        }
        pluginToTest.pluginLogger = mockLog
        pluginToTest.logCleanerRunnable = mockedRunnable

        //THEN
        pluginToTest.activateLogCleaner()

        //VERIFY
        verify(mockedRunnable).setNow(argThat { javaClass == Date::class.java })
        verify(mockedRunnable).run()
    }

    @Test
    fun initFieldsOfLogCleaner() {
        //WHEN
        val mockedInstances = createMockedInstanceFactory()
        val pluginToTest: LogCleanerPlugin = mock {
            on { initFields() }.thenCallRealMethod()
            on { createInstanceFactory() } doReturn mockedInstances
            on { config } doReturn mock<FileConfiguration>()
        }

        //THEN
        pluginToTest.initFields()

        //VERIFY
        Assertions.assertThat(pluginToTest.instanceFactory).isNotNull()
        Assertions.assertThat(pluginToTest.pluginLogger).isNotNull()
        Assertions.assertThat(pluginToTest.settingManager).isNotNull()
        Assertions.assertThat(pluginToTest.fileHelper).isNotNull()
        Assertions.assertThat(pluginToTest.logCleanerRunnable).isNotNull()
    }

    private fun createMockedInstanceFactory(): InstanceFactory {
        return mock {
            on { createSettingsManager(any(), any()) } doReturn mock<SettingsManager>()
            on { getLogger(any()) } doReturn mock<Logger>()
            on { createFileHelper(any()) } doReturn mock<FileHelper>()
            on { createLogCleanerRunnable(any(), any(), any()) } doReturn mock<LogCleanerRunnable>()
        }
    }
}