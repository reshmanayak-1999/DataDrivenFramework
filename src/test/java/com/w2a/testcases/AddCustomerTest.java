package com.w2a.testcases;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends TestBase {
	
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomer(Hashtable<String,String> data) throws InterruptedException {
		
		 driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			/*
			 * driver.findElement(By.cssSelector(OR.getProperty("firstName"))).sendKeys(
			 * firstName);
			 * driver.findElement(By.cssSelector(OR.getProperty("lastName"))).sendKeys(
			 * lastName);
			 * driver.findElement(By.cssSelector(OR.getProperty("postCode"))).sendKeys(
			 * postCode);
			 * driver.findElement(By.cssSelector(OR.getProperty("addBtn"))).click();
			 */
		 
		 if(!data.equals("Y")) {
			 throw new SkipException("Skipping the test case as the Run Mode for data set is NO");
		 }
		 
		 type("firstName_CSS",data.get("fistName"));
		 type("lastName_CSS",data.get("lastName"));
		 type("postCode_CSS",data.get("postCode"));
		 click("addBtn_CSS");
		 
		Thread.sleep(3000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		
		alert.accept();
		
		
	}
	

	

}
