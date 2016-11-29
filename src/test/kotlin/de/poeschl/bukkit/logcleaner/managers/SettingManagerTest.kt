package de.poeschl.bukkit.logcleaner.managers

import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions
import org.bukkit.configuration.file.FileConfiguration
import org.junit.Test
import org.mockito.Mockito.`when`
import java.util.logging.Logger

class SettingManagerTest {

    @Test
    fun updateConfig() {
        val voidLogger: Logger = mock()
        val mockedConfig: FileConfiguration = mock()
        `when`(mockedConfig.getInt(SettingManager.KEEP_DAYS_KEY)).thenReturn(2)
        val newConfig: FileConfiguration = mock()
        `when`(newConfig.getInt(SettingManager.KEEP_DAYS_KEY)).thenReturn(10)
        val testSettingsManager = SettingManager(mockedConfig, voidLogger)

        testSettingsManager.updateConfig(newConfig)

        Assertions.assertThat(testSettingsManager.keepDays).isEqualTo(10)
    }

    @Test
    fun getKeepDays() {
        val voidLogger: Logger = mock()
        val mockedConfig: FileConfiguration = mock()
        `when`(mockedConfig.getInt(SettingManager.KEEP_DAYS_KEY)).thenReturn(2)

        val testSettingsManager = SettingManager(mockedConfig, voidLogger)

        Assertions.assertThat(testSettingsManager.keepDays).isEqualTo(2)
    }
}