package com.qa.nayu.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.qa.nayu.factory.DriverFactory;
import com.qa.nayu.pages.AccountsPage;
import com.qa.nayu.pages.CommonsPage;
import com.qa.nayu.pages.LoginPage;
import com.qa.nayu.pages.ProductInfoPage;
import com.qa.nayu.pages.RegistrationPage;
import com.qa.nayu.pages.SearchResultPage;

@Listeners ()
public class BaseTest {

	DriverFactory df;

	public WebDriver driver;
	public Properties prop;
	public LoginPage loginpage;
	public AccountsPage accPage;
	public SearchResultPage searchResPg;	
	public ProductInfoPage productInfoPage;
	public RegistrationPage registrationPage;
	public CommonsPage commonsPage;

		
	 // for running class' from test .xml testrunners
	@Parameters({ "browser", "browserversion"})
	@BeforeTest
	public void setUp(String browserName, String browserVersion) {
		df = new DriverFactory();
		prop = df.inti_props();
		prop.setProperty("browser", browserName);
		driver = df.init_Driver(browserName, browserVersion);
		loginpage = new LoginPage(driver);
		commonsPage = new CommonsPage(driver);
		
	}
	
/*
 
	// for running individual class tests without .xml testrunner
	@BeforeTest 
	public void setUp() {
		df = new DriverFactory();
		prop = df.inti_props();
		driver = df.init_Driver(prop.getProperty("browser"), "99");
		loginpage = new LoginPage(driver);
		
	}
*/
	
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

	
	
}
