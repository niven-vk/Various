package com.rec.zadanie3;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Zadanie3Application {

	public static void main(String[] args) {

		try {
			resolve();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void resolve() throws SQLException {
		Properties prop = new Properties();

		try (InputStream input = Zadanie3Application.class.getClassLoader()
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
		ExecutorService executor = Executors.newFixedThreadPool(70);
		List<Future<JSONObject>> list = new ArrayList<Future<JSONObject>>();
		try (Connection connection = DriverManager.getConnection(prop.getProperty("db.url"),
				prop.getProperty("db.user"), prop.getProperty("db.password"));
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("SELECT ip FROM ip_in;");) {

			while (rs.next()) {
				list.add(executor.submit(new SendGET(rs.getString("ip"))));
			}
			List<JSONObject> jsonList = new ArrayList<JSONObject>();
			for (Future<JSONObject> result : list) {
				jsonList.add(result.get());
			}

			modifyDB(jsonList, connection);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation successfull");
	}

	private static void modifyDB(List<JSONObject> list, Connection connection) throws SQLException {

		try (PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO ip_out(ip,country,batch_id,t_stamp) VALUES (?,?,?,CURRENT_TIMESTAMP)");

				PreparedStatement preparedStatement2 = connection.prepareStatement(
						"INSERT INTO countries (name,code,code3,emoji) VALUES (?,?,?,?) ON CONFLICT (name) DO NOTHING;");) {

			int i = 1;

			for (JSONObject json : list) {

				preparedStatement.setString(1, json.getString("ip"));
				preparedStatement.setString(2, json.getString("countryName"));
				preparedStatement.setInt(3, i / 500);
				preparedStatement.addBatch();

				preparedStatement2.setString(1, json.getString("countryName"));
				preparedStatement2.setString(2, json.getString("countryCode"));
				preparedStatement2.setString(3, json.getString("countryCode3"));
				preparedStatement2.setString(4, json.getString("countryEmoji"));
				preparedStatement2.addBatch();

				i++;

				if (i % 500 == 0 || i == list.size() + 1) {
					preparedStatement.executeBatch();
					preparedStatement2.executeBatch();
					System.out.println("Batch #" + i / 500 + " complete!");

				}
			}

		} catch (SQLException e) {
			System.out.println("Database modification failed: ");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
