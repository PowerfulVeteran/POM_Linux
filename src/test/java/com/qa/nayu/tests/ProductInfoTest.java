package com.qa.nayu.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.nayu.base.BaseTest;

public class ProductInfoTest extends BaseTest {

	SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public void productInfoSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider 
	public Object [] [] searchData() {
		return new Object[] [] {{"iMac"},{"iPhone"},{"Macbook"}};
	}
	
	@Test (dataProvider = "searchData", priority = 0)
	public void ProductCountTest(String productName) {
		searchResPg = accPage.doSearch(productName);
		Assert.assertTrue(searchResPg.getProductResultsCount() > 0);
	}

	@Test( priority = 1)
	public void productInfoTest() {
		searchResPg = accPage.doSearch("MacBook");
		productInfoPage = searchResPg.selectProductFromResults("MacBook");
		Assert.assertEquals(productInfoPage.getProductHeaderText(), "MacBook");
	}

	@Test(priority = 3)
	public void productImagesTest() {
		searchResPg = accPage.doSearch("MacBook");
		productInfoPage = searchResPg.selectProductFromResults("MacBook Pro");
		Assert.assertTrue(productInfoPage.getProductImagesCount() == 4);
	}

	@Test (priority = 4)
	public void getProductInfoTest() {
		searchResPg = accPage.doSearch("MacBook");
		productInfoPage = searchResPg.selectProductFromResults("MacBook Pro");
		Map<String, String> actualProductMetaData = productInfoPage.getProductInformation();
		actualProductMetaData.forEach((k, v) -> System.out.println(k + " : " + v));

		softAssert.assertEquals(actualProductMetaData.get("name"), "MacBook Pro");
		softAssert.assertEquals(actualProductMetaData.get("Brand"), "Apple");
		softAssert.assertEquals(actualProductMetaData.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(actualProductMetaData.get("price"), "$2,000.00");

		softAssert.assertAll();
	}

	@Test(priority = 2)
	public void checkCart() {

		searchResPg = accPage.doSearch("iMac");
		productInfoPage = searchResPg.selectProductFromResults("iMac");
		productInfoPage.addToCart();
		Assert.assertTrue(productInfoPage.checkCart("iMac"), "iMac");
	}
	
}
