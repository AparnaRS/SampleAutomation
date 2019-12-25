package Utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;



public class ExtentReportFactory {
	public static String screenShotFolder = "C://AutomationScreenshots";
	protected static ExtentReports extentReportManager(Logger log, Properties config) {
		ExtentReports extent;
		log.info("Initializing Extent Report");
		extent = new ExtentReports(config.getProperty("reportPath"), true);
		extent.loadConfig(new File("src/main/resources/extent-config.xml"));
		extent.addSystemInfo("Environment", config.getProperty("testEnv"));
		return extent;
	}
	public static void takeScreenShot(WebDriver driver, String screenshotName) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String currentTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
			String filePath = screenShotFolder + File.separator + screenshotName + "_" +currentTimeStamp
					+ ".jpg";

			FileUtils.copyFile(scrFile, new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			;
		} catch (Exception e1) {
			e1.printStackTrace();
			
		}

	}
}
