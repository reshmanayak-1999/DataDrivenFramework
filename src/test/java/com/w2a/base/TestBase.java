package com.w2a.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public ExtentReports report = ExtentManager.getInstance();
	public static WebDriverWait wait;
	public static ExcelReader excel = new ExcelReader("C:\\Users\\Reshma\\eclipse-workspace\\DataDrivenFramework\\src\\test\\resources\\excel\\testdata.xlsx");
	public static ExtentTest test;
	
	@BeforeSuite
	public void setUp() throws IOException {
		if(driver==null) {
		FileInputStream fis = new FileInputStream("C:\\Users\\Reshma\\eclipse-workspace\\DataDrivenFramework\\src\\test\\resources\\properties\\Config.properties");
	    config.load(fis);
	    log.debug("config file loaded");
	    fis = new FileInputStream("C:\\Users\\Reshma\\eclipse-workspace\\DataDrivenFramework\\src\\test\\resources\\properties\\OR.properties");
	    OR.load(fis);
	    log.debug("OR file loaded");
	}
		if(config.getProperty("browser").equals("chrome")) {
			//System.setProperty("webdriver.chrome.driver", "C:\\Users\\Reshma\\eclipse-workspace\\DataDrivenFramework\\src\\test\\resources\\executables\\chrome.exe");
			WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		log.debug("Chrome Browser launched");
		}else if(config.getProperty("browser").equals("firefox")) {
			//System.setProperty("webdriver.gecko.driver", "C:\\Users\\Reshma\\eclipse-workspace\\DataDrivenFramework\\src\\test\\resources\\executables\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else if(config.getProperty("browser").equals("edge")) {
		//System.setProperty("webdriver.ie.driver", "C:\\Users\\Reshma\\eclipse-workspace\\DataDrivenFramework\\src\\test\\resources\\executables\\IEDriverServer.exe");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
	}
driver.get(config.getProperty("testsiteurl"));
log.debug("Navigated to testsiteurl: "+ config.getProperty("testsiteurl"));
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
wait = new WebDriverWait(driver,Duration.ofSeconds(5));
}
	
	public void click(String locator) {
		if(locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_XPATH")){
		driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO,"Clicking on :"+locator);
	}
	
	public void type(String locator, String value) {
		if(locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}
		test.log(LogStatus.INFO,"typing in:"+locator+"Entered value as:"+value);
	}
	
	static WebElement dropdown;
	public void select(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
		dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		}else if(locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		test.log(LogStatus.INFO,"Selecting from dropdown :"+locator+"Entered value as:"+value);
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		}catch(NoSuchElementException e) {
			return false;
		}
		
		
	}
	 public static void verifyEquals(String actual, String expected) throws IOException {
		 try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable t) {
			TestUtil.captureScreenshot();
			//ReportNG
			Reporter.log("<br>"+"verification failure :"+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200 </a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			//Extent Reports
			test.log(LogStatus.FAIL, "Verification Failed with exception :"+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		}
	 }
	
	
	@AfterSuite
	public void closeDown() {
		if(driver!=null) {
		driver.quit();
		log.debug("Test Execution Completed");
		}
	}
}
