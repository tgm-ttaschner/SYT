package at.tm.rmi.client;

import java.net.URI;
import java.rmi.registry.*;

import at.tm.rmi.server.Calculator;

/**
 * @author Patrik Malik
 * @author Thomas Taschner
 * @version 04.01.2015
 * 
 * The client connects to the server to get the specified amount of digits of pi using an ip address, a port number and the amount of digits that should be calculated.
 *
 */
public class Client {
	
	private int decimal_places;
	private URI address;
	
	/**
	 * @param ip the server's ip address
	 * @param port the server's port number
	 * @param decimal_places the amount of pi's decimal places you want the server to calculate
	 */
	public Client(URI address, int decimal_places)	{
		this.address = address;
		this.decimal_places = decimal_places;
	}
	
	/**
	 * Locates the registry, looks up a remote reference's name and prints out the response.
	 * If no connection can be established then an exception is thrown.
	 */
	public void connect()	{
		try {
			Registry registry = LocateRegistry.getRegistry(this.address.getHost(), this.address.getPort());
			Calculator stub = (Calculator) registry.lookup("Calculator");
			System.out.println("response: " + stub.pi(this.decimal_places));
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
		}
	}
}