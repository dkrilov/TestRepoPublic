package com.blazemeter.jmeter;

import kg.apc.emulators.TestJMeterUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.assertEquals;


public class RandomCSVDataSetConfigGuiTest {

    @BeforeClass
    public static void setUpClass()
            throws Exception {
        TestJMeterUtils.createJmeterEnv();
    }


    @Test
    public void showGui() throws Exception {
        if (!GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance()) {
            RandomExcelDataSetConfigGui gui = new RandomExcelDataSetConfigGui();
            JDialog frame = new JDialog();
            frame.add(gui);

            frame.setPreferredSize(new Dimension(800, 600));
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            while (frame.isVisible()) {
                Thread.sleep(100);
            }
        }
    }

    @Test
    public void testGui() throws Exception {
        RandomExcelDataSetConfigGui gui = new RandomExcelDataSetConfigGui();

        RandomExcelDataSetConfig element1 = (RandomExcelDataSetConfig) gui.createTestElement();
        RandomExcelDataSetConfig element2 = (RandomExcelDataSetConfig) gui.createTestElement();

        element1.setFilename("filename");
        element1.setVariableNames("vars");


        gui.configure(element1);
        gui.modifyTestElement(element2);

        assertEquals(element1.getFilename(), element2.getFilename());
        assertEquals(element1.getVariableNames(), element2.getVariableNames());

        gui.clearGui();
        gui.modifyTestElement(element2);

        assertEquals("", element2.getFilename());
        assertEquals("", element2.getVariableNames());
;
    }
}