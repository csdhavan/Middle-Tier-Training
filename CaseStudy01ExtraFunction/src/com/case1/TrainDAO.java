package com.case1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TrainDAO {
	final String DRIVER_NAME = "oracle.jdbc.OracleDriver";
	final String DB_URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	final String USERNAME = "hr";
	final String PASSWORD = "hr";
	
	public Train findTrain(int trainNo) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER_NAME);
		Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		Statement statement = connection.createStatement();
		
		String query = "SELECT * FROM TRAINS WHERE TRAIN_NO = " + trainNo;
		ResultSet trainSet = statement.executeQuery(query);
		boolean status = trainSet.next();
		
		try {
			if(status) {
				String trainName = trainSet.getString(2);
				String source = trainSet.getString(3);
				String destination = trainSet.getString(4);
				double ticketPrice = trainSet.getDouble(5);
				return new Train(trainNo, trainName, source, destination, ticketPrice);
			}
		}catch(Exception e) {
			System.out.println("Train with given train number does not exist");
			connection.close();
			return null;
		}
		connection.close();
		return null;
	}
}