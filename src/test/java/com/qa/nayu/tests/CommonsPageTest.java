package com.qa.nayu.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.nayu.base.BaseTest;
import com.qa.nayu.utils.Constants;

public class CommonsPageTest extends BaseTest {
		

	@Test
	public void parentMenuTest() {
		List<String> actualMenuList = commonsPage.getMenuLinksList();
		System.out.println(actualMenuList);
		Assert.assertEquals(actualMenuList, Constants.COMMONS_PAGE_Expected_parentmenueList);
	}

	@DataProvider
	public Object[][] menuData() {
		return new Object[][] { { "Components", "Monitors" }, 
								{ "Desktop", "PC" } };
	}

	@Test(dataProvider = "menuData")
	public void selectMonitorsTest(String parentMenu, String subMenu) {
		commonsPage.selectSubMenu(parentMenu, subMenu);
	}

}
