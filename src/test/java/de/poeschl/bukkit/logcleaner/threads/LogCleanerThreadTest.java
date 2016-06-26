package de.poeschl.bukkit.logcleaner.threads;

import de.poeschl.bukkit.logcleaner.helper.FileHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class LogCleanerThreadTest {

    private FileHelper mockFileHelper;
    private Calendar mockedNow;
    private List<File> fakeFiles;

    @Before
    public void setUp() throws Exception {
        mockFileHelper = Mockito.mock(FileHelper.class);

        fakeFiles = new ArrayList<>();
        fakeFiles.add(new File("1234-12-12-1.log.gz"));
        fakeFiles.add(new File("1234-12-12-2.log.gz"));
        fakeFiles.add(new File("1234-12-11-1.log.gz"));
        fakeFiles.add(new File("1234-12-11-2.log.gz"));

        setupMockFileHelper(mockFileHelper);

        mockedNow = Calendar.getInstance();
        mockedNow.set(1234, Calendar.DECEMBER, 12);
    }

    @Test
    public void runNothingToClean() throws Exception {
        mockedNow.set(1234, Calendar.DECEMBER, 12);

        //Every log is within the last two days
        LogCleanerThread testThread =
                new LogCleanerThread(mockFileHelper, 2, Mockito.mock(File.class));
        testThread.setNow(mockedNow.getTime());
        testThread.run();
        verify(mockFileHelper, never()).delete(any(File.class));
    }

    @Test
    public void runSomethingToClean() throws Exception {
        mockedNow.set(1234, Calendar.DECEMBER, 13);

        //Two logs are out of the days
        LogCleanerThread testThread =
                new LogCleanerThread(mockFileHelper, 2, Mockito.mock(File.class));
        testThread.setNow(mockedNow.getTime());

        testThread.run();
        verify(mockFileHelper, times(1)).delete(fakeFiles.get(2));
        verify(mockFileHelper, times(1)).delete(fakeFiles.get(3));
    }

    @Test
    public void runShortInterval() throws Exception {
        mockedNow.set(1234, Calendar.DECEMBER, 12);

        //Two logs are out of the days, because of only ond day logs
        LogCleanerThread testThread =
                new LogCleanerThread(mockFileHelper, 1, Mockito.mock(File.class));
        testThread.setNow(mockedNow.getTime());

        testThread.run();
        verify(mockFileHelper, times(1)).delete(fakeFiles.get(2));
        verify(mockFileHelper, times(1)).delete(fakeFiles.get(3));
    }

    private void setupMockFileHelper(FileHelper mockFileHelper) {
        Calendar cal = Calendar.getInstance();

        when(mockFileHelper.getLogArchives(any(File.class))).thenReturn(fakeFiles);

        cal.set(1234, Calendar.DECEMBER, 12);
        when(mockFileHelper.getLogArchiveDate(fakeFiles.get(0))).thenReturn(cal.getTime());
        when(mockFileHelper.getLogArchiveDate(fakeFiles.get(1))).thenReturn(cal.getTime());

        cal.set(1234, Calendar.DECEMBER, 11);
        when(mockFileHelper.getLogArchiveDate(fakeFiles.get(2))).thenReturn(cal.getTime());
        when(mockFileHelper.getLogArchiveDate(fakeFiles.get(3))).thenReturn(cal.getTime());
    }
}