package Pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.BasePageObject;
import Utilities.BaseTest;




public class ReactShoppingPage extends BasePageObject<ReactShoppingPage> {
	public ReactShoppingPage(WebDriver driver, Logger log, Properties prop, Properties config,
			JavascriptExecutor jse) {
		super(driver, log, prop, config, jse);
		// TODO Auto-generated constructor stub
	}
	
	private By shelfItems = By.xpath(prop.getProperty("shelfItems"));
	private By countOfProductsDisplayed = By.xpath(prop.getProperty("countOfProductsDisplayed"));
	private By closedCartItemsCount=By.xpath(prop.getProperty("closedCartItemsCount"));
	private By productsDisplayed=By.xpath(prop.getProperty("productsDisplayed"));
	private By cartOpen=By.xpath(prop.getProperty("cartOpen"));
	private By openedCartItemsCount=By.xpath(prop.getProperty("openedCartItemsCount"));		
	private By productAddedToCart=By.xpath(prop.getProperty("productAddedToCart"));	
	private By removeItemFromCart=By.xpath(prop.getProperty("removeItemFromCart"));	
	private By closeCart = By.xpath(prop.getProperty("closeCart"));
	private By imageOfProducts = By.xpath(prop.getProperty("imageOfProducts"));
	private By cartSubtotal = By.xpath(prop.getProperty("cartSubtotal"));
	private By sizeFilterAll = By.xpath(prop.getProperty("sizeFilterAll"));
	private By selectSort = By.xpath(prop.getProperty("selectSort"));
	private By allPrices = By.xpath(prop.getProperty("allPrices"));
	
	public void selectSortOption(String sortOption) throws InterruptedException{
		selectDropdownValue(selectSort, sortOption);
		Thread.sleep(5000);
	}
	
	public boolean isSortedLowestToHighest(){
		boolean isSorted=true;
		List<WebElement> allPriceElements = findElements(allPrices);
		for(int i=0;i<allPriceElements.size()-1;i++){
			float priceOfI = Float.parseFloat(allPriceElements.get(i).getText().replace("$", ""));
			float priceOfI_Next = Float.parseFloat(allPriceElements.get(i+1).getText().replace("$", ""));
			if (!(priceOfI <= priceOfI_Next)){
				isSorted=false;
			}
		}
		return isSorted;
	}
	public boolean isSortedHighestToLowest(){
		boolean isSorted=true;
		List<WebElement> allPriceElements = findElements(allPrices);
		for(int i=0;i<allPriceElements.size()-1;i++){
			float priceOfI = Float.parseFloat(allPriceElements.get(i).getText().replace("$", ""));
			float priceOfI_Next = Float.parseFloat(allPriceElements.get(i+1).getText().replace("$", ""));
			if (!(priceOfI >= priceOfI_Next)){
				isSorted=false;
			}
		}
		return isSorted;
	}
	public void applyFilter(String size) throws InterruptedException{
		 By filter = By.xpath("//input[@type='checkbox'][@value='"+size+"']");
		 click(filter);
		 Thread.sleep(2000);
	}
	public boolean isFilterSelected(String size) throws InterruptedException{
		 By filter = By.xpath("//input[@type='checkbox'][@value='"+size+"']");
		 WebElement filterEle = find(filter);
		 return filterEle.isSelected();
	}
	
	public void unselectAllFilters() throws InterruptedException{
		List<WebElement> allFilters = findElements(sizeFilterAll);
		for (int i=0;i<allFilters.size();i++){
			if(allFilters.get(i).isSelected()){
				click(allFilters.get(i));
				Thread.sleep(1000);
			}
		}
 	}
	
	public int getCountOfInventoryForSize(String size){
		String inventoryForSize = BaseTest.inventory.getProperty(size);
		if(inventoryForSize.equals("")){
			return 0;
		}else if(!inventoryForSize.contains(",")){
			return 1;
		}else{
		String[] productList = inventoryForSize.split(",");
		return productList.length;
		}
	}
	
	
	
	public float getSubtotalForCart(){
		return Float.parseFloat(getText(cartSubtotal).replace("$", ""));
	}
	public void clickOnImageOfFirstProduct(){
		WebElement firstProductImage = getFirstChild(imageOfProducts);
		click(firstProductImage);
	}
	
	public void closeCart() throws InterruptedException{
		Thread.sleep(2000);
		click(closeCart);
		Thread.sleep(5000);
	}
	
