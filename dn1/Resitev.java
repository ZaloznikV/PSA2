import java.io.*;
import java.util.*;

public class Resitev {// vstavljamo vozlišča v AVL drevo. vsako dodajanje logn časa, iskanje na intervalu O(k+logn)
	// kjer k število hiš v intervalu.  A podmnozica B. cca 25s za a in b. zakaj pocasi???
	public static void resi(String vhodnaDatoteka, String izhodnaDatoteka) throws IOException {
		// Bralna dototeka.
		Scanner in = new Scanner(new FileInputStream(vhodnaDatoteka));
		in.useDelimiter("\\s+");

		// Pisalna datoteka.
		FileWriter out = new FileWriter(izhodnaDatoteka);

		// Logika reševanja problema.
		solveTask(in, out);

		// Zapri file writer in scanner.
		out.flush();
		out.close();
		in.close();
	}

	// Resevanje naloge.
	public static void solveTask(Scanner in, FileWriter out) throws IOException {
		int nHouses = in.nextInt(); //odvec
		int nMoving = in.nextInt();
		int nQueries = in.nextInt();

		AVLTree tree = new AVLTree();

		for (int j = 0; j < nMoving + nQueries; j++) {//iteracija po vsem
			String cmd = in.next();

			if (cmd.equals("V")) {
				// Vstavljanje.
				int iHouse = in.nextInt();
				int pets_nm = in.nextInt();
				tree.insert(iHouse, pets_nm); //doda hisno in st zivali stevilko v AVL drevo

			}
			else if (cmd.equals("P")) { // Poizvedovanje.
				int a = in.nextInt();
				int b = in.nextInt();

				int rezultat = solveQuery(a, b, tree.getRoot()); //zacnemo poizvedbo v korenu

				out.write(rezultat + "\r\n");
			}
		}
	}

	public static int solveQuery(int a, int b, AVLTree.Node node) {
		//vrne rezultat poizvedbe, torej stevilo vseh zivali v hisah na [a,b]
		int sum = 0;
		if (node == null){ //ce prazno vrnemo 0
			return sum;
		}

		if (b < node.key) {//gledamo samo levo polovico
			sum = sum + solveQuery(a, b, node.left);
		}
		else if (node.key < a) {//gledamo samo desno polovico
			sum = sum + solveQuery(a, b, node.right);
		}
		else {//vrednost med a in b, pristejemo + gremo pogledati oba otroka
			sum = (sum + node.value + solveQuery(a, b, node.right) + solveQuery(a, b, node.left));
		}

		return sum;
	}
}








