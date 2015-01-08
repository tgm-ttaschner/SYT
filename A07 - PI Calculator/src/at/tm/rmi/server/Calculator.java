package at.tm.rmi.server;

import java.math.BigDecimal;
import java.rmi.*;

/**
 * @author Patrik Malik
 * @author Thomas Taschner
 * @version 08.01.2015
 * 
 * Interface which inherits from Remote. It's only method calciulates PI.
 * To achieve that it takes the amount of decimal places (of PI) as an input parameter and returns PI up to a certain decimal place as a BigInteger. 
 *
 */
public interface Calculator extends Remote	{
	public BigDecimal pi (int anzahl_nachkommastellen) throws RemoteException;
}