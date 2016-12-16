package io.github.poeschl.bukkit.logcleaner.utils

import io.github.poeschl.bukkit.logcleaner.helper.FileHelper
import io.github.poeschl.bukkit.logcleaner.helper.FileHelperImpl
import io.github.poeschl.bukkit.logcleaner.managers.SettingsManager
import io.github.poeschl.bukkit.logcleaner.managers.SettingsManagerImpl
import io.github.poeschl.bukkit.logcleaner.threads.LogCleanerRunnable
import io.github.poeschl.bukkit.logcleaner.threads.LogCleanerRunnableImpl
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger

open class InstanceFactory {
    open internal fun createSettingsManager(config: FileConfiguration, logger: Logger): SettingsManager {
        return SettingsManagerImpl(config, logger)
    }

    open internal fun getLogger(javaPlugin: JavaPlugin): Logger {
        return javaPlugin.logger
    }

    open internal fun createFileHelper(logger: Logger): FileHelper {
        return FileHelperImpl(logger)
    }

    open internal fun createLogCleanerRunnable(fileHelper: FileHelper, settingManager: SettingsManager, logFolder: File): LogCleanerRunnable {
        return LogCleanerRunnableImpl(fileHelper, settingManager.keepDays, logFolder)
    }
}
