package dn2;

import java.io.*;
import java.util.*;

public class Resitev {
	public static void resi(String vhodnaDatoteka, String izhodnaDatoteka) throws IOException {
		// Bralna datoteka
		Scanner in = new Scanner(new FileInputStream(vhodnaDatoteka));
		in.useDelimiter("\\s+");

		//Pisalna datoteka
		FileWriter out = new FileWriter(izhodnaDatoteka);

		//Logika resevanja
		SolveTask(in, out);

		//Zapri file writter in scanner
		out.flush();
		out.close();
		in.close();
	}

	//resevanje naloge
	public static void SolveTask(Scanner in, Writer out) throws IOException {
		double D = in.nextInt(); // stevilo dodajanj
		double P = in.nextInt(); // stevilo poizvedb
		double I = in.nextInt(); // stevilo izbrisov
		double n = I + D + P; //skupno st vrstic se
		int napakanekje = 0; //za iskanje napak v določeni poizvedbi
		AVLTree tree = new AVLTree(); //novo prazno drevo v katerega bomo vstavljali
		HashMap<Double, ArrayList<Pair>> dict = new HashMap<>(); // kljuc datum, vrednosti intervalov


		for (int i = 0; i < n; i++) {
			String vrsta = in.next();

			if (vrsta.equals("dodaj")) { // dodajanje
				String userName = in.next(); //nerabimo
				double time = in.nextDouble(); //datum rojstva,
				tree.insert(time); // doda datum rojstva v drevo

				int nbSections = in.nextInt(); //stevilo odsekov
				ArrayList<Pair> seznam = new ArrayList<>();

				for (int j = 0; j < nbSections; j++) {
					int a = in.nextInt(); //zacetek itnervala
					Pair par1 = new Pair(a, true);
					seznam.add(par1);
					int b = in.nextInt(); //konec intervala
					Pair par2 = new Pair(b, false);
					seznam.add(par2);

				}
				dict.put(time, seznam); //dodamo v slovar kljuc je datum rojstva, vrednost seznam intervalov

			}

			else if (vrsta.equals("odstrani")) { //odstranjevanje uporabnika
				String userName = in.next(); //ne rabimo
				double time = in.nextDouble();
				tree.delete(time); //odstrani čas iz AVL drevesa, v slovarju načeloma lahko ostane in ni treba odstranjevat
			}

			else if (vrsta.equals("poizvedba")) { //poizvedba
				napakanekje++; //
				//System.out.println("začetek poizvedb");
				double time = in.nextDouble();
				//Iščemo 5 najbližjih

				/*if (napakanekje == 32595) {
				System.out.println("poizvedba za " + time);
				}*/

				double[] seznam = new double[5];

				for (int j = 0; j < 5; j++) {//Iščemo 5 najbližjih
					seznam[j] = getClosest(tree.getRoot(), time); //dodamo najbližji element
					//System.out.println(seznam[j]);

					/*if (napakanekje == 32595) {
						System.out.println(seznam[j]);
					}*/
					tree.delete(seznam[j]); //odstranimo da iščemo naslednjega najbližjega v log(n)
				}

				for (int j = 0; j < 5; j++) {//Vrnemo nazaj 5 odstranjenih vsako v log(n)
					tree.insert(seznam[j]);
				}
				ArrayList<Pair> s1 = dict.get(seznam[0]); //lahko bi tudi elegantneje z list list pair
				ArrayList<Pair> s2 = dict.get(seznam[1]);
				ArrayList<Pair> s3 = dict.get(seznam[2]);
				ArrayList<Pair> s4 = dict.get(seznam[3]);
				ArrayList<Pair> s5 = dict.get(seznam[4]);

				int rezultat = poizvedi(s1, s2, s3, s4, s5, napakanekje);
				out.write(rezultat + "\r\n");
			}
		}
	}

	private static int poizvedi(ArrayList<Pair> s1, ArrayList<Pair> s2, ArrayList<Pair> s3, ArrayList<Pair> s4, ArrayList<Pair> s5, int napaka) {

		//System.out.println("zacetek");
		ArrayList<Pair> combined = new ArrayList<>();
		combined.addAll(s1);
		combined.addAll(s2);
		combined.addAll(s3);
		combined.addAll(s4);
		combined.addAll(s5);
		Collections.sort(combined); //uredi arraylist parov po vrsti, najprej po stevilkah potem po boolih, v razredu Pair funkcija za to

//finding
		int n = combined.size();
		int maxStevec = 0;
		int stevec = 0;
		int zacetek = 0;
		for (int j = 0; j < n; j++){

			Pair par = combined.get(j);
			int vrednost = par.getKey();

			boolean bool = par.getValue();
			if (bool == true){
				stevec++;
				if (stevec > maxStevec){
					zacetek = vrednost;
					maxStevec = stevec;
				}
			}
			else if (bool == false){
				stevec--;
			}
		}

		return zacetek;
	}

	private static double getClosest(AVLTree.Node koren, double key) {
		if (koren == null) return -1;
		double currentValue = koren.key;
		if (currentValue == key) return currentValue;

		else if (currentValue > key) {
			if (koren.left == null) return currentValue;
			//get closest on left
			double closest = getClosest(koren.left, key);
			if (Math.abs(closest - key) > Math.abs(currentValue - key)) {
				return currentValue;
			}
			return closest;
		} else {
			if (koren.right == null) return currentValue;
			//get closest on right
			double closest = getClosest(koren.right, key);
			if (Math.abs(closest - key) > Math.abs(currentValue - key)) {
				return currentValue;
			}
			return closest;
		}

	}
}


