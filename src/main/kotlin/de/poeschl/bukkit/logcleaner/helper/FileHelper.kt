package de.poeschl.bukkit.logcleaner.helper

import java.io.File
import java.io.FilenameFilter
import java.util.*
import java.util.logging.Logger

interface FileHelper {
    fun getLogArchives(logFolder: File): List<File>

    fun getLogArchiveDate(file: File): Date

    fun delete(file: File): Boolean

    companion object {
        val LOGS_FOLDER_NAME = "logs"
    }
}

class FileHelperImpl(private val logger: Logger) : FileHelper {

    override fun getLogArchives(logFolder: File): List<File> {
        val archiveFilter = FilenameFilter { dir, name -> name.endsWith(".gz") }

        return logFolder.listFiles(archiveFilter).asList()
    }

    override fun getLogArchiveDate(file: File): Date {
        val splitedName = file.name.split("-")

        val cal = Calendar.getInstance()
        cal.set(Integer.parseInt(splitedName[0]), Integer.parseInt(splitedName[1]) - 1, Integer.parseInt(splitedName[2]))
        return cal.time
    }

    override fun delete(file: File): Boolean {
        logger.info("Deleted log:" + file.name)
        return file.delete()
    }
}
