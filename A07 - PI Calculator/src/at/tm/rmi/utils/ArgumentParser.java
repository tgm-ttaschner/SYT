package at.tm.rmi.utils;

import org.apache.commons.cli.*;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * @version 08.01.2015
 * 
 * This class parses arguments and prints a help message on the console if needed.
 * The Apache Commons CLI is used for easy parsing of the arguments.
 * Various arguments are parsed.
 * The fist entered argument decides which kind of connection should be established.
 * If something goes wrong during parsing, an error message and the help screen is displayed.
 */
public class ArgumentParser {

	public static Options options = new Options();

	@SuppressWarnings("static-access")
	public static PIArgs parseArguments(String... args) throws IllegalArgumentException {

		PIArgs piargs = new PIArgs();

		/* Creating the options (-> arguments) */
		Option hostname = OptionBuilder.hasArg().isRequired().withDescription("The hostname of the Balancer you want to connect with. Argument for client and server.").withArgName("hostname").create("h");
		Option port = OptionBuilder.hasArg().withType(Number.class).isRequired().withDescription("The port of the Balancer you want to connect with. Argument for client, server and balancer.").withArgName("port").create("p");
		Option server_count = OptionBuilder.hasArg().withType(Number.class).withDescription("The number of servers you want to create. Argument for server.").withArgName("servercount").create("S");
		Option client_count = OptionBuilder.hasArg().withType(Number.class).withDescription("The number of clients you want to create. Argument for client.").withArgName("clientcount").create("C");
		Option decimal_places = OptionBuilder.hasArg().isRequired().withType(Number.class).withDescription("Amount of decimal places of PI. Argument for client.").withArgName("decimal").create("d");
		Option server_name = OptionBuilder.hasArg().isRequired().withDescription("The name of the server you want to create. Argument for server.").withArgName("servername").create("n");
		Option serverport = OptionBuilder.hasArg().withType(Number.class).isRequired().withDescription("The port the servers works with, if you start more than one server, the number gets incremented").withArgName("serverport").create("P");

		/* Creating the dummy options (-> dummy arguments) */
		Option client_arg = OptionBuilder.isRequired().withDescription("Selects the client to start").withArgName("client").create("c");
		Option server_arg = OptionBuilder.isRequired().withDescription("Selects the server to start").withArgName("server").create("s");
		Option balancer_arg = OptionBuilder.isRequired().withDescription("Selects the balancer to start").withArgName("balancer").create("b");

		/*
		 * Depending on the value of the dummy argument some specific arguments are used.
		 * Each value checks for different (meaningful) arguments.
		 * E.g. a server doesn't need an argument used for the client count.
		 * '-c' checks for client arguments.
		 * '-s' checks for server arguments.
		 * '-b' checks for balancer arguments.
		 */
		int valid = 0;
		int position = 0;
		
		/* Performs a quick check if more than one connection deciding argument was entered */
		for(int i = 0; i<args.length;i++){
			if(args[i].equals("-c")||args[i].equals("-b")||args[i].equals("-s")){
				position = i;
				valid++;
			}
		}

		if(valid!=1){
			throw new IllegalArgumentException("cannot use more or less than one of the three parameters (-s,-b,-c)");
		}

		if (args[position].equals("-c"))	{
			piargs.setType('c');
			options.addOption(client_arg);
			options.addOption(hostname);
			options.addOption(port);
			options.addOption(decimal_places);
			options.addOption(client_count);
		} else if (args[position].equals("-s")) {
			piargs.setType('s');
			options.addOption(server_arg);
			options.addOption(hostname);
			options.addOption(port);
			options.addOption(serverport);
			options.addOption(server_name);
			options.addOption(server_count);
		} else if (args[position].equals("-b")) {
			piargs.setType('b');
			options.addOption(balancer_arg);
			options.addOption(port);
		}

		/* Runs the CLI Parser and tries to parse the arguments. An error message and the help screen is displayed if something goes wrong. */
		CommandLineParser clip = new BasicParser();
		CommandLine line = null;
		try {
			line = clip.parse(options, args);
		} catch (ParseException e) {
			System.err.println("A problem occurred while parsing the arguments, check if your arguments are valid.");
			printHelp();
		}

		/* Sets the arguments for each connection type. */
		try {
			if (piargs.getType()=='c')	{
				piargs.setClientcount(Integer.parseInt(line.getOptionValue("C")));
				piargs.setHostname(line.getOptionValue("h"));
				piargs.setPort(Integer.parseInt(line.getOptionValue("p")));
				piargs.setDecimal_places(Integer.parseInt(line.getOptionValue("d")));
			} else if (piargs.getType()=='s')	{
				piargs.setHostname(line.getOptionValue("h"));
				piargs.setPort(Integer.parseInt(line.getOptionValue("p")));
				piargs.setServer_name(line.getOptionValue("n"));
				piargs.setServercount(Integer.parseInt(line.getOptionValue("S")));
				piargs.setServerport(Integer.parseInt(line.getOptionValue("P")));
			} else if (piargs.getType()=='b')	{
				piargs.setPort(Integer.parseInt(line.getOptionValue("p")));
			}
		} catch (NullPointerException e)	{
			System.exit(1);
		}

		return piargs;
	}

	/**
	 * Static method which prints out a help message onto the console.
	 */
	public static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("RMI", ArgumentParser.options);
	}
}