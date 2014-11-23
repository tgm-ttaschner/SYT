package taschner_weinberger;

import java.util.ArrayList;

import org.apache.activemq.*;

import javax.jms.*;

public class JMSChatReceiver {

	private static String user = ActiveMQConnection.DEFAULT_USER;
	private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String subject = "VSDBChat";

	public static void main( String[] args ) {

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
			destination = session.createTopic( subject );

			// Create the consumer
			consumer = session.createConsumer( destination );

			// Start receiving
			//TextMessage message = (TextMessage) consumer.receive();

			// Pfusch Code ab hier
			ArrayList<TextMessage> message = new ArrayList<TextMessage>();
			message.add((TextMessage) consumer.receive());

			for (int i = 0; i < message.size(); i++)	{
				if ( message != null ) {
					System.out.println("Message received: " + message.get(i).getText() );
					System.out.println("'" + url + "'");
					message.get(i).acknowledge();
				}
			}


			connection.stop();

		} catch (Exception e) {

			System.out.println("[MessageConsumer] Caught: " + e);
			e.printStackTrace();

		} finally {

			try { 
				consumer.close();
			} catch ( Exception e ) {

			}

			try {
				session.close();
			} catch ( Exception e ) {

			}

			try {
				connection.close();
			} catch ( Exception e ) {

			}

		}

	} // end main
}