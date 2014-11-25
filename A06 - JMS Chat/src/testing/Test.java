package testing;

import taschner_weinberger.*;

/**
 * 
 * @author Michael
 *
 */
public class Test {

	public static void main(String[] args) {
		Thread empfangen = new Thread(new JMSChatReceiver());
		empfangen.start();
		
		Thread senden = new Thread(new JMSChatSender());
		senden.start();
	}
}
