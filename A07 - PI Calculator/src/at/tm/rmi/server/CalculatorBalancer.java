package at.tm.rmi.server;

import java.math.BigDecimal;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 05.01.2015
 * 
 * A Balancer which also can calculate the decimal places of PI.
 * Upon initialization it binds itself in form of an exported UnicastRemoteObject to the registry in two ways, as a balancer and a calculator.
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
 * An appropriate error message will be printed out if something goes wrong.
 */
@SuppressWarnings("serial")
public class CalculatorBalancer implements BalancerInterface {

	private ConcurrentHashMap<String, ServerInterface> servers;
	private static int count;
	private int port;

	/**
	 * Sets up a balancer on a specified port.
	 * The constructor creates a registry on the given port and binds itself in form of an exported UnicastRemoteObject as a balancer and calculator to it.
	 * A RemoteException is caught if the registry cannot be accessed for any reason.
	 * 
	 * @param port the port the balancer operates on
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public CalculatorBalancer(int port) throws RemoteException {

		this.port = port;
		count = 1;

		System.out.println("Starting Balancer");

		servers = new ConcurrentHashMap<String, ServerInterface>();

		Registry registry = null;

		try {
			registry = LocateRegistry.createRegistry(this.getPort());
		} catch (RemoteException e) {
			System.out.println("A problem occured while creating a registry");
			System.exit(314);
		}
		try {
			registry.rebind("Calculator", UnicastRemoteObject.exportObject(this, this.getPort()));
		} catch (RemoteException e) {
			System.out.println("A problem occured while binding to its registry.");
			System.exit(3141);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.tm.rmi.server.Calculator#pi(int)
	 * 
	 * Input: the amount of decimal places of PI Output: the calculated decimal places of PI
	 * 
	 * The calculator objects and their names are stored in a ConcurrentHashMap (thread safe HashMap). 
	 * For better navigation the objects names are additionally stored in an String array.
	 * Then the calculator object gets assigned to a PI Server and count gets incremented.
	 * Finally the desired decimal places for the calculator object get returned.
	 * 
	 * A NotBoundException is caught if the registry cannot be loaded and/or looked up.
	 */
	@Override
	public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {

		String[] keys = new String[servers.size()];

		servers.keySet().toArray(keys);

		ServerInterface currServer = servers.get(keys[count % servers.size()]);

		count++;

		Calculator cal = null;
		try {
			cal = (Calculator) currServer.getRegistry().lookup("Calculator");
		} catch (NotBoundException e) {
			System.out.println("An error occurred while trying to load or lookup the registry");
		}
		return cal.pi(anzahl_nachkommastellen);
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.BalancerInterface#addServer(java.lang.String, at.tm.rmi.server.ServerInterface)
	 */
	public void addServer(String key, ServerInterface server) {
		this.getServers().put(key, server);
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.BalancerInterface#removeServer(java.lang.String)
	 */
	public void removeServer(String key) {
		this.getServers().remove(key);
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.BalancerInterface#getServers()
	 */
	public ConcurrentHashMap<String, ServerInterface> getServers() {
		return servers;
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.BalancerInterface#setServers(java.util.concurrent.ConcurrentHashMap)
	 */
	public void setServers(ConcurrentHashMap<String, ServerInterface> servers) {
		this.servers = servers;
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