package at.tm.rmi.server;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 * Remote Class for the "Hello, world!" example.
 */
public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

	private int decimalplaces;
	
	public CalculatorImpl() throws RemoteException {
		decimalplaces = 0;
	}
	
	@Override
	public BigDecimal pi(int anzahl_nachkommastellen) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDecimalplaces() {
		return decimalplaces;
	}

	public void setDecimalplaces(int decimalplaces) {
		this.decimalplaces = decimalplaces;
	}
	
}