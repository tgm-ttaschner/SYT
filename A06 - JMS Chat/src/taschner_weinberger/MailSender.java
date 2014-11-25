package taschner_weinberger;

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
	
	private String textmessage;

	private int port;

	public MailSender(String ip, int port, String message) {
		this.ip = ip;
		this.textmessage = message;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTextmessage() {
		return textmessage;
	}

	public void setTextmessage(String textmessage) {
		this.textmessage = textmessage;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
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

			// create a simple message to say "Hello"
			TextMessage message = queueSession.createTextMessage("MAIL FROM " + "[" + ip + "]" + textmessage);

			// send the message
			queueSender.send(message);

			System.out.println("[sent] " + message.getText());

			//queueConn.close();

		} catch (Exception e)	{
			e.getMessage();
		}

	}
}