package com.qa.nayu.factory;

import java.util.Properties;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.microsoft.edge.seleniumtools.EdgeOptions;



public class OptionsManager {
	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions ep;

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim()))
			co.addArguments("--headless");
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim()))
			co.addArguments("--incognito");
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim()))
			fo.addArguments("--headless");
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim()))
			fo.addArguments("-private");
		return fo;
	}
	
	
	public EdgeOptions getEdgeOptions() {
		ep = new EdgeOptions ();
		
		if (Boolean.parseBoolean(prop.getProperty("headless").trim()))
			ep.addArguments("--headless");
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim()))
			ep.addArguments("-inprivate");
		return ep;
	}

}
