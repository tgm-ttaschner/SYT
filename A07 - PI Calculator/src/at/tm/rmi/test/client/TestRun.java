package at.tm.rmi.test.client;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.BeforeClass;
import org.junit.Test;

import at.tm.rmi.client.Run;

public class TestRun {

	@BeforeClass
	public static void setup()	{

	}

	@Test
	public void runOkBalancer() {
		String[] runbalancer = {"-b", "-p", "5052"};

		Run.main(runbalancer);
		
		//System.setOut(new PrintStream(new ByteArrayOutputStream()));
		
	}

	@Test
	public void runOkServer() {
		
		String[] runserver = {"-s", "-h", "localhost", "-p", "5052", "-n", "Server", "-S", "2", "-P", "5055"};

		Run.main(runserver);
		
	}

	@Test
	public void runOkClient() {
		String[] runserver = {"-c", "-h", "localhost", "-p", "5052", "-C", "1", "-d", "1"};

		Run.main(runserver);
	}
}