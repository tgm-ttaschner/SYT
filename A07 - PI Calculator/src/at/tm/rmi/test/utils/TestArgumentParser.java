package at.tm.rmi.test.utils;

import static org.junit.Assert.assertEquals;

import org.apache.commons.cli.Options;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import at.tm.rmi.utils.ArgumentParser;
import at.tm.rmi.utils.PIArgs;

public class TestArgumentParser {

	@Test
	public void parseArgumentsB() {
		String[] args = {"-b","-p","5052"};
		PIArgs pi = ArgumentParser.parseArguments(args);
		PIArgs ex = new PIArgs();
		ex.setType('b');
		ex.setPort(5052);
		String exp = ""+ex.getHostname()+ex.getPort()+ex.getServer_name()+ex.getServercount()+""+ex.getServerport()+ex.getType()+""+ex.getDecimal_places();
		String pic = ""+pi.getHostname()+pi.getPort()+pi.getServer_name()+pi.getServercount()+""+pi.getServerport()+pi.getType()+""+pi.getDecimal_places();
		
		assertEquals(exp,pic);
	}

	@Test
	public void parseArgumentsC() {
		ArgumentParser.options = new Options();
		String[] args = {"-c","-h","localhost","-p","5052","-d","2","-C","2"};
		PIArgs pi = ArgumentParser.parseArguments(args);
		PIArgs ex = new PIArgs();
		ex.setType('c');
		ex.setHostname("localhost");
		ex.setPort(5052);
		ex.setDecimal_places(2);
		ex.setClientcount(2);
		String exp = ""+ex.getHostname()+ex.getPort()+ex.getServer_name()+ex.getServercount()+""+ex.getServerport()+ex.getType()+""+ex.getDecimal_places();
		String pic = ""+pi.getHostname()+pi.getPort()+pi.getServer_name()+pi.getServercount()+""+pi.getServerport()+pi.getType()+""+pi.getDecimal_places();

		assertEquals(exp,pic);
	}
	
	@Test
	public void parseArgumentsS() {
		ArgumentParser.options = new Options();
		String[] args = {"-s","-h","localhost","-p","5052","-P","5055","-n","Server","-S","2"};
		PIArgs pi = ArgumentParser.parseArguments(args);
		PIArgs ex = new PIArgs();
		ex.setType('s');
		ex.setHostname("localhost");
		ex.setPort(5052);
		ex.setServerport(5055);
		ex.setServercount(2);
		ex.setServer_name("Server");
		String exp = ""+ex.getHostname()+ex.getPort()+ex.getServer_name()+ex.getServercount()+""+ex.getServerport()+ex.getType()+""+ex.getDecimal_places();
		String pic = ""+pi.getHostname()+pi.getPort()+pi.getServer_name()+pi.getServercount()+""+pi.getServerport()+pi.getType()+""+pi.getDecimal_places();

		assertEquals(exp,pic);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void parseArgumentsInvalid() {
		ArgumentParser.options = new Options();
		String[] args = {"-c","-h","localhost","-p","5052","-d","2","-C","2","-b"};
		PIArgs pi = ArgumentParser.parseArguments(args);
	}
}
