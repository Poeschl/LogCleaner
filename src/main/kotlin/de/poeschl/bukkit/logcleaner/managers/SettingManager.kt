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

class SettingsManagerImpl(private var config: FileConfiguration?, private val logger: Logger?) : SettingsManager {

    override val keepDays: Int
        get() {
            if (config != null) {
                val keepDays = config!!.getInt(KEEP_DAYS_KEY)
                return keepDays
            } else {
                logger?.warning("Couldn't read keepdays property! No config is applied.")
                return Int.MAX_VALUE
            }
        }

    override fun updateConfig(config: FileConfiguration) {
        this.config = config
    }
}
