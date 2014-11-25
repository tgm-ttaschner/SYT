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
		Thread empfangen = null;
		Thread senden = null;
		
		if (args.length == 3 ^ args.length == 5) {
			if (args.length == 5) {
				int temp = Integer.parseInt(args[4]);
				empfangen = new Thread(new JMSChatReceiver(args[0], args[1], args[2], args[3], temp));
				senden = new Thread(new JMSChatSender(args[0], args[1], args[2], args[3], temp));
			} else {
				empfangen = new Thread(new JMSChatReceiver(args[0], args[1], args[2], "-1", -1));
				senden = new Thread(new JMSChatSender(args[0], args[1], args[2], "-1", -1));
			}
		}
		
		empfangen.start();
		senden.start();
	}
}
