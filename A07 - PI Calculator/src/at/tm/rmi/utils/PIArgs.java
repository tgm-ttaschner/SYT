package at.tm.rmi.utils;

public class PIArgs {

	private String hostname;
	private int port;
	private int servercount;
	private int clientcount;
	private int decimal_places;
	private String server_name;
	private char type;
	private int serverport;

	public PIArgs() {
		this.port = 5052;
		this.hostname = "localhost";
		this.servercount = 1;
		this.clientcount = 1;
		this.decimal_places = 4;
		this.server_name = "Server";
		this.serverport = 5055;
	};

	public PIArgs(String hostname, int port, int servercount, int clientcount, int decimal_places, String server_name, int serverport) {
		this.hostname = hostname;
		this.port = port;
		this.servercount = servercount;
		this.clientcount = clientcount;
		this.decimal_places = decimal_places;
		this.server_name = server_name;
		this.serverport = serverport;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getServercount() {
		return servercount;
	}

	public void setServercount(int servercount) {
		this.servercount = servercount;
	}

	public int getClientcount() {
		return clientcount;
	}

	public void setClientcount(int clientcount) {
		this.clientcount = clientcount;
	}

	public int getDecimal_places() {
		return decimal_places;
	}

	public void setDecimal_places(int decimal_places) {
		this.decimal_places = decimal_places;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public int getServerport() {
		return serverport;
	}

	public void setServerport(int serverport) {
		this.serverport = serverport;
	}

}
