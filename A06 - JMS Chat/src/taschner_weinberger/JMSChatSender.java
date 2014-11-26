package taschner_weinberger;

import java.io.*;

import org.apache.activemq.*;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 
 * Bei Aufruf des Threads wird der Inhalt der Konsoleneingabe genommen und an einen Receiver mit spezifischen Chatraum weitergeleitet. (natuerlich beachte man, dass der aktive Prozess beim Broker stattfindet)
 * 
 * Des Weiteren wird ueberprueft, ob als erstes Wort 'MAIL' zum Verschicken oder 'MAILBOX' zum Empfangen von Privatnachrichten steht. Ist dies der Fall, so wird entsprechender Thread aufgerufen.
 * 
 * @author Thomas Taschner/Michael Weinberger 4AHITT
 * @version 20141127
 *
 */
public class JMSChatSender implements Runnable	{

	private String user;

	private String password = ActiveMQConnection.DEFAULT_PASSWORD;

	private String subject;

	private String ip;

	private int port;

	private String url;
	
	/**
	 * 
	 * Erstellt ein neues JMSChatSender-Objekt mit den eingegebenen Parametern.
	 * 
	 * @param ip Die eingegebene IP-Adresse.
	 * @param user Der eingegebene Username.
	 * @param subject Der eingegebene Name des Chatraums.
	 * @param port Die eingegebene Portnummer. (Sollte wenn moeglich nur verwendet werden, wenn Standard-Port besetzt ist)
	 */
	public JMSChatSender(String ip, String user, String subject, int port) {
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
	 * @return password Das Passwort, das zurueckgeliefert wird.
	 * 
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * 
	 * Setzt das Passwort auf den eingegebenen Wert.
	 * 
	 * @param password Das gewuenschte Passwort.
	 * 
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * 
	 * Liefert den Namen des Chatraums zurueck.
	 * 
	 * @return subject Der Name des Chatraums, der zurueckgeliefert wird.
	 * 
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 
	 * Setzt den Namen des Chatraums auf den eingegebenen Wert.
	 * 
	 * @param subject Der gewuenschte Name des Chatraums.
	 * 
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}


	/**
	 * 
	 * Liefert die IP-Adresse zurueck.
	 * 
	 * @return ip Die IP-Adresse, der zurueckgeliefert wird.
	 * 
	 */
	public String getIp() {
		return ip;
	}


	/**
	 * 
	 * Setzt die IP-Adresse auf den eingegebenen Wert.
	 * 
	 * @param ip Die gewuenschte IP-Adresse.
	 * 
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}


	/**
	 * 
	 * Liefert den Port zurueck.
	 * 
	 * @return port Der Port, der zurueckgeliefert wird.
	 * 
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 
	 * Setzt den Port auf den eingegebenen Wert.
	 * 
	 * @param port Der gewuenschte Port.
	 * 
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * Liefert die URL zurueck.
	 * 
	 * @return url Die URL, die zurueckgeliefert wird.
	 * 
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * 
	 * Setzt die URL auf den eingegebenen Wert.
	 * 
	 * @param url Die gewuenschte URL.
	 * 
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * 
	 * Der Thread, in dem das Versenden bei Aufruf abgearbeitet wird.
	 * 
	 */
	@Override
	public void run() {

		try {

			// Create the connection.
			Session session = null;
			Connection connection = null;
			MessageProducer producer = null;
			Destination destination = null;

			String input = "";

			try {

				ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
				connection = connectionFactory.createConnection();
				connection.start();

				// Create the session
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				destination = session.createTopic(subject);

				// Create the producer.
				producer = session.createProducer(destination);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

				while (true)	{

					BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

					String text = r.readLine().trim();

					String[] words = text.split(" ");

					input = user + " [" + ip + "]: " + text;

					if (words[0].equals("MAIL") && words.length >= 3)	{
						input = "";
						new Thread(new MailSender(words[1], port, text)).start();
					} else	{

					}

					if (text.equals("MAILBOX"))	{
						input = "";
						new Thread(new MailReceiver(ip, port)).start();
					}

					if (text.equals("EXIT"))	{
						System.exit(0);
					}

					// Create the message
					TextMessage message = session.createTextMessage(input);
					producer.send(message);

				}

				//connection.stop();

			} catch (Exception e) {

				System.out.println("[MessageProducer] Caught: " + e);
				e.printStackTrace();

			} finally {

				try {
					producer.close();
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