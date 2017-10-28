package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IReservationSession extends Remote {

	public Set<CarType> checkForAvailableCarTypes(Date from, Date end) 
			throws RemoteException;
	
	public Quote createQuote(ReservationConstraints constraints, String client)
			throws RemoteException, ReservationException;
	
	public List<Reservation> confirmQuotes(String name)
			throws RemoteException;
	
	public void cancelReservation(Reservation res) 
			throws RemoteException;
	
	public List<Reservation> getRenterReservations(String clientname)
			throws RemoteException;

	public String getCheapestCarType(Date start, Date end, String region)
			throws RemoteException;

}