package com.pms.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

public class AddAProductBeforeSignIn 
{
	private WebPageNavigation navigation;
	public static ExtentReports extent =new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger;
	private XLSReader reader;

	@BeforeSuite
	public void beforeSuiteSetup() 
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		htmlReporter = new ExtentHtmlReporter("./Reports/AddAProductBeforeSignIn "+ timeStamp +".html");
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setDocumentTitle("LiquorCart");
	}

	@BeforeMethod
	@Parameters("browserName")
	public void launchUrl(String browserName) throws BiffException, IOException
	{				
		reader = new XLSReader();

		this.navigation = new WebPageNavigation(reader,browserName);

		htmlReporter.config().setReportName("LiquorCart "+browserName);

	}
	@Test(alwaysRun = true)
	public void addAProductBeforeSignIn() throws IOException, Exception
	{	

		try {
			WebPageNavigation.openBrowser(Constants.PROD_ENVIRONMENT_LC);	

			logger = extent.createTest("Select Location");

			WebDriverWait wait = new WebDriverWait(this.navigation.getDriver(),90);

			JavascriptExecutor js = (JavascriptExecutor) this.navigation.getDriver();

			this.navigation.storeSelection();

			logger.log(Status.PASS, "Navigated to Boca Liquor Store");

			logger = extent.createTest("Search & Add item and proceed");

			for(int j=2;j<= this.reader.getRowCount("AddAProductBeforeSignIn");)
			{
				WebElement liquor = this.navigation.chooseElement(WebelementType.XPATH, "//span[text()='"+this.reader.getCellData("AddAProductBeforeSignIn", "Category", j)+ "']");	

				Actions act = new Actions(this.navigation.getDriver());

				act.moveToElement(liquor).build().perform();

				Thread.sleep(1000);

				String expectedValue = this.reader.getCellData("AddAProductBeforeSignIn", "SubCategory", j);
				WebElement clickItem = this.navigation.chooseElement(WebelementType.XPATH, "//span[text()='"+expectedValue+"']");
				wait.until(ExpectedConditions.elementToBeClickable(clickItem));
				clickItem.click();		

				Thread.sleep(3000);

				JavascriptExecutor js1 = (JavascriptExecutor)this.navigation.getDriver();
				js1.executeScript("window.scrollBy(0,450)", "");

				this.navigation.listClick(By.xpath("//ol[@class='products list items product-items']//li[1]"));

				logger.log(Status.PASS, "Item Selected");

				WebElement qty = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='qty']");
				qty.clear();
				qty.sendKeys(this.reader.getCellData("AddAProductBeforeSignIn", "qty", j));

				WebElement addToCart = this.navigation.chooseElement(WebelementType.XPATH, "//button[@title='Add to Cart']");
				wait.until(ExpectedConditions.elementToBeClickable(addToCart));
				addToCart.click();


				break;
			}

			Thread.sleep(6000);
			js.executeScript("", "window.scrollBy(0,350)");

			WebElement itemStatus = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_ITEM_STATUS);
			
			if(itemStatus.getText().equals("Item not available"))
			{
				logger.log(Status.FAIL, itemStatus.getText());
				Assert.assertTrue(false);
			}
			else
			{
				// We can apply other conditions like Session Timeout etc
				logger.log(Status.PASS, itemStatus.getText());
			}


			WebElement SignInButton = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign In']");
			wait.until(ExpectedConditions.elementToBeClickable(SignInButton));
			SignInButton.click();

			Thread.sleep(3000);

			this.navigation.loginToLc();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Sign Out']")));

			logger.log(Status.PASS, "User Signed in Succesfully!");

			WebElement signOut = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign Out']");
			wait.until(ExpectedConditions.elementToBeClickable(signOut));
			signOut.click();

			logger.log(Status.PASS, "Signed Out");

		}catch(Exception e)
		{

			logger.log(Status.ERROR, "Test Case Failed!");
		}

	}
	@AfterMethod
	public void getResult(ITestResult result) throws Exception
	{
		try {
			@SuppressWarnings("static-access")
			String screenshotPath = this.navigation.getScreenshot(this.navigation.getDriver(),result.getName());
			if(result.getStatus() == ITestResult.FAILURE){
				logger.addScreenCaptureFromPath(screenshotPath);

				logger.log(Status.ERROR, "Test Case Failed");

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
