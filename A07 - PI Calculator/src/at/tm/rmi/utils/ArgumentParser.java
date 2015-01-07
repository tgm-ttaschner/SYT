package at.tm.rmi.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {

	public static Options options = new Options();

	@SuppressWarnings("static-access")
	public static Map<String, String> parseArguments(String... args) {

		Map<String, String> ret = new HashMap<String, String>();
		
		Option hostname = OptionBuilder.hasArg().isRequired().withDescription("The hostname of the Balancer you want to connect with").withArgName("hostname").create("h");
		Option port = OptionBuilder.hasArg().isRequired().withDescription("The port of the Balancer you want to connect with").withArgName("port").create("p");
		Option server = OptionBuilder.hasArg().isRequired().withDescription("The number of servers you want to create").withArgName("servercount").create("s");
		Option client = OptionBuilder.hasArg().isRequired().withDescription("The number of clients you want to create").withArgName("clientcount").create("c");

		options.addOption(hostname);
		options.addOption(port);
		options.addOption(server);
		options.addOption(client);

		CommandLineParser clip = new BasicParser();
		CommandLine line = null;
		try {
			line = clip.parse(options, args);
		} catch (ParseException e) {
			System.err.println("A problem occurred while parsing the arguments, check if your arguments are valid.");
			printHelp();
		}

		ret.put("hostname", line.getOptionValue("h"));
		ret.put("port", line.getOptionValue("p"));
		ret.put("server", line.getOptionValue("s"));
		ret.put("client", line.getOptionValue("c"));
		
		return ret;
		
	}
	
	public static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("RMI", ArgumentParser.options);
	}
}
