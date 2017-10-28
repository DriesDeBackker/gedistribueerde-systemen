package rental;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ReservationSession implements IReservationSession {
	
	private String name;
	private CarRentalAgency carRentalAgency;
	private List<Quote> quotes;

	ReservationSession(String name, CarRentalAgency agency) {
		this.setName(name);
		this.setCarRentalAgency(agency);
		this.quotes = new ArrayList<Quote>();
	}
	
	@Override
	public Set<CarType> checkForAvailableCarTypes(Date start, Date end) throws RemoteException {
		return this.carRentalAgency.getAvailableCarTypes(start, end);
	}
	
	@Override
	public String getCheapestCarType(Date start, Date end, String region) {
		return this.carRentalAgency.getCheapestCarType(start, end, region);
		
	}

	@Override
	public Quote createQuote(ReservationConstraints constraints, String client)
			throws RemoteException, ReservationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> confirmQuotes(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelReservation(Reservation res) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reservation> getRenterReservations(String clientname) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfReservationsForCarType(String carType) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setCarRentalAgency(CarRentalAgency carRentalAgency) {
		this.carRentalAgency = carRentalAgency;
	}

}
