package at.tm.rmi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class getMyIp {

	/**
	 * Gets the current WAN IP
	 * source: René Hollander, JMS-Chat
	 * 
	 * @return WAN IP
	 * @throws IOException
	 *             Throws an IOException if there was an error getting the ip
	 *             
	 * 
	 */
	public static InetAddress getIp() throws IOException {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		return InetAddress.getByName(in.readLine());
	}
}
