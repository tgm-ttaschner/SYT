package at.tm.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

	private Calculator calc;
	private int port;

	public Server(int port) {
		this.port = port;
	}

	public void bindToRegistry(Calculator calcimpl) throws RemoteException {

		Calculator stub = (Calculator) UnicastRemoteObject.exportObject(calcimpl, this.port);
		
		Registry registry = LocateRegistry.createRegistry(this.port);
		registry.rebind("Calculator", stub);
		calc = stub;
		
		System.err.println("Server ready");
	}
}