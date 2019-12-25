package com.aparna.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TestConfiguration {
	
	protected static Properties loadTestConfigurations(String testEnv,Logger log) {
		File file;
		FileInputStream fileInput = null;
		Properties config = new Properties();
		log.info("Reading/Loading test configurations");
		file = new File("src/main/resources/config.properties");
		try {
			fileInput = new FileInputStream(file);
			config.load(fileInput);
			if(testEnv.equalsIgnoreCase("qa2") || testEnv.equalsIgnoreCase("qa1")){
				config.setProperty("env", "http://"+testEnv+".app.invoke.com");
				config.setProperty("envDashboard", "https://"+testEnv+".app.invoke.com/a/ui/dashboard/");
				config.setProperty("testEnv", testEnv);
			}else if (testEnv.equalsIgnoreCase("staging") || testEnv.equalsIgnoreCase("prod")){
				config.setProperty("env", "https://"+testEnv+".app.invoke.comt");
				config.setProperty("envDashboard", "https://"+testEnv+".app.invoke.com/a/ui/dashboard/");
				config.setProperty("testEnv", testEnv);
			}
			
		} catch(FileNotFoundException e) {
			throw new RuntimeException("File " + "src/main/resources/config.properties" + " file.\n" + e.getStackTrace().toString());
		} catch(IOException  e) {
			throw new RuntimeException("Could not read " + "src/main/resources/config.properties" + " file.\n" + e.getStackTrace().toString());
		}
		return config;
	}	
	
	protected static Properties loadInventory() {
		File file;
		FileInputStream fileInput = null;
		Properties config = new Properties();
		
		file = new File("src/main/resources/inventoryManagement.properties");
		try {
			fileInput = new FileInputStream(file);
			config.load(fileInput);
		
			
		} catch(FileNotFoundException e) {
			throw new RuntimeException("File " + "src/main/resources/inventoryManagement.properties" + " file.\n" + e.getStackTrace().toString());
		} catch(IOException  e) {
			throw new RuntimeException("Could not read " + "src/main/resources/inventoryManagement.properties" + " file.\n" + e.getStackTrace().toString());
		}
		return config;
	}	
	
}
