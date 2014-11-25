package testing;

import taschner_weinberger.*;

public class MailTestReceive {
	public static void main(String[] args) {
		Thread t = new Thread(new MailReceiver());
		
		t.start();
	}
}
