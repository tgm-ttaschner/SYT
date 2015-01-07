package at.tm.rmi.server;

import java.io.Serializable;
import java.net.URI;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 05.01.2015
 * 
 *          Starts a server on a given port with a given name. It connects with
 *          a balancer via the balancers URL and transmits him a calculator
 *          object. To achieve that the server gets the balancers registry and
 *          looks a balancer object up. Then the server adds itself to the list
 *          (map) of available balancers and rebinds the balancer as a balancer
 *          and a calculator to the registry. The disconnect procedure works the
 *          same way. The only thing that is different is that the server
 *          doesn't need to get the balancers registry, also it gets removed
 *          instead of added. An error is printed if anything should go wrong.
 *          This class also provides getter and setter methods for its
 *          attributes.
 */
public class Server implements ServerInterface{

	private Calculator calc;

	private String name;
	private int port;
	private Registry registry;
	private BalancerInterface bal;

	/**
	 * @param port
	 *            the port the server will start on
	 * @param name
	 *            the name the server will have
	 * 
	 *            Creates a server on a given port with a given name.
	 */
	public Server(String name, int port, Calculator calcimpl) throws RemoteException{
		this.port = port;
		this.name = name;
		this.calc = calcimpl;

		this.registry = null;

		try {
			this.registry = LocateRegistry.createRegistry(this.getPort());
		} catch (RemoteException e) {
			System.out.println("A problem occured while creating a registry");
			System.exit(314);
		}

		Calculator stub = null;
		try {
			stub = (Calculator) UnicastRemoteObject.exportObject(this.getCalc(), this.getPort());
		} catch (RemoteException e) {
			System.err.println("A Problem occured while creating a stub object");
		}

		try {
			this.registry.rebind("Calculator", stub);
		} catch (Exception e) {
			System.err.println("A Problem occurred while rebinding the Calculator");
		}
			

	}

	/**
	 * @param balancer
	 *            the balancers URI (\\ IP or URL:port)
	 * @param calcimpl
	 *            the calculator object that will be added to the balancer
	 * 
	 *            Gets the given balancers registry, adds the calculator object
	 *            to the balancers list and rebinds the balancer. Prints out the
	 *            error if one should occur.
	 */
	public void connect(URI balancer) throws RemoteException{
		try {
			Registry regis = null;
			regis = LocateRegistry.getRegistry(balancer.getHost(), balancer.getPort());
			System.out.println("Getting Balancer Registry");
			
			bal = (BalancerInterface) regis.lookup("Calculator");
			System.out.println("Registry Lookup");
			
			bal.addServer(this.name, (ServerInterface) UnicastRemoteObject.exportObject(this, this.getPort()));
			System.out.println("Add Server");
//			Shouldn't be necessary to do
//			regis.rebind("Balancer", bal);
//			regis.rebind("Calculator", bal);
			
//			System.out.println("Registry Rebind");
			System.out.println("Server");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurred while " + this.getName() + " tried to connect.");
		}

	}

	/**
	 * Disconnects a calculator from the balancer. The balancers registry is
	 * looked up, the current server is removed and the balancer is rebound.
	 * Prints out the error if one should occur.
	 */
	public void disconnect() {
		try {
			registry.unbind(this.name);
			bal.removeServer(this.name);
		} catch (Exception e) {
			System.out.println("An error occurred while " + this.name + " tried to disconnect.");
		}
	}

	/**
	 * @return the calculator object
	 * 
	 *         Getter for calculator.
	 */
	public Calculator getCalc() throws RemoteException{
		return calc;
	}

	/**
	 * @param calc
	 *            the calculator object
	 * 
	 *            Setter for calculator.
	 * 
	 *            The balancers registry is looked up, the calculator object
	 *            added to the balancers list and the balancer gets rebound to
	 *            the registry. Prints out the error if one should occur and
	 *            terminates the program.
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

	/**
	 * @return the servers name
	 * 
	 *         Getter for name.
	 */
	public String getName() throws RemoteException{
		return name;
	}

	/**
	 * @param name
	 *            the servers name
	 * 
	 *            Setter for name.
	 */
	public void setName(String name) throws RemoteException{
		this.name = name;
	}

	/**
	 * @return the balancers registry
	 * 
	 *         Getter for registry.
	 */
	public Registry getRegistry() throws RemoteException{
		return registry;
	}

	/**
	 * @param registry
	 *            the balancers registry
	 * 
	 *            Setter for registry.
	 */
	public void setRegistry(Registry registry) throws RemoteException{
		this.registry = registry;
	}

	public int getPort() throws RemoteException{
		return port;
	}

	public void setPort(int port) throws RemoteException{
		this.port = port;
	}

}