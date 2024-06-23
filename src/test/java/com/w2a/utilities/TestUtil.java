package com.w2a.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.w2a.base.TestBase;

public class TestUtil extends TestBase {
	
	public static String screenshotPath;
	public static String screenshotName;
	
	public static void captureScreenshot() throws IOException {
		Date d = new Date();
		System.out.println(d.toString().replace(":", "_").replace(" ", "_")+".jpg");
		screenshotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		TakesScreenshot screenshot = ((TakesScreenshot)driver);
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir")+"\\target\\suefire-reports\\html\\"+screenshotName);
		FileUtils.copyFile(srcFile, destFile);
		
	}
	@DataProvider(name="dp")
	public Object[][] getData(Method m) throws Exception{
		String sheetName=m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
				 if (rows <= 1) {
		        return new Object[0][0]; // Return an empty array if no data is present
		    }

		 Object[][] data = new Object[rows-1][cols];
		 Hashtable<String, String> table = null;
		
		for(int rowNum = 2; rowNum <= rows; rowNum++) {
			table =new  Hashtable<String,String>();
			for(int colNum=0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum-2][0]=table;
			}
		}
		
		
		return data;
				
		
	}
	
	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		String sheetName="test_suite";
		int rows = excel.getRowCount(sheetName);
		for(int rowNum=2;rowNum<=rows;rowNum++) {
			String testcase=excel.getCellData(sheetName, "TCID", rowNum);
			if(testcase.equalsIgnoreCase(testName)) {
				String runMode=excel.getCellData(sheetName, "Runmode", rowNum);
				if(runMode.equalsIgnoreCase("Y")) {
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}

}
