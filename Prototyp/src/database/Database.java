package database;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;


/**
 * Interface for RMI
 */
public interface Database extends Remote{
	
	
	/**
	 * Use only for queries. Executes the given database-operation.
	 * @param query - Database-operation to execute
	 * @return ResultSet if there is any else return null
	 */
	public ResultSet executeQuery(String query) throws RemoteException;
	
	/**
	 * Use only for new names. Writes information to database and execute needed steps to assign values.
	 * @param data
	 */
	public void recordNewName(String name) throws RemoteException;
	
}
