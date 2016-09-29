package itba.pod.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import itba.pod.ClienteEntradas;

public class ClienteEntradasImpl implements ClienteEntradas {

	public ClienteEntradasImpl() throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);
	}

	@Override
	public void entradaReservada(String recital) {
		System.out.println("Entradas Reservadas para " + recital);
	}

	@Override
	public void entradaVipConfirmada(String recital, String entrada) {
		System.out.println("Entradas VIP Reservadas para " + recital);
	}

	@Override
	public void entradaConfirmada(String recital, String entrada) {
		System.out.println("Entradas " + entrada + " confirmadas para " + recital);
	}

	@Override
	public void recitalAgotado(String recital) {
		System.out.println("ATENCION! Recital Agotado");
	}

	@Override
	public void recitalCancelado(String recital) {
		System.out.println("ATENCION! Recital Cancelado");
	}
}
