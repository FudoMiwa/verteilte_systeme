package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	Connection connection;
	PreparedStatement ps;
	ResultSet result;
	
	//Methode zur Verbindungsherstellung
	private Connection OpenConnection(){
		
	    Connection connection = null;
	    
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/prototyp","root","java");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
		System.out.println(connection);
	    return connection;	
	}
	
	public void recordNewName(String name){		
		try{
			Connection connection = OpenConnection();
			connection.createStatement().execute("INSERT INTO namen VALUES(''"+ ",'" + name + "')");
		}catch(Exception e){
			System.out.println("Error to insert data into database:" + e);
		}
		
		finally{		 	
			try {
				connection.close();		
			}catch (SQLException e) {	
				System.out.println("Error to closing connection:" + e);	
			}
		}
	}

	public static void main(String[] args){

	}
}
