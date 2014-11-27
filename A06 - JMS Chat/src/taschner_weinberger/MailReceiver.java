package taschner_weinberger;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.QueueSession;
import javax.jms.QueueReceiver;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;

/**
 * 
 * Der MailReceiver arbeitet private Nachrichten ab, indem er diese bei Aufruf des Befehls 'MAILBOX' entgegennehmen kann, und sie aus der Queue holt und ausgibt.
 * 
 * Bei mehreren Nachrichten muss mehrere Male 'MAILBOX' aufgerufen werden, bis keine neue Nachricht mehr vorhanden ist.
 * 
 * Hier wird die JMS Queue verwendet.
 * 
 * @author Thomas Taschner/Michael Weinberger 4AHITT
 * @version 20141127
 *
 */
public class MailReceiver implements Runnable	{

	private String ip;

	private int port;
	
	/**
	 * 
	 * Erstellt ein neues MailReceiver-Objekt mit den gegebenen Parametern.
	 * 
	 * @param ip Die IP-Adresse, die gespeichert wird.
	 * @param port Die Portnummer, die gespeichert wird.
	 * 
	 */
	public MailReceiver(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	/**
	 * 
	 * Liefert die IP-Adresse zurueck.
	 * 
	 * @return ip Die angeforderte IP-Adresse.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 
	 * Setzt die IP-Adresse auf den gewuenschten Wert.
	 * 
	 * @param ip Die mitgegebene IP-Adresse.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 
	 * Liefert die Portnummer zurueck.
	 * 
	 * @return port Die angeforderte Portnummer.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 
	 * Setzt die Portnummer auf den gewuenschten Wert.
	 * 
	 * @param port Die mitgegebene Portnummer.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * Der Thread, where all magic happens :)
	 * 
	 */
	@Override
	public void run() {
		try {

			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			env.put(Context.PROVIDER_URL, "tcp://"+ ip +":" + port);
			env.put("queue.queueSampleQueue", ip);
			// get the initial context
			InitialContext ctx = new InitialContext(env);

			// lookup the queue object
			Queue queue = (Queue) ctx.lookup("queueSampleQueue");

			// lookup the queue connection factory
			QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");

			// create a queue connection
			QueueConnection queueConn = connFactory.createQueueConnection();

			// create a queue session
			QueueSession queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// create a queue receiver
			QueueReceiver queueReceiver = queueSession.createReceiver(queue);

			// start the connection
			queueConn.start();

			// receive a message
			TextMessage message = (TextMessage) queueReceiver.receive();

			// print the message
			System.out.println(message.getText());

			// close the queue connection
			queueConn.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}