package ubung2.marshalling;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Person {
	private String namensfeld;
	private int zahl;
	private Date geburtsdatum;

	public Person() {
		namensfeld = "";
		zahl = 0;
		geburtsdatum = new Date();
	}

	public Person(String name, int zahl, Date geburtsdatum) {
		namensfeld = name;
		this.zahl = zahl;
		this.geburtsdatum = geburtsdatum;
	}

	public byte[] toByteArray() {
		byte[] ret = new byte[0];
		
		try {
			byte[] temp = namensfeld.getBytes("UTF-8");
			ret = combineArrays(ret, toLittleEndian(temp));

			temp = Integer.toString(zahl).getBytes("UTF-8");
			ret = combineArrays(ret, toLittleEndian(temp));

			temp = Long.toString(geburtsdatum.getTime()).getBytes("UTF-8");
			ret = combineArrays(ret, toLittleEndian(temp));
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
	 * Dreht den Inhalt des Arrays um. abc --> bca
	 * @param in
	 * @return
	 */
	private static byte[] toLittleEndian(byte[] in) {
		byte[] ret = new byte[in.length];

		for (int i = 0, j = in.length - 1; i < ret.length; i++, j--)
			ret[i] = in[j];

		return ret;
	}

	/**
	 * Konkateniert 2 Arrays. a + b = ab.
	 * @param a
	 * @param b
	 * @return
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
	
	private static void validate(byte[] in, byte[] checksum1) throws Exception{
		MessageDigest md5 = MessageDigest.getInstance("MD5");

		if(!(MessageDigest.isEqual(checksum1, md5.digest(in))))
			throw new Exception("Checksummen stimmen nicht ueberein");
		
		String s = new String(in, "UTF-8");
		if(!(s.matches("\\D{1,}.\\d{2,}.\\d{2,}.")))
			throw new Exception("Plausblitaetspruefung nicht bestanden");
	}

	private static byte[] createChecksum(byte[] in){
		byte[] ret = null;
		
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			ret = md5.digest(in);
		} catch (NoSuchAlgorithmException e) {e.printStackTrace();}
		
		return ret;
	}
	
	public static void main(String[] args) {
		// Zeitzone ist auf Systemdefault gesetzt
		GregorianCalendar gebdatum = new GregorianCalendar(1980, Calendar.FEBRUARY, 10);

		Person p1 = new Person("Hans", 12, gebdatum.getTime());
		Person p2 = new Person();

		byte[] p1Data = p1.toByteArray();
		byte[] checksum = createChecksum(p1Data);
		
		try {
			validate(p1Data, checksum);
			p2.fromByteArray(p1Data);
		} catch (Exception e1) {e1.printStackTrace();}
		
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