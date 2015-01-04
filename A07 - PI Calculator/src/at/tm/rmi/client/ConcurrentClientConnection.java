package at.tm.rmi.client;

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
			Client c = new Client("localhost", 5052, i*2);
			c.connect();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}