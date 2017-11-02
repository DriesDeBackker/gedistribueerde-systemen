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

public class ReservationSession implements IReservationSession, Serializable {
	
	private String name;
	private NamingService namingService;
	private List<Quote> quotes;
	private List<Reservation> reservations;

	ReservationSession(String name, NamingService agency) {
		this.setName(name);
		this.setNamingService(agency);
		this.quotes = new ArrayList<Quote>();
		this.reservations = new ArrayList<Reservation>();
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
		Set<CarRentalCompany> crcs = getNamingService().getCarRentalCompanies();
        for (CarRentalCompany crc : crcs) {
            if((crc.hasRegion(constraints.getRegion()))
                && (crc.isAvailable(constraints.getCarType(), constraints.getStartDate(), constraints.getEndDate()))) {
                Quote quote = crc.createQuote(constraints, client);
                this.quotes.add(quote);
                return quote;
            }
        }
        throw new ReservationException("> No cars available to satisfy the given constraints.");
	}

	@Override
	public List<Reservation> confirmQuotes(String name) throws RemoteException, ReservationException{
		for (Quote quote: quotes) {
			CarRentalCompany crc;
			try {
				crc = getNamingService().getCarRentalCompanyByName(quote.getRentalCompany());
	            try{
	            	
	                Reservation res = crc.confirmQuote(quote);
	                this.reservations.add(res);
	            }
	            catch(ReservationException e){
	                for(Reservation r : this.reservations){
	                    crc.cancelReservation(r);
	                    this.reservations.remove(r);
	                }
	            }
			} catch (Exception e1) {
				throw new ReservationException("Unexpected naming error during reservation.");
			}
        }
        return this.reservations;
	}
	
	/*
	@Override
	public void cancelReservation(Reservation res) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	*/
	/*
	@Override
	public List<Reservation> getRenterReservations(String clientname) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setNamingService(NamingService namingService) {
		this.namingService = namingService;
	}

	public NamingService getNamingService() {
		return namingService;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

}
