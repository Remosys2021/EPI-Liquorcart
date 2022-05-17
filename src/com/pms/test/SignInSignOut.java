package com.pms.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
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
import com.pms.util.TestData;
import com.pms.util.WebPageNavigation;
import com.pms.util.WebelementType;
import jxl.read.biff.BiffException;

public class SignInSignOut 
{
	private TestData testData;
	private WebPageNavigation navigation;
	public static ExtentReports extent =new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger;

	@BeforeSuite
	public void beforeSuiteSetup() 
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		htmlReporter = new ExtentHtmlReporter("./Reports/SignInSignOut "+ timeStamp +".html");
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setDocumentTitle("LiquorCart");
	}

	@BeforeMethod
	@Parameters("browserName")
	public void launchUrl(String browserName) throws BiffException, IOException
	{				
		testData = new TestData("SignInSignOut");

		this.navigation = new WebPageNavigation(testData,browserName);

		htmlReporter.config().setReportName("LiquorCart "+browserName);
	}
	@Test(alwaysRun = true)
	public void signIn() throws IOException, Exception
	{	
		WebPageNavigation.openBrowser(Constants.DEV_ENVIRONMENT_LC);	
		
		logger = extent.createTest("Login");

		WebElement SignInButton = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign In']");
		SignInButton.click();

		Thread.sleep(3000);
		
		this.navigation.loginToLc();
		
		WebDriverWait wait = new WebDriverWait(this.navigation.getDriver(),90);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Sign Out']")));
		
		this.navigation.storeSelection();
	}

	@Test(dependsOnMethods = { "signIn" })
	public void signOut() throws InterruptedException
	{
		 WebDriverWait wait = new WebDriverWait(this.navigation.getDriver(),90);

		logger.log(Status.PASS, "User Signed in Succesfully!");

		Thread.sleep(3000);

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
	public void afterSuite() 
	{
		extent.flush();
	}
}
