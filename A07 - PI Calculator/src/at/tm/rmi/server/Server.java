package at.tm.rmi.server;

import java.net.URI;
import java.rmi.registry.*;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 05.01.2015
 * 
 * Starts a server on a given port with a given name.
 * It connects with a balancer via the balancers URL and transmits him a calculator object.
 * To achieve that the server gets the balancers registry and looks a balancer object up.
 * Then the server adds itself to the list (map) of available balancers and rebinds the balancer as a balancer and a calculator to the registry.
 * The disconnect procedure works the same way.
 * The only thing that is different is that the server doesn't need to get the balancers registry, also it gets removed instead of added.
 * An error is printed if anything should go wrong.
 * This class also provides getter and setter methods for its attributes.
 */
public class Server {

	private Calculator calc;
	
	private String name;
	
	private Registry registry;

	/**
	 * @param port the port the server will start on
	 * @param name the name the server will have
	 * 
	 * Creates a server on a given port with a given name.
	 */
	public Server(String name) {
		this.name = name;
	}
	
	/**
	 * @param balancer the balancers URI (\\ IP or URL:port)
	 * @param calcimpl the calculator object that will be added to the balancer
	 * 
	 * Gets the given balancers registry, adds the calculator object to the balancers list and rebinds the balancer.
	 * Prints out the error if one should occur.
	 */
	public void connect(URI balancer, Calculator calcimpl)	{
		try {
			registry = LocateRegistry.getRegistry(balancer.getHost(), balancer.getPort());
			System.out.println("Getting Registry");
			CalculatorBalancer bal = (CalculatorBalancer) registry.lookup("Balancer");
			System.out.println("Registry Lookup");
			bal.addImplementation(this.name, calcimpl);
			System.out.println("Add Impl");
			registry.rebind("Balancer", bal);
			registry.rebind("Calculator", bal);
			System.out.println("Registry Rebind");
			System.out.println("Server");
		} catch (Exception e) {
			System.out.println("An error occurred while " + this.getName() + " tried to connect.");
		}
		
	}
	
	/**
	 * Disconnects a calculator from the balancer.
	 * The balancers registry is looked up, the current server is removed and the balancer is rebound.
	 * Prints out the error if one should occur.
	 */
	public void disconnect()	{
		try {
			CalculatorBalancer bal = (CalculatorBalancer) registry.lookup("Balancer");
			bal.removeImplementation(this.name);
			registry.rebind("Balancer", bal);
			registry.rebind("Calculator", bal);
		} catch (Exception e) {
			System.out.println("An error occurred while " + this.getName() + " tried to disconnect.");
		}
	}

	/**
	 * @return the calculator object
	 * 
	 * Getter for calculator.
	 */
	public Calculator getCalc() {
		return calc;
	}

	/**
	 * @param calc the calculator object
	 * 
	 * Setter for calculator.
	 * 
	 * The balancers registry is looked up, the calculator object added to the balancers list and the balancer gets rebound to the registry.
	 * Prints out the error if one should occur and terminates the program.
	 */
	public void setCalc(Calculator calc) {
		this.calc = calc;
		
		CalculatorBalancer bal;
		try {
			bal = (CalculatorBalancer) registry.lookup("Balancer");
			bal.addImplementation(this.name, this.calc);
			registry.rebind("Balancer", bal);
			registry.rebind("Calculator", bal);
		} catch (Exception e) {
			System.out.println("An Error occured");
			System.exit(314159);
		}
	}

	/**
	 * @return the servers name
	 * 
	 * Getter for name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the servers name
	 * 
	 * Setter for name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the balancers registry
	 * 
	 * Getter for registry.
	 */
	public Registry getRegistry() {
		return registry;
	}

	/**
	 * @param registry the balancers registry
	 * 
	 * Setter for registry.
	 */
	public void setRegistry(Registry registry) {
		this.registry = registry;
	}
}