package taschner_weinberger;

import org.apache.activemq.*;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 
 * Der Receiver wartet darauf, dass der Sender Nachrichten im bestimmten Chatraum uebermittelt, gibt diese aus und sendet dem Sender eine Bestaetigung.
 * 
 * Aenderung: Leere Nachrichten ("" oder null) werden nicht mehr ausgegeben.
 * 
 * Hier wird JMS Topic verwendet.
 * 
 * @author Thomas Taschner/Michael Weinberger 4AHITT
 * @version 20141126
 *
 */
public class JMSChatReceiver implements Runnable	{

	private String user;
	
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;

	private String subject;

	private String ip;

	private int port;

	private String url;
	
	/**
	 * 
	 * Erstellt ein neues JMSChatReceiver-Objekt mit den mitgegebenen Parametern.
	 * 
	 * @param ip Die IP-Adresse
	 * @param user Der Benutzername
	 * @param subject Der Chatraum
	 * @param port Die Portnummer
	 */
	public JMSChatReceiver(String ip, String user, String subject, int port) {
		this.ip = ip;
		this.user = user;
		this.subject = subject;
		this.port = port;
		
		url = "failover://tcp://" + ip + ":" + port;
		
	}

	/**
	 * 
	 * Liefert den Usernamen zurueck.
	 * 
	 * @return user Der Username, der zurueckgeliefert wird.
	 * 
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * Setzt den Usernamen auf den eingegebenen Wert.
	 * 
	 * @param user Der gewuenschte Username.
	 * 
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 
	 * Liefert das Passwort zurueck.
	 * 
	 * @return user Das Passwort, das zurueckgeliefert wird.
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * Setzt das Passwort auf den eingegebenen Wert.
	 * 
	 * @param user Das gewuenschte Passwort.
	 * 
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * Liefert den Namen des Chatraums zurueck.
	 * 
	 * @return user Der Name des Chatraums, der zurueckgeliefert wird.
	 * 
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 
	 * Setzt den Namen des Chatraums auf den eingegebenen Wert.
	 * 
	 * @param user Der gewuenschte Name des Chatraums.
	 * 
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 
	 * Liefert die IP-Adresse zurueck.
	 * 
	 * @return user Die IP-Adresse, der zurueckgeliefert wird.
	 * 
	 */
	public String getIp() {
		return ip;
	}
	
	/**
	 * 
	 * Setzt die IP-Adresse auf den eingegebenen Wert.
	 * 
	 * @param user Die gewuenschte IP-Adresse.
	 * 
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 
	 * Liefert den Port zurueck.
	 * 
	 * @return user Der Port, der zurueckgeliefert wird.
	 * 
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 
	 * Setzt den Port auf den eingegebenen Wert.
	 * 
	 * @param user Der gewuenschte Port.
	 * 
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * Liefert die URL zurueck.
	 * 
	 * @return user Die URL, die zurueckgeliefert wird.
	 * 
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * Setzt die URL auf den eingegebenen Wert.
	 * 
	 * @param user Die gewuenschte URL.
	 * 
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * Der Thread, in dem das Empfangen bei Aufruf abgearbeitet wird.
	 * 
	 */
	@Override
	public void run() {

		try {

			// Create the connection.
			Session session = null;
			Connection connection = null;
			MessageConsumer consumer = null;
			Destination destination = null;

			try {
				ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
				connection = connectionFactory.createConnection();
				connection.start();

				// Create the session
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				destination = session.createTopic(subject);

				// Create the consumer
				consumer = session.createConsumer(destination);

				while (true)	{

					// Start receiving
					TextMessage message = (TextMessage) consumer.receive();
					if (message != null && !message.getText().endsWith(" ")) {
						System.out.println(message.getText());
						message.acknowledge();
					}

				}

				//connection.stop();

			} catch (Exception e) {

				System.out.println("[MessageConsumer] Caught: " + e);
				e.printStackTrace();

			} finally {

				try {
					consumer.close();
				} catch (Exception e) {

				}
				try {
					session.close();
				} catch (Exception e) {

				}
				try {
					connection.close();
				} catch (Exception e) {

				}
			}

		} catch (Exception e)	{
			e.getMessage();
		}
	}
}