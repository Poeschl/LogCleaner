package de.poeschl.bukkit.logcleaner.helper

import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions
import org.assertj.core.util.DateUtil
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.io.File
import java.util.*
import java.util.logging.Logger


class FileHelperTest {

    private var testFileHelper: FileHelper? = null

    @Before
    fun setUp() {
        val voidLogger: Logger = mock()
        testFileHelper = FileHelper(voidLogger)
    }

    @Test
    fun getLogArchives() {
        val mockFolder: File = mock()
        val mockFileList = arrayOf(File("a.log.gz"), File("b.log.gz"))
        `when`<Array<File>>(mockFolder.listFiles(ArgumentMatchers.any(java.io.FilenameFilter::class.java))).thenReturn(mockFileList)

        val resultFiles = testFileHelper?.getLogArchives(mockFolder)

        Assertions.assertThat(resultFiles).containsAll(mockFileList.asList())
    }

    @Test
    fun getLogArchiveDate() {
        val mockArchive: File = mock()
        val expected = Calendar.getInstance()
        expected.set(1234, Calendar.DECEMBER, 12)
        val expectedDay = DateUtil.dayOfMonthOf(expected.time)
        `when`(mockArchive.name).thenReturn("1234-12-12-9.log.gz")

        val resultDay = DateUtil.dayOfMonthOf(testFileHelper?.getLogArchiveDate(mockArchive))

        Assertions.assertThat(resultDay).isEqualTo(expectedDay)
    }

    @Test
    fun delete() {
        val mockFile: File = mock()

        testFileHelper?.delete(mockFile)

        verify(mockFile).delete()
    }

}