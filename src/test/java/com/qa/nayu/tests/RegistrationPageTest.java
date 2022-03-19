package com.qa.nayu.tests;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.nayu.base.BaseTest;
import com.qa.nayu.utils.Constants;
import com.qa.nayu.utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest {

	@BeforeClass
	public void setupRegister () {
		registrationPage = loginpage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[] [] getRegisterData () {
		Object regData[] [] = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	/*
	@Test (enabled = false)
	// this was used before using ExcelUtil
	public void userRegistrationTest () {
		Assert.assertTrue(registrationPage.accountRegistration("Meet", "Patel", 
						"meetpate223@gmail.com", "232325689", "Meetp@123", "yes"));
	}
	*/
	public String getRandomEmail () {
		Random randomGenerator = new Random();
		String email = "TestDataEmailId" + randomGenerator.nextInt(10000) + "@testmail.com";
		return email;
	}
	
	
	
	
	@Test (dataProvider = "getRegisterData")  //variable name's sequence same as in excel sheet 
	public void userRegistrationTest(String firstname, String lastname, 
									 String telephone, String password, String subscribe 	) {
		
		Assert.assertTrue(
				registrationPage.accountRegistration(firstname, lastname, 
													getRandomEmail(), telephone, 
													password, subscribe));
														
	}

}
