package dn3;
import java.io.*;
import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Resitev {
	public static void resi(String vhodnaDatoteka, String izhodnaDatoteka) throws IOException {
		// Bralna datoteka
		Scanner in = new Scanner(new FileInputStream(vhodnaDatoteka));
		in.useDelimiter("\\s+");

		//pisalna datoteka
		FileWriter out = new FileWriter(izhodnaDatoteka);

		//logika resevanja problema
		solveTask(in,out);

		//zapri file writer in scanner
		out.flush();
		out.close();
		in.close();
	}

	//Reševanje naloge
	public static void solveTask(Scanner in, FileWriter out) throws IOException {
		int P = in.nextInt(); //stevilo pesmi
		int D = in.nextInt(); // stevilo demotov
		int l_p = in.nextInt(); //dolzina seznamov ki predstavljajo pesmi
		int l_d = in.nextInt(); //dolzina seznamov ki predstavljajo demoposnetke

		double [][][] pesmi = new double [P][l_p - l_d+ 1][3]; //vsaka pesem, za vsako podzaporedje dolzine l_d hranimo povprecje,
		double[][] PESMI = new double [P][l_p]; //vse pesmi

		double [][] demoti = new double [D][l_d]; //hranimo demote, nepotrebno

		for(int i = 0; i < P; i++){ //naredimo array vseh pesmi
			double [] pesem = new double[l_p];
			for(int j = 0; j < l_p; j++){
				double A = Double.parseDouble(  in.next());
				pesem[j] = A;
			}
			PESMI[i] = pesem;

			double [][] pesem_odseki = new double [l_p - l_d + 1][3]; //tukaj hranimo podatke o posameznem odseku
			double[] vsote_intervalov = new double[l_p - l_d + 1];
			double[] vsota_kv_intervalov = new double [l_p - l_d + 1];
			double[] kvadrat_vsot_intervalov = new double[l_p - l_d + 1];

			double prva_intervalov = 0;
			double prva_kv_intervalov = 0;

			for (int j = 0; j < l_p - l_d ; j++){
				prva_intervalov = prva_intervalov + pesem[j];
				prva_kv_intervalov = prva_kv_intervalov + pesem[j]*pesem[j];
			}

			vsote_intervalov[0] = prva_intervalov;
			vsota_kv_intervalov[0] = prva_kv_intervalov;
			kvadrat_vsot_intervalov[0] = prva_intervalov * prva_intervalov;
			pesem_odseki[0][0] = vsote_intervalov[0];
			pesem_odseki[0][1] = vsota_kv_intervalov[0];
			pesem_odseki[0][2] = kvadrat_vsot_intervalov[0];

			for (int j = 0; j < l_p - l_d ; j++ ) { //racunamo povprecje, vsoto kvadratov, kvadrat vsote za vse od 1 dalje
				vsote_intervalov[j+1] = vsote_intervalov[j] + pesem[j + l_p - l_d] - pesem[j];
				vsota_kv_intervalov[j+1] = vsota_kv_intervalov[j] + pesem[j + l_p - l_d ] * pesem[j + l_p - l_d ] - pesem[j]*pesem[j];
				kvadrat_vsot_intervalov[j+1] = vsote_intervalov[j+1]*vsote_intervalov[j+1];
				pesem_odseki[j+1][0]= vsote_intervalov[j+1];
				pesem_odseki[j+1][1] = vsota_kv_intervalov[j+1];
				pesem_odseki[j+1][2] = kvadrat_vsot_intervalov[j+1];
			}


			pesmi[i] = pesem_odseki; //trojka statističnih podatkov za vsako pesem

			//System.out.println(Arrays.deepToString(pesmi[i]));
			//System.out.println(pesmi.length); //zunanji array stevilka oz st pesmi
			//System.out.println(pesmi[0].length); //st podintervalov



		}

		for(int i = 0; i < D; i++){ //beremo vse demote
			double [] demo = new double[l_d];
			for(int j = 0; j < l_d; j++){
				double A = Double.parseDouble(in.next());
				demo[l_d - j - 1] = A; //obrnemo seznam drgace kar k ce ni obrnjen seznam, na povprecje, in vsote kvadratov ne vpliva
			}
			demoti[i] = demo; //nepotrebno

			//System.out.println(Arrays.toString(demo)); test

			String rezultat = poizvedi(pesmi, demo, PESMI, l_d ); //poiscemo pesem in mesto pesmi med vsemi s katerimi se doticni demo ujema
			out.write(rezultat + "\r\n");
		}



	}

	public static String poizvedi(double[][][] pesmi, double[] demo, double[][] vse_pesmi, int dolzina_demota){
		double toll = (double) 0.000001;
		double vsota = 0;
		double vsota_kvadratov = 0;

		for (int j = 0; j < demo.length; j++) {
			vsota = vsota + demo[j];
			vsota_kvadratov = vsota_kvadratov + demo[j]*demo[j];

		}

		double kvadrat_vsot = vsota * vsota;

		//statisticni podatki za demota
		double Y1 = vsota;
		double Y2 = vsota_kvadratov;
		double Y3 = kvadrat_vsot;

		for (int i = 0; i <  pesmi.length; i++){ //pregledamo vse pesmi
			double[] cela_pesem = vse_pesmi[i];

			double[] koeficienti = FFT.zmnoziPolinome(cela_pesem, demo);
			//System.out.println(Arrays.toString(cela_pesem));
			//System.out.println(Arrays.toString(demo));
			//System.out.println(Arrays.toString(koeficienti));
			//System.out.println(koeficienti[5]);
			//System.out.println("konec");


			for (int j = 0; j <  pesmi[0].length; j++){ //pesmi[0] = l_p - l_d+ 1, st podzaporedij dolzine kot demo
				//System.out.println(Arrays.toString(pesmi[i])); //test
				//System.out.println(Arrays.toString(demo));
				double X1 = pesmi[i][j][0]; //vsota
				double X2 = pesmi[i][j][1]; //vsota kvadratov
				double X3 = pesmi[i][j][2]; //kvadrat vsot
				double corr = korelacija(X1, X2, X3, Y1, Y2, Y3, koeficienti[j + dolzina_demota - 1 ] ,dolzina_demota );
				if (abs(corr - 1) < toll){
					String s = String.format("%d",i) + " " + String.format("%d", j);
					return s;

				}
			}
		}

		return "";
	}

	public static double korelacija(double X1, double X2, double X3, double Y1, double Y2, double Y3, double produkt, int dolzina){
		double corr = (dolzina * produkt - X1*Y1) / (sqrt(dolzina * X2 - X3)* sqrt(dolzina * Y2 - Y3));
		//System.out.println(corr);
		return corr;
	}
}
