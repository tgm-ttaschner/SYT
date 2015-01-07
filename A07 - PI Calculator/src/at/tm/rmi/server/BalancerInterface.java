package at.tm.rmi.server;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public interface BalancerInterface extends Calculator, Serializable, Remote{

	public void addServer(String key, ServerInterface serverInterface) throws RemoteException;
	public void removeServer(String key) throws RemoteException;
	public ConcurrentHashMap<String, ServerInterface> getServers() throws RemoteException;
	public void setServers(ConcurrentHashMap<String, ServerInterface> servers) throws RemoteException;
	public int getPort() throws RemoteException;
	public void setPort(int port) throws RemoteException;
	
	
}
