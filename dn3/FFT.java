package dn3;
import dn3.Complex;

import java.util.Arrays;

public class FFT {
	/**
	 * Hitra Fourierova transformacija.
	 * @param a seznam compleksnih stevil
	 * @param jeInverzno
	 * @return
	 */
	public static Complex[] fft(Complex[] a, boolean jeInverzno) {
		int n = a.length;
		if (n == 1) {
			return a;
		} else {
			Complex[] sodi = new Complex[n / 2];
			Complex[] lihi = new Complex[n / 2];
			for (int i = 0; 2 * i < n; i++) {
				sodi[i] = a[2*i];
				lihi[i] = a[2*i+1];
			}
			Complex[] sodiFFT = fft(sodi, jeInverzno);
			Complex[] lihiFFT = fft(lihi, jeInverzno);
			Complex w = new Complex(1.0);
			double alfa = 2 * Math.PI / n * (jeInverzno ? -1 : 1);
			Complex w0 = new Complex(Math.cos(alfa), Math.sin(alfa));
			for (int i = 0; 2 * i < n; i++) {
				Complex c = w.times(lihiFFT[i]);
				a[i] = sodiFFT[i].plus(c);
				a[i + n/2] = sodiFFT[i].minus(c);
				if (jeInverzno) {
					a[i] = a[i].divide(2.0);
					a[i + n/2] = a[i + n/2].divide(2.0);
				}
				w = w.times(w0);
				}
		}
		return a;
	}
	
	public static double[] zmnoziPolinome(double[] a, double[] b) {
		int stA = a.length - 1;
		int stB = b.length - 1;
		int n = 1; // dolzina razsirjenih seznamov: vsaj za 1 vec kot stopnja produkta
		while (n <= stA + stB) {
			n *= 2;
		}
		Complex[] aC = new Complex[n];
		Complex[] bC = new Complex[n];
		for (int i = 0; i < n; i++) {
			aC[i] = i <= stA ? new Complex(a[i]) : new Complex(0.0);
			bC[i] = i <= stB ? new Complex(b[i]) : new Complex(0.0);
		}
		// transformiraj
		Complex[] aFFT = FFT.fft(aC, false);
		Complex[] bFFT = FFT.fft(bC, false);
		// po tockah zmnozi
		Complex[] abFFT = new Complex[n];
		for (int i = 0; i < n; i++) {
			abFFT[i] = aFFT[i].times(bFFT[i]);
		}
		// odtransformiraj
		double[] ab = Complex.toDouble(FFT.fft(abFFT, true));
		return Arrays.copyOf(ab, stA + stB + 1);
	}
	
	public static String zmnoziStevila(String a, String b) {
		return null;
	}
	
	private static double[] predelajVSeznam(String x) {
		double[] xD = new double[x.length()];
		for(int i = 0; i < xD.length; i++) {
			xD[xD.length - 1 - i] = x.charAt(i) - '0';
		}
		return xD;
	}

}
