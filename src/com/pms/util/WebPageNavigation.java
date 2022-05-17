package com.pms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebPageNavigation {

	private static Properties properties;

	static 
	{
		properties = new Properties();
		try 
		{
			properties.load(new FileInputStream("./test.properties"));
		} catch (IOException e) 
		{
			throw new TestPmsException(e);
		}
	}

	private static final Logger logger = Logger.getLogger(WebPageNavigation.class);
	private static WebDriver driver;
	private TestData testData;
	private XLSReader reader;
	private static String browserName;
	private static WebDriverWait wait;

	public WebPageNavigation(TestData testData,String browserName) {
		this.testData = testData;
		WebPageNavigation.browserName = browserName;
	}

	public WebPageNavigation(XLSReader reader,String browserName) {
		this.reader = reader;
		WebPageNavigation.browserName = browserName;
	}
	
	public XLSReader getreader() {
		return reader;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	public TestData getTestData() {
		return testData;
	}

	public WebElement chooseElement(WebelementType webElementType, String path) {

		WebElement webElement;

		switch (webElementType) {
		case ID:
			webElement = driver.findElement(By.id(path));
			break;
		case CLASS_NAME:
			webElement = driver.findElement(By.className(path));
			break;
		case LINK_TEXT:
			webElement = driver.findElement(By.linkText(path));
			break;
		case XPATH:
			webElement = driver.findElement(By.xpath(path));
			break;
		case CSS:
			webElement = driver.findElement(By.cssSelector(path));
			break;
		default:
			throw new TestPmsException("web element type not found");
		}
		return webElement;
	}

	public WebElement findElement(String path) {
		return WebPageNavigation.driver.findElement(By.id(path)); 
	}

	public Select getDropdownValue(WebElement webElement, SelectByType selectByType, String colName, int row) {
		Select select = new Select(webElement);

		switch (selectByType) {
		case VISIBLE_TEXT:
			select.selectByVisibleText(this.testData.readCell(this.testData.getCell(colName), row));
			break;
		case VALUE:
			select.selectByValue(this.testData.readCell(this.testData.getCell(colName), row));
			break;
		default:
			throw new TestPmsException("select by type not found");
		}

		return select;
	}


	public void loginToLc () throws InterruptedException {

		login();
	}


	public void login() throws InterruptedException
	{
		wait = new WebDriverWait(this.getDriver(),90);

		WebElement email = this.chooseElement(WebelementType.ID, "email");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
		email.sendKeys(properties.getProperty("Username"));

		WebElement pass = this.chooseElement(WebelementType.ID, "pass");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("pass")));
		pass.sendKeys(properties.getProperty("Password"));

		WebElement SignIn = this.chooseElement(WebelementType.XPATH, "//button[@name='send']");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='send']")));
		SignIn.click();
	}

	public String getText(String id) 
	{
		return this.findElement(id).getText();
	}

	public void close() {
		//	driver.close();
		driver.quit();
	}

	@SuppressWarnings("deprecation")
	public static String openBrowser(String env) 
	{
		try {

			switch(browserName){

			case"GC":
				//System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
				WebDriverManager.chromedriver().clearResolutionCache().setup();
				HashMap<String, Object> chromePrefs = new HashMap<>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", Constants.USERDIR);
				ChromeOptions options = new ChromeOptions();
				HashMap<String, Object> chromeOptionsMap = new HashMap<>();
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("--test-type");
				options.addArguments("enable-automation"); 
				options.addArguments("--headless"); 
				options.addArguments("--window-size=1920,1080"); 
				options.addArguments("--no-sandbox"); 
				options.addArguments("--disable-extensions"); 
				options.addArguments("--dns-prefetch-disable"); 
				options.addArguments("--disable-gpu");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver();
				driver.get(env);
				driver.manage().deleteAllCookies();
				//Dimension d = new Dimension(1920, 1080);
				driver.manage().window().maximize();

				break;

			case"FF":
				System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");
				ProfilesIni profile = new ProfilesIni();
				FirefoxProfile fxProfile = profile.getProfile("AutomationProfileTest");
				fxProfile.setPreference("javascript.enabled", true);
				FirefoxOptions options1 = new FirefoxOptions();
				options1.setProfile(fxProfile);				
				options1.addPreference("browser.startup.homepage",env);
				//options1.setCapability("marionette", true);
				options1.setCapability("browser.link.open_newwindow", 2);
				options1.setCapability("browser.helperApps.neverAsk.saveToDisk","application/msword");
				options1.setCapability("browser.helperApps.alwaysAsk.force", false);
				options1.setCapability("browser.download.manager.showWhenStarting", false);
				options1.setCapability("browser.download.folderList", 2);
				options1.setCapability("browser.download.dir", Constants.USERDIR);
				driver = new FirefoxDriver(); 
				driver.get(env);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
				break;

			case"Safari":
				driver = new SafariDriver();
				driver.get(env);
				driver.manage().window().maximize();
				break;

			case"IE":
				System.setProperty("webdriver.ie.driver","./IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				driver.get(env);
				driver.manage().window().maximize();
				break;


			default:
				throw new TestPmsException("Please Select Browser (Safari OR FF OR GC)");

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return env;
	}


	public void scrollHandle(WebElement name)
	{
		Point xxx = name.getLocation();
		((JavascriptExecutor)this.getDriver()).executeScript("scroll"+xxx);
	}

	public void windowHandle(int window)
	{
		ArrayList<String> tab = new ArrayList<> (driver.getWindowHandles());
		driver.switchTo().window(tab.get(window));
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {

		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);

		return destination;
	}
	public static String getScreenshotWithoutName(WebDriver driver) throws Exception {

		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);

		return destination;
	}
	public List<WebElement> chooseElements(WebelementType webElementType, String path) {

		List<WebElement> webElement;

		switch (webElementType) {
		case ID:
			webElement = driver.findElements(By.id(path));
			break;
		case CLASS_NAME:
			webElement = driver.findElements(By.className(path));
			break;
		case LINK_TEXT:
			webElement = driver.findElements(By.linkText(path));
			break;
		case XPATH:
			webElement = driver.findElements(By.xpath(path));
			break;
		case CSS:
			webElement = driver.findElements(By.cssSelector(path));
			break;
		default:
			throw new TestPmsException("web element type not found");
		}
		return webElement;
	}

	public void list(By by,String text)
	{
		List<WebElement> listOfCreditCardNum = this.getDriver().findElements(by);

		int count = listOfCreditCardNum.size();

		for(int l=0;l<count;l++)
		{
			int x = listOfCreditCardNum.get(l).getLocation().getX();

			if(x!=0)
			{
				listOfCreditCardNum.get(l).sendKeys(text);;
			}
		}
	}

	public void listClick(By by)
	{
		List<WebElement> listOfCreditCardNum = this.getDriver().findElements(by);

		int count = listOfCreditCardNum.size();

		for(int l=0;l<count;l++)
		{
			int x = listOfCreditCardNum.get(l).getLocation().getX();

			if(x!=0)
			{
				listOfCreditCardNum.get(l).click();
			}
		}
	}

	public void listGetText(By by)
	{
		List<WebElement> listOfCreditCardNum = this.getDriver().findElements(by);

		int count = listOfCreditCardNum.size();

		for(int l=0;l<count;l++)
		{
			int x = listOfCreditCardNum.get(l).getLocation().getX();

			if(x!=0)
			{
				String nam = listOfCreditCardNum.get(l).getText();
			}
		}
	}


	public String getDataFromPropertiesFile(String key)
	{
		String value = properties.getProperty(key);
		return value;
	}

	public void customWaitAndClickElement(WebElement element) throws InterruptedException 
	{
		int count = 0;
		while (count < 90) {
			try {
				element.click();
				break;
			} catch (Throwable e) 
			{
				Thread.sleep(500);
				count++;
			}
		}}
	public void actions(WebDriver driver , WebElement element)
	{
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
	}
	
	public void explicitWaitForElementClickability(WebDriver driver, WebElement element) 
	{
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void storeSelection()
	{
		WebElement bocaLiquor = this.chooseElement(WebelementType.XPATH, Constants.XPATH_BOCA_LIQ_STORE);
		bocaLiquor.click();

		Set<String> ids = this.getDriver().getWindowHandles();
		Iterator<String> iterator = ids.iterator();
		String parentID = iterator.next();
		String childID = iterator.next();

		this.getDriver().switchTo().window(childID);
	}
}
