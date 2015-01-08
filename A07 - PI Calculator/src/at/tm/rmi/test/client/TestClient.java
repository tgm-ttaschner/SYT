package at.tm.rmi.test.client;

import static org.junit.Assert.*;

import org.junit.*;

import at.tm.rmi.client.Client;

public class TestClient {
	
	Client c;
	
	@Before
	public void setup()	{
		
	}

	@Test
	public void testValidConnection2Decimals() {
		c = new Client(address, decimal_places);
	}

}
