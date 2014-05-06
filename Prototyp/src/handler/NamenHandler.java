package handler;


import java.rmi.RemoteException;
import javax.faces.bean.ManagedBean;
import database.DBUtil;



@ManagedBean
public class NamenHandler {

	private String name;
		
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Insert data into database. Return next page.
	 * @return ausgabe.xhtlm 
	 */
	public String speichern(){
		try{
			DBUtil.getStub().recordNewName(getName());
		}catch(RemoteException e){
			e.printStackTrace();
		}
		return "/ausgabe.xhtml";
	}
	

	
}

