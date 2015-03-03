package org.apache.camel.example.etl;

import static org.junit.Assert.*;

import org.junit.*;

public class TestPersonDocument {
	
	private PersonDocument pd;
	
	@Before
	public void setUp()	{
		pd = new PersonDocument();
	}

	@Test
	public void testCity() {
		pd.setCity("Wien");
		assertEquals("Wien", pd.getCity());
	}
	
	@Test
	public void testFirstName() {
		pd.setFirstName("Herbert");
		assertEquals("Herbert", pd.getFirstName());
	}
	
	@Test
	public void testLastName() {
		pd.setLastName("Eichelmann");
		assertEquals("Eichelmann", pd.getLastName());
	}
	
	@Test
	public void testToString() {
		pd.setUser("BeidelDondi");
		assertEquals("Person[user: BeidelDondi]", pd.toString());
	}

}