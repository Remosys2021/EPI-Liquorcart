package com.pms.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.testng.log4testng.Logger;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestData {
	
	private static final Logger logger = Logger.getLogger(TestData.class);
	private static final String TEST_DATA ="./TestData.xls";
	private Sheet wrksheet;
	private Workbook wrkbook = null;
	private Map<String, Integer> dict = new HashMap<>();
	private int totalNumerOfRows = 0;

	public TestData(String sheetName) throws BiffException, IOException {
		try {
			wrkbook = Workbook.getWorkbook(new File(TEST_DATA));
			wrksheet = wrkbook.getSheet(sheetName);
			this.totalNumerOfRows=wrksheet.getRows();
			columnDictionary();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Sheet getWrksheet() {
		return wrksheet;
	}

	public Workbook getWrkbook() {
		return wrkbook;
	}

	public Map<String, Integer> getDict() {
		return dict;
	}

	public String readCell(int column, int row) {
		return this.wrksheet.getCell(column, row).getContents();
	}

	public String readCell(String colName, int row) {
		return this.wrksheet.getCell(this.getCell(colName), row).getContents();
	}


	public int getCell(String colName) {
		try {
			int value;
			value = dict.get(colName).intValue();
			return value;
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	
	private void columnDictionary() {
		
		for (int col = 0; col < this.wrksheet.getColumns(); col++) {
			dict.put(readCell(col, 0), col);
		}
	}
	
	public int getTotalNumerOfRows() {
		return totalNumerOfRows;
	}

}
