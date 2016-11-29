package de.poeschl.bukkit.logcleaner.threads

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import de.poeschl.bukkit.logcleaner.createDate
import de.poeschl.bukkit.logcleaner.helper.FileHelper
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import java.io.File
import java.util.*


class LogCleanerThreadTest {

    private val fakeFiles = listOf(File("1234-12-12-1.log.gz"), File("1234-12-12-2.log.gz"), File("1234-12-11-1.log.gz"), File("1234-12-11-2.log.gz"))


    @Test
    fun runNothingToClean() {
        val mockedFileHelper: FileHelper = setupMockFileHelper(mock())
        val mockedNow = Calendar.getInstance().createDate(1234, Calendar.DECEMBER, 12)
        //Every log is within the last two days
        val testThread = LogCleanerThread(mockedFileHelper, 2, mock())
        testThread.setNow(mockedNow)

        testThread.run()

        verify(mockedFileHelper, never()).delete(ArgumentMatchers.any(File::class.java))
    }

    @Test
    fun runSomethingToClean() {
        val mockedFileHelper: FileHelper = setupMockFileHelper(mock())
        val mockedNow = Calendar.getInstance().createDate(1234, Calendar.DECEMBER, 13)
        //Every log is out of the two days
        val testThread = LogCleanerThread(mockedFileHelper, 2, mock())
        testThread.setNow(mockedNow)

        testThread.run()

        verify(mockedFileHelper).delete(fakeFiles[2])
        verify(mockedFileHelper).delete(fakeFiles[3])
    }

    @Test
    fun runShortInterval() {
        val mockedFileHelper: FileHelper = setupMockFileHelper(mock())
        val mockedNow = Calendar.getInstance().createDate(1234, Calendar.DECEMBER, 13)
        //Only one day difference
        val testThread = LogCleanerThread(mockedFileHelper, 1, mock())
        testThread.setNow(mockedNow)

        testThread.run()
        verify(mockedFileHelper).delete(fakeFiles[2])
        verify(mockedFileHelper).delete(fakeFiles[3])
    }

    private fun setupMockFileHelper(mockFileHelper: FileHelper): FileHelper {
        val cal = Calendar.getInstance()

        `when`(mockFileHelper.getLogArchives(ArgumentMatchers.any(File::class.java))).thenReturn(fakeFiles)

        cal.set(1234, Calendar.DECEMBER, 12)
        `when`(mockFileHelper.getLogArchiveDate(fakeFiles[0])).thenReturn(cal.time)
        `when`(mockFileHelper.getLogArchiveDate(fakeFiles[1])).thenReturn(cal.time)

        cal.set(1234, Calendar.DECEMBER, 11)
        `when`(mockFileHelper.getLogArchiveDate(fakeFiles[2])).thenReturn(cal.time)
        `when`(mockFileHelper.getLogArchiveDate(fakeFiles[3])).thenReturn(cal.time)

        return mockFileHelper
    }
}