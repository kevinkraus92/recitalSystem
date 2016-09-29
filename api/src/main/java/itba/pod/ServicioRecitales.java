package itba.pod;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioRecitales extends Remote {
	public void crear(String nombre, int cantidadParaConfirmar, int entradasVip, int capacidadMaxima) throws RemoteException;
	
	public void cancelar(String nombre) throws RemoteException;	
}
