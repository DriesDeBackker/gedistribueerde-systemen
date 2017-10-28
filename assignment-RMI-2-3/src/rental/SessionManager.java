package rental;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SessionManager implements ISessionManager {
	
	private ArrayList<ManagerSession> managerSessions;
	private ArrayList<ReservationSession> reservationSessions;
	private CarRentalAgency carRentalAgency;

	SessionManager(CarRentalAgency carRentalAgency) {
		this.managerSessions = new ArrayList<ManagerSession>();
		this.reservationSessions = new ArrayList<ReservationSession>();
		this.carRentalAgency = carRentalAgency;
	}

	@Override
	public IReservationSession getNewReservationSession(String name) throws RemoteException {
		try {
			ReservationSession newReservationSession = new ReservationSession(name, this.carRentalAgency);
			this.reservationSessions.add(newReservationSession);
			//return (IReservationSession)UnicastRemoteObject.exportObject(newReservationSession, 0);
			//Don't yet know at this point whether the statement above or the one below should be used.
			return newReservationSession;
		} catch (Exception e) {
			throw new RemoteException("Could not create a reservation session.");
		}
	}
	
	@Override
	public IManagerSession getNewManagerSession(String name, String carRentalName) throws RemoteException {
		try {
			ManagerSession newManagerSession = new ManagerSession(name, carRentalName, this.carRentalAgency);
			this.managerSessions.add(newManagerSession);
			//return (IManagerSession)UnicastRemoteObject.exportObject(newManagerSession, 0);
			//Don't yet know at this point whether the statement above or the one below should be used.
			return newManagerSession;
		} catch (Exception e) {
			throw new RemoteException("Could not create a manager session.");
		}

	}
}