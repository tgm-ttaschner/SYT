package at.tm.rmi.client;

import java.net.*;
import java.rmi.RemoteException;

import org.apache.logging.log4j.*;

import at.tm.rmi.server.*;
import at.tm.rmi.utils.*;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 08.01.2015
 * 
 * This class starts the whole program.
 * It starts with a SecurityManager check. If no SecurityManager was set then a new default one will be created and set instead.
 * Then there's parsing and exception handling. If everything goes well the program should start.
 * An appropriate error message will pop up if something goes wrong.
 */
public class Run {

	private static final Logger LOGGER = LogManager.getLogger(Run.class);
	
	/**
	 * @param args arguments which will be checked and then used to start the program
	 */
	public static void main(String[] args) {

		/* SecurityManager  check, creates a new one if none exist */
		if (System.getSecurityManager() == null) {
			// try{
			// System.setProperty("java.security.policy",
			// System.class.getResource("/java.policy").toString());
			// }catch(Exception e){
			// System.err.println("policy file: java.policy was not found or could not be set as property");
			// }
			System.setSecurityManager(new MySecurityManager());
		}

		PIArgs piargs = null;
		try{
			piargs = ArgumentParser.parseArguments(args);
		}catch(IllegalArgumentException e){
			LOGGER.error("An error occurred while parsing the arguments: " + e.getMessage());
			System.exit(1);
		}
		/* 
		 * Check which dummy argument was entered and try to proceed.
		 * 'b' creates a new (Calculator)Balancer, 'c' a new Client and 's' a new Server.
		 */
		if (piargs.getType() == 'b') {
			try {
				new CalculatorBalancer(piargs.getPort());
			} catch (RemoteException e) {
				LOGGER.error("A remote connection error occurred");
				LOGGER.error("Maybe the balancer couldn't start?");
				System.exit(2);
			}
		} else if (piargs.getType() == 'c') {

			/* 
			 * Creates a certain given amount of clients which connect to a given server on a given port and ask for PI.
			 * An URISyntaxException is caught if the user manages to enter an invalid URI or the server cannot be reached.
			 * Finally, the result is printed out.
			 */
			for (int i = 0; i < piargs.getClientcount(); i++) {
				Client c = null;
				try {
					c = new Client(new URI("//" + piargs.getHostname() + ":" + piargs.getPort()), piargs.getDecimal_places());
				} catch (URISyntaxException e) {
					LOGGER.error("A problem occurred while creating a client. "
							+ "Please make sure that the server is up and running and check your input for eventual typos.");
					System.exit(3);
				}
				String out;
				out = ((out = c.connect())!=null) ? out : "";
				LOGGER.info(out);
			}

			/*
			 * Creates a certain given amount of servers which run on a given IP/URL on a given port, have a name and a certain task to perform.
			 * An URISyntaxException is caught if the user manages to enter an invalid URI.
			 * A RemoteException is caught if the server cannot be created on the given location.
			 * Finally, the result is printed out.
			 */
		} else if (piargs.getType() == 's') {
			for (int i = 0; i < piargs.getServercount(); i++) {
				Server s = null;
				try {
					s = new Server(piargs.getServer_name() + i,piargs.getServerport()+i,new CalculatorImpl());
					s.connect(new URI("//" + piargs.getHostname() + ":" + piargs.getPort()));
				} catch (URISyntaxException | RemoteException e) {
					LOGGER.error("A problem occurred while creating a server "
							+ "Please make sure that the server is up and running and check your input for eventual typos.");
					System.exit(4);
				}
			}
		}
	}
}