package at.tm.rmi.client;

import java.net.URI;
import java.net.URISyntaxException;

import at.tm.rmi.server.CalculatorBalancer;
import at.tm.rmi.server.CalculatorImpl;
import at.tm.rmi.server.Server;
import at.tm.rmi.utils.ArgumentParser;
import at.tm.rmi.utils.PIArgs;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 05.01.2015
 * 
 *          Sets the security manager, starts (and connects) 3 servers, runs 3
 *          concurrent clients (for testing purposes) and closes the server
 *          connections
 */
public class Run {

	/**
	 * @param args
	 *            input parameters
	 */
	public static void main(String[] args) {

		if (System.getSecurityManager() == null) {
			// try{
			// System.setProperty("java.security.policy",
			// System.class.getResource("/java.policy").toString());
			// }catch(Exception e){
			// System.err.println("policy file: java.policy was not found or could not be set as property");
			// }
			System.setSecurityManager(new SecurityManager());
		}

		PIArgs piargs = ArgumentParser.parseArguments(args);

		if (piargs.getType() == 'b') {
			new CalculatorBalancer(piargs.getPort());
		} else if (piargs.getType() == 'c') {
			// Client[] clients = new Client[piargs.getClientcount()];

			for (int i = 0; i < piargs.getClientcount(); i++) {
				Client c = null;
				try {
					c = new Client(new URI("//" + piargs.getHostname() + ":" + piargs.getPort()), piargs.getDecimal_places());
				} catch (URISyntaxException e) {
					System.err.println("A problem occurred while creating a client");
				}
				System.out.println(c.connect());
			}
		} else if (piargs.getType() == 's') {
			for (int i = 0; i < piargs.getServercount(); i++) {
				Server s = new Server(piargs.getServer_name() + i);
				try {
					s.connect(new URI("//" + piargs.getHostname() + ":" + piargs.getPort()), new CalculatorImpl());
				} catch (URISyntaxException e) {
					System.err.println("A problem occurred while creating a server");
				}
			}
		}
	}
}