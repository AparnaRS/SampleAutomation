package testCases;


import org.testng.annotations.Test;

import Pages.*;

import java.util.LinkedHashMap;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import Utilities.BaseTest; 


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
		Assert.assertEquals(rsp.isCartOpen(), true, "Failed: Cart not open after adding product to cart.");
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 1, "Failed: product count in cart not correct");
		LinkedHashMap<String,String> productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(Integer.parseInt(productAddedToCart.get("productQuantity")), 1, "Failed: product quantity in cart not correct");
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 1);
		rsp.closeCart();
		rsp.addFirstItemToCart();
		productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(Integer.parseInt(productAddedToCart.get("productQuantity")), 2, "Failed: product quantity in cart not correct after '+'");
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 2);
		rsp.decrementProductQuantityInCart();
		productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(Integer.parseInt(productAddedToCart.get("productQuantity")), 1, "Failed: product quantity in cart not correct after '-'");
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 1);
		rsp.increamentProductQuantityInCart();
		productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(Integer.parseInt(productAddedToCart.get("productQuantity")), 2, "Failed: product quantity in cart not correct after '+'");
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 2);
	}
	
	@Test(priority = 3, groups = {"cart"}, description = "Verify that subtotal gets correctly updated when product quantity is increased or decreased.")
	public void verifySubTotalGetsUpdatedCorrectly() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		LinkedHashMap<String,String> productListed = rsp.getDetailsOfFirstProductDisplayed();
		float productPrice = Float.parseFloat(productListed.get("productPrice").replace("$","").trim());
		rsp.addFirstItemToCart();
		Assert.assertEquals(rsp.isCartOpen(), true, "Failed: Cart not open after adding product to cart.");
		Assert.assertEquals(rsp.getOpenedCartItemsCount(), 1, "Failed: product count in cart not correct");
		float subtotal = rsp.getSubtotalForCart();
		Assert.assertEquals(productPrice, subtotal, "Failed: subtotal in cart does not match product price");
		rsp.increamentProductQuantityInCart();
		subtotal = rsp.getSubtotalForCart();
		Assert.assertEquals(productPrice*2, subtotal, "Failed: subtotal in cart does not match when product quantity increased");
		rsp.decrementProductQuantityInCart();
		subtotal = rsp.getSubtotalForCart();
		Assert.assertEquals(productPrice, subtotal, "Failed: subtotal in cart does not match when product quantity decreased");
	}
	@Test(priority = 4, groups = {"cart"}, description="Verify that clicking on product image does not add product to cart.")
	public void verifyClickingOnProductImageDoesNotAddProductToCart() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		LinkedHashMap<String,String> productListed = rsp.getDetailsOfFirstProductDisplayed();
		rsp.clickOnImageOfFirstProduct();
		Assert.assertEquals(rsp.isCartOpen(), false, "Failed: Cart should not be open on clicking on product.");
		LinkedHashMap<String,String> productAddedToCart = rsp.getDetailsOfProductAddedToCart();
		Assert.assertEquals(rsp.areProductDetailsMapsEqual(productListed,productAddedToCart), false, "Failed: product should not have been added to cart on clicking on ptroduct image.");
		
	}
	
}
