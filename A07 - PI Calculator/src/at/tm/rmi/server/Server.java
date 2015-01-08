package at.tm.rmi.server;

import java.net.URI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.tm.rmi.client.Client;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 05.01.2015
 * Starts a server on a given port with a given name.
 * It connects with a balancer via the balancers URL and transmits him a calculator object.
 * To achieve that the server gets the balancers registry and looks a balancer object up.
 * Then the server adds itself to the list (map) of available servers and rebinds the balancer as a calculator to the registry 
 * (The classes that need the balancer as Balancer can cast them to a BalancerInterface, thats why we don't cast the exported Object.
 * The disconnect procedure works the same way.
 * The only thing that is different is that the server doesn't need to get the balancers registry, also it gets removed instead of added.
 * An error is printed if anything should go wrong.
 * This class also provides getter and setter methods for its attributes.
 */
@SuppressWarnings("serial")
public class Server implements ServerInterface	{

	private static final Logger LOGGER = LogManager.getLogger(Server.class);
	
	private Calculator calc;
	private String name;
	private int port;
	private Registry registry;
	private BalancerInterface bal;

	/**
	 * @param port the port the server starts
	 * @param name the name the server gets
	 * @param calcimpl the PI calculation object (the implementation of a picalculator), basically the server's task
	 * @throws RemoteException is thrown when a remote error occurs (e.g. no connection to the server)
	 * 
	 * Creates a server on a given port with a given name with a given task.
	 * A local registry gets created on the given port.
	 * Then the object gets exported in form of an UnicastRemoteObject (locally) and finally gets bound to its own registry.
	 * 
	 * A RemoteException is caught when a remote error occurs.
	 */
	public Server(String name, int port, Calculator calcimpl) throws RemoteException{
		this.port = port;
		this.name = name;
		this.calc = calcimpl;

		this.registry = null;

		/* Tries to create a local registry */
		try {
			this.registry = LocateRegistry.createRegistry(this.getPort());
		} catch (RemoteException e) {
			LOGGER.error(this.getName() + " encountered a problem while creating a registry");
			System.exit(314);
		} catch (IllegalArgumentException e){
			LOGGER.error("Please enter a valid portnumber (>=1)");
		}

		Calculator stub = null;
		/* Tries to export itself */
		try {
			stub = (Calculator) UnicastRemoteObject.exportObject(this.getCalc(), this.getPort());
		} catch (RemoteException e) {
			LOGGER.error(this.getName() + "encountered a problem while creating a stub object");
		}

		/* Tries to rebind itself to its local registry */
		try {
			this.registry.rebind("Calculator", stub);
		} catch (Exception e) {
			LOGGER.error(this.getName() + " encountered a problem while rebinding the Calculator");
		}


	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.ServerInterface#connect(java.net.URI)
	 */
	public void connect(URI balancer) throws RemoteException{
		try {
			Registry regis = null;
			/* Loads the balancers registry */
			regis = LocateRegistry.getRegistry(balancer.getHost(), balancer.getPort());
			LOGGER.info("Getting Balancer Registry");

			/* The looksup the Balancer and casts it to the Interface 
			 * (the Balancer is named Calculato, because you cant export the same Object with two names) */
			bal = (BalancerInterface) regis.lookup("Calculator");
			LOGGER.info("Registry Lookup");

			/*
			 * The balancer add the servers name and the server in form of a UnicastRemoteObject.
			 * A NotBoundException is caught if the lookup cannot be done.
			 */
			bal.addServer(this.name, (ServerInterface) UnicastRemoteObject.exportObject(this, this.getPort()));
			LOGGER.info("Add Server");
			LOGGER.info("Server");
		} catch (NotBoundException e) {
			LOGGER.error(this.getName() + " cannot lookup an object which hasn't been bound");
		}

	}

	/**
	 * @throws RemoteException is thrown when a remote error occurrs (e.g. no connection to the server)
	 * 
	 * Disconnects by unbinding its Calculator from its own registry.
	 * It also tells the balancer that the server is no longer available by removing it from the balancers server list.
	 */
	public void disconnect() throws RemoteException {
		try {
			registry.unbind("Calculator");
			bal.removeServer(this.name);
		} catch (RemoteException e) {
			LOGGER.error(this.getName() + " couldn't connect to the specified ressource");
		} catch (NotBoundException e) {
			LOGGER.error(this.getName() + " cannot unbind a ressource which hasn't been bound");
		}
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.ServerInterface#getCalc()
	 */
	public Calculator getCalc() throws RemoteException{
		return calc;
	}

	/*
	 * @param calc the calculator object
	 * 
	 * Setter for calculator.
	 * 
	 * The balancers registry is looked up, the calculator object added to the balancers list and the balancer gets rebound to the registry.
	 * Prints out the error if one should occur and terminates the program.
	 */
	//	public void setCalc(Calculator calc) {
	//		this.calc = calc;
	//
	//		CalculatorBalancer bal;
	//		try {
	//			bal = (CalculatorBalancer) registry.lookup("Balancer");
	//			bal.addImplementation(this.name, this.calc);
	//			registry.rebind("Balancer", bal);
	//			registry.rebind("Calculator", bal);
	//		} catch (Exception e) {
	//			System.out.println("An Error occured");
	//			System.exit(314159);
	//		}
	//	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.ServerInterface#getName()
	 */
	public String getName() throws RemoteException{
		return name;
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.ServerInterface#setName(java.lang.String)
	 */
	public void setName(String name) throws RemoteException{
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.ServerInterface#getRegistry()
	 */
	public Registry getRegistry() throws RemoteException{
		return registry;
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.ServerInterface#setRegistry(java.rmi.registry.Registry)
	 */
	public void setRegistry(Registry registry) throws RemoteException{
		this.registry = registry;
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.ServerInterface#getPort()
	 */
	public int getPort() throws RemoteException{
		return port;
	}

	/* (non-Javadoc)
	 * @see at.tm.rmi.server.ServerInterface#setPort(int)
	 */
	public void setPort(int port) throws RemoteException{
		this.port = port;
	}
}