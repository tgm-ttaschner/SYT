package taschner_weinberger;

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
		
		if (args.length == 3 ^ args.length == 4) {
			if (args.length == 4) {
				int temp = Integer.parseInt(args[4]);
				empfangen = new Thread(new JMSChatReceiver(args[0], args[1], args[2], temp));
				senden = new Thread(new JMSChatSender(args[0], args[1], args[2], temp));
			} else {
				empfangen = new Thread(new JMSChatReceiver(args[0], args[1], args[2], 61616));
				senden = new Thread(new JMSChatSender(args[0], args[1], args[2], 61616));
			}
		} else {
			System.err.println("Fehlerhafte Parameter!");
			System.exit(1);
		}
		
		empfangen.start();
		senden.start();
	}
}
