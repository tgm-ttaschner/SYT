package at.tm.rmi.test.server;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import at.tm.rmi.server.CalculatorBalancer;
import at.tm.rmi.server.CalculatorImpl;
import at.tm.rmi.server.Server;
import at.tm.rmi.server.ServerInterface;

public class TestCalculatorBalancer {

	static CalculatorBalancer cb;
	static Server s;
		
	@BeforeClass
	 public static void setup() throws RemoteException, URISyntaxException {
	  cb = new CalculatorBalancer(5052);
	  s = new Server("Server 1", 5053, new CalculatorImpl());
	  s.connect(new URI("//localhost:5052"));
	 }
	
	@Before
	public void count(){
		cb.setCount(1);
	}
	
	@Test
	public void Constructor(){
		String bal = ""+cb.getPort()+""+cb.getCount();
		String exp = "50521";
		assertEquals(exp,bal);
	}
	
	@Test
	public void pi(){
		try {
			assertEquals("3.14",""+cb.pi(2));
		} catch (RemoteException e) {
		}
	}
	
	@Test
	public void setPort(){
		cb.setPort(5099);
		assertEquals(5099,cb.getPort());
	}

}
