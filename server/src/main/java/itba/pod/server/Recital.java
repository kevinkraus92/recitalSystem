package itba.pod.server;

public class Recital {

	static int idCount;

	int id;
	String name;
	int confirmationNumber;
	int maxCapacity;
	int VIPTickets;
	
	int countTickets;

	String state;

	public Recital(String name, int confirmationNumber, int maxCapacity, int vIPTickets) {
		super();
		this.id = idCount++;
		this.confirmationNumber = confirmationNumber;
		this.maxCapacity = maxCapacity;
		VIPTickets = vIPTickets;
		this.state = "A confirmar";
		this.name = name;
		this.countTickets=0;
	}

}
