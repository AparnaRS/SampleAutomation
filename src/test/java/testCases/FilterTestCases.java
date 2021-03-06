package testCases;


import org.testng.annotations.Test;

import Pages.*;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Utilities.BaseTest; 


public class FilterTestCases extends BaseTest {
	@BeforeMethod
	public static void beforeMethod() throws InterruptedException{
	driver.get(config.getProperty("applicationUrl"));
	Thread.sleep(5000);
	}
	@AfterMethod
	public static void unselectAllSelectedFilters() throws InterruptedException{
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		rsp.unselectAllFilters();
	}
	
	@Test(priority = 5, groups = {"cart"}, description="Verify cart is empty by default")
	public void verifyDefaultCartState() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		Assert.assertEquals(rsp.getClosedCartItemsCount(),0);
	}
	@Test(priority = 6, groups = {"filters"}, description="Verify count of products displayed is correct")
	public void verifyCountOfElementsIsCorrect() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		int countOfProducts = rsp.getCountOfProducts();
		int countDisplayed = rsp.getCountOfProductsDisplayed();
		Assert.assertEquals(countOfProducts, countDisplayed, "Failed: Product count does not match the number of products listed");
	}
	
	
	@Test(priority = 7, groups = {"filters"}, description="Verify filter when exactly 1 product matches filter criteria")
	public void verifyfilterWhenOnlyOneMatchingProduct() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		rsp.applyFilter("XS");
		Assert.assertEquals(rsp.isFilterSelected("XS"), true,"Failed: Filter not applied on clicking");
		int countOfProducts = rsp.getCountOfProducts();
		int countDisplayed = rsp.getCountOfProductsDisplayed();
		Assert.assertEquals(countOfProducts, countDisplayed, "Failed: Count of products shown does not match the actual number of products displayed");
		int countInInventory = rsp.getCountOfInventoryForSize("XS");
		Assert.assertEquals(countOfProducts, countInInventory, "Failed: Number of products listed does not match with inventory count");
	}
	
	@Test(priority = 8, groups = {"filters"}, description ="Verify filter when no product matches filter criteria")
	public void verifyFilterWhenNoMatchingProduct() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		rsp.applyFilter("ML");
		Assert.assertEquals(rsp.isFilterSelected("ML"), true, "Failed: Filter not applied on clicking");
		int countOfProducts = rsp.getCountOfProducts();
		int countDisplayed = rsp.getCountOfProductsDisplayed();
		Assert.assertEquals(countOfProducts, countDisplayed, "Failed: Count of products shown does not match the actual number of products displayed");
		int countInInventory = rsp.getCountOfInventoryForSize("ML");
		Assert.assertEquals(countOfProducts, countInInventory,  "Failed: Number of products listed does not match with inventory count");
	}
	@Test(priority = 9, groups = {"filters"}, description = "Verify filter when multiple products matches filter criteria")
	public void verifyFilterWhenMultipleMatchingProduct() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		rsp.applyFilter("L");
		Assert.assertEquals(rsp.isFilterSelected("L"), true, "Failed: Filter not applied on clicking");
		int countOfProducts = rsp.getCountOfProducts();
		int countDisplayed = rsp.getCountOfProductsDisplayed();
		Assert.assertEquals(countOfProducts, countDisplayed, "Failed: Count of products shown does not match the actual number of products displayed");
		int countInInventory = rsp.getCountOfInventoryForSize("L");
		Assert.assertEquals(countOfProducts, countInInventory, "Failed: Number of products listed does not match with inventory count");
	}

	
}
