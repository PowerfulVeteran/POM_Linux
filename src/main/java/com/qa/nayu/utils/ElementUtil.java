package com.qa.nayu.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.nayu.factory.DriverFactory;

public class ElementUtil {
	private static final Logger logs = Logger.getLogger(String.valueOf(ElementUtil.class));
	private WebDriver driver;
	JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	public WebElement getElement(By locator) {
		logs.info("Locator is-->"+ locator);
		WebElement element = driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		logs.info("Element is-->"+ element);
		return element;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doSendKeys(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);

	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doGetText(By locator) {
		return waitForElementPresence(locator, 5).getText();
	}

	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public List<String> getElementsTextList(By locator) {
		List<String> elementTextList = new ArrayList<String>();
		List<WebElement> eList = getElements(locator);

		for (WebElement e : eList) {
			if (!e.getText().isEmpty()) {
				elementTextList.add(e.getText());
			}
		}

		return elementTextList;
	}

	public List<String> getElementAttributeList(By locator, String attrName) {
		List<String> attrList = new ArrayList<String>();
		List<WebElement> elementList = getElements(locator);

		for (WebElement e : elementList) {
			attrList.add(e.getAttribute(attrName));
		}

		return attrList;
	}

	/* DROPDOWN UTIL */
	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doDeselectall(By locator) {
		Select select = new Select(getElement(locator));
		select.deselectAll();
	}

	public void doSelectDropDownVal(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionList = select.getOptions();
		for (WebElement e : optionList) {
			String temp = e.getText();
			if (temp.equals(value)) {
				e.click();
				break;
			}
		}

	}

	public void doSelectDropDownWithoutSelect(By locator, String value) {

		List<WebElement> optionList = getElements(locator);
		for (WebElement e : optionList) {
			if (e.getText().equals(value)) {
				e.click();
				break;
			}
		}

	}

	public void selectChoiceJquery(By locator, String... select) {
		if (select == null) {
			return;
		}

		List<WebElement> dropDownList = getElements(locator);
		if (!select[0].equalsIgnoreCase("all")) {

			for (int i = 0; i < (dropDownList.size()); i++) {
				String val = dropDownList.get(i).getText();

				for (int j = 0; j < select.length; j++) {
					if (val.equals(select[j])) {
						dropDownList.get(i).click();
						break;
					}

				}
			}

		} else {// select all //try catch to deal ElementNotInteractableException.

			try {
				for (WebElement e : dropDownList) {
					e.click();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	public WebDriver doSwitchWindows(WebDriver driver, String WinID) {

		driver.switchTo().window(WinID);

		return driver;

	}

	public void doActionsSendKeys(By locator, String val) {
		Actions act = new Actions(driver);
		WebElement ele = getElement(locator);
		ele.clear();
		act.sendKeys(ele, val).perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}

	public void doActionsClick(WebElement element) {
		Actions act = new Actions(driver);
		act.click(element).perform();
	}

	public void doActionsDoubleClick(By locator) {
		Actions act = new Actions(driver);
		act.doubleClick(getElement(locator)).perform();
	}

	public void doActionsMoveToElement(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).perform();
	}
	// *******************************wait utils******************************
	// WebDriverWait:

	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * This method is specifically for tagName
	 * 
	 * @param timeOut
	 * @param locator
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By elementsLocation, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementsLocation));
	}

	public List<WebElement> WaitForElementsVisibility(By elementsLocation, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementsLocation));
	}

	public WebElement waitForElementVisibility(By elementsLocation, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		WebElement ele = driver.findElement(elementsLocation);
		return wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public void printElementsText(By locator, int timeOut) {
		waitForElementsPresence(locator, timeOut).stream().forEach(ele -> System.out.println(ele.getText()));
	}

	public void printListElements(List<String> eleList) {
		eleList.forEach(ele -> System.out.println(ele));
	}

	public List<String> getElementsListWithText(By locator, int timeOut, String filterVal) {
		return waitForElementsPresence(locator, timeOut).stream().filter(ele -> ele.getText().contains(filterVal))
				.map(ele -> ele.getText()).collect(Collectors.toList());
	}

	public List<String> getElementsListWithText(By locator, int timeOut) {
		return waitForElementsPresence(locator, timeOut).stream().map(ele -> ele.getText())
				.collect(Collectors.toList());
	}

	public List<WebElement> getElementsList(By locator, int timeOut, String filterVal) {
		return waitForElementsPresence(locator, timeOut).stream().filter(ele -> ele.getText().contains(filterVal))
				.collect(Collectors.toList());
	}

	public List<WebElement> getElementsList(By locator, int timeOut) {

		return waitForElementsPresence(locator, timeOut).stream().collect(Collectors.toList());

	}

	public List<String> getElementsTextList(By elementsLocation, int timeOut) {
		List<String> elementsTxt = new ArrayList<String>();
		List<WebElement> elements = WaitForElementsVisibility(elementsLocation, timeOut);

		for (WebElement e : elements) {
			if (!e.getText().isEmpty()) {
				elementsTxt.add(e.getText());
			}
		}
		return elementsTxt;
	}

	public WebElement waitForElementToBeClickable(int timeOut, By elementLocation) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.elementToBeClickable(elementLocation));
	}

	public WebElement waitForElementToBeClickable(int timeOut, WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.elementToBeClickable(ele));
	}

	public void clickWhernReady(int timeOut, By elementLocation) {
		waitForElementToBeClickable(timeOut, elementLocation).click();
	}

	public Alert waitForAlertPresence(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		return waitForAlertPresence(timeOut).getText();
	}

	public void acceptAlert(int timeOut) {
		waitForAlertPresence(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForAlertPresence(timeOut).dismiss();
	}

	public String waitForTitle(int timeOut, String titletxt) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.titleIs(titletxt));
		return (driver.getTitle());
	}

	public String waitForTitle(int timeOut, String titletxt, int callingtime) {
		WebDriverWait wait = new WebDriverWait(driver, 10, callingtime);// calling time milliseconds
		wait.until(ExpectedConditions.titleIs(titletxt));
		return (driver.getTitle());
	}

	// polling time is the time interval at which selenium will request to look for
	// title
	public String waitForTitleContains(int timeOut, String partialtxt, int pollingtime) {
		WebDriverWait wait = new WebDriverWait(driver, 10, pollingtime);// polling time milliseconds
		wait.until(ExpectedConditions.titleContains(partialtxt));
		return (driver.getTitle());
	}

	public String waitForTitleContains(int timeOut, String partialtxt) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.titleContains(partialtxt));
		return (driver.getTitle());
	}

	public boolean waitForUrl(int timeOut, String urlFragmentTxt) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.urlContains(urlFragmentTxt));
	}

	public String getPageUrl() {

		return driver.getCurrentUrl();
	}

	public WebDriver waitForFrame(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	public WebDriver waitForFrame(int timeOut, String frameNameOrId) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
	}

	public WebDriver waitForFrame(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public WebDriver waitForFrame(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	// FluentWait:

	public WebElement waitForElementWithFluentWait(By locator, int timeOut, long pollingTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	public List<WebElement> waitForElementsWithFluentWait(By locator, int timeOut, long pollingTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	public Alert waitForAlertWithFluentWait(int timeOut, long pollingTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(pollingTime)).ignoring(NoAlertPresentException.class);

		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public WebDriver waitForFrameWithFluentWait(int timeOut, long pollingTime, By frameLocation) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(pollingTime)).ignoring(NoSuchFrameException.class);

		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocation));

	}

	public void doLogin(String role) {
		String creds[] = getCredentialsMap(role).split(":");
		System.out.println(creds[0] + " : " + creds[1]);
		By email = By.id("input-email");
		By password = By.id("input-password");
		By loginButton = By.xpath("//input[@value='Login']");

		driver.findElement(email).clear();
		driver.findElement(email).sendKeys(creds[0].trim());

		driver.findElement(password).clear();
		driver.findElement(password).sendKeys(creds[1].trim());

		driver.findElement(loginButton).click();

	}

	public String getCredentialsMap(String role) {
		Map<String, String> credMap = new HashMap<String, String>();
		credMap.put("admin", "admin@g.com:admin123");
		credMap.put("seller", "seller@g.com:seller123");
		credMap.put("vendor", "vendor@g.com:vendor123");
		credMap.put("customer", "customer@g.com:cust123");
		credMap.put("partner", "partner@g.com:partner123");
		credMap.put("distributor", "distributor@g.com:distributor123");

		if (credMap.containsKey(role)) {
			return credMap.get(role);
		}
		return null;
	}
	/* DATE/CALENDAR UTILS */

	/* DATE/CALENDAR UTILS */

}
