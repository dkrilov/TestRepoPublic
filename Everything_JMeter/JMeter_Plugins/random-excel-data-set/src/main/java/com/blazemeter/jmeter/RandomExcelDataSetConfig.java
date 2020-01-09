package com.blazemeter.jmeter;

import com.blazemeter.excel.RandomExcelReader;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.ThreadListener;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;

import java.io.IOException;

public class RandomExcelDataSetConfig extends ConfigTestElement implements NoThreadClone, LoopIterationListener, TestStateListener, ThreadListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggingManager.getLoggerForClass();

    public static final String FILENAME = "filename";
    public static final String VARIABLE_NAMES = "variableNames";
    public static final String WORKSHEET = "worksheet";
	public static final String STARTATCELL = "startatcell";
	public static final String ACTIVESHEET = "activesheet";

    private RandomExcelReader randomExcelReader;

    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        if (randomExcelReader == null) {
            throw new JMeterStopThreadException("All records in the Excel file have been passed.");
        }
            readConsistent();
    }


    private void readConsistent() {
        final RandomExcelReader reader = getReader();
        synchronized (reader) {
            if (reader.hasNextRecord()) {
            	String temp[]=reader.readNextLine();
                JMeterVariables variables = JMeterContextService.getContext().getVariables();
                putVariables(variables, getDestinationVariableKeys(), temp);
            } else {
                randomExcelReader = null;
                throw new JMeterStopThreadException("All records in the Excel file have been passed.");
            }
        }
    }

    public String[] getDestinationVariableKeys() {
        String vars = getVariableNames();
        return 
                JOrphanUtils.split(vars, ",");
    }

    private void putVariables(JMeterVariables variables, String[] keys, String[] values) {
        int minLen = (keys.length > values.length) ? values.length : keys.length;
        for (int i = 0; i < minLen; i++) {
            variables.put(keys[i], values[i]);
        }
    }

    private RandomExcelReader getReader() {
        return randomExcelReader;
    }

    private RandomExcelReader createRandomExcelReader() {
        return new RandomExcelReader(
                getFilename(),
                hasVariablesNames(),
                getWorksheet(),
                getStartatcell(),
                isActiveSheet()
        );
    }

    private boolean hasVariablesNames() {
        String vars = getVariableNames();
        return (vars != null && !vars.isEmpty());
    }


    @Override
    public void threadStarted() {

    }

    @Override
    public void threadFinished() {
        RandomExcelReader reader = getReader();
        if (reader != null) {
            try {
                reader.closeConsistentReader();
            } catch (IOException e) {
                LOGGER.warn("Failed to close Consistent Reader", e);
            }
        }
    }

    @Override
    public void testStarted() {
        testStarted("*local*");
    }

    @Override
    public void testStarted(String s) {
        randomExcelReader = createRandomExcelReader();
    }

    @Override
    public void testEnded() {
        testEnded("*local*");
    }

    @Override
    public void testEnded(String s) {
        try {
            if (randomExcelReader != null) {
                randomExcelReader.closeConsistentReader();
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to close Consistent Reader", e);
        }
        randomExcelReader = null;
    }


    public String getFilename() {
        return getPropertyAsString(FILENAME);
    }

    public void setFilename(String filename) {
        setProperty(FILENAME, filename);
    }


	public String getWorksheet() {
		return getPropertyAsString(WORKSHEET);
	}

	public String getStartatcell() {
		return getPropertyAsString(STARTATCELL);
	}
	
	public void  setStartatcell(String startatcell) {
		setProperty(STARTATCELL,startatcell);
	}
	
	public void  setWorksheet(String worksheet) {
		setProperty(WORKSHEET,worksheet);
	}


    public String getVariableNames() {
        return getPropertyAsString(VARIABLE_NAMES);
    }

    public void setVariableNames(String variableNames) {
        setProperty(VARIABLE_NAMES, variableNames);
    }

    public boolean isActiveSheet() {
        return getPropertyAsBoolean(ACTIVESHEET);
    }

    public void setisActiveSheet(boolean activesheet) {
        setProperty(ACTIVESHEET, activesheet);
    }
    
}
