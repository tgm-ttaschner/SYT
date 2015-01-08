package at.tm.rmi.server;

import java.io.Serializable;
import java.rmi.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Patrick Malik
 * @version 08.01.2015
 * 
 * Interface of the CalculatorBalancer class.
 * This Interface extends the Interfaces Calculator, Serializable and Remote.
 * 
 * It implements the Calculator Interface to look like a Calculator, so the client can use the Balacer as a Calculator.
 * The Serializable Interface gets implemented to support the Transportation of the Balancer 
 * 	(export and binding to the registry, see {@link CalculatorBalancer})
 * It implements the Remote Interface for functional reasons, so we can bind it to its registry and use it from another Object.
 * 
 * It provides all essential methods for server and port management.
 */
public interface BalancerInterface extends Calculator, Serializable, Remote	{
	
	/**
	 * Adds a server which can provide a {@link CalculatorImpl}.
	 * 
	 * @param key the servers name
	 * @param serverInterface the server object (written as Interface to support the exporting)
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public void addServer(String key, ServerInterface serverInterface) throws RemoteException;
	
	/**
	 * Removes a server which can provide a {@link CalculatorImpl}.
	 * 
	 * @param key the {@link Server} name
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public void removeServer(String key) throws RemoteException;
	
	/**
	 * Returns the ConcurrentHashMap where all the {@link Server} and its names are stored.
	 * 
	 * @return the map containing the {@link Server} and its names
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public ConcurrentHashMap<String, ServerInterface> getServers() throws RemoteException;
	
	/**
	 * Sets a ConcurrentHashMap where all the {@link Server} and its names are stored.
	 * 
	 * @param servers the map containing all the balancer servers and its names
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public void setServers(ConcurrentHashMap<String, ServerInterface> servers) throws RemoteException;
	
	/**
	 * Gets the port the balancer operates on.
	 * 
	 * @return the port the balancer operates on
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public int getPort() throws RemoteException;
	
	/**
	 * Sets the port the balancer should operate on.
	 * 
	 * @param port the port the balancer should operate on
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public void setPort(int port) throws RemoteException;
}