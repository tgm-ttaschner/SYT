package at.tm.rmi.client;

import java.net.*;

/**
 * @author Thomas Taschner
 * @version 04.01.2015
 * 
 * Creates a couple of clients, connects to the server and then lets the thread sleep for 2 seconds.
 * A couple of these threads are being used by the Run class.
 */
public class ConcurrentClientConnection implements Runnable	{
	@Override
	public void run()	{
		for (int i = 0; i < 10; i++)	{
			Client c;
			try {
				c = new Client(new URI("//localhost:5052"), i*2);
				c.connect();
			} catch (URISyntaxException e1) {
				System.out.println("The client couldn't connect to the server (make sure you entered the correct address and the sever is up and running)");
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("An error occurred while the thread tried to sleep");
			}
		}
	}
}