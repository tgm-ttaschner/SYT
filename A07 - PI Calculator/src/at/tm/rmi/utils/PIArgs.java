package at.tm.rmi.utils;

public class PIArgs {
	
	private String hostname;
	private int port;
	private int server;
	private int client;
	
	public PIArgs(String hostname, int port, int server, int client) {
		this.hostname = hostname;
		this.port = port;
		this.server = server;
		this.client = client;
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

	public int getServer() {
		return server;
	}

	public void setServer(int server) {
		this.server = server;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}
}
