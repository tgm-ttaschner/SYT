package taschner_weinberger.testing;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

import taschner_weinberger.JMSChatCLI;
import taschner_weinberger.JMSChatReceiver;
import taschner_weinberger.JMSChatSender;

/**
 * @author Thomas Taschner
 * 
 * In dieser Klasse wird der JMS Chat auf seine korrekte Funktionsweise ueberprueft.
 * Der Einfachheit halber wird davon ausgegangen, dass der Broker auf localhost:61616 ausgefuehrt wird.
 * In der Praxis sind aber auch Verbindung nach und von Drauﬂen auf jedem beliebigen freien Port moeglich.
 *
 */
public class TestJMSChat {

	@Test
	public void testOKRun3Args() {
		String[] main = {"localhost", "test", "room123"};

		// Nur zu Coveragezwecken
		new JMSChatCLI().main(main);
	}

	@Test
	public void testOKRun4Args() {
		String[] main = {"localhost", "test", "room123", "61616"};
		JMSChatCLI.main(main);
	}


	@Test
	public void testRun0Args() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		//redirect the System-output (normaly the console) to a variable
		System.setErr(new PrintStream(outContent));

		//call your method here
		String[] main = {};
		JMSChatCLI.main(main);

		//check if your error message is in the output variable
		assertEquals("Fehlerhafte Parameter!", outContent.toString());
	}

	@Test
	public void testRunNullArgs() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		//redirect the System-output (normaly the console) to a variable
		System.setErr(new PrintStream(outContent));

		//call your method here
		JMSChatCLI.main(null);

		//check if your error message is in the output variable
		assertEquals("Null Parameter!", outContent.toString());
	}

	@Test
	public void testChatReceiverConstructor() {
		new JMSChatReceiver("localhost", "test123", "abc", 61616);
	}

	@Test
	public void testChatReceiverUserAttribute() {
		JMSChatReceiver r = new JMSChatReceiver("localhost", "test123", "abc", 61616);
		r.setUser("tt");
		assertEquals("tt", r.getUser());
	}

	@Test
	public void testChatReceiverPasswordAttribute() {
		JMSChatReceiver r = new JMSChatReceiver("localhost", "test123", "abc", 61616);
		r.setPassword("letmein");
		assertEquals("letmein", r.getPassword());
	}

	@Test
	public void testChatReceiverSubjectAttribute() {
		JMSChatReceiver r = new JMSChatReceiver("localhost", "test123", "abc", 61616);
		r.setSubject("topic123");
		assertEquals("topic123", r.getSubject());
	}

	@Test
	public void testChatReceiverIPAttribute() {
		JMSChatReceiver r = new JMSChatReceiver("localhost", "test123", "abc", 61616);
		r.setIp("1.2.3.4");
		assertEquals("1.2.3.4", r.getIp());
	}

	@Test
	public void testChatReceiverPortAttribute() {
		JMSChatReceiver r = new JMSChatReceiver("localhost", "test123", "abc", 61616);
		r.setPort(1234);
		assertEquals(1234, r.getPort());
	}

	@Test
	public void testChatReceiverURLAttribute() {
		JMSChatReceiver r = new JMSChatReceiver("localhost", "test123", "abc", 61616);
		r.setUrl("failover://tcp://" + "4.5.6.7" + ":" + 6789);
		assertEquals("failover://tcp://" + "4.5.6.7" + ":" + 6789, r.getUrl());
	}
	
	/*
	@Test
	public void testChatReceiverRun() {
		//JMSChatReceiver r = new JMSChatReceiver("localhost", "test123", "abc", 61616);

		byte[] input = "test".getBytes();
		ByteArrayInputStream in = new ByteArrayInputStream(input);
		System.setIn(in);

		// optionally, reset System.in to its original
		System.setIn(System.in);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		//redirect the System-output (normaly the console) to a variable
		System.setOut(new PrintStream(outContent));

		//call your method here
		new Thread(new JMSChatSender("localhost", "test123", "abc", 61616)).run();
		new Thread(new JMSChatReceiver("localhost", "test123", "abc", 61616)).run();

		//check if your error message is in the output variable
		assertEquals("test", outContent.toString());
	}
	
	*/
}