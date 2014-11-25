package taschner_weinberger;

import org.apache.activemq.*;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSChatReceiver implements Runnable	{

	private String user;
	
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;

	private String subject;

	private String ip;

	private int port;

	private String url;
	

	public JMSChatReceiver(String ip, String user, String subject, int port) {
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
					if ( message != null ) {
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
