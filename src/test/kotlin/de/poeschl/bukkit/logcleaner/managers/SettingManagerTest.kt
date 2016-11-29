package de.poeschl.bukkit.logcleaner.managers

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.bukkit.configuration.file.FileConfiguration
import org.junit.Test
import java.util.logging.Logger

class SettingsManagerTest {

    @Test
    fun updateConfig() {
        val voidLogger: Logger = mock()
        val mockedConfig: FileConfiguration = mock()
        whenever(mockedConfig.getInt(SettingsManager.KEEP_DAYS_KEY)).thenReturn(2)
        val newConfig: FileConfiguration = mock()
        whenever(newConfig.getInt(SettingsManager.KEEP_DAYS_KEY)).thenReturn(10)
        val testSettingsManager = SettingsManagerImpl(mockedConfig, voidLogger)

        testSettingsManager.updateConfig(newConfig)

        Assertions.assertThat(testSettingsManager.keepDays).isEqualTo(10)
    }

    @Test
    fun getKeepDays() {
        val voidLogger: Logger = mock()
        val mockedConfig: FileConfiguration = mock()
        whenever(mockedConfig.getInt(SettingsManager.KEEP_DAYS_KEY)).thenReturn(2)

        val testSettingsManager = SettingsManagerImpl(mockedConfig, voidLogger)

        Assertions.assertThat(testSettingsManager.keepDays).isEqualTo(2)
    }
}