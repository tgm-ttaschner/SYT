package at.tm.rmi.utils;

/**
 * @author Patrick Malik
 * @author Thomas Taschner
 * 
 * This class provides a default constructor which sets some predefined values and parametrized one where you can input all the parameters the class has to offer.
 * It also features getter and setter methods for its attributes.
 *
 */
public class PIArgs {

	private String hostname;
	private int port;
	private int servercount;
	private int clientcount;
	private int decimal_places;
	private String server_name;
	private char type;
	private int serverport;

	/**
	 * Default constructor, initializes with some predefined values
	 */
	public PIArgs() {
		this.port = 5052;
		this.hostname = "localhost";
		this.servercount = 1;
		this.clientcount = 1;
		this.decimal_places = 4;
		this.server_name = "Server";
		this.serverport = 5055;
	};

	/**
	 * Constructor which uses every possible class attribute to set it.
	 * 
	 * @param hostname the targets hostname or IP address
	 * @param port the targets port which is used for establishing a connection
	 * @param servercount the amount of servers that are used
	 * @param clientcount the amount of clients that are used
	 * @param decimal_places the amount of calculated decimal places of PI
	 * @param server_name the servers name
	 * @param serverport the port the server uses
	 */
	public PIArgs(String hostname, int port, int servercount, int clientcount, int decimal_places, String server_name, int serverport) {
		this.hostname = hostname;
		this.port = port;
		this.servercount = servercount;
		this.clientcount = clientcount;
		this.decimal_places = decimal_places;
		this.server_name = server_name;
		this.serverport = serverport;
	}

	/**
	 * Getter for type.
	 * 
	 * @return the character used for deciding which connection type should be parsed
	 */
	public char getType() {
		return type;
	}

	/**
	 * Setter for type.
	 * 
	 * @param type the character used for deciding which connection type should be parsed
	 */
	public void setType(char type) {
		this.type = type;
	}

	/**
	 * Getter for hostname.
	 * 
	 * @return the targets hostname or IP address
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Setter for hostname.
	 * 
	 * @param hostname the targets hostname or IP address
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * Getter for port.
	 * 
	 * @return the targets port which is used for establishing a connection
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Setter for port.
	 * 
	 * @param port the targets port which is used for establishing a connection
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Getter for servercount.
	 * 
	 * @return the amount of servers that are used
	 */
	public int getServercount() {
		return servercount;
	}

	/**
	 * Setter for servercount.
	 * 
	 * @param servercount the amount of servers that are used
	 */
	public void setServercount(int servercount) {
		this.servercount = servercount;
	}

	/**
	 * Getter for clientcount.
	 * 
	 * @return the amount of clients that are used
	 */
	public int getClientcount() {
		return clientcount;
	}

	/**
	 * Setter for clientcount.
	 * 
	 * @param clientcount the amount of clients that are used
	 */
	public void setClientcount(int clientcount) {
		this.clientcount = clientcount;
	}

	/**
	 * Getter for decimal_places.
	 * 
	 * @return the amount of calculated decimal places of PI
	 */
	public int getDecimal_places() {
		return decimal_places;
	}

	/**
	 * Setter for decimal_places.
	 * 
	 * @param decimal_places the amount of calculated decimal places of PI
	 */
	public void setDecimal_places(int decimal_places) {
		this.decimal_places = decimal_places;
	}

	/**
	 * Getter for server_name.
	 * 
	 * @return the servers name
	 */
	public String getServer_name() {
		return server_name;
	}

	/**
	 * Setter for server_name.
	 * 
	 * @param server_name the servers name
	 */
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	/**
	 * Getter for serverport.
	 * 
	 * @return the port the server uses
	 */
	public int getServerport() {
		return serverport;
	}

	/**
	 * Setter for serverport.
	 * 
	 * @param serverport the port the server uses
	 */
	public void setServerport(int serverport) {
		this.serverport = serverport;
	}
}