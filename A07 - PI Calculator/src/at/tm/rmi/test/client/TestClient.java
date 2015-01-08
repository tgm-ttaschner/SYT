package at.tm.rmi.test.client;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import org.junit.*;

import at.tm.rmi.client.Client;
import at.tm.rmi.server.CalculatorBalancer;
import at.tm.rmi.server.CalculatorImpl;
import at.tm.rmi.server.Server;

public class TestClient {
	
	static CalculatorBalancer cb;
	Client c;
	static Server s;
	
	@BeforeClass
	public static void setup() throws RemoteException, URISyntaxException	{
		cb = new CalculatorBalancer(5052);
		s = new Server("Server 1", 5053, new CalculatorImpl());
		s.connect(new URI("//localhost:5052"));
	}

	@Test
	public void testValidConnection2Decimals() throws IllegalArgumentException, URISyntaxException, RemoteException {
		c = new Client(new URI("//localhost:5052"), 2);
		
		assertEquals("response: 3.14", c.connect());
	}
	
	@Test
	public void testValidConnection0Decimals() throws IllegalArgumentException, URISyntaxException, RemoteException {
		c = new Client(new URI("//localhost:5052"), 0);
		
		assertEquals("response: 3", c.connect());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testValidConnectionNegative1Decimals() throws IllegalArgumentException, URISyntaxException, RemoteException {
		c = new Client(new URI("//localhost:5052"), -1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testValidConnection0Port() throws IllegalArgumentException, URISyntaxException, RemoteException {
		c = new Client(new URI("//localhost:0"), 5);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testValidConnection1Port() throws IllegalArgumentException, URISyntaxException, RemoteException {
		c = new Client(new URI("//localhost:0"), -50);
	}
	
	@Test
	public void testInvalidConnection() throws IllegalArgumentException, URISyntaxException, RemoteException {
		c = new Client(new URI("//remotehost:5052"), 5055);
		
		//ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		
		//System.setOut(new PrintStream(out));
		
		//System.setOut(null);
	}
}