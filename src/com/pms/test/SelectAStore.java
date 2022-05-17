package com.pms.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.pms.util.Constants;
import com.pms.util.WebPageNavigation;
import com.pms.util.WebelementType;
import com.pms.util.XLSReader;

import jxl.read.biff.BiffException;

public class SelectAStore {

	private XLSReader reader;
	private WebPageNavigation navigation;
	public static ExtentReports extent = new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger;

	@BeforeSuite
	public void beforeSuiteSetup() {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		htmlReporter = new ExtentHtmlReporter("./Reports/SelectStore " + timeStamp + ".html");
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setDocumentTitle("LiquorCart");
	}

	@BeforeMethod
	@Parameters("browserName")
	public void launchUrl(String browserName) throws BiffException, IOException {
		reader = new XLSReader();

		this.navigation = new WebPageNavigation(reader, browserName);

		htmlReporter.config().setReportName("SelectAStore " + browserName);

	}

	@Test
	public void selectStore() throws InterruptedException {

		logger = extent.createTest("Selecting the Store");
		WebPageNavigation.openBrowser("https://devstore.epicommercestore.com/");

		WebElement deliveryAddress = this.navigation.chooseElement(WebelementType.XPATH,
				Constants.XPATH_ENTER_DELIVERY_ADD);

		deliveryAddress.click();
		WebElement continueBTN = this.navigation.chooseElement(WebelementType.XPATH,
				Constants.XPATH_BTN_CONTINUE_SPLASH_SCRN);
		deliveryAddress.sendKeys(this.reader.getCellData("SelectAStore", "DeliveryAddress", 2));

		logger.info("Entering the delivery address : " + this.reader.getCellData("SelectAStore", "DeliveryAddress", 2));

		Thread.sleep(40000);

		try 
		{

			deliveryAddress.sendKeys(Keys.BACK_SPACE);
			WebElement yamato = this.navigation.chooseElement(WebelementType.XPATH,"//div[@class='pac-container pac-logo hdpi']//div//span//span[text()='Yamato Rd']");
			yamato.click();
		
			Thread.sleep(3000);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		continueBTN.click();

		Thread.sleep(3000);

		WebElement title_SelectANewStore = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_TITLE_SELECT_A_STORE_PAGE_);
		List<WebElement> storeList = this.navigation.chooseElements(WebelementType.XPATH, Constants.XPATH_LIST_OF_STORE_SELECT_A_STORE_PAGE);

		if(title_SelectANewStore.getText().equals(this.reader.getCellData("SelectAStore", "Title", 2))) {
			logger.log(Status.PASS, "User is navigated to the : " + title_SelectANewStore.getText());

			for(WebElement wb : storeList)
			{
				logger.info("List of stores available : "+wb.getText());
			}

		}else
		{
			logger.log(Status.FAIL,  "User is not able to navigate to the Select a Store page");

		}	

		/**
		 *  Navigating to child window
		 */
		this.navigation.storeSelection();

		/*
		 * Validating Storename 
		 */
		WebElement store = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_STORENAME);
		String storeName = store.getText();
		
		WebElement storeAdd = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_STOREADDRESS);
		String storeAddress = storeAdd.getText();

		if(storeName.contains(this.reader.getCellData("SelectAStore", "StoreName", 2)))
		{
			logger.log(Status.PASS, "Store is selected successfully and info is being displayed as : " + storeName + " " +storeAddress );

		}

		else
		{
			logger.log(Status.FAIL, " Store is not selected ");
		}

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		try {

			@SuppressWarnings("static-access")
			String screenshotPath = this.navigation.getScreenshot(this.navigation.getDriver(), result.getName());
			if (result.getStatus() == ITestResult.FAILURE) {
				logger.addScreenCaptureFromPath(screenshotPath);
				logger.log(Status.ERROR, "Test Case Failed");
			}

			else if (result.getStatus() == ITestResult.SKIP) {
				logger.log(Status.SKIP, "Test Case Skipped is " + result.getName());
			}
		} catch (Exception e) {
			logger.log(Status.ERROR, "Test Error");
		}

	}

	@AfterTest
	public void tearDown() {
		this.navigation.getDriver().quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		extent.flush();
	}

}
