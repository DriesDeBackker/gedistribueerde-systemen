package rental;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CarRentalAgency implements ICarRentalAgency {
	
	private ArrayList<CarRentalCompany> carRentalCompanies;
	private ArrayList<ManagerSession> managerSessions;
	private ArrayList<ReservationSession> reservationSessions;

	CarRentalAgency(){
		this.carRentalCompanies = new ArrayList<CarRentalCompany>();
		this.managerSessions = new ArrayList<ManagerSession>();
		this.reservationSessions = new ArrayList<ReservationSession>();
	}

	public Set<CarType> getAvailableCarTypes(Date from, Date end) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IReservationSession getNewReservationSession(String name) throws RemoteException {
		ReservationSession newReservationSession = new ReservationSession(name, this);
		this.reservationSessions.add(newReservationSession);
		//return (IReservationSession)UnicastRemoteObject.exportObject(newReservationSession, 0);
		//Don't yet know at this point whether the statement above or the one below should be used.
		return newReservationSession;
	}
	
	@Override
	public IManagerSession getNewManagerSession(String name, String carRentalName) throws RemoteException {
		ManagerSession newManagerSession = new ManagerSession(name, carRentalName, this);
		this.managerSessions.add(newManagerSession);
		//return (IManagerSession)UnicastRemoteObject.exportObject(newManagerSession, 0);
		//Don't yet know at this point whether the statement above or the one below should be used.
		return newManagerSession;
	}
	
	/*
	@Override
	public Quote createQuote(ReservationConstraints constraints, String client)
			throws RemoteException, ReservationException {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	public boolean canCreateQuote(ReservationConstraints constraint, String client) {
		// TODO implement this
		return true;
	}
	
	/*
	@Override
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException {
		// TODO implement this
		return null;
	}*/
	
	/*
	@Override
	public void cancelReservation(Reservation res) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	*/
	
	public List<Reservation> getRenterReservations(String clientname) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getNumberOfReservationsForCarType(String carType) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	public CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Set<String> getBestClients(){
		// TODO Auto-generated method stub
		return null;
	}

	public String getCheapestCarType(Date start, Date end, String region) {
		// TODO Auto-generated method stub
		return null;
	}

}
