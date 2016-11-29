package de.poeschl.bukkit.logcleaner.managers

import de.poeschl.bukkit.logcleaner.managers.SettingsManager.Companion.KEEP_DAYS_KEY
import org.bukkit.configuration.file.FileConfiguration

import java.util.logging.Logger

interface SettingsManager {

    val keepDays: Int

    fun updateConfig(config: FileConfiguration)

    companion object {
        val KEEP_DAYS_KEY = "keepLogsFromLastDays"
    }
}

class SettingsManagerImpl(private var config: FileConfiguration, private val logger: Logger) : SettingsManager {

    override val keepDays: Int
        get() {
            return config.getInt(KEEP_DAYS_KEY)
        }

    override fun updateConfig(config: FileConfiguration) {
        this.config = config
    }
}
