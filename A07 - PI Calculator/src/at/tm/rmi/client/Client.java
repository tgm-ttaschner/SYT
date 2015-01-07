package at.tm.rmi.client;

import java.net.URI;
import java.rmi.registry.*;

import at.tm.rmi.server.Calculator;

/**
 * @author Patrik Malik
 * @author Thomas Taschner
 * @version 07.01.2015
 * 
 * The client connects to the server to get the specified amount of digits of pi using an ip address, a port number and the amount of digits that should be calculated.
 * The class also provides a connect method which loads a local registry, looks up a calculator and returns the servers response.
 */
public class Client {

	private int decimal_places;
	private URI address;

	/**
	 * @param address the server's ip address and port number (e.g. '//localhost:5052')
	 * @param decimal_places the amount of decimal places of PI the server will calculate
	 */
	public Client(URI address, int decimal_places) throws IllegalArgumentException {
		if (decimal_places < 0)	{
			throw new IllegalArgumentException("Please enter a valid number (>= 0) for the calculation of the decimal places of PI");
		} else {
			try {
				this.address = address;
				this.decimal_places = decimal_places;
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number for the calculation of the decimal places of PI");
			}
		}
	}

	/**
	 * Locates the registry, looks up a remote reference's name and returns the response.
	 * If no connection can be established then an exception is thrown.
	 * 
	 * @return the servers response, the calculated decimal places of PI up to a certain point
	 */
	public String connect() {
		try {
			Registry registry = LocateRegistry.getRegistry(this.address.getHost(), this.address.getPort());
			Calculator stub = (Calculator) registry.lookup("Calculator");
			//System.out.println("Client");
			return "response: " + stub.pi(this.decimal_places);
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
		}
		return null;
	}
}