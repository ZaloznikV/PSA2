import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		long t0 = System.currentTimeMillis();
		
		
		// VNESI POTI DATOTEK
		String vhodnaDatoteka = "nalogaB.txt";
		String izhodnaDatoteka = "test.txt";
		String zakodiraneResitve = "resitveB.pub";
		
		// VASA RESITEV
		Resitev.resi(vhodnaDatoteka, izhodnaDatoteka);

		long t1 = System.currentTimeMillis();
		
		PreveriResitev.preveri(t0, t1, izhodnaDatoteka, zakodiraneResitve);
	}

}
