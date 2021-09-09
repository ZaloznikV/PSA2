package dn3; // tukaj bo treba najbrz spremeniti

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {

		float [][] bla = new float [50][18000];
		// VNESI POTI DATOTEK
		String vhodnaDatoteka = "NalogaA.txt";
		String izhodnaDatoteka = "test.txt";
		String resitve = "NalogaA.sol";
		double[] p1 = new double[] {1, 2, 3};
		double[] p2 = new double[] {3, 1};
		// (1 + 2x + 3x^2)(3 + x) = 3 + 7x + 11x^2 + 3x^3
		System.out.println("p1 * p2:" + Arrays.toString(dn3.FFT.zmnoziPolinome(p1, p2)));

		long t0 = System.currentTimeMillis();
		Resitev.resi(vhodnaDatoteka, izhodnaDatoteka);
		long t1 = System.currentTimeMillis();
		PreveriResitev.preveri(t0, t1, izhodnaDatoteka, resitve);
	}
}
