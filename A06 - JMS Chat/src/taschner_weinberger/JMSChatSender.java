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

	private static String user = ActiveMQConnection.DEFAULT_USER;
	private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String subject = "VSDBChat";

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

				while (!input.equals("EXIT"))	{

					BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

					input = r.readLine();

					// Create the message
					TextMessage message = session.createTextMessage(input);
					producer.send(message);
					System.out.println(message.getText());

				}

				connection.stop();

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

	public static void main(String[] args) {
		new JMSChatSender().run();
	}
}