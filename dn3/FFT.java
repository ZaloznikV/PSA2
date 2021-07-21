package dn3;
import java.util.Arrays;

public class FFT {
	/**
	 * Hitra Fourierova transformacija.
	 * @param a seznam compleksnih stevil
	 * @param jeInverzno
	 * @return
	 */
	public static dn3.Complex[] fft(dn3.Complex[] a, boolean jeInverzno) {
		int n = a.length;
		if (n == 1) {
			return a;
		} else {
			dn3.Complex[] sodi = new dn3.Complex[n / 2];
			dn3.Complex[] lihi = new dn3.Complex[n / 2];
			for (int i = 0; 2 * i < n; i++) {
				sodi[i] = a[2*i];
				lihi[i] = a[2*i+1];
			}
			dn3.Complex[] sodiFFT = fft(sodi, jeInverzno);
			dn3.Complex[] lihiFFT = fft(lihi, jeInverzno);
			dn3.Complex w = new dn3.Complex(1.0);
			double alfa = 2 * Math.PI / n * (jeInverzno ? -1 : 1);
			dn3.Complex w0 = new dn3.Complex(Math.cos(alfa), Math.sin(alfa));
			for (int i = 0; 2 * i < n; i++) {
				dn3.Complex c = w.times(lihiFFT[i]);
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
		dn3.Complex[] aC = new dn3.Complex[n];
		dn3.Complex[] bC = new dn3.Complex[n];
		for (int i = 0; i < n; i++) {
			aC[i] = i <= stA ? new dn3.Complex(a[i]) : new dn3.Complex(0.0);
			bC[i] = i <= stB ? new dn3.Complex(b[i]) : new dn3.Complex(0.0);
		}
		// transformiraj
		dn3.Complex[] aFFT = FFT.fft(aC, false);
		dn3.Complex[] bFFT = FFT.fft(bC, false);
		// po tockah zmnozi
		dn3.Complex[] abFFT = new dn3.Complex[n];
		for (int i = 0; i < n; i++) {
			abFFT[i] = aFFT[i].times(bFFT[i]);
		}
		// odtransformiraj
		double[] ab = dn3.Complex.toDouble(FFT.fft(abFFT, true));
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
