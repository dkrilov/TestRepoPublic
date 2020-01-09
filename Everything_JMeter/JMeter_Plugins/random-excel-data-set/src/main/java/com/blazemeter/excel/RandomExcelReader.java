package com.blazemeter.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.jmeter.services.FileServer;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomExcelReader {
    private static final Logger LOGGER = LoggingManager.getLoggerForClass();

    private File file;

    private ArrayList<Integer> offsets;
//    private 
    private int curPos = 0;
    private int maxColoums=0;
    private Random random;

    private BufferedReader consistentReader;
    private String[] header;
    private Workbook excelWorkbook = null;
    private Sheet excelSheet=null;
    private String workSheet;
    private String startAtCell;
    private boolean isActiveSheet;
    private int startRow;
    private int startColoumn;


    public RandomExcelReader(String filename, boolean hasVariableNames,String worksheet,String startatcell,boolean isactivesheet) {
        File f = new File(filename);
        this.file = (f.isAbsolute() || f.exists()) ? f : new File(FileServer.getFileServer().getBaseDir(), filename);
        this.workSheet=worksheet;
        this.startAtCell=startatcell;
        this.isActiveSheet=isactivesheet;
        try {
        	this.file = (f.isAbsolute() || f.exists()) ? f : new File(FileServer.getFileServer().getBaseDir(), filename);
	       	FileInputStream inputStream = new FileInputStream(file);
	       	excelWorkbook = new XSSFWorkbook(inputStream);
	       	excelSheet = excelWorkbook.getSheet(this.workSheet);
	       	if (excelSheet==null) {
	       		if(!isActiveSheet) {
	       			throw new Exception("The WorkSheet with name '  "+workSheet+"  ' dosen't exists.Please enter a valid worksheet name. or check isActive checkbox to select default worksheet");
	       		}
	       		else {
	       			LOGGER.warn("The given worksheet is not valid. So trying to pick a active worksheet.");
		       		excelSheet =excelWorkbook.getSheetAt(excelWorkbook.getActiveSheetIndex());
		       		if(excelSheet==null) {
		       			if(this.workSheet.trim().length()==0) {
		       				throw new Exception("There is no Active Sheet to pick. Please try mentioning a worksheet name.");
		       			}
		       			else {
		       				throw new Exception("The given worksheet "+workSheet+" dosen't exists and There is no Active Sheet to pick. Please try mentioning a worksheet name.");
		       			}
		       		}
		       	}
	       	}
	       	try {
	       		setStartRow();
		       	setStartColoumn();	
	       	}
	       	catch(Exception e) {
	       		throw new Exception("please provide a valid startAtCell field. Example 'A1' where A is coloum  and 1 is the row to start reading from");
	       	}
            initOffsets();
            getMaxColoumsFilled();
        } catch (Exception ex) {
            LOGGER.error("Cannot initialize RandomexcelReader, because of error: ", ex);
            throw new RuntimeException("Cannot initialize RandomexcelReader, because of error: " + ex.getMessage(), ex);
        }
    }

    private void setStartRow() {
    	String[] part = startAtCell.split("(?<=\\D)(?=\\d)");
    	startColoumn=CellReference.convertColStringToIndex(part[0]);
    }
    
    private void setStartColoumn() {
    	String[] part = startAtCell.split("(?<=\\D)(?=\\d)");
    	startRow=Integer.parseInt(part[1]);
    }


    public void closeConsistentReader() throws IOException {
        consistentReader.close();
    }

    public boolean hasNextRecord() {
    	if (curPos < offsets.size()) {
            return true;
        }
        return false;
    }


    public String[] getHeader() {
        return header;
    }

    public void getMaxColoumsFilled() {
    	int maxcoloums=0;
    	for(int i=0;i<offsets.size();i++) {
    		Row row = excelSheet.getRow(i);
    		if(row.getLastCellNum()>maxcoloums) {
    			maxcoloums=row.getLastCellNum();
    		}
    	}
    	maxColoums=maxcoloums;
    }
    //look here which is root code
    
    public String[] readNextLine() {
        try {
            ArrayList<String> rowData=new ArrayList<String>();
            Row row = excelSheet.getRow(curPos);
            for (int j = startColoumn; j < maxColoums; j++) {
            	if(row.getCell(j)!=null) {
            		String tem=row.getCell(j).getStringCellValue();
                	rowData.add(tem==null? "" : tem);
            	}
            	else {
            		rowData.add("");
            	}
            }
            curPos++;
            String[] convertedRowData = new String[rowData.size()];
            convertedRowData = rowData.toArray(convertedRowData);
            return convertedRowData;
        } catch (Exception ex) {
            LOGGER.error("Cannot get next record from excel file: ", ex);
            throw new RuntimeException("Cannot get next record from excel file: " + ex.getMessage(), ex);
        }
    }

    public long getNextLineAddr() {
        int pos = getRandomPos();
        swap(curPos + pos);
        long lineAddr = offsets.get(curPos);
        curPos++;
        return lineAddr;
    }

    private void swap(int pos) {
        Integer tmp = offsets.get(curPos);
        offsets.set(curPos, offsets.get(pos));
        offsets.set(pos, tmp);
    }


    private int getRandomPos() {
        return random.nextInt(offsets.size() - curPos);
    }


    private void initOffsets() throws IOException {
        offsets = new ArrayList<>();
        int numberOfRows=excelSheet.getPhysicalNumberOfRows();
        for(int i=0;i<numberOfRows-2;i++) {
        	offsets.add(i);
        }
        curPos=startRow-1;
        LOGGER.info("Reading finished. Found " + offsets.size() + " records in your excel file");
    }
    
}
