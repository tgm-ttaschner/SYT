package at.tm.rmi.client;

import java.rmi.RemoteException;

import at.tm.rmi.server.CalculatorImpl;
import at.tm.rmi.server.Server;

public class Main {
	public static void main(String[] args) {
		Server s = new Server(5052);
		try {
			s.bindToRegistry(new CalculatorImpl());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Client c = new Client("localhost", 5052, 5);
		c.connect();
	}
}
