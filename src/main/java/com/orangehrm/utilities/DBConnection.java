package com.orangehrm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/orangehrm";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";

	public static Connection getDBConnection() {
		try {
			System.out.println("Starting DB Connection...");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println("DB Connection setup successfully");
			return conn;
		} catch (SQLException e) {
			System.out.println("Got error in DB connection...");
			e.printStackTrace();
			return null;
		}
	}
	//Get the employee details from database and store in map
	public static Map<String, String> getEmployeeDetails(String employee_id){
		String query="SELECT emp_firstname, emp_middle_name, emp_lastname from hs_hr_employee Where employee_id="+employee_id;
		
		Map<String, String> employeeDetails=new HashMap<>();
		
		try(Connection conn= getDBConnection();
			 Statement stmt= conn.createStatement();
			 ResultSet rs=stmt.executeQuery(query);){
			
			System.out.println("Executing Query: "+query);
			
			if(rs.next()) {
				String firstName=rs.getString("emp_firstname");
				String middleName= rs.getString("emp_middle_name");
				String lastName= rs.getString("emp_lastname");
				
				//store in map
				employeeDetails.put("First_name", firstName);
				employeeDetails.put("Middle_name", middleName!=null? middleName:" ");
				employeeDetails.put("Last_name", lastName);
				
				System.out.println("Query executed.");
				System.out.println("Employee details found");
			}
			else {
				System.out.println("Employee details not found");
			}
		}catch(Exception e) {
			System.out.println("Error execting in query..");
			e.getStackTrace();
		}
		return employeeDetails;
	}

}
