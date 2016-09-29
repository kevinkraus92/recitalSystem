package itba.pod.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import itba.pod.ClienteEntradas;
import itba.pod.ServicioEntradas;
import itba.pod.ServicioRecitales;

public class ServicioImpl implements ServicioRecitales, ServicioEntradas {

	Map<String, Recital> map;
	Set<ClienteEntradas> clients;
	Set<ClienteEntradas> clientsVIP;

	public ServicioImpl() throws RemoteException {
		map = new HashMap<>();
		UnicastRemoteObject.exportObject(this, 0);
	}

	@Override
	public void crear(String nombre, int cantidadParaConfirmar, int entradasVip, int capacidadMaxima)
			throws RemoteException {
		Recital r = new Recital(nombre, cantidadParaConfirmar, entradasVip, capacidadMaxima);
		map.put(nombre, r);
	}

	@Override
	public void cancelar(String nombre) throws RemoteException {
		Recital r = map.get(nombre);
		if (r == null)
			return;
		r.state = "CANCELADO";
		for (ClienteEntradas clienteEntradas : clients) {
			clienteEntradas.recitalCancelado(nombre);
		}
		for (ClienteEntradas clienteEntradas : clientsVIP) {
			clienteEntradas.recitalCancelado(nombre);
		}

	}

	@Override
	public void solicitarEntrada(String nombreRecital, ClienteEntradas handler) throws RemoteException {
		synchronized (this) {
			Recital r = map.get(nombreRecital);
			if (r == null)
				return;
			switch (r.state) {
			case "A CONFIRMAR":
				r.countTickets++;
				if (r.countTickets < r.confirmationNumber) {
					if (r.countTickets <= r.VIPTickets) {
						clientsVIP.add(handler);
						handler.entradaReservada(nombreRecital);
					} else {
						clients.add(handler);
						handler.entradaReservada(nombreRecital);
					}
				} else if (r.countTickets == r.confirmationNumber) {
					r.state = "CONFIRMADO";
					clients.add(handler);
					for (ClienteEntradas clienteEntradas : clientsVIP) {
						clienteEntradas.entradaVipConfirmada(nombreRecital, "Entrada VIP");
					}
					for (ClienteEntradas clienteEntradas : clients) {
						clienteEntradas.entradaConfirmada(nombreRecital, "Entrada Pobre");
					}
				}
				break;
			case "CONFIRMADO":
				if (r.countTickets < r.maxCapacity) {
					r.countTickets++;
					clients.add(handler);
					handler.entradaConfirmada(nombreRecital, "Entrada Pobre");
					if (r.countTickets == r.maxCapacity) {
						r.state = "AGOTADO";
					}
				}
				break;
			case "AGOTADO":
				handler.recitalAgotado(nombreRecital);
				break;
			case "CANCELADO":
				handler.recitalCancelado(nombreRecital);
				break;

			default:
				break;
			}

		}
	}

}