	public void openPage() {
		log.info("Navigating to " + config.getProperty("testEnv") + " " + "URL");
		getPage(config.getProperty("applicationUrl"));
	}
	public int getCountOfProducts(){
		int countOfProducts = getCountOfAllChildElements(shelfItems);
		log.info("Total number of products found are: "+countOfProducts);
		return countOfProducts;
	}
	public int getCountOfProductsDisplayed(){
		String countDisplay = getText(countOfProductsDisplayed);
		countDisplay = countDisplay.replace(" Product(s) found.", "");
		int count = Integer.parseInt(countDisplay);
		log.info("Count of products displayed is: "+count);
		return count;
	}
	
	public int getClosedCartItemsCount(){
		return Integer.parseInt(getText(closedCartItemsCount));
	}
	public int getOpenedCartItemsCount(){
		return Integer.parseInt(getText(openedCartItemsCount));
	}
	public LinkedHashMap<String,String> getDetailsOfFirstProductDisplayed(){
		LinkedHashMap<String,String> productDetails = new LinkedHashMap<String,String>();
		WebElement firstProduct =getFirstChild(productsDisplayed);
		String productName = getText(firstProduct.findElement(By.tagName("p")));
		productDetails.put("productName",productName);
		String productPrice = getText(firstProduct.findElement(By.xpath(".//div[@class='val']")));
		productDetails.put("productPrice",productPrice);
		String productInstallment = getText(firstProduct.findElement(By.xpath(".//div[@class='installment']")));
		productInstallment = productInstallment.replace("or ","");
		productInstallment=productInstallment.replaceAll(" ", "");
		productDetails.put("productInstallment", productInstallment);
		return productDetails;
	}
	
	public LinkedHashMap<String,String> getDetailsOfProductAddedToCart(){
		LinkedHashMap<String,String> productDetails = new LinkedHashMap<String,String>();
		WebElement productAddedToCartElement = getFirstChild(productAddedToCart);
		String nameOfFirstProductAddedToCart = productAddedToCartElement.findElement(By.xpath(".//p[@class='title']")).getText();
		productDetails.put("productName", nameOfFirstProductAddedToCart);
		String productPrice = productAddedToCartElement.findElement(By.xpath(".//p[@class='sub-price__val']")).getText();
		productPrice = productPrice.replace(" ", "");
		productDetails.put("productPrice", productPrice);
		String productInstallment = productAddedToCartElement.findElement(By.xpath(".//small[@class='sub-price__installment']")).getText();
		productInstallment=productInstallment.replace("OR UP TO ","");
		productInstallment=productInstallment.replaceAll(" ", "");
		productDetails.put("productInstallment", productInstallment);
		String productQuantity = productAddedToCartElement.findElement(By.xpath(".//p[@class='desc']")).getText();
		productQuantity=productQuantity.substring(productQuantity.indexOf("Quantity: ")+10, productQuantity.length());
		productDetails.put("productQuantity", productQuantity);
		return productDetails;
	}
	public void decrementProductQuantityInCart() throws InterruptedException{
		WebElement productAddedToCartElement = getFirstChild(productAddedToCart);
		WebElement buttonDecrement = productAddedToCartElement.findElement(By.xpath(".//button[@class='change-product-button'][text()='-']"));
		click(buttonDecrement);
		Thread.sleep(3000);
	}
	public void increamentProductQuantityInCart() throws InterruptedException{
		WebElement productAddedToCartElement = getFirstChild(productAddedToCart);
		WebElement buttonIncreament = productAddedToCartElement.findElement(By.xpath(".//button[@class='change-product-button'][text()='+']"));
		click(buttonIncreament);
		Thread.sleep(3000);
	}
	public void addFirstItemToCart() throws InterruptedException{
		//LinkedHashMap<String,String> productDetails = new LinkedHashMap<String,String>();
		WebElement firstProduct = getFirstChild(productsDisplayed);
		WebElement addToCartFirstProduct = firstProduct.findElement(By.xpath(".//div[text()='Add to cart']"));
		click(addToCartFirstProduct);
		Thread.sleep(5000);
	}
	
	public boolean areProductDetailsMapsEqual(LinkedHashMap<String,String> map1, LinkedHashMap<String,String> map2){
		return map1.get("productName").equals(map2.get("productName")) && map1.get("productPrice").equals(map2.get("productPrice")) && map1.get("productInstallment").equals(map2.get("productInstallment"));
	//return map1==map2;
	}
	public boolean isCartOpen() throws InterruptedException{
		Thread.sleep(3000);
		return isElementDisplayed(cartOpen);
	}
	public void clearCartItems() throws InterruptedException{
		List<WebElement> allClearIcons = findElements(removeItemFromCart);
		int totalItems = allClearIcons.size();
		for (int i=0;i<totalItems;i++){
			click(allClearIcons.get(i));
			Thread.sleep(5000);
		}
	}
	
}
