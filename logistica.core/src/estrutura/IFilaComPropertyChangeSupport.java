package estrutura;

import java.beans.PropertyChangeListener;

public interface IFilaComPropertyChangeSupport extends IFila {

	void addPropertyChangeListener(PropertyChangeListener l);

}
