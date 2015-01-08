package at.tm.rmi.test.server;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.BeforeClass;
import org.junit.Test;

import at.tm.rmi.server.CalculatorBalancer;
import at.tm.rmi.server.CalculatorImpl;
import at.tm.rmi.server.Server;

public class TestServer {

	static CalculatorBalancer cb;
	static Server s;

	@BeforeClass
	public static void setup() throws RemoteException, URISyntaxException {
		cb = new CalculatorBalancer(5052);
		s = new Server("Server 1", 5053, new CalculatorImpl());
		s.connect(new URI("//localhost:5052"));
	}

	@Test
	public void disconnect() {
		try {
			s.disconnect();
			assertNull(cb.getServers().get(s.getName()));
		} catch (RemoteException e) {
		}

	}

	@Test
	public void registry() {
		try {
			s.setRegistry(LocateRegistry.getRegistry(5052));
			assertEquals(LocateRegistry.getRegistry(5052), s.getRegistry());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void name() {
		try {
			s.setName("ServerTest");
			assertEquals("ServerTest", s.getName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
