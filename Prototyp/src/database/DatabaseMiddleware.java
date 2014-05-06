package database;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.rowset.CachedRowSetImpl;

public class DatabaseMiddleware implements Database{
	
	private static Connection connection;
	
	private static Connection getConnection(){
		
		if(connection == null){
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost/prototyp","root","java");
				} catch (SQLException e) {e.printStackTrace();}
		}
		
		return connection;
	}
	
	/**
	 * Use only for queries. Executes the given database-operation.
	 * @param query - Database-operation to execute
	 * @return ResultSet if there is any else return null
	 */
	public CachedRowSetImpl executeQuery(String query){
		CachedRowSetImpl ret = null;
		try{
			ret = new CachedRowSetImpl();
			ret.populate(getConnection().createStatement().executeQuery(query));
		} catch (SQLException e) {e.printStackTrace();}
		
		return ret;
	}
	
	/**
	 * Executes any database-operation.
	 * @param sqlCommand - Database-operation to execute
	 */
	private static void execute(String sqlCommand){
		try{
			getConnection().createStatement().execute(sqlCommand);
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	/**
	 * Use only for new names. Writes information to database and execute needed steps to assign values.
	 * @param data
	 */
	public void recordNewName(String name){		
		
		String tmp = "INSERT INTO namen (Name) VALUES(" + "'" + name + "'" + ");";
		execute(tmp);
	}

	public static void main(String[] args) {
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		
		try{
			String name = "Database";
			Database engine = new DatabaseMiddleware();
			Database stub = (Database) UnicastRemoteObject.exportObject(engine, 0);
			Registry reg = LocateRegistry.getRegistry();
			reg.rebind(name, stub);
			System.out.println("DatabaseEngine bound");
		}catch(Exception e){
			System.err.println("DatabaseEngine exception:");
			e.printStackTrace();
		}
	}
}
