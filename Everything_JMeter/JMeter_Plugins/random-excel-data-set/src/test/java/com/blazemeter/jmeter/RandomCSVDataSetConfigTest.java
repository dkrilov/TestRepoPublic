package com.blazemeter.jmeter;

import kg.apc.emulators.TestJMeterUtils;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomCSVDataSetConfigTest {

    @BeforeClass
    public static void setUpClass()
            throws Exception {
        TestJMeterUtils.createJmeterEnv();
    }


    @Test
    public void testProperties() throws Exception {
        RandomExcelDataSetConfig randomCSV = new RandomExcelDataSetConfig();

        String testString = "testString";

        randomCSV.setFilename(testString);
        assertEquals(testString, randomCSV.getFilename());

        randomCSV.setVariableNames(testString);
        assertEquals(testString, randomCSV.getVariableNames());

    }

    // User set variables count less than columns count in file
    @Test
    public void testPutVariables1() throws Exception {
        String path = this.getClass().getResource("/SpaceDelimiter.csv").getPath();

        JMeterVariables jMeterVariables = new JMeterVariables();
        JMeterContextService.getContext().setVariables(jMeterVariables);

        RandomExcelDataSetConfig config = new RandomExcelDataSetConfig();

        config.setFilename(path);
        config.setVariableNames("column1,column2");

        config.testStarted();

        config.iterationStart(null);
        assertEquals(2, jMeterVariables.entrySet().size());
        assertEquals("1", jMeterVariables.get("column1"));
        assertEquals("2", jMeterVariables.get("column2"));
    }

    // User set variables count more than columns count in file
    @Test
    public void testPutVariables2() throws Exception {
        String path = this.getClass().getResource("/SpaceDelimiter.csv").getPath();

        JMeterVariables jMeterVariables = new JMeterVariables();
        JMeterContextService.getContext().setVariables(jMeterVariables);

        RandomExcelDataSetConfig config = new RandomExcelDataSetConfig();

        config.setFilename(path);
        config.setVariableNames("column1,column2,column3,column4");

        config.testStarted();

        config.iterationStart(null);
        assertEquals(3, jMeterVariables.entrySet().size());
        assertEquals("1", jMeterVariables.get("column1"));
        assertEquals("2", jMeterVariables.get("column2"));
        assertEquals("3", jMeterVariables.get("column3"));
    }

    // User set the same variables count like in file columns count
    @Test
    public void testPutVariables3() throws Exception {
        String path = this.getClass().getResource("/SpaceDelimiter.csv").getPath();

        JMeterVariables jMeterVariables = new JMeterVariables();
        JMeterContextService.getContext().setVariables(jMeterVariables);

        RandomExcelDataSetConfig config = new RandomExcelDataSetConfig();

        config.setFilename(path);
        config.setVariableNames("column1,column2,column3,column4");

        config.testStarted();

        config.iterationStart(null);
        assertEquals(3, jMeterVariables.entrySet().size());
        assertEquals("1", jMeterVariables.get("column1"));
        assertEquals("2", jMeterVariables.get("column2"));
        assertEquals("3", jMeterVariables.get("column3"));
    }
}