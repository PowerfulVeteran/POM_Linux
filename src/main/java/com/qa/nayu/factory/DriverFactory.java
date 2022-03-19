package com.qa.nayu.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.microsoft.edge.seleniumtools.EdgeDriver;
import com.qa.nayu.utils.ErrorMsgs;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Nyra this is BROWSER UTILITY CLASS
 *
 */

public class DriverFactory {
	private static final Logger logs = Logger.getLogger(String.valueOf(DriverFactory.class));
	
	
	public WebDriver driver;
	public Properties prop;
	private OptionsManager optionsmanager;

	public static String highlight = null; // to highlight the webelement with JS
	public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

	public WebDriver init_Driver(String browserName, String browserVersion) {
	
		highlight = prop.getProperty("highlight");
		optionsmanager = new OptionsManager(prop);

		//String browserName = prop.getProperty("browser").trim();
		String url = prop.getProperty("url").trim();

		switch (browserName.toLowerCase()) {

		case "safari":
			
			System.out.println(ErrorMsgs.BROWSER_NOT_FOUND_ERROR);
			System.setProperty("webdriver.safari.verboseLogging", "false");
			WebDriverManager.edgedriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				logs.info("Remote is TRUE..running on GRID...");
				init_remoteDriver(browserName, browserVersion);
			} else {
				logs.info("Remote is FALSE..running on LOCAL...");
				threadLocalDriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
				break;
			}

		case "chrome":
			
			System.setProperty("webdriver.chrome.verboseLogging", "false");
			WebDriverManager.chromedriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				logs.info("Remote is TRUE..running on GRID...");
				init_remoteDriver(browserName, browserVersion);
			} else {
				logs.info("Remote is FALSE..running on LOCAL...");
				threadLocalDriver.set(new ChromeDriver(optionsmanager.getChromeOptions()));
				break;
			}

		case "edge":
			
			System.setProperty("webdriver.edge.verboseLogging", "false");
			WebDriverManager.edgedriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				logs.info("Remote is TRUE..running on GRID...");
				init_remoteDriver(browserName, browserVersion);
			} else {
				logs.info("Remote is FALSE..running on LOCAL...");
				threadLocalDriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
				break;
			}

		case "firefox":
			System.setProperty("webdriver.firefox.verboseLogging", "false");
			WebDriverManager.firefoxdriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				logs.info("Remote is TRUE..running on GRID...");
				init_remoteDriver(browserName, browserVersion);
			} else {
				logs.info("Remote is FALSE..running on LOCAL...");
				threadLocalDriver.set(new FirefoxDriver(optionsmanager.getFirefoxOptions()));
				break;
			}

		default:
			System.out.print("Unexpected Input....");
			break;
		}
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().navigate().to(url);
		return getDriver();

	}

	/**
	 * This method will a copy of current driver state to the calling thread
	 * 
	 * @return
	 */
	public static synchronized WebDriver getDriver() {
		return threadLocalDriver.get();
	}

	/**
	 * 
	 * @return this method will return Properties Object
	 */
	public Properties inti_props() {
		FileInputStream property = null;
		prop = new Properties();

		String env = System.getProperty("env");

		if (env == null) {
			System.out.println("RUNNING ON ENVIRONMENT : ---> PRODUCTION\n");
			try {
				property = new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) {
				System.out.println(ErrorMsgs.PROPERTY_FILE_ABSENT_ERROR);
				e.printStackTrace();
			}
		} else {
			try {
				System.out.println("RUNNING ON ENVIRONMENT : ---> " + env + "\n");
				switch (env) {
				case "qa":
					property = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					property = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "dev":
					property = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				default:
					break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		try {
			prop.load(property);
		} catch (FileNotFoundException e) {
			System.out.println(ErrorMsgs.PROPERTY_FILE_ABSENT_ERROR);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * SCREEEN SHOT CREATION.
	 */

	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;

	}

	private void init_remoteDriver(String browserName, String browserVersion) {

		System.out.println("Running Tests on Remote Grid Server-->" + browserName.toUpperCase());

		if (browserName.equals("chrome")) {
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			
			cap.setCapability("browserName", "chrome");
//			cap.setCapability("browserVersion", browserVersion); //this capabilities are for selenoid setup
//			cap.setCapability("enableVNC", true);
//			cap.setCapability(ChromeOptions.CAPABILITY, optionsmanager.getChromeOptions());
			
			try {
				threadLocalDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browserName.equals("firefox")) {
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability("browserName", "firefox");
//			cap.setCapability("browserVersion", browserVersion);
//			cap.setCapability("enableVNC", true);
//			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsmanager.getFirefoxOptions());

			try {
				threadLocalDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			
		}/* else if (browserName.equals("edge")) {
			DesiredCapabilities cap = DesiredCapabilities.edge();
			cap.setCapability("browserName", "edge");
			cap.setCapability(EdgeOptions.CAPABILITY, optionsmanager.getEdgeOptions());

			try {
				threadLocalDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}*/

	}

}
