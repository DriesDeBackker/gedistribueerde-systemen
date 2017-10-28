package rental;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CarRentalAgency {
	
	private ArrayList<CarRentalCompany> carRentalCompanies;

	CarRentalAgency(){
		this.carRentalCompanies = new ArrayList<CarRentalCompany>();
	}

	public Set<CarType> getAvailableCarTypes(Date from, Date end) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
