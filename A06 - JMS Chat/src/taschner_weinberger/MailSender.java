package taschner_weinberger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

public class MailSender	implements Runnable	{

	public static void main(String[] args) throws Exception {
		new MailSender().run();
	}

	@Override
	public void run() {


		try {
			while (true)	{

				Properties env = new Properties();
				env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
				env.put(Context.PROVIDER_URL, "tcp://localhost:61616");
				env.put("queue.queueSampleQueue", "MyNewQueue");

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

				BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

				// create a simple message to say "Hello"
				TextMessage message = queueSession.createTextMessage("<" + env.getProperty(Context.PROVIDER_URL) + "> " + r.readLine());

				// send the message
				queueSender.send(message);

				System.out.println("[sent] " + message.getText());

				//queueConn.close();
			}

		} catch (Exception e)	{
			e.getMessage();
		}

	}
}