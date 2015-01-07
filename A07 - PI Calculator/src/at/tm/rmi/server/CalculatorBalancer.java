package at.tm.rmi.server;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 05.01.2015
 * 
 * A Balancer which also can calculate the decimal places of PI.
 * Upon initialization it binds itself to the registry in two ways, as a balancer and a calculator.
 * An exception is thrown if an error should occur.
 * 
 * The round robin principle is used to balance the workload by using a counter.
 * The server for calculating the workload gets chosen by taking the remainder of dividing count through the amount of available servers.
 * This way the workload gets distributed evenly.
 * 
 * A ConcurrentHashMap (thread safe) is used to store the object together with its name.
 * An additional String array only consisting of the keys is used for navigation (a navigable concurrent HashMap would be a big help).
 * This class also provides getter and setter methods for all its attributes.
 * 
 */
@SuppressWarnings("serial")
public class CalculatorBalancer implements Calculator, Serializable {

	private ConcurrentHashMap<String, Calculator> implementations;
	private static int count;
	private int port;

	/**
	 * @param port the port on which the balancer starts
	 * 
	 * Sets up a balancer on a specified port.
	 * The constructor creates a registry on the given port and binds itself as a balancer and calculator to it.
	 * An exception is thrown and the program terminates itself if an error occurrs.
	 */
	public CalculatorBalancer(int port) {
		this.port = port;
		count = 1;
		
		System.out.println("Starting Balancer");

		implementations = new ConcurrentHashMap<String, Calculator>();
		
		Registry registry = null;
		
		try {
			registry = LocateRegistry.createRegistry(this.getPort());
		} catch (RemoteException e) {
			System.out.println("A problem occured while creating a registry");
			System.exit(314);
		}
		try {
			registry.rebind("Balancer", this);
			registry.rebind("Calculator", this);
		} catch (RemoteException e) {
			System.out.println("A problem occured while binding to its registry.");
			System.exit(3141);
		}
		
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.Calculator#pi(int)
	 * 
	 * Input: the amount of decimal places of PI
	 * Output: the calculated decimal places of PI
	 * 
	 * The calculator objects and their names are stored in a ConcurrentHashMap (thread safe HashMap).
	 * For better navigatability (gibts das Wort überhaupt?) the objects names are additionally stored in an String array.
	 * Then the calculator object gets assigned to a PI Server and count gets incremented.
	 * Finally the desired decimal places for the calculator object get returned.
	 */
	@Override
	public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {

		String[] keys = new String[implementations.size()];
		
		implementations.keySet().toArray(keys);
		
		Calculator currImplementation = implementations.get(keys[count % implementations.size()]);
		
		//System.out.println(keys[count % implementations.size()]);
		
		count++;
		
		//System.out.println(count);
		
		return currImplementation.pi(anzahl_nachkommastellen);
	}

	/**
	 * @param key the objects name
	 * @param implementation the calculator object
	 * 
	 * Adds a calculator object together with its name.
	 */
	public void addImplementation(String key, Calculator implementation) {
		this.getImplementations().put(key, implementation);
	}
	
	/**
	 * @param key the objects name
	 * 
	 * Removes an object from the map via its key (name).
	 */
	public void removeImplementation(String key)	{
		this.getImplementations().remove(key);
	}

	/**
	 * @return the map itself
	 * 
	 * Getter for the ConcurrentHashMap.
	 */
	public ConcurrentHashMap<String, Calculator> getImplementations() {
		return implementations;
	}

	/**
	 * @param implementations the map
	 * 
	 * Setter for the ConcurrentHashMap.
	 */
	public void setImplementations(ConcurrentHashMap<String, Calculator> implementations) {
		this.implementations = implementations;
	}

	/**
	 * @return count (the counter)
	 * 
	 * Getter for count.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the counter variable
	 * 
	 * Setter for count.
	 */
	public void setCount(int count) {
		CalculatorBalancer.count = count;
	}

	/**
	 * @return the port
	 * 
	 * Getter for port.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port
	 * 
	 * Setter for Port.
	 */
	public void setPort(int port) {
		this.port = port;
	}
}