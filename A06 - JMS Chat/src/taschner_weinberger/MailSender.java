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

	private String ip;
	
	private String username;

	private int port;

	public MailSender(String ip, String username, int port) {
		this.ip = ip;
		this.username = username;
		this.port = port;
	}

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

			BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

			// create a simple message to say "Hello"
			TextMessage message = queueSession.createTextMessage("MAIL FROM " + username + "[" + ip + "]" + r.readLine());

			// send the message
			queueSender.send(message);

			System.out.println("[sent] " + message.getText());

			//queueConn.close();

		} catch (Exception e)	{
			e.getMessage();
		}

	}
}