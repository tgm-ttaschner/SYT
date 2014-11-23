package taschner_weinberger;

import org.apache.activemq.*;

import javax.jms.*;

public class JMSChatSender {

	private static String user = "Herbert Eichelmann";
	private static String password = "penis123";
	private static String url = "failover://tcp://localhost:61616";
	private static String subject = "VSDBChat";

	public static void main( String[] args ) {

		// Create the connection.
		Session session = null;
		Connection connection = null;
		MessageProducer producer = null;
		Destination destination = null;

		try {

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory( user, password, url );
			connection = connectionFactory.createConnection();
			connection.start();

			// Create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic( subject );

			// Create the producer.
			producer = session.createProducer(destination);
			producer.setDeliveryMode( DeliveryMode.NON_PERSISTENT );

			// Create the message
			TextMessage message = session.createTextMessage(user + "[ 127.0.0.1 ], password:" + password + ": \nThis message was sent at (ms): " + "now" );
			producer.send(message);
			System.out.println( message.getText() );
			connection.stop();

		} catch (Exception e) {

			System.out.println("[MessageProducer] Caught: " + e);
			e.printStackTrace();

		} finally {

			try { 
				producer.close();
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