package dn2;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

	public static void main(String[] args) throws IOException {
//		System.out.println(stringToMilliseconds("1935-12-25 00:01:01"));
//		System.out.println(stringToMilliseconds("2003-04-13 01:02:03"));
			// VNESI POTI DATOTEK

			String vhodnaDatoteka = "nalogaB.txt";
			String izhodnaDatoteka = "test.txt";
			String resitve = "nalogaB.sol";
			AVLTree bla = new AVLTree();
			bla.insert(10);
			bla.delete(10);

			long t0 = System.currentTimeMillis();
			dn2.Resitev.resi(vhodnaDatoteka, izhodnaDatoteka);
			long t1 = System.currentTimeMillis();
			dn2.PreveriResitev.preveri(t0, t1, izhodnaDatoteka, resitve);

	}
	
	public static long stringToMilliseconds(String t) {
		// spremeni 1935-12-03 00:09:58 v milisekunde od 1. 1. 1970
		try {
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(t).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
