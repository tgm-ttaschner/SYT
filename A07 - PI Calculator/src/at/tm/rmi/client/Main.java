package at.tm.rmi.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import at.tm.rmi.server.CalculatorBalancer;
import at.tm.rmi.server.CalculatorImpl;
import at.tm.rmi.server.Server;

public class Main {
	public static void main(String[] args) {
		
		CalculatorBalancer bal = new CalculatorBalancer(5052);
			
		try {
			Server s = new Server(5053, new URI("//192.168.0.22:5052"), new CalculatorImpl());
			Server s2 = new Server(5054, new URI("//192.168.0.22:5052"), new CalculatorImpl());
			Server s3 = new Server(5055, new URI("//192.168.0.22:5052"), new CalculatorImpl());
			
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Client c = new Client("192.168.0.22", 5052, 5);
		Client c2 = new Client("192.168.0.22", 5052, 6);
		Client c3 = new Client("192.168.0.22", 5052, 7);
		c.connect();
		c2.connect();
		c3.connect();
	}
}
