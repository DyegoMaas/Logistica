package servicos;

import java.beans.PropertyChangeListener;

public interface IServicoComPropertyChangeSupport extends IServico {

	void addPropertyChangeListener(PropertyChangeListener l);

	void removePropertyChangeListener(PropertyChangeListener l);
}
