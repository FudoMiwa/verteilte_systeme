package handler;


import javax.faces.bean.ManagedBean;

import database.Database;



@ManagedBean
public class NamenHandler {

	private long nameID;
	private String name;
	Database db = new Database();
	
	public long getNameID(){
		return this.nameID;
	}
	
	public void setNameID(long nameID){
		this.nameID = nameID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	public String speichern(){
		db.recordNewName(getName());
		return "/ausgabe.xhtml";
	}
	

	
}
