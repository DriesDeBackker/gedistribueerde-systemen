package rental;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class SessionManager implements ISessionManager {
	
	private ArrayList<ManagerSession> managerSessions;
	private ArrayList<ReservationSession> reservationSessions;
	private CarRentalAgency carRentalAgency;

	SessionManager(CarRentalAgency carRentalAgency){
		this.managerSessions = new ArrayList<ManagerSession>();
		this.reservationSessions = new ArrayList<ReservationSession>();
		this.carRentalAgency = carRentalAgency;
	}

	@Override
	public IReservationSession getNewReservationSession(String name) throws RemoteException {
		ReservationSession newReservationSession = new ReservationSession(name, this.carRentalAgency);
		this.reservationSessions.add(newReservationSession);
		//return (IReservationSession)UnicastRemoteObject.exportObject(newReservationSession, 0);
		//Don't yet know at this point whether the statement above or the one below should be used.
		return newReservationSession;
	}
	
	@Override
	public IManagerSession getNewManagerSession(String name, String carRentalName) throws RemoteException {
		ManagerSession newManagerSession = new ManagerSession(name, carRentalName, this.carRentalAgency);
		this.managerSessions.add(newManagerSession);
		//return (IManagerSession)UnicastRemoteObject.exportObject(newManagerSession, 0);
		//Don't yet know at this point whether the statement above or the one below should be used.
		return newManagerSession;
	}
}