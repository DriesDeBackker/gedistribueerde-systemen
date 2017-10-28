package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISessionManager extends Remote {

	public IReservationSession getNewReservationSession(String name)
			throws RemoteException;
	
	public IManagerSession getNewManagerSession(String name, String carRentalName)
			throws RemoteException;
	
}