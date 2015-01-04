package at.tm.rmi.server;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote{
	public BigDecimal pi (int anzahl_nachkommastellen) throws RemoteException;
}