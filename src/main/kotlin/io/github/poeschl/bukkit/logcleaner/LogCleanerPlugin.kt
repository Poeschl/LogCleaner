package io.github.poeschl.bukkit.logcleaner

import io.github.poeschl.bukkit.logcleaner.helper.FileHelper
import io.github.poeschl.bukkit.logcleaner.managers.SettingsManager
import io.github.poeschl.bukkit.logcleaner.threads.LogCleanerRunnable
import io.github.poeschl.bukkit.logcleaner.utils.InstanceFactory
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*
import java.util.logging.Logger

open class LogCleanerPlugin : JavaPlugin() {

    internal lateinit var instanceFactory: InstanceFactory
    internal lateinit var pluginLogger: Logger
    internal lateinit var settingManager: SettingsManager
    internal lateinit var fileHelper: FileHelper
    internal lateinit var logCleanerRunnable: LogCleanerRunnable

    override fun onEnable() {
        super.onEnable()
        val pdfFile = info

        if (config.getKeys(false).size == 0) {
            config
            saveDefaultConfig()
        }
        initFields()

        pluginLogger.info(pdfFile.name + " version " + pdfFile.version + " is enabled!")

        activateLogCleaner()
    }

    open internal val info: PluginDescriptionFile
        get() = description

    open internal fun initFields() {
        instanceFactory = createInstanceFactory()
        pluginLogger = instanceFactory.getLogger(this)
        settingManager = instanceFactory.createSettingsManager(config, pluginLogger)
        fileHelper = instanceFactory.createFileHelper(pluginLogger)
        logCleanerRunnable = instanceFactory.createLogCleanerRunnable(fileHelper, settingManager, LOG_FOLDER)
    }

    open internal fun createInstanceFactory(): InstanceFactory {
        return InstanceFactory()
    }

    open internal fun activateLogCleaner() {
        pluginLogger.info("Starting LogCleanerPlugin Thread")
        logCleanerRunnable.setNow(Date())
        Thread(logCleanerRunnable).start()
    }

    companion object {
        private val LOG_FOLDER = File("./" + FileHelper.LOGS_FOLDER_NAME)
    }
}
