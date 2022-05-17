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
import com.pms.util.WebPageNavigation;
import com.pms.util.WebelementType;
import com.pms.util.XLSReader;
import jxl.read.biff.BiffException;

public class Newsletter {

	private XLSReader reader;
	private WebPageNavigation navigation;
	public static ExtentReports extent = new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger;

	@BeforeSuite
	public void beforeSuiteSetup() {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		htmlReporter = new ExtentHtmlReporter("./Reports/Newsletter " + timeStamp + ".html");
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setDocumentTitle("LiquorCart");
	}

	@BeforeMethod
	@Parameters("browserName")
	public void launchUrl(String browserName) throws BiffException, IOException, InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(this.navigation.getDriver(),90);

		reader = new XLSReader();

		this.navigation = new WebPageNavigation(reader, browserName);

		htmlReporter.config().setReportName("LiquorCart "+browserName);
		logger = extent.createTest("Browser Name :" + browserName);

		WebPageNavigation.openBrowser(Constants.DEV_ENVIRONMENT_LC);

		this.navigation.storeSelection();

		WebElement SignInButton = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign In']");
		SignInButton.click();

		Thread.sleep(3000);

		logger = extent.createTest("Select Location");

		logger.log(Status.PASS, "Navigated to Boca Liquor Store");

		this.navigation.loginToLc();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Sign Out']")));

		logger.log(Status.PASS, "User Signed in Succesfully!");
	}

	@Test
	public void NewsletterSubscription() throws InterruptedException {

		logger = extent.createTest("Subscribe to Newsletter");

		WebElement account_Link = this.navigation.chooseElement(WebelementType.XPATH, Constants.XPATH_ACCOUNT_LINK);
		account_Link.click();

		WebElement box_Content = this.navigation.chooseElement(WebelementType.XPATH,
				Constants.XPATH_NEWSLETTERBOX_CONTENT);
		String newsLtr_bxcontent = box_Content.getText();

		logger.info(newsLtr_bxcontent);

		if (newsLtr_bxcontent.contains(this.reader.getCellData("Newsletter", "SubscriptionInfo", 2))) {
			WebElement editBTN = this.navigation.chooseElement(WebelementType.XPATH,
					Constants.XPATH_NEWSLETTERBOX_EDIT_BTN);
			editBTN.click();

			logger.info("Clicking on Edit button to subscribe the Newsletter");

			WebElement newsLetterSubscr_Title = this.navigation.chooseElement(WebelementType.XPATH,
					Constants.XPATH_NEWSLETTER_SUBSCRIPTION_TITLE);

			logger.info("User is navigated to " + newsLetterSubscr_Title.getText() + " Page");

			this.navigation.explicitWaitForElementClickability(this.navigation.getDriver(), newsLetterSubscr_Title);

			WebElement checkbox = this.navigation.chooseElement(WebelementType.ID,
					Constants.ID_NEWSLETTER_CHECKBOX_SUBSCRIPTION);
			checkbox.click();
			WebElement saveBTN = this.navigation.chooseElement(WebelementType.XPATH,
					Constants.XPATH_NEWSLETTER_SAVE_BTN);
			saveBTN.click();

			Thread.sleep(2000);

			WebElement successTXT = this.navigation.chooseElement(WebelementType.XPATH,
					Constants.XPATH_NEWSLETTER_SUBSCRIBED_SUCCESS_MESSAGE);

			logger.log(Status.PASS, successTXT.getText());



			WebElement subscriptionTXT = this.navigation.chooseElement(WebelementType.XPATH,
					Constants.XPATH_NEWSLETTERBOX_CONTENT);

			logger.info(subscriptionTXT.getText());


		} else {

			logger.info(" You are already subscribed to the newsletter ");

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
