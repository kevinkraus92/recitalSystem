package itba.pod;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioEntradas extends Remote {
	void solicitarEntrada(String nombreRecital, ClienteEntradas handler) throws RemoteException;	
}
