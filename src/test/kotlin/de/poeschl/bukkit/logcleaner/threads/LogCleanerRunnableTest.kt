package de.poeschl.bukkit.logcleaner.threads

import com.nhaarman.mockito_kotlin.*
import de.poeschl.bukkit.logcleaner.createDate
import de.poeschl.bukkit.logcleaner.helper.FileHelper
import org.junit.Test
import java.io.File
import java.util.*


class LogCleanerRunnableTest {

    val fakeFiles = listOf(File("1234-12-12-1.log.gz"), File("1234-12-12-2.log.gz"), File("1234-12-11-1.log.gz"), File("1234-12-11-2.log.gz"))

    @Test
    fun runNothingToClean() {
        val mockedFileHelper: FileHelper = setupMockFileHelper()
        val mockedNow = Calendar.getInstance().createDate(1234, Calendar.DECEMBER, 12)
        //Every log is within the last two days
        val testThread = LogCleanerRunnableImpl(mockedFileHelper, 2, mock())
        testThread.setNow(mockedNow)

        testThread.run()

        verify(mockedFileHelper, never()).delete(any())
    }

    @Test
    fun runSomethingToClean() {
        val mockedFileHelper: FileHelper = setupMockFileHelper()
        val mockedNow = Calendar.getInstance().createDate(1234, Calendar.DECEMBER, 13)
        //Every log is out of the two days
        val testThread = LogCleanerRunnableImpl(mockedFileHelper, 2, mock())
        testThread.setNow(mockedNow)

        testThread.run()

        verify(mockedFileHelper).delete(fakeFiles[2])
        verify(mockedFileHelper).delete(fakeFiles[3])
    }

    @Test
    fun runShortInterval() {
        val mockedFileHelper: FileHelper = setupMockFileHelper()
        val mockedNow = Calendar.getInstance().createDate(1234, Calendar.DECEMBER, 13)
        //Only one day difference
        val testThread = LogCleanerRunnableImpl(mockedFileHelper, 1, mock())
        testThread.setNow(mockedNow)

        testThread.run()
        verify(mockedFileHelper).delete(fakeFiles[2])
        verify(mockedFileHelper).delete(fakeFiles[3])
    }

    private fun setupMockFileHelper(): FileHelper {
        val newDate = Calendar.getInstance().createDate(1234, Calendar.DECEMBER, 12)
        val pastDate = Calendar.getInstance().createDate(1234, Calendar.DECEMBER, 11)
        val mockedFileHelper: FileHelper = mock {
            on { getLogArchives(any()) } doReturn fakeFiles
            on { getLogArchiveDate(fakeFiles[0]) } doReturn newDate
            on { getLogArchiveDate(fakeFiles[1]) } doReturn newDate
            on { getLogArchiveDate(fakeFiles[2]) } doReturn pastDate
            on { getLogArchiveDate(fakeFiles[3]) } doReturn pastDate
        }

        return mockedFileHelper
    }
}