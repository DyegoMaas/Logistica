package pedidos.entregas;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import pedidos.distribuicao.CentroDistribuicao;
import servicos.IServicoComPropertyChangeSupport;
import servicos.StatusExecucaoServico;
import utils.DelayHelper;

public class GeradorEntregas extends Thread implements IServicoComPropertyChangeSupport {

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);
	private StatusExecucaoServico statusExecucaoServico = null;

	private final ReentrantLock lock = new ReentrantLock();
	private Condition podeContinuar = lock.newCondition();

	private boolean continuarGerandoEntregas = true;
	private CentroDistribuicao centroDistribuicao;
	private int intervaloExecucao;

	public GeradorEntregas(CentroDistribuicao centroDistribuicao) {
		this.centroDistribuicao = centroDistribuicao;
	}

	@Override
	public void run() {
		while (true) {
			lock.lock();

			try {
				while (!continuarGerandoEntregas)
					podeContinuar.await();

				StatusExecucaoServico statusExecucaoServicoAnterior = statusExecucaoServico;
				statusExecucaoServico = StatusExecucaoServico.AGUARDANDO;
				changes.firePropertyChange("statusExecucaoServico", statusExecucaoServicoAnterior, statusExecucaoServico);

				centroDistribuicao.fazerEntrega();

				statusExecucaoServicoAnterior = statusExecucaoServico;
				statusExecucaoServico = StatusExecucaoServico.EXECUTANDO;
				changes.firePropertyChange("statusExecucaoServico", statusExecucaoServicoAnterior, statusExecucaoServico);

				DelayHelper.aguardar(intervaloExecucao);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			lock.unlock();
		}
	}

	@Override
	public void definirIntervaloExecucao(int milisegundos) {
		this.intervaloExecucao = milisegundos;
	}

	@Override
	public void interromper() throws InterruptedException {
		continuarGerandoEntregas = false;
	}

	private boolean started = false;

	@Override
	public void executar() {
		lock.lock();

		continuarGerandoEntregas = true;

		podeContinuar.signalAll();
		lock.unlock();

		if (!started) {
			start();
			started = true;
		}
	}

	@Override
	public void interromperOuExecutar() throws InterruptedException {
		if (continuarGerandoEntregas) {
			interromper();
		} else {
			executar();
		}
	}

	@Override
	public boolean isExecutando() {
		return continuarGerandoEntregas;
	}

	@Override
	public int getIntervaloExecucao() {
		return intervaloExecucao;
	}

	@Override
	public StatusExecucaoServico getStatusExecucao() {
		return statusExecucaoServico;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}
}