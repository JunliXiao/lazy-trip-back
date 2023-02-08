package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.ServiceLocator;

class ConnectTestDemo {
	public static void main(String[] args) {
		// MySQL 8之後連線URL需加上SSL與時區設定
//		String url = "jdbc:mysql://localhost:1433?useUnicode=yes&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Taipei";
		// MySQL 8之前
		// String url = "jdbc:mysql://localhost:3306";
//		String user = "root";
//		String password = "plato014";

//		String driverClass = "com.mysql.cj.jdbc.Driver";
//		try {
//			// JDBC4.0之前載入JDBC Driver的方式，現在可以省略
//			Class.forName(driverClass);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			return;
//		}
		

//		try (Connection connection = DriverManager.getConnection(url, user, password)) {
//			System.out.println("Connecting to MySQL successfully!!");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		// Test connection to my GCP MySQL database
		DataSource dataSource = ServiceLocator.getInstance().getDataSource();
		
		try (Connection connection = dataSource.getConnection()) {
			System.out.println("Connecting to MySQL successfully!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}