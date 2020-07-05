package com.rec.zadanie4;

import java.io.InputStream;
import java.util.Properties;

public class CurrentProperties {

	private static final CurrentProperties INSTANCE = new CurrentProperties();
	Properties prop = new Properties();

	private CurrentProperties() {

		try (InputStream input = Zadanie4Application.class.getClassLoader()
				.getResourceAsStream("application.properties")) {

			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				return;
			}

			prop.load(input);

		} catch (Exception e) {
			System.out.println("Property access failed: ");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static CurrentProperties getInstance() {
		return INSTANCE;
	}

	public Properties getProperties() {
		return prop;
	}
}
