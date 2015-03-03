package org.apache.camel.example.etl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestCustomerEntity {
	
	private CustomerEntity ce;
	
	@Before
	public void setUp()	{
		ce = new CustomerEntity();
		ce.setPhone("0123456789");
		ce.setStreet("Wexstraße 12/34");
		ce.setZip("1020 Wien");
	}

	@Test
	public void testGetPhone() {
		assertEquals("0123456789", ce.getPhone());
	}
	
	@Test
	public void testGetStreet() {
		assertEquals("Wexstraße 12/34", ce.getStreet());
	}
	
	@Test
	public void testGetZip() {
		assertEquals("1020 Wien", ce.getZip());
	}

}