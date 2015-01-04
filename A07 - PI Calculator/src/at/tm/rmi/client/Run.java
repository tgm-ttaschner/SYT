package at.tm.rmi.client;

import java.net.URI;
import java.util.concurrent.*;

import at.tm.rmi.server.*;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 05.01.2015
 * 
 * Sets the security manager, starts (and connects) 3 servers, runs 3 concurrent clients (for testing purposes) and closes the server connections
 */
public class Run  {
	
	/**
	 * @param args input parameters
	 */
	public static void main(String[] args) {
		
		Server s1 = null;
		Server s2 = null;
		Server s3 = null;
		
		if (System.getSecurityManager() == null) {
        	try{
        	System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
        	}catch(Exception e){
        		System.err.println("policy file: java.policy was not found or could not be set as property");
        	}
            System.setSecurityManager(new SecurityManager());
        }
			
		try {
			
			s1 = new Server(5053, "Server 1");
			s2 = new Server(5054, "Server 2");
			s3 = new Server(5055, "Server 3");
			
			s1.connect(new CalculatorImpl(), new URI("//localhost:5052"));
			s2.connect(new CalculatorImpl(), new URI("//localhost:5052"));
			s3.connect(new CalculatorImpl(), new URI("//localhost:5052"));
			
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Couldn't start the servers");
		}

		int numWorkers = 3;
		int threadPoolSize = 3;

		ExecutorService tpes = Executors.newFixedThreadPool(threadPoolSize);

		ConcurrentClientConnection[] workers = new ConcurrentClientConnection[numWorkers];
		
		for (int i = 0; i < numWorkers; i++) {
			workers[i] = new ConcurrentClientConnection();
			tpes.execute(workers[i]);
		}
		
		tpes.shutdown();
		
		s1.disconnect();
		s2.disconnect();
		s3.disconnect();
	}
}