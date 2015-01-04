package at.tm.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import at.tm.rmi.server.Calculator;

public class Client {

	private Client() {
	}

	public static void main(String[] args) {

		// String host = (args.length < 1) ? null : args[0];
		try {
			Registry registry = LocateRegistry.getRegistry("192.168.0.22", 5052);
			Calculator stub = (Calculator) registry.lookup("Hello");
			String response = stub.pi();
			System.out.println("response: " + response);
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}