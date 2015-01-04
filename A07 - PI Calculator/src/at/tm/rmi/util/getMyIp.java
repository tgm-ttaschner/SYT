package at.tm.rmi.util;

import java.io.*;
import java.net.*;

/**
 * @author René Hollander
 * @version 04.01.2015
 * 
 * This program returns the external ip of your internet connection by asking an external server for it.
 * The server's response is read and returned.
 *
 */
public class getMyIp {

	/**
	 * Gets the current WAN IP
	 * source: René Hollander, JMS-Chat
	 * 
	 * @return WAN IP
	 * @throws IOException
	 * Throws an IOException if there was an error getting the ip
	 */
	public static InetAddress getIp() throws IOException {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		return InetAddress.getByName(in.readLine());
	}
}