package at.tm.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 * Remote Class for the "Hello, world!" example.
 */
public class Hello extends UnicastRemoteObject implements HelloInterface {
	private String message;
	/**
	 * Construct a remote object
	 * 
	 * @param msg
	 *            the message of the remote object, such as "Hello, world!".
	 * @return
	 * @exception RemoteException
	 *                if the object handle cannot be constructed.
	 */
	public Hello(String msg) throws RemoteException {
		message = msg;
	}

	@Override
	public String sayHello() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}