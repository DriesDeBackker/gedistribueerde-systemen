package rental;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReservationSession implements IReservationSession {
	
	private String name;
	private NamingService namingService;
	private List<Quote> quotes;

	ReservationSession(String name, NamingService agency) {
		this.setName(name);
		this.setNamingService(agency);
		this.quotes = new ArrayList<Quote>();
	}
	
	@Override
	public Set<CarType> checkForAvailableCarTypes(Date start, Date end) throws RemoteException {
		Set<CarType> availableCarTypes = new HashSet<CarType>();
		for (CarRentalCompany company : this.namingService.getCarRentalCompanies()) {
			availableCarTypes.addAll(company.getAvailableCarTypes(start, end));
		}
		return availableCarTypes;
	}
	
	public String getCheapestCarType(Date start, Date end, String region) {
		double lowestPrice = 999999999;
		String cheapestCar = null;
		Map<String, Double> carTypeWithPrice = this.getAvailableCarTypesWithPrice(start, end, region);
		for(String carType : carTypeWithPrice.keySet()) {
			double price = carTypeWithPrice.get(carType);
			if (cheapestCar == null || price < lowestPrice) {
				cheapestCar = carType;
				lowestPrice = price;
			}
		}
		return cheapestCar;
	}

	private Map<String, Double> getAvailableCarTypesWithPrice(Date start, Date end, String region) {
		Set<CarType> availableCarTypes = this.getAvailableCarTypes(start, end, region);
		Map<String, Double> availableCarTypesWithPrice = new HashMap<String, Double>();
		for (CarType carType : availableCarTypes) {
			availableCarTypesWithPrice.put(carType.getName(), carType.getRentalPricePerDay());
		}
		return availableCarTypesWithPrice;
	}

	private Set<CarType> getAvailableCarTypes(Date start, Date end, String region) {
		Set<CarType> availableCarTypes = new HashSet<CarType>();
		for (CarRentalCompany company : this.namingService.getCarRentalCompanies()) {
			if (company.hasRegion(region)) {
				availableCarTypes.addAll(company.getAvailableCarTypes(start, end));
			}
		}
		return availableCarTypes;
	}

	
	/*
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

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setNamingService(NamingService namingService) {
		this.namingService = namingService;
	}

}
