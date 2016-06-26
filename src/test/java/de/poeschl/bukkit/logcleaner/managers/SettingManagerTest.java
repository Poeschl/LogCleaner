package de.poeschl.bukkit.logcleaner.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SettingManagerTest {

    private SettingManager testSettingsManager;
    private FileConfiguration defaultMockConfig;

    @Before
    public void setUp() {
        Logger voidLogger = Mockito.mock(Logger.class);
        defaultMockConfig = Mockito.mock(FileConfiguration.class);
        when(defaultMockConfig.getInt(SettingManager.KEEP_DAYS_KEY)).thenReturn(2);

        testSettingsManager = new SettingManager(defaultMockConfig, voidLogger);
    }

    @Test
    public void updateConfig() throws Exception {
        FileConfiguration newConfig = Mockito.mock(FileConfiguration.class);
        when(newConfig.getInt(SettingManager.KEEP_DAYS_KEY)).thenReturn(10);

        testSettingsManager.updateConfig(newConfig);

        assertEquals(10, testSettingsManager.getKeepDays());
    }

    @Test
    public void getKeepDays() throws Exception {
        assertEquals(2, testSettingsManager.getKeepDays());
    }

}