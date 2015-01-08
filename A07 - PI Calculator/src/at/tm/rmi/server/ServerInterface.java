package at.tm.rmi.server;

import java.io.Serializable;
import java.net.URI;
import java.rmi.*;
import java.rmi.registry.Registry;

/**
 * @author Patrick Malik
 * @version 08.01.2015
 * 
 * Interface of the Server class.
 * This Interface extends the Interfaces Serializable and Remote.
 * The extending of those two Interfaces in more precisely described in {@link BalancerInterface}
 * It provides all essential methods for connection, PI calculation, name, registry and port management.
 */
public interface ServerInterface extends Remote, Serializable	{
	
	/**
	 * Connects a server to the a balancer which necessary connectionarguments are given as parameters.
	 * 
	 * @param balancer the URI the balancer runs on
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public void connect(URI balancer) throws RemoteException;
	
	/**
	 * Getter for calculator.
	 * 
	 * @return the calculator object
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public Calculator getCalc() throws RemoteException;
	
	/**
	 * Getter for name.
	 * 
	 * @return the servers name
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public String getName() throws RemoteException;
	
	/**
	 * Setter for name.
	 * 
	 * @param name the name the server will have
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public void setName(String name) throws RemoteException;
	
	/**
	 * Getter for registry.
	 * 
	 * @return the server registry
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public Registry getRegistry() throws RemoteException;
	
	/**
	 * Setter for registry.
	 * 
	 * @param registry the servers registry
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public void setRegistry(Registry registry) throws RemoteException;
	
	/**
	 * Getter for port.
	 * 
	 * @return the port the server runs on
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 */
	public int getPort() throws RemoteException;
	
}