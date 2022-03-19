package com.qa.nayu.utils;


import java.util.Arrays;
import java.util.List;

public class Constants {

	public static final String LOGIN_PAGE_TITLE_SEARCH_STRING = "Account Login";
	public static final String LOGIN_PAGE_URL_SEARCH_STRING = "account/login";
	
	public static final String ACCOUNTS_PAGE_TITLE = "My Account";
	public static final String ACCOUNTS_PAGE_URL = "account/account";
	public static List<String> ACCOUNTS_PAGE_Expected_sectionList = Arrays
																	.asList("My Account",
																			"My Orders",
																			"My Affiliate Account",
																			"Newsletter");
	
	public static List<String> COMMONS_PAGE_Expected_parentmenueList = Arrays
			.asList("Desktops", "Laptops & Notebooks", "Components", "Tablets", "Software", "Phones & PDAs", "Cameras", "MP3 Players");
	
	public static final String REGISTRATION_SUCCESS = "Your Account Has Been Created!";
	
	//***SHEET NAMES***************:
	
	public static final String REGISTER_SHEET_NAME =  "nayu_TestData";
	
	
	
	
	
	
	
}
