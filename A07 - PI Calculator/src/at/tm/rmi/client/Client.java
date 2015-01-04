package at.tm.rmi.client;

import java.rmi.registry.*;

import at.tm.rmi.server.Calculator;

public class Client {

	public void connect()	{
		try {
			Registry registry = LocateRegistry.getRegistry("192.168.0.22", 5052);
			Calculator stub = (Calculator) registry.lookup("Calculator");
			System.out.println("response: " + stub.pi(-1));
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}