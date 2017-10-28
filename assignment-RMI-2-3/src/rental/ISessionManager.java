package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ISessionManager extends Remote {

	public IReservationSession getNewReservationSession(String name)
			throws RemoteException;
	
	public IManagerSession getNewManagerSession(String name, String carRentalName)
			throws RemoteException;
	
}