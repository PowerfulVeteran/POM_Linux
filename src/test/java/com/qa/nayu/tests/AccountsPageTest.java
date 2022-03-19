package com.qa.nayu.tests;

import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.nayu.base.BaseTest;
import com.qa.nayu.utils.Constants;
import com.qa.nayu.utils.ErrorMsgs;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class AccountsPageTest extends BaseTest {

	@BeforeClass //this annotation's priority is 3, therefore it will be invoked AFTER BaseTest's @BeforeTest annotation.
	public void accPageSetup () {
		accPage = loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@Description("Account Page Title Test...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 0, enabled = true)
	public void accountPageTitleTest() {
		String title = accPage.getAccountPageTitle();
		System.out.println("Account Page Title is:"+title);
		Assert.assertEquals(title, Constants.ACCOUNTS_PAGE_TITLE,ErrorMsgs.ACCOUNT_PAGE_TITLE_ERROR);
	}
	
	@Test
	@Description("Account Page Logo Visiblity Test...")
	@Severity(SeverityLevel.MINOR)
	public void accountPageLogoTest () {
		Assert.assertTrue(accPage.getAccountPageLogoHeader().isDisplayed());
	}
	
	@Test
	@Description("Account Page section header test...")
	@Severity(SeverityLevel.MINOR)
	public void accountPageSectionsTest () {
		List <String> secList = accPage.getAccountPageSectionsList();
		secList.stream().forEach(e->System.out.println(e));
		Collections.sort(Constants.ACCOUNTS_PAGE_Expected_sectionList);
		Assert.assertEquals(secList, Constants.ACCOUNTS_PAGE_Expected_sectionList);
	}
	
	@Test
	@Description("Account Page LougOut Link availablity Test...")
	@Severity(SeverityLevel.CRITICAL)
	public void accountPageLogoutLinkTest() {
		Assert.assertTrue(accPage. isAccountPageLogoutLinkDisplayed(), ErrorMsgs.ACCOUNT_PAGE_LOGOUT_LINK_ABSENT_ERROR);
	}
	
	@Test
	public void accountPageUrlTest () {
		Assert.assertTrue(accPage.getAccountPageUrl().contains(Constants.ACCOUNTS_PAGE_URL),ErrorMsgs.ACCOUTN_PAGE_URL_ERROR);
		
	}
	
	
	
}
