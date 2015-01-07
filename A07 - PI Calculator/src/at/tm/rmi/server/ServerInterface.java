package at.tm.rmi.server;

import java.io.Serializable;
import java.net.URI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public interface ServerInterface extends Remote, Serializable{
	public void connect(URI balancer) throws RemoteException;
	public Calculator getCalc() throws RemoteException;
	public String getName() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public Registry getRegistry() throws RemoteException;
	public void setRegistry(Registry registry) throws RemoteException;
	public int getPort() throws RemoteException;
	public void setPort(int port) throws RemoteException;
}
