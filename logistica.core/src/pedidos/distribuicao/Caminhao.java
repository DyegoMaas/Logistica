package pedidos.distribuicao;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Caminhao {
	private int capacidadeEmPacotes;
	private int pacotesCarregados;
	
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock read = readWriteLock.readLock();
	private final Lock write = readWriteLock.writeLock();
	
	private final Condition caminhaoCheio = read.newCondition();
	private final Condition caminhaoVazio = write.newCondition();

	public Caminhao(int capacidadeEmPacotes){
		this.capacidadeEmPacotes = capacidadeEmPacotes;		
	}
	
	public synchronized int getCapacidadeDisponivel(){
		return capacidadeEmPacotes - pacotesCarregados;
	}
	
	public void carregarPacotes(int numeroPacotes){
		pacotesCarregados += numeroPacotes;
	}
	
	public void efetuarEntregas() throws InterruptedException{
		read.lock();
		
		while(pacotesCarregados < capacidadeEmPacotes)
			caminhaoCheio.await();
		
		try {
//			System.out.printf("Withdrawing %f", amount);
//			double newBalance = balance - amount;
//			System.out.printf(", new balance is %f\n", newBalance);
//			balance = newBalance;
		} finally {
			read.unlock();
		}
	}
}
