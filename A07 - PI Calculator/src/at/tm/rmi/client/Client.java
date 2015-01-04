package at.tm.rmi.client;

import java.rmi.registry.*;

import at.tm.rmi.server.Calculator;

public class Client {
	
	private String ip;
	private int port;
	
	private int decimal_places;
	
	public Client(String ip, int port, int decimal_places)	{
		this.ip = ip;
		this.port = port;
		this.decimal_places = decimal_places;
	}

	public void connect()	{
		try {
			Registry registry = LocateRegistry.getRegistry(this.ip, this.port);
			Calculator stub = (Calculator) registry.lookup("Calculator");
			System.out.println("response: " + stub.pi(this.decimal_places));
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}