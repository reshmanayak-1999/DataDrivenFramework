package com.w2a.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase{
	
	@Test
	public void loginAsBankManager() throws InterruptedException {
		log.debug("Inside Login Test");
		/*
		 * driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		 * driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();
		 */
		click("bmlBtn_CSS");
		click("addCustBtn_CSS");
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))));
		Thread.sleep(3000);
		log.debug("Login completed successfully");
		/* Assert.fail("Login not successfull"); */
	}

}

