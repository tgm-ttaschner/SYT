package at.tm.rmi.utils;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {

	public static Options options = new Options();

	@SuppressWarnings("static-access")
	public static PIArgs parseArguments(String... args) {

		Option hostname = OptionBuilder.hasArg().isRequired().withDescription("The hostname of the Balancer you want to connect with. Argument for client and server.").withArgName("hostname").create("h");
		Option port = OptionBuilder.hasArg().withType(Number.class).isRequired().withDescription("The port of the Balancer you want to connect with. Argument for client, server and balancer.").withArgName("port").create("p");
		Option server_count = OptionBuilder.hasArg().withType(Number.class).withDescription("The number of servers you want to create. Argument for server.").withArgName("servercount").create("S");
		Option client_count = OptionBuilder.hasArg().withType(Number.class).withDescription("The number of clients you want to create. Argument for client.").withArgName("clientcount").create("C");
		Option decimal_places = OptionBuilder.hasArg().isRequired().withDescription("Amount of decimal places of PI. Argument for client.").withArgName("decimal").create("d");
		Option server_name = OptionBuilder.hasArg().isRequired().withDescription("The name of the server you want to create. Argument for server.").withArgName("servername").create("n");

		Option client_arg = OptionBuilder.isRequired().withDescription("Selects the client to start").withArgName("client").create("c");
		Option server_arg = OptionBuilder.isRequired().withDescription("Selects the server to start").withArgName("server").create("s");
		Option balancer_arg = OptionBuilder.isRequired().withDescription("Selects the balancer to start").withArgName("balancer").create("b");

		
		if (args[0].equals("-c"))	{
			options.addOption(client_arg);
			options.addOption(hostname);
			options.addOption(port);
			options.addOption(decimal_places);
			options.addOption(client_count);
		} else if (args[0].equals("-s")) {

			options.addOption(server_arg);
			options.addOption(hostname);
			options.addOption(port);
			options.addOption(server_name);
			options.addOption(server_count);

		} else if (args[0].equals("-b")) {
			options.addOption(balancer_arg);
			options.addOption(port);
		}

		CommandLineParser clip = new BasicParser();
		CommandLine line = null;
		try {
			line = clip.parse(options, args);
		} catch (ParseException e) {
			System.err.println("A problem occurred while parsing the arguments, check if your arguments are valid.");
			printHelp();
		}

		return new PIArgs(line.getOptionValue("h"), Integer.parseInt(line.getOptionValue("p")), Integer.parseInt(line.getOptionValue("s")), Integer.parseInt(line.getOptionValue("c")));

	}

	public static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("RMI", ArgumentParser.options);
	}
}