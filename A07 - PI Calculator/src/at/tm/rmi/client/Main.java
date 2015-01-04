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
		
		if (System.getSecurityManager() == null) {
        	try{
        	System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
        	}catch(Exception e){
        		System.err.println("policy file: java.policy was not found or could not be set as property");
        	}
            System.setSecurityManager(new SecurityManager());
        }
		
		CalculatorBalancer bal = new CalculatorBalancer(5052);
			
		try {
			Server s = new Server(5053, new URI("//192.168.0.22:5052"), "Server 1", new CalculatorImpl());
			Server s2 = new Server(5054, new URI("//192.168.0.22:5052"), "Server 2", new CalculatorImpl());
			Server s3 = new Server(5055, new URI("//192.168.0.22:5052"), "Server 3", new CalculatorImpl());
			
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Client c = new Client("192.168.0.22", 5052, 0);
		Client c2 = new Client("192.168.0.22", 5052, 5000);
		Client c3 = new Client("192.168.0.22", 5052, -2);
		c.connect();
		c2.connect();
		c3.connect();
	}
}
