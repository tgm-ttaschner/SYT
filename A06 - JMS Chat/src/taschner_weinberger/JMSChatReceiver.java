package taschner_weinberger;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSChatReceiver implements Runnable	{

	private String user;
	
	private String password;

	private String subject;

	private String ip;

	private int port;

	private String url = "failover://tcp://" + ip + ":" + port;

	public JMSChatReceiver(String ip, String user, String subject, String password, int port) {
		this.ip = ip;
		this.user = user;
		this.subject = subject;
		this.password = password;
		this.port = port;
		
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

				ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, url, password);
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
						System.out.println(user + "[" + ip + "] " + message.getText());
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
