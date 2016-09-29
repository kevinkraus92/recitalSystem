package itba.pod;

import java.rmi.Remote;

public interface ClienteEntradas extends Remote {
	public void entradaReservada(String recital);

	public void entradaVipConfirmada(String recital, String entrada);

	public void entradaConfirmada(String recital, String entrada);

	public void recitalAgotado(String recital);

	public void recitalCancelado(String recital);
}
