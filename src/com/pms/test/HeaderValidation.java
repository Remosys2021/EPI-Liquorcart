package com.pms.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

public class HeaderValidation {
	
	private XLSReader reader;
	private WebPageNavigation navigation;
	public static ExtentReports extent = new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger;
	public static ExtentTest logger1;

	@BeforeSuite
	public void beforeSuiteSetup() {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		htmlReporter = new ExtentHtmlReporter("./Reports/HeaderValidation " + timeStamp + ".html");
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setDocumentTitle("LiquorCart");
	}

	@SuppressWarnings("static-access")
	@BeforeMethod
	@Parameters("browserName")
	public void launchUrl(String browserName) throws BiffException, IOException, InterruptedException {

		reader = new XLSReader();

		this.navigation = new WebPageNavigation(reader, browserName);

		this.navigation.openBrowser(Constants.DEV_ENVIRONMENT_LC);

		logger = extent.createTest("Select Location");
		
		logger.log(Status.PASS, "Navigated to Boca Liquor Store");
		
		WebElement SignInButton = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign In']");
		SignInButton.click();

		Thread.sleep(3000);

		this.navigation.loginToLc();
			
		this.navigation.storeSelection();
		
		logger.log(Status.PASS, "User Signed in Succesfully!");

	}

	@Test
	public void HeaderValidation_AfterLogin() throws InterruptedException {

		logger = extent.createTest("Header Validation on HomePage after Login");
		/*
		 * Validating logo of the app
		 */
		WebElement logo = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_LOGO);
		if(logo.isDisplayed())
		{
			logger.log(Status.PASS, "Logo is displayed on the homepage of the application");
		}

		else {
			logger.log(Status.FAIL, "Logo is not displayed on the Home Page ");
		}

		Thread.sleep(2000);

		/*
		 * Validating Storename 
		 */
		WebElement store = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_STORENAME);
		String storeName = store.getText();
		WebElement storeAdd = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_STOREADDRESS);
		String storeAddress = storeAdd.getText();

		
		if(storeName.contains(this.reader.getCellData("HeaderValidation", "StoreName", 2)))
		{
			logger.log(Status.PASS, "Store info is being displayed as : " + storeName + " " +storeAddress );

		}

		else
		{
			logger.log(Status.FAIL, " Store details are wrong/missing from the header of the Home Page ");
		}

		Thread.sleep(1000);

		/*
		 * Validating Shopping cart icon on home page/header
		 */
		WebElement shoppingCart = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_SHOPPING_CART_ICON);
		if(shoppingCart.isDisplayed())
		{
			logger.log(Status.PASS, "Shopping Cart icon is displayed ");
		}
		else
		{
			logger.log(Status.FAIL, "Shopping Cart icon is missing from the header of the Home Page ");
		}



		/*
		 * Validating the visibility of the merchant's phone number on the header part of the app
		 */
		WebElement merchantPHN = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_MERCHANT_PHNE);

		WebElement merchantPHN_hrs = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_MERCHANT_HRS);

		String merchantPhone = merchantPHN.getText();

		String merchantPhoneHours = merchantPHN_hrs.getText();

		if(merchantPHN.isDisplayed())
		{
			logger.log(Status.PASS, "Merchant's phone and hours info is being displayed as : " + merchantPhone + "\n" + merchantPhoneHours );
		}
		else {
			logger.log(Status.FAIL, " Merchant's phone and hours details are wrong/missing from the header of the Home Page ");
		}


		/*
		 * Validating the signout button visibility
		 */
		WebElement signOutBTN = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_SIGNOU_BTN);
		if(signOutBTN.isDisplayed())
		{
			logger.log(Status.PASS, "Signout button is displayed ");

		}
		else
		{

			logger.log(Status.FAIL, " Sign Out button missing from the header of the Home Page ");
		}

		/*
		 * Validating the product category list on header
		 */

		List<WebElement> productCategoryList = this.navigation.chooseElements(WebelementType.XPATH, Constants.XPATH_PRODUCT_CATEGORY_LIST);

		List<String> collection = new ArrayList<>();

		for (WebElement wb : productCategoryList) {
			collection.add(wb.getText());

		}
		collection.removeIf(Objects::isNull);
		collection.removeIf(String::isEmpty);

		List<String> categoriesList = new ArrayList<>();
		String product ="";
		for (int i = 2; i <= this.reader.getRowCount("HeaderValidation"); i++) {

			String cellData = this.reader.getCellData("HeaderValidation", "category", i);
			
			product = cellData.toUpperCase();
			categoriesList.add(product);

		}
		
		Thread.sleep(2000);

		if(collection.equals(categoriesList))
		{
			logger.log(Status.PASS, collection+" All the categories are displayed on the header of the Home Page ");
		}


		else {
			logger.log(Status.FAIL, collection + "Categories are missing from the header of the Home Page ");
		}



		/*
		 * Validating Search bar
		 */

		WebElement searchBar = this.navigation.chooseElement(WebelementType.ID, Constants.ID_SEARCH_BAR);

		if(searchBar.isDisplayed())
		{
			logger.log(Status.PASS, " Search bar is displayed on the header of the Home Page ");
		}
		else
		{
			logger.log(Status.FAIL, " Search bar is missing from the header of the Home Page ");
		}


		/*
		 * Validating Theme image
		 */
		WebElement themeIMAGE = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_THEME_IMAGE);

		if(themeIMAGE.isDisplayed())
		{
			logger.log(Status.PASS, " Theme image is displayed on the Home Page ");
		}
		else
		{
			logger.log(Status.FAIL,  "Theme image  is missing from the header of the Home Page ");
		}


		/*
		 * Validating Select a store link on the header
		 */

		WebElement selectAstore = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_SELECT_A_STORE);

		if(selectAstore.getText().equals(this.reader.getCellData("HeaderValidation", "StoreLink", 2)))
		{
			logger.log(Status.PASS, selectAstore.getText() + " link is available");
		}
		else {
			logger.log(Status.FAIL,  "Link to select a new store is missing from the header");
		}
		selectAstore.click();

		Thread.sleep(3000);
		WebElement title_SelectANewStore = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_TITLE_SELECT_A_STORE_PAGE_);

		List<WebElement> storeList = this.navigation.chooseElements(WebelementType.XPATH, Constants.XPATH_LIST_OF_STORE_SELECT_A_STORE_PAGE);

		if(title_SelectANewStore.getText().equals(this.reader.getCellData("HeaderValidation", "Title", 2))) {
			logger.log(Status.PASS, "User is navigated to the : " + title_SelectANewStore.getText());

			for(WebElement wb : storeList)
			{
				logger.info("List of stores available : "+wb.getText());
			}

		}else
		{
			logger.log(Status.FAIL,  "User is not able to navigate to the Select a Store page");

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
