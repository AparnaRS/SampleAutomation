package com.aparna.Utilities;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFactory {
	
	protected static String DATA_FOLDER_PATH = "Drivers";
	public static WebDriver getDriver(String browser, Logger log) throws MalformedURLException {
	//	String downloadFilepath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test_data" 
	//+ File.separator + "DownloadReport";
		WebDriver driver;
		log.info("Starting " + browser + " driver");
		
		switch (browser) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", DATA_FOLDER_PATH+File.separator+"geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		
		case "chrome":
			
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			//chromePrefs.put("download.default_directory", downloadFilepath);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
			driver = new ChromeDriver();
			break;	
			
		case "edge":
			System.setProperty("webdriver.edge.driver", DATA_FOLDER_PATH+File.separator+"MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			break;	
		case "headless":
			
			//==============
			
			HashMap<String, Object> chromePrefs1 = new HashMap<String, Object>();
			chromePrefs1.put("profile.default_content_settings.popups", 0);
			//chromePrefs1.put("download.default_directory", downloadFilepath);
			ChromeOptions options1 = new ChromeOptions();
			options1.setExperimentalOption("prefs", chromePrefs1);
			DesiredCapabilities cap1 = DesiredCapabilities.chrome();
			cap1.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap1.setCapability(ChromeOptions.CAPABILITY, options1);
			//WebDriver driver = new ChromeDriver(cap);
			//==================
			System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
			//ChromeOptions options = new ChromeOptions();
	        options1.addArguments("headless","disable-gpu","no-sandbox","--window-size=1325x744");
	         driver = new ChromeDriver(options1);
			break;
			
		case "firefox_headless":
			FirefoxBinary firefoxBinary = new FirefoxBinary();
		    firefoxBinary.addCommandLineOptions("--headless");
		    System.setProperty("webdriver.gecko.driver", DATA_FOLDER_PATH+File.separator+"geckodriver.exe");
		    FirefoxOptions firefoxOptions = new FirefoxOptions();
		    firefoxOptions.setBinary(firefoxBinary);
		    driver = new FirefoxDriver(firefoxOptions);
		    driver.manage().window().maximize();
		break;
	
		
		default:
			System.setProperty("webdriver.chrome.driver", DATA_FOLDER_PATH+File.separator+"chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		//driver.manage().window().maximize();
		return driver;
	}
	public static String getChromeDriverPath() {
		String OS = System.getProperties().getProperty("os.name").toLowerCase();
		System.out.println("OS is : "+OS);
		if (OS.contains("windows")) {
			String CHROME_PATH = DATA_FOLDER_PATH + File.separator + "chromedriver.exe";

			return CHROME_PATH;
		}  else if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ){
			String CHROME_PATH = DATA_FOLDER_PATH + File.separator + "chromedriver_linux";

			return CHROME_PATH;
		}  else if (OS.contains("mac")) {
			String CHROME_PATH = DATA_FOLDER_PATH + File.separator + "chromedriver_mac";

			return CHROME_PATH;
		}else {
				String CHROME_PATH = DATA_FOLDER_PATH + File.separator + "chromedriver_mac";
				return CHROME_PATH;
		}
		
	}
	
	public static String getFirefoxDriverPath() {
		String OS = System.getProperties().getProperty("os.name").toLowerCase();
		System.out.println("OS is : "+OS);
		if (OS.contains("windows")) {
			String FIREFOX_PATH = DATA_FOLDER_PATH + File.separator + "geckodriver.exe";

			return FIREFOX_PATH;
		}  else if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ){
			String FIREFOX_PATH = DATA_FOLDER_PATH + File.separator + "geckodriver_linux";

			return FIREFOX_PATH;
		}  else if (OS.contains("mac")) {
			String FIREFOX_PATH = DATA_FOLDER_PATH + File.separator + "geckodriver_mac";

			return FIREFOX_PATH;
		}else {
				String FIREFOX_PATH = DATA_FOLDER_PATH + File.separator + "geckodriver_mac";
				return FIREFOX_PATH;
		}
		
	}
}
