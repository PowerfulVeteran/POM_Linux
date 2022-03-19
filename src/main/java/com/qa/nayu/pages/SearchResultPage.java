package com.qa.nayu.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.qa.nayu.utils.ElementUtil;

public class SearchResultPage {
	private WebDriver driver;
	ElementUtil elementUtil;

	private By SearchResults = By.xpath("//div [@class = 'product-thumb']");
	private By resultsItems = By.cssSelector("div.product-thumb h4 a");
	private By cartBtn = By.xpath(
			"//div [@class = 'caption']/h4//a[text()='HP LP3065']/../parent::div[@class='caption']/following-sibling::div[@class='button-group']//span[text()='Add to Cart']");
	public By mainCartContent = By.xpath("// div [@id ='cart'] //a[text()='MacBook']");
	public By mainCartBtn = By.xpath("// div [@id ='cart'] //button");

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);

	}

	public int getProductResultsCount() {
		return elementUtil.getElements(SearchResults).size();
	}

	public ProductInfoPage selectProductFromResults(String productName) {
		List<WebElement> searchResultList = elementUtil.getElementsList(resultsItems, 10);
		System.out.println("Total items displayed for \"" + productName + "\":" + searchResultList.size());

		for (WebElement e : searchResultList) {
			if (e.getText().equals(productName)) {
				e.click();
				break;
			}
		}
		return new ProductInfoPage(driver);
	}

	public void addToCart(String productName) {
		List<WebElement> searchResultList = elementUtil.getElementsList(resultsItems, 10);
		for (WebElement e : searchResultList) {
			if (e.getText().equals(productName)) {
				cartBtn = By.xpath("//div [@class = 'caption']/h4//a[text()='" + productName
						+ "']/../parent::div[@class='caption']/following-sibling::div[@class='button-group']//span[text()='Add to Cart']");
				elementUtil.getElement(cartBtn).click();
				break;
			} else {
				System.out.println("Produnct Not found");
				break;
			}
		}

	}

}
