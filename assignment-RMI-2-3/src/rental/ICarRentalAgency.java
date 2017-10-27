package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ICarRentalAgency extends Remote {

	public Set<CarType> getAvailableCarTypes(Date from, Date end) 
			throws RemoteException;
	
	public ReservationSession getNewReservationSession(String name)
			throws RemoteException;
	
	public ManagerSession getNewManagerSession(String name, String carRentalName)
			throws RemoteException;
	
	public Quote createQuote(ReservationConstraints constraints, String client)
			throws RemoteException, ReservationException;
	
	public Reservation confirmQuote(Quote quote)
			throws RemoteException, ReservationException;
	
	public void cancelReservation(Reservation res) 
			throws RemoteException;
	
	public List<Reservation> getRenterReservations(String clientname)
			throws RemoteException;
	
	public int getNumberOfReservationsForCarType(String carType)
			throws RemoteException;
}