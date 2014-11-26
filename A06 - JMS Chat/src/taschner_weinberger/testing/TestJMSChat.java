package taschner_weinberger.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import taschner_weinberger.JMSChatCLI;

/**
 * @author Thomas Taschner
 * 
 * In dieser Klasse wird der JMS Chat auf seine korrekte Funktionsweise ueberprueft.
 *
 */
public class TestJMSChat {

	@Test
	public void testOKRun3Args() {
		String[] main = {"localhost", "test", "room123"};
		JMSChatCLI.main(main);
	}
	
	@Test
	public void testOKRun4Args() {
		String[] main = {"localhost", "test", "room123", "61616"};
		JMSChatCLI.main(main);
	}

}
