package com.pms.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class BuyAProduct {

	private XLSReader reader;
	private WebPageNavigation navigation;
	public static ExtentReports extent =new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger;

	@BeforeSuite
	public void beforeSuiteSetup() 
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		htmlReporter = new ExtentHtmlReporter("./Reports/buyAproduct "+ timeStamp +".html");
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setDocumentTitle("LiquorCart");
	}

	@BeforeMethod
	@Parameters("browserName")
	public void launchUrl(String browserName) throws BiffException, IOException
	{				
		reader = new  XLSReader();

		this.navigation = new WebPageNavigation(reader,browserName);

		htmlReporter.config().setReportName("LiquorCart "+browserName);

	}
	@SuppressWarnings("static-access")
	@Test(alwaysRun = true)
	public void buyProducts() throws IOException, Exception
	{	

		WebPageNavigation.openBrowser(Constants.DEV_ENVIRONMENT_LC);	

		WebDriverWait wait = new WebDriverWait(this.navigation.getDriver(),90);

		JavascriptExecutor js = (JavascriptExecutor) this.navigation.getDriver();

		//this.navigation.storeSelection();

		logger = extent.createTest("Select Location");

		logger.log(Status.PASS, "Navigated to Boca Liquor Store");

		WebElement SignInButton = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign In']");
		SignInButton.click();

		Thread.sleep(3000);

		this.navigation.loginToLc();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Sign Out']")));
		
		this.navigation.storeSelection();

		logger.log(Status.PASS, "User Signed in Succesfully!");

		logger = extent.createTest("Add item and proceed");
		
		for(int k=2;k <= this.reader.getRowCount("addToWishList") ;k++)
		{

			JavascriptExecutor js1 = (JavascriptExecutor)this.navigation.getDriver();
			js1.executeScript("window.scrollBy(0,450)", "");

			WebElement search = this.navigation.chooseElement(WebelementType.XPATH, "//input[@id='search']");
			search.clear();
			search.sendKeys(this.reader.getCellData("addToWishList", "Items", k));
			search.submit();

			Thread.sleep(3000);

			WebElement productNamesList = this.navigation.chooseElement(WebelementType.XPATH,"//ol[@class='products list items product-items']//li[" + k + "]");

			if (productNamesList.getText().contains(this.reader.getCellData("addToWishList", "Items", k)))
			{

				WebElement addBTN = this.navigation.chooseElement(WebelementType.XPATH,"//ol[@class='products list items product-items']//li["+k+"]//div//div//div//div[@class='actions-primary']//button//span[text()='Add to Cart']");
				js1.executeScript("", "window.scrollBy(0,350)");
				addBTN.click();

				logger.log(Status.PASS, "Item Added :"+this.reader.getCellData("addToWishList", "Items", k));

				Thread.sleep(3000);

			}else
			{
				logger.log(Status.FAIL, "Item not added/available.");
				this.navigation.getScreenshot(this.navigation.getDriver(), "Product not available");
			}
		}


		js.executeScript("", "window.scrollBy(0,350)");

		WebElement clickOnCart = this.navigation.chooseElement(WebelementType.XPATH, "//a[@href='https://devstore.epicommercestore.com/checkout/cart/']");
		clickOnCart.click();
		
		List<WebElement> listOfItems = this.navigation.getDriver().findElements(By.className("minicart-items"));
		
		for(int h=0;h<listOfItems.size();h++)
		{
			String itemsName = listOfItems.get(h).getText();

			logger.log(Status.INFO, "List of items in cart :"+itemsName);
		}
		
		WebElement proceedToCheckout = this.navigation.chooseElement(WebelementType.XPATH, "//button[text()='Proceed to Checkout']");
		proceedToCheckout.click();

		Thread.sleep(8000);

		logger = extent.createTest("Shipping");


		List<WebElement> listOfElements = this.navigation.getDriver().findElements(By.xpath("//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr/td"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"label_method_flatrate_flatrate\"]")));

		for(int k=0;k<listOfElements.size();k++)
		{
			String listOfItemText = listOfElements.get(k).getText();
			Thread.sleep(500);
			if(listOfItemText.contains("Flat Rate"))
			{
				WebElement radio = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"label_method_flatrate_flatrate\"]");
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"label_method_flatrate_flatrate\"]")));
				radio.click();

				logger.log(Status.INFO, "Flat Rate selected");

				break;
			}

		}

		Thread.sleep(1000);

		js.executeScript("", "window.scrollBy(0,750)");

		WebElement next = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"shipping-method-buttons-container\"]/div/button");
		next.click();

		Thread.sleep(6000);

		WebElement payMethod = this.navigation.chooseElement(WebelementType.ID, "epi");
		payMethod.click();

		logger.log(Status.INFO, "Pay Online selected");

		Thread.sleep(6000);

		WebElement proceedToCheckout1 = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"checkout-payment-method-load\"]/div/div/div[3]/div[2]/div[4]/div/button/span");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkout-payment-method-load\"]/div/div/div[3]/div[2]/div[4]/div/button/span")));
		proceedToCheckout1.click();

		logger = extent.createTest("Payment Gateway");

		Thread.sleep(10000);

		this.navigation.list(By.xpath("//*[@id=\"tx_iframe_tokenExIframeDiv\"]"), (this.reader.getCellData("addToWishList", "CardNum", 2)));

		this.navigation.list(By.xpath("//input[@name='name']"),(this.reader.getCellData("addToWishList", "Name", 2)));

		WebElement cvvNum = this.navigation.chooseElement(WebelementType.ID, "tx_iframe_cvv_iframe-cvc");
		cvvNum.sendKeys(this.reader.getCellData("addToWishList", "cvv", 2));

		logger.log(Status.PASS, "Payment details entered!");

		this.navigation.list(By.xpath("//input[@name='expiry']"),(this.reader.getCellData("addToWishList", "MMYY", 2))); 
		//this.testData.readCell("MMYY", 1));

		WebElement pay = this.navigation.chooseElement(WebelementType.XPATH, "//button[text()='PAY']");
		wait.until(ExpectedConditions.elementToBeClickable(pay));
		pay.click();

		Thread.sleep(8000);

		//logger.log(Status.PASS, "Payment done!");

		WebElement orderID = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"maincontent\"]/div[3]/div/div[2]/p[1]");
		wait.until(ExpectedConditions.visibilityOf(orderID));
		String orderIDtext = orderID.getText();

		Thread.sleep(2000);
		
		logger.log(Status.PASS, "Payment done!");

		logger.log(Status.INFO, " "+orderIDtext);

		WebElement account = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"html-body\"]/div[2]/header/div[1]/div/ul/li[1]/a");
		wait.until(ExpectedConditions.elementToBeClickable(account));
		account.click();

		WebElement myOrders = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"block-collapsible-nav\"]/ul/li[2]/a");
		wait.until(ExpectedConditions.elementToBeClickable(myOrders));
		myOrders.click();

		List<WebElement> listOfOrders = this.navigation.getDriver().findElements(By.xpath("//*[@id=\"my-orders-table\"]/tbody/tr[1]"));

		for(int g=0;g<listOfOrders.size();g++)
		{

			String orderListValues = listOfOrders.get(g).getText();

			logger.log(Status.INFO, "My Order : "+orderListValues);

		}

		WebElement signOut = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign Out']");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Sign Out']")));
		signOut.click();

		logger.log(Status.PASS, "Logout Successful");

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception
	{
		try {
			@SuppressWarnings("static-access")
			String screenshotPath = this.navigation.getScreenshot(this.navigation.getDriver(),result.getName());
			if(result.getStatus() == ITestResult.FAILURE){
				logger.addScreenCaptureFromPath(screenshotPath);
				
				logger.log(Status.FAIL, "Test Case failed");

			}else if(result.getStatus() == ITestResult.SKIP){
				logger.log(Status.SKIP, "Test Case Skipped is "+result.getName());
			}}catch(Exception e)
		{
				logger.log(Status.ERROR, "Test Error");
		}
	}
	@AfterTest
	public void tearDown()
	{
		this.navigation.getDriver().quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		extent.flush();
	}
}

