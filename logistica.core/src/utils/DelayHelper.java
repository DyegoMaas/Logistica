package utils;

public abstract class DelayHelper {
	
	public static void aguardar(long milisegundos){
		if(milisegundos == 0)
			return;
		
		try {
			Thread.sleep(milisegundos);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
