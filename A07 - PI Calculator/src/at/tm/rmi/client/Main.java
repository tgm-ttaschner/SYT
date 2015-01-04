package at.tm.rmi.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import at.tm.rmi.server.CalculatorBalancer;
import at.tm.rmi.server.CalculatorImpl;
import at.tm.rmi.server.Server;

public class Main implements Runnable	{
	
	@Override
	public void run()	{
		
		
		
		
		for (int i = 0; i < 10; i++)	{
			Client c = new Client("localhost", 5052, i*1000);
			c.connect();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
}
