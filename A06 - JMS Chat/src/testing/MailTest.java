package testing;

import taschner_weinberger.*;

public class MailTest {
	public static void main(String[] args) {
		Thread t = new Thread(new MailSender());
		
		t.start();
	}
}
