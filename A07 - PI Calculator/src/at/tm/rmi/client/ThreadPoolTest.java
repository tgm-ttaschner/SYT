package at.tm.rmi.client;

import java.net.URI;
import java.util.concurrent.*;

import at.tm.rmi.server.CalculatorBalancer;
import at.tm.rmi.server.CalculatorImpl;
import at.tm.rmi.server.Server;

public class ThreadPoolTest 	{
	
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
		
		CalculatorBalancer bal = new CalculatorBalancer(5052);
			
		try {
			s1 = new Server(5053, "Server 1");
			s2 = new Server(5054, "Server 2");
			s3 = new Server(5055, "Server 3");
			
			s1.connect(new CalculatorImpl(), new URI("//localhost:5052"));
			s2.connect(new CalculatorImpl(), new URI("//localhost:5052"));
			s3.connect(new CalculatorImpl(), new URI("//localhost:5052"));
			
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		int numWorkers = 3;
		int threadPoolSize = 3;

		ExecutorService tpes = Executors.newFixedThreadPool(threadPoolSize);

		Main[] workers = new Main[numWorkers];
		
		for (int i = 0; i < numWorkers; i++) {
			workers[i] = new Main();
			tpes.execute(workers[i]);
		}
		
		tpes.shutdown();
		
		
		s1.disconnect();
		s2.disconnect();
		s3.disconnect();
	}
}