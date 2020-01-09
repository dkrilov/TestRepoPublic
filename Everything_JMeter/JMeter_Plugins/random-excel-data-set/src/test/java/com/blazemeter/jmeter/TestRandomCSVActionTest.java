package com.blazemeter.jmeter;

import kg.apc.emulators.TestJMeterUtils;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;


public class TestRandomCSVActionTest {

    @BeforeClass
    public static void setUpClass()
            throws Exception {
        TestJMeterUtils.createJmeterEnv();
    }

    @Test
    public void testAction() throws Exception {
        String path = this.getClass().getResource("/JMeterCsvResults.csv").getPath();

        RandomExcelDataSetConfig config = new RandomExcelDataSetConfig();
        config.setFilename(path);
        // special check for skip first line. It must no skip and return all lines in file.
        config.setVariableNames("aaa,aaa1");

        RandomExcelDataSetConfigGui gui = new RandomExcelDataSetConfigGui();

        gui.configure(config);

        TestRandomExcelAction action = new TestRandomExcelAction(gui);

        action.actionPerformed(null);
        assertTrue(gui.getCheckArea().getText().startsWith("Reading CSV successfully finished, 4 records found"));

        config.setFilename("");
        gui.configure(config);
        action.actionPerformed(null);
        assertTrue(gui.getCheckArea().getText().contains("Is a directory"));
    }
}