package com.aparna.testcases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aparna.Utilities.BaseTest;
import com.aparna.pages.*; 


public class SortTestCases extends BaseTest {
	@BeforeMethod
	public static void beforeMethod() throws InterruptedException{
	driver.get(config.getProperty("applicationUrl"));
	Thread.sleep(5000);
	}
	
	
	@Test(priority = 10, groups = {"sort"}, description = "Verify sort by price Lowest to Highest")
	public void verifySortFromLowestToHighestPrice() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		rsp.selectSortOption("lowestprice");
		Assert.assertEquals(rsp.isSortedLowestToHighest(), true);
	}
	

	@Test(priority = 11, groups = {"sort"}, description = "Verify sort by price Highest to Lowest")
	public void verifySortHighestToLowestPrice() throws InterruptedException {
		ReactShoppingPage rsp = new ReactShoppingPage(driver, log, prop, config, jse);
		rsp.selectSortOption("highestprice");
		Assert.assertEquals(rsp.isSortedHighestToLowest(), true);
	}
	
}
