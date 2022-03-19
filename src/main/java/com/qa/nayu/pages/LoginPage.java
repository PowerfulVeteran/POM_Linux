package com.qa.nayu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.nayu.utils.Constants;
import com.qa.nayu.utils.ElementUtil;

import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;


@Epic("Epic 431: Design LoginPage of DemoCart app.")
@Story("US 1203: Develop a separate environment for 'qa'. ")
public class LoginPage {
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	//By locators:
	private By username = By.xpath("//input[@id='input-email']");
	private By password = By.xpath("//input[@id='input-password']");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.xpath("//div[@class='form-group']//a[text()='Forgotten Password']");
	private By registerLink = By.linkText("Register");
	private By loginErrorMsg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	//constructors:
	public LoginPage (WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	//Public Page Action Methods:
	
	@Step("Getting Title...")
	public String getLoginPageTitle() {
		return elementUtil.waitForTitleContains(5, Constants.LOGIN_PAGE_TITLE_SEARCH_STRING);
	}
	@Step("Verifying url...")
	public String getLoginPageUrl() {
		return elementUtil.getPageUrl();
	}
	@Step("Verifying Password Link's Presence...")
	public Boolean isForgotPwdLinkAvailable() {
		return elementUtil.doIsDisplayed(forgotPwdLink);
	}
	@Step("Authanticating ueser with UserName : {0} and Password : {1}")
	public AccountsPage doLogin(String un, String pwd) {
		elementUtil.doSendKeys(username, un);
		elementUtil.doSendKeys(password, pwd);;
		elementUtil.doClick(loginButton);
		
		return new AccountsPage(driver);
	}
	
	public boolean doLogin_Negativ(String un, String pwd) {
		elementUtil.doSendKeys(username, un);
		elementUtil.doSendKeys(password, pwd);;
		elementUtil.doClick(loginButton);
		return elementUtil.doIsDisplayed(loginErrorMsg);
		
	}
	
	@Step("Navigating to Registration page...")
	public RegistrationPage navigateToRegisterPage () {
		elementUtil.doActionsClick(registerLink);
		return new RegistrationPage(driver);
	}
		
	
}
