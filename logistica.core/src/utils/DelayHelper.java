package utils;

public abstract class DelayHelper {

	private static int fator = 1;

	public static void aguardar(long milisegundos) {
		if (milisegundos == 0)
			return;
		try {
			Thread.sleep(milisegundos * fator);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int getFator() {
		return fator;
	}

	public static void setFator(int novoFator) {
		fator = novoFator;
	}
}
