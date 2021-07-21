package dn3;
import java.io.*;
import java.util.*;

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

		float [][][] pesmi = new float [P][l_p - l_d+ 1][3]; //vsaka pesem, za vsako podzaporedje dolzine l_d hranimo povprecje,
		float [][] demoti = new float [D][l_d]; //hranimo demote, nepotrebno

		for(int i = 0; i < P; i++){ //naredimo array vseh pesmi
			float [] pesem = new float[l_p];
			for(int j = 0; j < l_p; j++){
				float A = Float.parseFloat(  in.next());
				pesem[j] = A;
			}

			float [][] pesem_odseki = new float [l_p - l_d + 1][3]; //tukaj hranimo podatke o posameznem odseku
			float[] vsote_intervalov = new float[l_p - l_d + 1];
			float[] vsota_kv_intervalov = new float [l_p - l_d + 1];
			float[] kvadrat_vsot_intervalov = new float[l_p - l_d + 1];

			float prva_intervalov = 0;
			float prva_kv_intervalov = 0;

			for (int i = 0; i < l_p - l_d + 1; i++){
				prva_intervalov = prva_intervalov + pesem[i];
				prva_kv_intervalov = prva_kv_intervalov + pesem[i]*pesem[i];
			}

			vsote_intervalov[0] = prva_intervalov;
			vsota_kv_intervalov[0] = prva_kv_intervalov;
			kvadrat_vsot_intervalov[0] = prva_intervalov * prva_intervalov;


			for (int j = 0; j < l_p - l_d + 1; j++ ) { //racunamo povprecje, vsoto kvadratov, kvadrat vsote iz prve z rekurzivno zvezo

			}


			pesmi[i] = pesem_odseki;

		}

		for(int i = 0; i < D; i++){
			float [] demo = new float[l_d];
			for(int j = 0; j < l_d; j++){
				float A = Float.parseFloat(in.next());
				demo[l_d - j - 1] = A; //obrnemo seznam drgace kar k ce ni obrnjen seznam
			}
			demoti[i] = demo; //nepotrebno??

			String rezultat = poizvedi(pesmi, demo); //poizvedi bo iskalo mesto v pesmi s katero se ujema
			out.write(rezultat + "\r\n");
		}



	}

	public static String poizvedi(float[][][] pesmi, float[] demo){
		return "-1";
	}
}
