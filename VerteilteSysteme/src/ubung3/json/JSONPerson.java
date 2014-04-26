package ubung3.json;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.*;


public class JSONPerson {
	private String namensfeld;
	private int zahl;
	private Date geburtsdatum;

	public JSONPerson() {
		namensfeld = "";
		zahl = 0;
		geburtsdatum = new Date();
	}

	public JSONPerson(String name, int zahl, Date geburtsdatum) {
		namensfeld = name;
		this.zahl = zahl;
		this.geburtsdatum = geburtsdatum;
	}

	public byte[] toByteArray() {
		byte[] ret = new byte[0];
		
		try {
			byte[] temp = namensfeld.getBytes("UTF-8");
			ret = combineArrays(ret, temp);

			temp = Integer.toString(zahl).getBytes("UTF-8");
			ret = combineArrays(ret, temp);

			temp = Long.toString(geburtsdatum.getTime()).getBytes("UTF-8");
			ret = combineArrays(ret, temp);
		} catch (Exception e) {e.printStackTrace();}
		
		return ret;
	}

	public void fromByteArray(byte[] array) throws Exception {
		int i = -1;
		
		while (array[++i] != -1 ) 
			namensfeld = new String(new byte[] { array[i] }, "UTF-8") + namensfeld;

		String sZahl = "";
		
		while (array[++i] != -1 ) 
			sZahl = new String(new byte[] { array[i] }, "UTF-8") + sZahl;
		
		zahl = Integer.parseInt(sZahl, 10);
		
		sZahl = "";
		
		while (array[++i] != -1) 
			sZahl = new String(new byte[] { array[i] }, "UTF-8") + sZahl;

		geburtsdatum.setTime(Long.parseLong(sZahl, 10));
	}

	/**
	 * Konkateniert zwei Arrays. a + b = ab.
	 * @param byte[] a
	 * @param byte[] b
	 * @return byte[] ret
	 * @throws UnsupportedEncodingException 
	 */
	private static byte[] combineArrays(byte[] a, byte[] b) throws UnsupportedEncodingException {
		byte[] ret = new byte[a.length + b.length + 1];

		int retIndex = 0;

		for (byte x : a)
			ret[retIndex++] = x;
		
		for (byte x : b)
			ret[retIndex++] = x;

		ret[retIndex++] = -1;
		return ret;
	}
	
	/**
	 * Schreibt Daten in eine Textdatei im JSON Format.
	 * @param String name
	 * @param int zahl
	 * @param long geburtsdatum
	 * @param String dateiname
	 */	
	public static void writeIntoFile(String name, int zahl, long geburtsdatum, String dateiname){
		JSONObject obj = new JSONObject();
		
		obj.put("Name", name);
		obj.put("Nummer", zahl);
		obj.put("Datum", geburtsdatum);
		
		BufferedWriter bw;
		
		try{
			bw = new BufferedWriter(new FileWriter(dateiname));
			obj.write(bw).flush();
		}catch (IOException e) { e.printStackTrace();}
	}
	
	/**
	 * Liest String unter Angabe des Schluessels und des Dateinamens aus JSON Datei.
	 * @param String file
	 * @param String key
	 */	
	public static String getName(String file, String key) throws FileNotFoundException{
		
		Reader br = new BufferedReader(new FileReader(file));
		
		JSONTokener jt = new JSONTokener(br);
		JSONObject jsonObj = new JSONObject(jt);
		
		return jsonObj.getString(key);

	}
	
	/**
	 * Liest Integer unter Angabe des Schluessels und des Dateinamens aus JSON Datei.
	 * @param String file
	 * @param String key
	 */	
	public static int getZahl(String file, String key) throws FileNotFoundException{
		Reader br = new BufferedReader(new FileReader(file));
		
		JSONTokener jt = new JSONTokener(br);
		JSONObject jsonObj = new JSONObject(jt);
			
		return jsonObj.getInt(key);
	}
	
	/**
	 * Liest Daten unter Angabe des Schluessels und des Dateinamens aus JSON Datei.
	 * @param String file
	 * @param String key
	 */	
	public static Date getGeburtsdatum(String file, String key) throws FileNotFoundException{
		Reader br = new BufferedReader(new FileReader(file));
		
		JSONTokener jt = new JSONTokener(br);
		JSONObject jsonObj = new JSONObject(jt);
		
		Date d = new Date(jsonObj.getLong(key));
		return d;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		String filepath = "C:\\Users\\Philipp\\git\\verteilte_systeme\\VerteilteSysteme\\person.txt";
		GregorianCalendar gebdatum = new GregorianCalendar(1990, Calendar.SEPTEMBER, 06);
		
		writeIntoFile("Meisterin Herzlos", 21, gebdatum.getTimeInMillis(), "person.txt");
		//System.out.println(getName(filepath, "Name"));
		//System.out.println(getZahl(filepath, "Nummer"));
		//System.out.println(getGeburtsdatum(filepath, "Datum"));
		
		JSONPerson p1 = new JSONPerson(getName(filepath, "Name"), getZahl(filepath, "Nummer"), getGeburtsdatum(filepath, "Datum"));
		JSONPerson p2 = new JSONPerson();
		
		byte[] p1Data = p1.toByteArray();
		
		try{
			p2.fromByteArray(p1Data);
		}catch (Exception e1) {e1.printStackTrace();}
		
		SimpleDateFormat sdf = new SimpleDateFormat();

		System.out.println("--> P1");
		System.out.println("Name: " + p1.namensfeld);
		System.out.println("Zahl: " + p1.zahl);
		System.out.println("Geburtsdatum: " + sdf.format(p1.geburtsdatum));
		
		System.out.println("\n--> P2");
		System.out.println("Name: " + p2.namensfeld);
		System.out.println("Zahl: " + p2.zahl);
		System.out.println("Geburtsdatum: " + sdf.format(p2.geburtsdatum));
	}
}
