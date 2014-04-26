package ubung3.uuid;

import java.util.UUID;

public class CheckUUID {
	
	
	/**
	 * Generiert eine zufaellige oder pseudozufaellige UUID
	 * @return UUID Typ 4
	 */
	public static UUID getUUIDRandom(){
		return UUID.randomUUID();
	}
	
	/**
	 * Generiert eine namensbasierte UUID (MD5-gehasht)
	 * @param String
	 * @return UUID Typ 3
	 */
	public static UUID getUUIDName(String name){
		return UUID.nameUUIDFromBytes((name).getBytes());
	}
	
	/**
	 * Prueft UUID auf Wohlgeformtheit
	 * @param UUID
	 * @throws Exception
	 */
	public static void validateUUID(UUID in) throws Exception{	
		String checkUUID = new String(in.toString());
		if(!(checkUUID.matches("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}")))
			throw new Exception("Plausblitaetspruefung nicht bestanden");		
	}

	public static void main(String[] args){
		
		UUID myRandomID = getUUIDRandom();
		System.out.println("UUID Typ 4: " + myRandomID);
		
		try {
			validateUUID(myRandomID);
		} catch (Exception e1) {e1.printStackTrace();}
		;
		
		UUID myNameUUID = getUUIDName("Guten Tag");
		System.out.println("UUID Typ 3: " + myNameUUID);
		
		try {
			validateUUID(myRandomID);
		} catch (Exception e1) {e1.printStackTrace();}
		;
		
	}
}
