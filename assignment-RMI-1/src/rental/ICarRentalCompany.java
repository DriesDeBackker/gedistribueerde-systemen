package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.Date;
import java.util.Set;

public interface ICarRentalCompany extends Remote {

	public Set<CarType> getAvailableCarTypes(Date from, Date end) 
			throws RemoteException;
	
	public Quote createQuote(ReservationConstraints constraints, String client)
			throws ReservationException;
	
	public Reservation confirmQuote(Quote quote)
			throws ReservationException;
	
	public void cancelReservation(Reservation res);
			
}