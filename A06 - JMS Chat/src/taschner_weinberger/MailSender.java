package taschner_weinberger;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.QueueSender;
import javax.jms.DeliveryMode;
import javax.jms.QueueSession;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;

/**
 * 
 * Der MailSender arbeitet private Nachrichten ab, indem er diese an den Empfaenger schickt, sprich in die Queue schreibt, bis diese vom Receiver verarbeitet werden zu einem unbestimmten Zeitpunkt.
 * 
 * Hier wird die JMS Queue verwendet.
 * 
 * @author Thomas Taschner/Michael Weinberger 4AHITT
 * @version 20141127
 *
 */
public class MailSender	implements Runnable	{

	private String ip;
	
	private String username;
	
	private String textmessage;

	private int port;

	/**
	 * 
	 * Erstellt ein neues MailSender-Objekt mit den gegebenen Parametern.
	 * 
	 * @param ip Die IP-Adresse, die gespeichert wird.
	 * @param port Die Portnummer, die gespeichert wird.
	 * @param message Die Nachricht, die verschickt werden soll.
	 * 
	 */
	public MailSender(String ip, int port, String message) {
		this.ip = ip;
		this.textmessage = message;
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
	 * @return ip Die mitgegebene IP-Adresse.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 
	 * Liefert den Usernamen zurueck.
	 * 
	 * @return user Der angeforderte Username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * Setzt den Usernamen auf den gewuenschten Wert.
	 * 
	 * @return user Der mitgegebene Username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * Die Nachricht, die verschickt werden soll wird hier zurueckgegeben.
	 * 
	 * @return msg Die Textnachricht.
	 */
	public String getTextmessage() {
		return textmessage;
	}

	/**
	 * 
	 * Hier kann die zu schickende Textnachricht haendisch manipuliert werden.
	 * 
	 * @param textmessage Die gewuenschte Textnachricht.
	 */
	public void setTextmessage(String textmessage) {
		this.textmessage = textmessage;
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
	 * @return port Die mitgegebene Portnummer.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * Der Thread, der bei Aufruf die Nachricht losschickt.
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
			QueueSession queueSession = queueConn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

			// create a queue sender
			QueueSender queueSender = queueSession.createSender(queue);
			queueSender.setDeliveryMode(DeliveryMode.PERSISTENT);

			// create a simple message to say "Hello"
			
			if (textmessage.startsWith("MAIL " + ip))	{
				textmessage = textmessage.substring(("MAIL " + ip).length()).trim();
			}
			
			TextMessage message = queueSession.createTextMessage("MAIL FROM " + "[" + ip + "]: " + textmessage);

			// send the message
			queueSender.send(message);

			System.out.println("[sent] " + message.getText());

			//queueConn.close();

		} catch (Exception e)	{
			e.getMessage();
		}

	}
}