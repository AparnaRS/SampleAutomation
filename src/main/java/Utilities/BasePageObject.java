package Utilities;

import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject<T> {
	protected WebDriver driver;
	protected Logger log;
	protected Properties prop;
	protected Properties config;
	protected JavascriptExecutor jse;

	protected BasePageObject(WebDriver driver, Logger log, Properties prop, Properties config, JavascriptExecutor jse) {
		this.driver = driver;
		this.log = log;
		this.prop = prop;
		this.config = config;
		this.jse = jse;
	}
	
	@SuppressWarnings("unchecked")
	protected T getPage(String url) {
		driver.get(url);
		return (T) this;
	}
	
	protected void openNewTab() throws InterruptedException {
		Thread.sleep(2000);
		((JavascriptExecutor)driver).executeScript("window.open();");
		Thread.sleep(2000);
		
	}

	protected void typeInput(String keyInput, By element) {
		clearText(element);
		find(element).sendKeys(keyInput);
	}

	protected void sendKeys(String keyInput, By element) {
		WebElement testElement = driver.findElement(element);

		jse.executeScript("arguments[0].style.backgroundColor = '#A80000'",testElement);
		find(element).sendKeys(keyInput);
		
	}
	protected WebElement find(By element) {
		return driver.findElement(element);
	}
	
	protected List<WebElement> findElements(By element) {
		return driver.findElements(element);
	}
	protected int getCountOfAllChildElements(By element) {
		 List<WebElement> allChilds = findElements(element);
		return allChilds.size();
	}
	
	protected WebElement getFirstChild(By element){
		 List<WebElement> allChilds = findElements(element);
			return allChilds.get(0);
	}
	protected void click(By element) {
		try{
			find(element).click();
	}catch(Exception e){
		WebElement webElement = driver.findElement(element);
		//((Locatable) webElement).getCoordinates().inViewPort();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", webElement);
	}
	}
	
		protected void click(WebElement element) {
			try{
				element.click();
		}catch(Exception e){
			
			
		}	
	}
	protected boolean clickElementWhenReady(By element) throws InterruptedException {
		
		int attempts = 0;
		while (attempts < 4) {
			try {
				find(element).click();
				break;
			} catch (Exception e) {
				if (attempts==3){
					e.printStackTrace();	
				}
				Thread.sleep(3000);
				attempts++;
			}
			
		}
		if(attempts==3){
			return false;
		}else{
			return true;
		}
	}
	
	
	protected void waitForVisibilityOfElement(By locator, Integer attemptMax) throws InterruptedException {
		Integer attempts = 0;
	
		while (attempts < attemptMax) {
			try {
				Thread.sleep(1000);
				//waitFor(ExpectedConditions.visibilityOfElementLocated(locator), (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
				//waitFor(ExpectedConditions.presenceOfElementLocated(locator), (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
				isElementDisplayed(locator);
				break;
			} catch (StaleElementReferenceException e) {
				if(attempts == attemptMax-1){
					e.printStackTrace();
				}
				attempts++;
			}catch(Exception e1){
				if(attempts ==attemptMax-1){
					e1.printStackTrace();
				}
				attempts++;
			}
			
			
		}
	}
	
	private void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
		timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : Integer.parseInt(config.getProperty("defaultExplicitWait"));
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(condition);
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
	protected String getText(By element) {
		return find(element).getText();
	
	}
	protected String getText(WebElement element) {
		return element.getText();
	
	}
	protected void clearText(By element) {
		int len = find(element).getAttribute("value").length(); 
		for(int i = 0; i < len; i++ ) {
			find(element).sendKeys(Keys.BACK_SPACE);
		}
	}
	
	protected void clickMouseHoverLink(By elementMenu, By elementSubMenu) {
		Actions action = new Actions(driver);
		action.moveToElement(find(elementMenu)).perform();
		action.moveToElement(find(elementSubMenu));
		action.click();
		action.perform();
	}
	
	protected boolean getElementStatus(By element) {
		return find(element).isEnabled();
	}
	
	protected boolean isButtonDisabled(By element){
		WebElement button = find(element);
		String classes = button.getAttribute("class");
		boolean isDisabled = classes.contains("btn btn-disabled");
		return isDisabled;
	}
	
	protected boolean isElementDisplayed(By element) throws InterruptedException {
		return find(element).isDisplayed();
	}
	
	protected boolean isElementSelected(By element) {
		return find(element).isSelected();
	}
	
	protected String getElementAttributeValue(By element, String attribute) {
		return find(element).getAttribute(attribute);
	}
	
	protected void tabOut(By element) {
		find(element).sendKeys(Keys.TAB);
	}
	
	protected void selectDropdownValue(By element, String value) {
		Select drpMediaType = new Select(find(element));
		drpMediaType.selectByValue(value);
	}
}
