package taschner_weinberger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSChatSender implements Runnable	{

	private String user;

	private String password = ActiveMQConnection.DEFAULT_PASSWORD;

	private String subject;

	private String ip;

	private int port;

	private String url;

	public JMSChatSender(String ip, String user, String subject, int port) {
		this.ip = ip;
		this.user = user;
		this.subject = subject;
		this.port = port;

		url = "failover://tcp://" + ip + ":" + port;
	}



	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public int getPort() {
		return port;
	}



	public void setPort(int port) {
		this.port = port;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



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

					String text = r.readLine();

					String[] words = text.split(" ");

					if (text.trim() != "")	{

						if (words[0].equals("MAIL"))	{
							new Thread(new MailSender(words[1], port, text)).start();
						}

						if (text.trim().equals("MAILBOX"))	{
							new Thread(new MailReceiver(ip, port)).start();
						}

						if (text.trim().equals("EXIT"))	{
							System.exit(0);
						}

						input = user + " [" + ip + "]: " + text;

						// Create the message
						TextMessage message = session.createTextMessage(input);
						producer.send(message);
					} else	{
						
					}

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