package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
	// protected static WebDriver driver;
	// private static ActionDriver actionDriver;

	// Creating Thread local variables for WebDriver and ActionDriver
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();

	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

	// Load the Configuration File
	@BeforeSuite
	public void loadConfig() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		logger.info("Config.properties files loaded");

		// Start the Extent Report
		// ExtentManager.getReporter(); -- This has been implemented in TestListener
	}

	private synchronized void launchBrowser() {
		// Initialize the WebDriver Based on Browser defined in config.properties file
		String browser = prop.getProperty("browser");
		if (browser.equalsIgnoreCase("Chrome")) {
			// driver = new ChromeDriver();
			driver.set(new ChromeDriver()); // New Changes as per Thread
			ExtentManager.registerDriver(getDriver());
			logger.info("ChromeDriver Initialized");
		} else if (browser.equalsIgnoreCase("FireFox")) {
			// driver = new FirefoxDriver();
			driver.set(new FirefoxDriver()); // New Changes as per Thread
			ExtentManager.registerDriver(getDriver());
			logger.info("FirefoxDriver Initialized");
		} else if (browser.equalsIgnoreCase("Edge")) {
			// driver = new EdgeDriver();
			driver.set(new EdgeDriver()); // New Changes as per Thread
			ExtentManager.registerDriver(getDriver());
			logger.info("Edge Initialized");
		} else {
			throw new IllegalArgumentException("Browser Not supported: " + browser);
		}
	}

	/*
	 * This is for configuring Browser settings like implicit wait Maximize Browser
	 * and navigating to url
	 */
	private void configBrowser() {
		// implicit wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait)); // New Changes as per Thread

		// Maximize the Browser
		getDriver().manage().window().maximize(); // New Changes as per Thread

		// Navigate to URL
		try {
			getDriver().get(prop.getProperty("url")); // New Changes as per Thread
		} catch (Exception e) {
			System.out.println("Failed to Navigate to URL: " + e.getMessage());
		}
	}

	@BeforeMethod(alwaysRun = true)
	public synchronized void setup() throws IOException {
		System.out.println("Setting up WebDriver for:" + this.getClass().getSimpleName());
		launchBrowser();
		configBrowser();
		staticWait(2);

		// Sample logger message
		logger.info("WebDriver Initialized and Browser Maximized");
		logger.trace("This is a Trace message");
		logger.error("This is a error message");
		logger.debug("This is a debug message");
		logger.fatal("This is a fatal message");
		logger.warn("This is a warm message");

		/*
		 * // Initialize the ActionDriver instance if (actionDriver == null) {
		 * actionDriver = new ActionDriver(driver);
		 * logger.info("Action Driver Instance is created."); }
		 */

		// Initialize the ActionDriver instance with ThreadLocal
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("Action Driver initalized for current thread " + Thread.currentThread().getId());

	}

	@AfterMethod(alwaysRun = true)
	public synchronized void tearDown() {
		if (getDriver() != null) { // New Changes as per Thread
			try {
				getDriver().quit(); // New Changes as per Thread
			} catch (Exception e) {
				System.out.println("Unable to Quit the Browser: " + e.getMessage());
			}
		}
		logger.info("WebDriver instance is closed");
		driver.remove(); // New Changes as per Thread
		actionDriver.remove(); // New Changes as per Thread
		// driver = null;
		// actionDriver = null;
		// ExtentManager.endTest(); -- This is implemented in TestListener
	}

	/**
	 * //Driver Getter method public WebDriver getDriver() { return driver; }
	 **/
	/**
	 * // Getter Method for WebDriver public static WebDriver getDriver() {
	 * 
	 * if (driver.get() == null) { // New Changes as per Thread
	 * System.out.println("WebDriver instance is null"); throw new
	 * IllegalStateException("WebDriver instance is null"); } return driver.get(); }
	 * 
	 * // Getter Method for Action Driver public static ActionDriver
	 * getActionDriver() {
	 * 
	 * if (actionDriver.get() == null) { // New Changes as per Thread
	 * System.out.println("actionDriver instance is null"); throw new
	 * IllegalStateException("actionDriver instance is null"); } return
	 * actionDriver.get(); }
	 **/

	// Getter Method for WebDriver
	public static WebDriver getDriver() {
		return driver.get(); // return null if not initialized
	}

	// Getter Method for Action Driver
	public static ActionDriver getActionDriver() {
		return actionDriver.get();
	}

	// Driver Setter Method
	public void setDriver(ThreadLocal<WebDriver> driver) { // New Changes as per Thread
		this.driver = driver;
	}

	// Static Wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

	// Prop Getter Method
	public static Properties getProp() {
		return prop;
	}

}
