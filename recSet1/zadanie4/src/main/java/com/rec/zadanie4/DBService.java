package com.rec.zadanie4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import emoji4j.EmojiUtils;

public class DBService {

	public static String get_ip() {
		String result = "";
		try (Connection connection = DriverManager.getConnection(
				CurrentProperties.getInstance().getProperties().getProperty("db.url"),
				CurrentProperties.getInstance().getProperties().getProperty("db.user"),
				CurrentProperties.getInstance().getProperties().getProperty("db.password"));
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("SELECT * FROM ip_out;");) {
			while (rs.next()) {
				result += rs.getString("ip") + " " + rs.getString("batch_id") + " " + rs.getString("t_stamp") + " "
						+ rs.getString("country") + "<br/>";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return result;
	}

	public static String getCountries() {
		String result = "";
		try (Connection connection = DriverManager.getConnection(
				CurrentProperties.getInstance().getProperties().getProperty("db.url"),
				CurrentProperties.getInstance().getProperties().getProperty("db.user"),
				CurrentProperties.getInstance().getProperties().getProperty("db.password"));
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("SELECT * FROM countries;");) {
			while (rs.next()) {
				result += rs.getString("name") + " " + rs.getString("code") + " " + rs.getString("code3") + " "
						+ rs.getString("emoji") + "<br/>";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return EmojiUtils.hexHtmlify(result);
	}

	public static String get_report() {
		String result = "";
		try (Connection connection = DriverManager.getConnection(
				CurrentProperties.getInstance().getProperties().getProperty("db.url"),
				CurrentProperties.getInstance().getProperties().getProperty("db.user"),
				CurrentProperties.getInstance().getProperties().getProperty("db.password"));
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(
						"SELECT  batch_id,ip_in.ip IS NOT NULL AND ip_out.ip IS NOT NULL as status,t_stamp,ip_in.ip as ipi, ip_out.ip as ipo,country "
								+ "FROM ip_in FULL OUTER JOIN ip_out ON ip_in.ip=ip_out.ip;");) {

			while (rs.next()) {
				result += rs.getString("batch_id") + " " + rs.getString("status") + " " + rs.getString("t_stamp") + " "
						+ ((rs.getString("ipi") == null) ? rs.getString("ipo") : rs.getString("ipi")) + " "
						+ rs.getString("country") + "<br/>";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return result;
	}

	public static String add_row(String ip, String batch_id, String country, String code, String code3, String emoji) {

		try (Connection connection = DriverManager.getConnection(
				CurrentProperties.getInstance().getProperties().getProperty("db.url"),
				CurrentProperties.getInstance().getProperties().getProperty("db.user"),
				CurrentProperties.getInstance().getProperties().getProperty("db.password"));
				PreparedStatement preparedStatement = connection.prepareStatement(
						"INSERT INTO ip_out(ip,country,batch_id,t_stamp) VALUES (?,?,?,CURRENT_TIMESTAMP)");
				PreparedStatement preparedStatement2 = connection.prepareStatement(
						"INSERT INTO countries (name,code,code3,emoji) VALUES (?,?,?,?) ON CONFLICT (name) DO NOTHING;");) {
			preparedStatement.setString(1, ip);
			preparedStatement.setString(2, country);
			preparedStatement.setInt(3, Integer.parseInt(batch_id));
			preparedStatement.addBatch();

			preparedStatement2.setString(1, country);
			preparedStatement2.setString(2, code);
			preparedStatement2.setString(3, code3);
			preparedStatement2.setString(4, emoji);
			preparedStatement2.addBatch();
			preparedStatement.executeBatch();
			preparedStatement2.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return "DB insertion failed";
		}
		return "DB insertion succeded";
	}

}
