package at.tm.rmi.server;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class CalculatorBalancer implements Calculator,Serializable {

	private List<Calculator> implementations;
	private int count;
	private int port;

	public CalculatorBalancer(int port) {
		this.port = port;
		count = 1;

		implementations = new ArrayList<Calculator>();
		
		// example //www.facebook.com:5052
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

	}

	@Override
	public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {

		Calculator currImplementation = implementations.get(count % implementations.size());
		count++;

		// Calculator stub = ;
		// try {
		// stub = (Calculator) registry.lookup("Calculator");
		// } catch (NotBoundException e) {
		//
		// }
		System.out.println(count);
		return currImplementation.pi(anzahl_nachkommastellen);
	}

	public void addImplementation(Calculator implementation) {
		this.getImplementations().add(implementation);
	}

	public List<Calculator> getImplementations() {
		return implementations;
	}

	public void setImplementations(List<Calculator> implementations) {
		this.implementations = implementations;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
