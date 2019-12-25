package com.aparna.testcases;


import org.testng.annotations.Test;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aparna.Utilities.BaseTest;
import com.aparna.pages.*; 


public class CartTestCases extends BaseTest {
	@BeforeClass
	public static void beforeMethod() throws InterruptedException{
	driver.get(config.getProperty("applicationUrl"));
	Thread.sleep(5000);
	}
	@AfterMethod
	public static void clearCart() throws InterruptedException{
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		rsp.clearCartItems();
		rsp.closeCart();
	}
	
	
	@Test(priority = 1, groups = {"cart"}, description = "Verify that the item for which 'Add to card' was clicked, is getting added to cart.")
	public void verifyCorrectItemGetsAddedToCart() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		LinkedHashMap<String,String> productListed = rsp.getDetailsOfFirstProductDisplayed();
		rsp.addFirstItemToCart();
		Assert.assertEquals(rsp.isCartOpen(), true);
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 1);
		LinkedHashMap<String,String> productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(rsp.areProductDetailsMapsEqual(productListed,productAddedToCart), true);	
		
	}
	
	@Test(priority = 2, groups = {"cart"}, description = "Verify that when same product is added multiple times or removed, quanity is correctly updated")
	public void verifyQuantityGetsUpdatedCorrectly() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		//LinkedHashMap<String,String> productListed = rsp.getDetailsOfFirstProductDisplayed();
		rsp.addFirstItemToCart();
		Assert.assertEquals(rsp.isCartOpen(), true);
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 1);
		LinkedHashMap<String,String> productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(Integer.parseInt(productAddedToCart.get("productQuantity")), 1);
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 1);
		rsp.closeCart();
		rsp.addFirstItemToCart();
		productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(Integer.parseInt(productAddedToCart.get("productQuantity")), 2);
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 2);
		rsp.decrementProductQuantityInCart();
		productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(Integer.parseInt(productAddedToCart.get("productQuantity")), 1);
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 1);
		rsp.increamentProductQuantityInCart();
		productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(Integer.parseInt(productAddedToCart.get("productQuantity")), 2);
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 2);
	}
	
	@Test(priority = 3, groups = {"cart"}, description = "Verify that subtotal gets correctly updated when product quantity is increased or decreased.")
	public void verifySubTotalGetsUpdatedCorrectly() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		LinkedHashMap<String,String> productListed = rsp.getDetailsOfFirstProductDisplayed();
		float productPrice = Float.parseFloat(productListed.get("productPrice").replace("$","").trim());
		rsp.addFirstItemToCart();
		Assert.assertEquals(rsp.isCartOpen(), true);
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 1);
		float subtotal = rsp.getSubtotalForCart();
		Assert.assertEquals(productPrice, subtotal);
		rsp.increamentProductQuantityInCart();
		subtotal = rsp.getSubtotalForCart();
		Assert.assertEquals(productPrice*2, subtotal);
		rsp.decrementProductQuantityInCart();
		subtotal = rsp.getSubtotalForCart();
		Assert.assertEquals(productPrice, subtotal);
	}
	@Test(priority = 4, groups = {"cart"}, description="Verify that clicking on product image does not add product to cart.")
	public void verifyClickingOnProductImageDoesNotAddProductToCart() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		LinkedHashMap<String,String> productListed = rsp.getDetailsOfFirstProductDisplayed();
		rsp.clickOnImageOfFirstProduct();
		Assert.assertEquals(rsp.isCartOpen(), false);
		LinkedHashMap<String,String> productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(rsp.areProductDetailsMapsEqual(productListed,productAddedToCart), false);
		
	}
	
}
