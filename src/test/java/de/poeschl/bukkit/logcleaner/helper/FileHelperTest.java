package de.poeschl.bukkit.logcleaner.helper;

import org.apache.commons.lang.time.DateUtils;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Calendar;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileHelperTest {

    private FileHelper testFileHelper;

    @Before
    public void setUp() {
        Logger voidLogger = Mockito.mock(Logger.class);
        testFileHelper = new FileHelper(voidLogger);
    }

    @Test
    public void getLogArchives() throws Exception {
        File mockFolder = Mockito.mock(File.class);
        File[] mockFileList = new File[]{new File("a.log.gz"), new File("b.log.gz")};
        when(mockFolder.listFiles(any(FilenameFilter.class))).thenReturn(mockFileList);

        assertThat(testFileHelper.getLogArchives(mockFolder),
                IsIterableContainingInOrder.contains(mockFileList));
    }

    @Test
    public void getLogArchiveDate() throws Exception {
        File mockArchive = Mockito.mock(File.class);
        when(mockArchive.getName()).thenReturn("1234-12-12-9.log.gz");

        Calendar expected = Calendar.getInstance();
        expected.set(1234, Calendar.DECEMBER, 12);


        assertEquals(DateUtils.truncate(expected.getTime(), Calendar.DAY_OF_MONTH),
                DateUtils.truncate(testFileHelper.getLogArchiveDate(mockArchive), Calendar.DAY_OF_MONTH));
    }

    @Test
    public void delete() throws Exception {
        File mockFile = Mockito.mock(File.class);

        testFileHelper.delete(mockFile);
        verify(mockFile).delete();
    }

}