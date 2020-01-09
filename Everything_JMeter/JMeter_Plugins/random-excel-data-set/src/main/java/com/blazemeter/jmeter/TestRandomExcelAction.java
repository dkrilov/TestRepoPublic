package com.blazemeter.jmeter;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.apache.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRandomExcelAction implements ActionListener {

    private static final Logger LOGGER = LoggingManager.getLoggerForClass();
    private static final int PREVIEW_MAX_SIZE = 20;
    private final RandomExcelDataSetConfigGui randomExcelConfigGui;

    public TestRandomExcelAction(RandomExcelDataSetConfigGui randomExcelConfigGui) {
        this.randomExcelConfigGui = randomExcelConfigGui;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        final RandomExcelDataSetConfig config = (RandomExcelDataSetConfig) randomExcelConfigGui.createTestElement();

        JTextArea checkArea = randomExcelConfigGui.getCheckArea();

        try {
            config.setFilename(compoundValue(config.getFilename()));
            config.setVariableNames(compoundValue(config.getVariableNames()));
            config.setWorksheet(config.getWorksheet());
            config.setStartatcell(config.getStartatcell());
            config.setisActiveSheet(config.isActiveSheet());

            JMeterVariables jMeterVariables = new JMeterVariables();
            LOGGER.info("deepak");
            JMeterContextService.getContext().setVariables(jMeterVariables);
            final List<Map<String, String>> result = new ArrayList<>();

            config.testStarted();

            String[] destinationVariableKeys = config.getDestinationVariableKeys();

            try {
                for (int i = 0; i < PREVIEW_MAX_SIZE; i++) {
                    config.iterationStart(null);
                    Map<String, String> record = new HashMap<>();
                    for (String var : destinationVariableKeys) {
                        record.put(var, jMeterVariables.get(var));
                    }
                    result.add(record);
                }
            } catch (JMeterStopThreadException ex) {
                // OK
            }

            config.testEnded();

            final StringBuilder builder = new StringBuilder();

            builder.append("Reading Excel successfully finished, ").append(result.size()).append(" records found:\r\n");
            for (Map<String, String> record : result) {
                for (String key : record.keySet()) {
                    builder.append("${").append(key).append("} = ");
                    builder.append(record.get(key));
                    builder.append("\r\n");
                }
                builder.append("------------\r\n");
            }

            checkArea.setText(builder.toString());
            // move scroll to top
            checkArea.setCaretPosition(0);
        } catch (RuntimeException | InvalidVariableException ex) {
            checkArea.setText(ex.getMessage());
            LOGGER.warn("Failed to test Excel Reading ", ex);
        }
    }


    private String compoundValue(String val) throws InvalidVariableException {
        CompoundVariable compoundVariable = new CompoundVariable();
        compoundVariable.setParameters(val);
        return compoundVariable.execute();
    }
}
