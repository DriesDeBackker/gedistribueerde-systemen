package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IManagerSession extends Remote {

	public void registerCarRentalCompany(String name)
			throws RemoteException;
	
	public void unregisterCarRentalCompany(String name)
			throws RemoteException;
	
	public int getNumberOfReservationsForCarType(String carRental, String carType)
			throws RemoteException;
	
	public Set<String> getBestClients()
			throws RemoteException;

	public CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year)
			throws RemoteException;

	public Set<String> getAllRegisteredCarCompanies()
			throws RemoteException;
}