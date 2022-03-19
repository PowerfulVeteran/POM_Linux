package com.qa.nayu.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.nayu.utils.ElementUtil;

public class ProductInfoPage {

	private ElementUtil elementUtil;
	private WebDriver driver;

	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails li img");
	private By productMetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
	private By productPriceData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");

	private By quantity = By.id("input-quantity");
	private By addToCartButton = By.id("button-cart");
	private By cartButton = By.xpath("//div[@id='cart']//button[@data-toggle='dropdown']");
	private By cartSuccessMessage = By.xpath("//div [@class='alert alert-success alert-dismissible']");

//	constructor(s):
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}

	// Page class Methods:

	public String getProductHeaderText() {
		return elementUtil.doGetText(productHeader);
	}

	public int getProductImagesCount() {
		return elementUtil.getElements(productImages).size();
	}

	public Map<String, String> getProductInformation() {
		Map<String, String> productInfoMap = new HashMap<String, String>();
		productInfoMap.put("name", getProductHeaderText());

		List<WebElement> metaDataList = elementUtil.getElements(productMetaData);
		System.out.println("Total Product MetaData: " + metaDataList.size());
		// for meta data:
		for (WebElement e : metaDataList) {

			String meta[] = e.getText().split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productInfoMap.put(metaKey, metaValue);

		}
		// price:
		List<WebElement> priceList = elementUtil.getElements(productPriceData);
		String price = priceList.get(0).getText().trim();
		String ExPrice = priceList.get(1).getText().trim();

		productInfoMap.put("price", price);
		productInfoMap.put("ExTaxPrice", ExPrice);

		return productInfoMap;
	}

	public void selectQuantity(String qty) {
		elementUtil.doSendKeys(quantity, qty);
	}

	public void addToCart() {
		elementUtil.waitForElementToBeClickable(10, addToCartButton).click();

	}

	public String getSuccessMessage() {
		return elementUtil.doGetText(cartSuccessMessage);
	}

	public boolean checkCart(String productName) {
		elementUtil.doClick(cartButton);
		return elementUtil
				.waitForElementToBeClickable(10,
						By.xpath("//div[@id='cart']//a [contains(text(),'" + productName + "')]"))
				.getText().equals(productName);
	}

}
