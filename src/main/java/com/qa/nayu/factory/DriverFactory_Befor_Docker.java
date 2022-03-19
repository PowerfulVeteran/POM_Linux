package com.qa.nayu.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.microsoft.edge.seleniumtools.EdgeDriver;
import com.qa.nayu.utils.ErrorMsgs;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Nyra this is BROWSER UTILITY CLASS
 *
 */

public class DriverFactory_Befor_Docker {

	public WebDriver driver;
	Properties prop;
	private OptionsManager optionsmanager;

	public static String highlight = null; // to highlight the webelement with JS
	public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

	public WebDriver init_Driver(Properties prop) {

		highlight = prop.getProperty("highlight");
		optionsmanager = new OptionsManager(prop);

		String browserName = prop.getProperty("browser").trim();
		String url = prop.getProperty("url").trim();
		/**
		 * boolean invalid = true; Scanner input=null; byte browser=0; System.out.
		 * print("1) SAFARI\n2) CHROME\n3) EDGE\n4) FIREFOX\nInput a Browser Number to
		 * Launch it:" ); while(invalid) { input = new Scanner(System.in); try { browser
		 * = input.nextByte();// Read user input if(browser>0 && browser <5) { invalid
		 * =false; } else { invalid=true; System.out.printf("INVALID INPUT, please try
		 * again $>"); //System.out.printf("\""+browser+"\""+" is a INVALID INPUT,
		 * please retry $>" ); } } catch (Exception e) { invalid = true;
		 * System.out.printf("INVALID INPUT, please try again $>"); } }
		 * 
		 * input.close();
		 */
		switch (browserName.toLowerCase()) {
		case "safari":
			System.out.println(ErrorMsgs.BROWSER_NOT_FOUND_ERROR);
			WebDriverManager.edgedriver().setup();
			// driver = new EdgeDriver(optionsmanager.getEdgeOptions());
			threadLocalDriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
			break;
		case "chrome":
			// System.setProperty("webdriver.chrome.driver",
			// "G:\\Programming\\Soft\\chromedriver\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver(optionsmanager.getChromeOptions());
			threadLocalDriver.set(new ChromeDriver(optionsmanager.getChromeOptions()));
			break;
		case "edge":
			// System.setProperty("webdriver.edge.driver",
			// "G:\\Programming\\Soft\\edgedriver\\EdgeDriver.exe");
			WebDriverManager.edgedriver().setup();
			// driver = new EdgeDriver(optionsmanager.getEdgeOptions());
			threadLocalDriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			// driver = new FirefoxDriver(optionsmanager.getFirefoxOptions());
			threadLocalDriver.set(new FirefoxDriver(optionsmanager.getFirefoxOptions()));
			break;

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
				System.out.println("RUNNING ON ENVIRONMENT : ---> "+env+"\n") ;
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
	
	public String getScreenshot () {
		File src = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		
		String path = System.getProperty("user.dir")+"/screenshots/"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	
	}
		
}
