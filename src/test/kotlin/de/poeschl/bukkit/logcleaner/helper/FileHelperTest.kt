package de.poeschl.bukkit.logcleaner.helper

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import de.poeschl.bukkit.logcleaner.createDate
import org.assertj.core.api.Assertions
import org.assertj.core.util.DateUtil
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.verify
import java.io.File
import java.util.*
import java.util.logging.Logger


class FileHelperTest {

    @Test
    fun getLogArchives() {
        val mockFolder: File = mock()
        val mockFileList = arrayOf(File("a.log.gz"), File("b.log.gz"))
        val voidLogger: Logger = mock()
        val testFileHelper = FileHelperImpl(voidLogger)
        whenever(mockFolder.listFiles(ArgumentMatchers.any(java.io.FilenameFilter::class.java))).thenReturn(mockFileList)

        val resultFiles = testFileHelper.getLogArchives(mockFolder)

        Assertions.assertThat(resultFiles).containsAll(mockFileList.asList())
    }

    @Test
    fun getLogArchiveDate() {
        val mockArchive: File = mock()
        val expectedDay = DateUtil.dayOfMonthOf(Calendar.getInstance().createDate(1234, Calendar.DECEMBER, 12))
        val voidLogger: Logger = mock()
        val testFileHelper = FileHelperImpl(voidLogger)
        whenever(mockArchive.name).thenReturn("1234-12-12-9.log.gz")

        val resultDay = DateUtil.dayOfMonthOf(testFileHelper.getLogArchiveDate(mockArchive))

        Assertions.assertThat(resultDay).isEqualTo(expectedDay)
    }

    @Test
    fun delete() {
        val mockFile: File = mock()
        val voidLogger: Logger = mock()
        val testFileHelper = FileHelperImpl(voidLogger)

        testFileHelper.delete(mockFile)

        verify(mockFile).delete()
    }

}