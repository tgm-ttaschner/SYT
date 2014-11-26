package taschner_weinberger;

/**
 * 
 * In dieser Klasse werden die Konsolenparameter ausgelesen und die jeweilige Thread-Instanz gestartet.
 * Gibt es 3 Eingabeparameter, so werden die Threads mit dem Standardport gestartet, was bei einer vierstelligen Eingabe
 * selbst festgelegt werden kann.
 * 
 * Sollte es mehr oder weniger Parameter geben, so wird das Programm mit einer Fehlermeldung auf der Konsole abgebrochen.
 * 
 * @author Thomas Taschner/Michael Weinberger 4AHITT
 * @version 20141126
 *
 */
public class JMSChatCLI {
	
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
