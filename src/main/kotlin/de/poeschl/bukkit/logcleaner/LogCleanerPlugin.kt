package de.poeschl.bukkit.logcleaner

import de.poeschl.bukkit.logcleaner.helper.FileHelper
import de.poeschl.bukkit.logcleaner.managers.SettingsManager
import de.poeschl.bukkit.logcleaner.threads.LogCleanerRunnable
import de.poeschl.bukkit.logcleaner.utils.InstanceFactory
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*
import java.util.logging.Logger

open class LogCleanerPlugin : JavaPlugin() {

    internal var instanceFactory: InstanceFactory? = null
    internal var pluginLogger: Logger? = null
    internal var settingManager: SettingsManager? = null
    internal var fileHelper: FileHelper? = null
    internal var logCleanerRunnable: LogCleanerRunnable? = null

    override fun onEnable() {
        super.onEnable()
        val pdfFile = info

        if (config.getKeys(false).size == 0) {
            config
            saveDefaultConfig()
        }
        initFields()

        pluginLogger!!.info(pdfFile.name + " version " + pdfFile.version + " is enabled!")

        activateLogCleaner()
    }

    open internal val info: PluginDescriptionFile
        get() = description

    open internal fun initFields() {
        instanceFactory = createInstanceFactory()
        pluginLogger = instanceFactory!!.getLogger(this)
        settingManager = instanceFactory!!.createSettingsManager(config, pluginLogger!!)
        fileHelper = instanceFactory!!.createFileHelper(pluginLogger!!)
        logCleanerRunnable = instanceFactory!!.createLogCleanerRunnable(fileHelper!!, settingManager!!, LOG_FOLDER)
    }

    open internal fun createInstanceFactory(): InstanceFactory {
        return InstanceFactory()
    }

    open internal fun activateLogCleaner() {
        pluginLogger!!.info("Starting LogCleanerPlugin Thread")
        logCleanerRunnable!!.setNow(Date())
        Thread(logCleanerRunnable).start()
    }

    companion object {
        private val LOG_FOLDER = File("./" + FileHelper.LOGS_FOLDER_NAME)
    }
}
