package com.qa.nayu.tests;

import com.qa.nayu.base.BaseTest;
import com.qa.nayu.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

	@Test(priority = 1, enabled = true)
	public void loginPageTitleTest() {
		String title = loginpage.getLoginPageTitle();
		System.out.println("Loging page title is:" + title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE_SEARCH_STRING);
	}

	@Test(priority = 2, enabled = true)
	public void getLoginPageUrlTest() {
		String url = loginpage.getLoginPageUrl();
		System.out.println("Loging page Url is:" + url);
		Assert.assertTrue(url.contains(Constants.LOGIN_PAGE_URL_SEARCH_STRING));
	}

	@Test(priority = 3, enabled = true)
	public void forgotPwdLinkTest() {
		Assert.assertTrue(loginpage.isForgotPwdLinkAvailable());
	}

	@Test(priority = 4, enabled = true)
	public void loginTest() {
		loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	@DataProvider
	public Object [] [] Negative_LoginTest_Data() {
		return new Object[][] {{"test@gmail.com","Test@123"},{"test1@gmail.com", "kladf;sd"}};
	}
	
	
	@Test(dataProvider = "Negative_LoginTest_Data", enabled = false)
	public void loginTest_Negative(String un, String pwd) {
		Assert.assertTrue(loginpage.doLogin_Negativ(un,pwd));
	}
}
