package at.tm.rmi.test.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import at.tm.rmi.utils.PIArgs;

public class TestPIArgs {

	@Test
	public void PIArgsConstr() {
		PIArgs pi = new PIArgs("localhost", 5052, 1, 1, 1, "Server", 5055);
		
		String pic = ""+pi.getHostname()+pi.getPort()+pi.getServer_name()+pi.getServercount()+""+pi.getClientcount()+""+pi.getServerport()+""+pi.getDecimal_places();
		String exp = "localhost5052Server1150551";
		
		assertEquals(exp, pic);
		
	}

}
