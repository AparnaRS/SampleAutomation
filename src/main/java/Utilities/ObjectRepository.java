package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ObjectRepository {
	
	protected static Properties loadObjectRepository(Logger log) {
		File file;
		FileInputStream fileInput = null;
		Properties prop = new Properties();
		log.info("Reading/Loading object repository property file");
		file = new File("src/main/resources/objects.properties");
		try {
			fileInput = new FileInputStream(file);
			prop.load(fileInput);
		} catch(FileNotFoundException e) {
			throw new RuntimeException("File " + "src/main/resources/objects.properties" + " file.\n" + e.getStackTrace().toString());
		} catch(IOException  e) {
			throw new RuntimeException("Could not read " + "src/main/resources/objects.properties" + " file.\n" + e.getStackTrace().toString());
		}
		return prop;
	}	
}
