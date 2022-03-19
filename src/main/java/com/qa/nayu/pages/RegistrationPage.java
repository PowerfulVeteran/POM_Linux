package com.qa.nayu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.qa.nayu.utils.Constants;
import com.qa.nayu.utils.ElementUtil;

public class RegistrationPage {

	private ElementUtil elementUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");

	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input");

	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private By sucessMessg = By.cssSelector("div#content h1");

	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	// constructor:
	public RegistrationPage(WebDriver driver) {
		elementUtil = new ElementUtil(driver);
	}

	public boolean accountRegistration(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		elementUtil.doSendKeys(this.firstName, firstName);
		elementUtil.doSendKeys(this.email, email);
		elementUtil.doSendKeys(this.password, password);
		elementUtil.doSendKeys(this.lastName, lastName);
		elementUtil.doSendKeys(this.telephone, telephone);
		elementUtil.doSendKeys(this.confirmpassword, password);
		
		if (subscribe.equals("yes")) {
			elementUtil.doClick(subscribeYes);
		}else {
			elementUtil.doClick(subscribeNo);
		}
		elementUtil.doClick(agreeCheckBox);
		elementUtil.doClick(continueButton);
		
		//next page:
		
		String msg = elementUtil.waitForElementVisibility(sucessMessg, 7).getText();
		if (msg.contains(Constants.REGISTRATION_SUCCESS)) {
			elementUtil.doClick(logoutLink);
			elementUtil.doClick(registerLink);
			return true;
		}
		else {
			return false;
		}
	}

}
