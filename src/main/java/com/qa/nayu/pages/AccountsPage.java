package com.qa.nayu.pages;

import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.nayu.utils.Constants;
import com.qa.nayu.utils.ElementUtil;

public class AccountsPage {

	private ElementUtil elementUtil;
	private WebDriver driver;

	// private By locators:
	private By acc_SectionHeaders = By.cssSelector("div#content h2");
	private By logoHeader = By.cssSelector("div#logo a");
	private By logoutLink = By.linkText("Logout");
	private By serchfield = By.xpath("//div [@id='search']/input[@name='search']");
	private By searchButton = By.cssSelector("div #search button[type='button']");

	// constructor(s):
	public AccountsPage(WebDriver driver) {
		elementUtil = new ElementUtil(driver);
		this.driver = driver;
	}

	// page action methods:
	public String getAccountPageTitle() {
		return elementUtil.waitForTitle(5, Constants.ACCOUNTS_PAGE_TITLE);
	}

	public String getAccountPageUrl() {
		return elementUtil.getPageUrl();
	}

	public WebElement getAccountPageLogoHeader() {
		return elementUtil.getElement(logoHeader);
	}

	public Boolean isAccountPageLogoutLinkDisplayed() {
		return elementUtil.getElement(logoutLink).isDisplayed();
	}

	public List<String> getAccountPageSectionsList() {
		List<String> secsList = elementUtil.getElementsTextList(acc_SectionHeaders, 5);
		Collections.sort(secsList);
		return secsList;
	}

	// search Method:
	public SearchResultPage doSearch(String productName) {
		System.out.println("Searching " + productName + "...");
		elementUtil.doActionsSendKeys(serchfield, productName);
		elementUtil.doActionsClick(searchButton);
		return new SearchResultPage(driver);
	}

}
