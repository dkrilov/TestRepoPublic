package com.blazemeter.jmeter;

import kg.apc.jmeter.JMeterPluginsUtils;
import kg.apc.jmeter.gui.BrowseAction;
import kg.apc.jmeter.gui.GuiBuilderHelper;
import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.testelement.TestElement;

import javax.swing.*;
import java.awt.*;

// code working starts here
public class RandomExcelDataSetConfigGui extends AbstractConfigGui {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO: use full URL and change cmn version to 0.6 after it has been released
    public static final String WIKIPAGE = "RandomExcelDataSetConfig";

    private JTextField filenameField;
    private JButton browseButton;

    private JTextField variableNamesField;
    private JTextField workSheet;
    private JTextField startAtCell;
    private JCheckBox isactiveSheet;

    private JButton checkButton;
    private JTextArea checkArea;

    public RandomExcelDataSetConfigGui() {
        initGui();
        initGuiValues();
    }

    private void initGui() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());

        Container topPanel = makeTitlePanel();

        add(JMeterPluginsUtils.addHelpLinkToPanel(topPanel, WIKIPAGE), BorderLayout.NORTH);
        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;

        GridBagConstraints editConstraints = new GridBagConstraints();
        editConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        editConstraints.weightx = 1.0;
        editConstraints.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addToPanel(mainPanel, labelConstraints, 0, row, new JLabel("Filename: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, row, filenameField = new JTextField(20));
        
        addToPanel(mainPanel, labelConstraints, 2, row, browseButton = new JButton("Browse..."));
        row++;
        GuiBuilderHelper.strechItemToComponent(filenameField, browseButton);

        editConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        labelConstraints.insets = new java.awt.Insets(2, 0, 0, 0);

        browseButton.addActionListener(new BrowseAction(filenameField, false));


        addToPanel(mainPanel, labelConstraints, 0, row, new JLabel("Variable names (comma-delimited): ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, row, variableNamesField = new JTextField(20));
        row++;

        
        addToPanel(mainPanel, labelConstraints, 0, row, new JLabel("Worksheet: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, row, workSheet = new JTextField(20));
        row++;
        
        addToPanel(mainPanel, labelConstraints, 0, row, new JLabel("Start At Cell: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, row, startAtCell = new JTextField(20));
        row++;
        
        addToPanel(mainPanel, labelConstraints, 0, row, new JLabel("<html>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Active Sheet:<br/>&emsp;&emsp;&emsp;&emsp;(If worksheet given dosen't exists, default worksheet is used)<br/> </html>", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, row, isactiveSheet = new JCheckBox());
        row++;

        editConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        labelConstraints.insets = new java.awt.Insets(4, 0, 0, 2);

        addToPanel(mainPanel, labelConstraints, 0, row, checkButton = new JButton("Test Excel Reading"));

        labelConstraints.insets = new java.awt.Insets(4, 0, 0, 0);

        checkArea = new JTextArea();
        addToPanel(mainPanel, editConstraints, 1, row, GuiBuilderHelper.getTextAreaScrollPaneContainer(checkArea, 10));
        checkButton.addActionListener(new TestRandomExcelAction(this));
        checkArea.setEditable(false);
        checkArea.setOpaque(false);


        JPanel container = new JPanel(new BorderLayout());
        container.add(mainPanel, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
    }

    private void initGuiValues() {
    	filenameField.setText("");
        variableNamesField.setText("");
        workSheet.setText("");
        startAtCell.setText("");
        isactiveSheet.setSelected(true);
    }

    private void addToPanel(JPanel panel, GridBagConstraints constraints, int col, int row, JComponent component) {
        constraints.gridx = col;
        constraints.gridy = row;
        panel.add(component, constraints);
    }

    @Override
    public String getLabelResource() {
        return "random_Excel_data_set_config";
    }

    @Override
    public String getStaticLabel() {
        return "bzm - Random Excel Data Set Config";
    }

    @Override
    public TestElement createTestElement() {
        RandomExcelDataSetConfig element = new RandomExcelDataSetConfig();
        modifyTestElement(element);
        return element;
    }

    @Override
    public void modifyTestElement(TestElement element) {
        configureTestElement(element);
        if (element instanceof RandomExcelDataSetConfig) {
            RandomExcelDataSetConfig randomExcel = (RandomExcelDataSetConfig) element;

            randomExcel.setFilename(this.filenameField.getText());
            randomExcel.setVariableNames(this.variableNamesField.getText());
            randomExcel.setWorksheet(this.workSheet.getText());
            randomExcel.setStartatcell(this.startAtCell.getText());
            randomExcel.setisActiveSheet(this.isactiveSheet.isSelected());
        }
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);

        if (element instanceof RandomExcelDataSetConfig) {
            RandomExcelDataSetConfig randomExcel = (RandomExcelDataSetConfig) element;

            filenameField.setText(randomExcel.getFilename());
            variableNamesField.setText(randomExcel.getVariableNames());
            workSheet.setText(randomExcel.getWorksheet());
            startAtCell.setText(randomExcel.getStartatcell());

            isactiveSheet.setSelected(randomExcel.isActiveSheet());

        }
    }

    @Override
    public void clearGui() {
        super.clearGui();
        initGuiValues();
    }

    public JTextArea getCheckArea() {
        return checkArea;
    }
}
