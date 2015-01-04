package at.tm.rmi.server;

import java.net.URI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Server {

	private Calculator calc;
	private int port;
	
	private String name;
	
	private Registry registry;

	public Server(int port, URI balancer, String name, Calculator calcimpl) throws RemoteException, NotBoundException {
		this.port = port;
		this.name = name;
		
		registry = LocateRegistry.getRegistry(balancer.getHost(), balancer.getPort());
		CalculatorBalancer bal = (CalculatorBalancer) registry.lookup("Balancer");
		bal.addImplementation(this.name, calcimpl);
		registry.rebind("Balancer", bal);
		registry.rebind("Calculator", bal);
	}

	// public void bindToRegistry(Calculator calcimpl) throws RemoteException {
	//
	// Calculator stub = (Calculator) UnicastRemoteObject.exportObject(calcimpl,
	// this.port);
	//
	// Registry registry = LocateRegistry.createRegistry(this.port);
	// registry.rebind("Calculator", stub);
	// calc = stub;
	// System.err.println("Server ready");
	// }

	public Calculator getCalc() {
		return calc;
	}

	public void setCalc(Calculator calc) {
		this.calc = calc;
		
		CalculatorBalancer bal;
		try {
			bal = (CalculatorBalancer) registry.lookup("Balancer");
			bal.addImplementation(this.name, this.calc);
			registry.rebind("Balancer", bal);
			registry.rebind("Calculator", bal);
		} catch (RemoteException | NotBoundException e) {
			System.out.println("An Error occured");
			System.exit(314159);
		}
		
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}
}