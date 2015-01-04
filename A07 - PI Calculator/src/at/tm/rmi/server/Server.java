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

	public Server(int port, URI balancer, Calculator calcimpl) throws RemoteException, NotBoundException {
		this.port = port;
		
		Registry registry = LocateRegistry.getRegistry(balancer.getHost(), balancer.getPort());
		CalculatorBalancer bal = (CalculatorBalancer) registry.lookup("Balancer");
		bal.addImplementation(calcimpl);
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
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}