package taschner_weinberger;

import java.io.*;

/**
 * 
 * @author Michael/Thomas
 *
 */
public class JMSChatCLI {
	
	public void getProperties() {

	}
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.exit(1);
		}
		
		Thread empfangen = new Thread(new JMSChatReceiver(args[0], args[1], args[2])));
		
		Thread senden = new Thread(new JMSChatSender(new JMSChatReceiver(args[0], args[1], args[2])));
		
		empfangen.start();
		senden.start();
	}
}
