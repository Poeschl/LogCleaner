package io.github.poeschl.bukkit.logcleaner.threads


import io.github.poeschl.bukkit.logcleaner.helper.FileHelper
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

interface LogCleanerRunnable : Runnable {

    fun setNow(now: Date)
}

class LogCleanerRunnableImpl(private val fileHelper: FileHelper, private val daysToKeep: Int, private val logFolder: File) : LogCleanerRunnable {
    private var now: Date? = null

    override fun setNow(now: Date) {
        this.now = now
    }

    override fun run() {
        if (now != null) {
            val archiveList = fileHelper.getLogArchives(logFolder)
            for (archive in archiveList) {
                val timeDiff = now!!.time - fileHelper.getLogArchiveDate(archive).time
                if (TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS) >= daysToKeep) {
                    fileHelper.delete(archive)
                }
            }
        }
    }
}
