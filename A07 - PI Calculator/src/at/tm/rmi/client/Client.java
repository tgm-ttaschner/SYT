package at.tm.rmi.client;

import java.net.URI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.tm.rmi.server.Calculator;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 07.01.2015
 * 
 * The client connects to the server to ask for pi (with a specified amount of digits) using an ip address, a port number and the amount of decimalplaces that should be calculated.
 * The class also provides a connect method which loads the registry of the balancer, looks up a calculator and returns the servers response.
 * An appropriate error message will be printed if something goes wrong.
 */
public class Client {

	private static final Logger LOGGER = LogManager.getLogger(Client.class);

	
	private int decimal_places;
	private URI address;

	/**
	 * Checks if a valid number was entered (>= 0) and then tries to set the address and the amount of decimal places of PI which should be calculated.
	 * If an invalid number for the decimal places is entered (< 0) then an InvalidArgumentException is thrown.
	 * Also catches a NumberFormatException in case something else than a number is entered.
	 * 
	 * @param address the server's ip address and port number (e.g. '//localhost:5052')
	 * @param decimal_places the amount of decimal places of PI the server will calculate
	 * @throws IllegalArgumentException thrown when an invalid number (< 0) for decimal_places was entered. Try entering a valid one (>= 0).
	 */
	public Client(URI address, int decimal_places) throws IllegalArgumentException {
		if (decimal_places < 0)	{
			if(address.getPort()<=0){
				throw new IllegalArgumentException("Please enter a valid portnumber (>0)");
			}else{
				throw new IllegalArgumentException("Please enter a valid number (>= 0) for the calculation of the decimal places of PI");
			}
		} else {
			try {
				this.address = address;
				this.decimal_places = decimal_places;
			} catch (NumberFormatException e) {
				LOGGER.error("Please enter a number for the calculation of the decimal places of PI");
			}
		}
	}

	/**
	 * Locates the registry, looks up a remote reference's name and returns the response.
	 * If no connection can be established then an exception is thrown.
	 * A RemoteException is caught when no connection to the target could be established.
	 * A NotBoundException is caught when the registry basically cannot be used for whatever reason.
	 * 
	 * @return the servers response, the calculated decimal places of PI up to a certain point
	 */
	public String connect() {
		try {
			Registry registry = LocateRegistry.getRegistry(this.address.getHost(), this.address.getPort());
			Calculator stub = (Calculator) registry.lookup("Calculator");
			//System.out.println("Client");
			return "response: " + stub.pi(this.decimal_places);
		} catch (RemoteException e) {
			LOGGER.error("The client couldn't connect with " + this.address.getHost() + "on port" + this.address.getPort());
		} catch (NotBoundException ex) {
			LOGGER.error("The client couldn't bind to the registry or look it up");
		}
		return null;
	}
}