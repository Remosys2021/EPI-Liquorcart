package com.pms.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.ITestResult;
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

public class NewUserSignUp {

	private XLSReader reader;
	private WebPageNavigation navigation;
	public static ExtentReports extent =new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger;

	@BeforeSuite
	public void beforeSuiteSetup() {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		htmlReporter = new ExtentHtmlReporter("./Reports/NewUserSignup "+ timeStamp +".html");
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

		htmlReporter.config().setReportName("SingleUserLogin "+browserName);
	}
	@Test
	public void userSignUp() throws IOException, Exception
	{	
		Random randomGenerator = new Random();  
		int randomInt = randomGenerator.nextInt(10000); 
		
		WebPageNavigation.openBrowser(Constants.DEV_ENVIRONMENT_LC);	

		logger = extent.createTest("Select Location");

		WebDriverWait wait = new WebDriverWait(this.navigation.getDriver(),90);
		
		this.navigation.storeSelection();

		logger.log(Status.PASS, "Navigated to Boca Liquor Store");
		
		WebElement SignInButton = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign In']");
		SignInButton.click();

		Thread.sleep(3000);
		
		logger = extent.createTest("Create New Account");

		WebElement createOne = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[2]/div[2]/div/div/a");
		createOne.click();

		Thread.sleep(3000);
		
		WebElement firstName = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='firstname']");
		firstName.sendKeys(this.reader.getCellData("NewUserSignup", "FirstName", 2));
	
		WebElement lastName = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='lastname']");
		lastName.sendKeys(this.reader.getCellData("NewUserSignup", "LastName", 2));
		
//		WebElement checkSubs = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='is_subscribed']");
//		checkSubs.click();
		
		WebElement remoteAssist = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='assistance_allowed_checkbox']");
		remoteAssist.click();
		
		WebElement email = this.navigation.chooseElement(WebelementType.ID, "email_address");
		email.clear();
		
		String emailText = "sonal"+randomInt+"@gmail.com";
		email.sendKeys(emailText);
	
		logger.log(Status.PASS, "Email entered :"+emailText);
		
		WebElement pass = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='password']");
		pass.clear();
		pass.sendKeys(this.reader.getCellData("NewUserSignup", "Password", 2));
		
		WebElement cnfrmPass = this.navigation.chooseElement(WebelementType.ID, "password-confirmation");
		cnfrmPass.sendKeys(this.reader.getCellData("NewUserSignup", "Password", 2));

		logger.log(Status.PASS, "Pass entered");

		WebElement createAnAccount = this.navigation.chooseElement(WebelementType.XPATH, "//button[@type='submit']");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		createAnAccount.click();

		Thread.sleep(3000);

		WebElement welcomeName = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"maincontent\"]/div[1]/div[2]/div/div/div");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Sign Out']")));

		String welcomeNameText = welcomeName.getText();

		logger.log(Status.PASS, "New account created succesfully");
		
		logger.log(Status.PASS, "Sign in successful :"+welcomeNameText);
		
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
	public void afterSuite() {
		extent.flush();
	}

}
