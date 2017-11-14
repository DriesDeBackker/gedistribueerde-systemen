package session;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateful;
import rental.CarRentalCompany;
import rental.CarType;
import rental.PersistenceManager;
import rental.Quote;
import rental.RentalStore;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Stateful
public class CarRentalSession implements CarRentalSessionRemote {

    private String renter;
    private List<Quote> quotes = new LinkedList<Quote>();
    private Set<CarType> availableCarTypes;
    private PersistenceManager pm;

    @Override
    public Set<String> getAllRentalCompanies() {
        return new HashSet<String>(pm.getRentalCompanyNames());
    }
    
    public List<CarType> getAvailableCarTypes(Date start, Date end) {
        List<CarType> availableCarTypes = new LinkedList<CarType>();
        for(String crc : getAllRentalCompanies()) {
            //TODO: rewrite with direct queries
            for(CarType ct : pm.getCarRentalCompany(crc).getAvailableCarTypes(start, end)) {
                if(!availableCarTypes.contains(ct))
                    availableCarTypes.add(ct);
            }
        }
        return availableCarTypes;
    }
    
    //OK
    public Quote addQuote(ReservationConstraints constraints) throws ReservationException {
        List<CarRentalCompany> companies = pm.getRentalCompanies();
        for (CarRentalCompany company : companies) {
            try {
                return this.createQuote(renter, constraints);
            } catch(ReservationException re) {
                //
            }
        }
        throw new ReservationException("<No cars available in any company to satisfy the given constraints.");
    }
    
    //OK
    public Quote createQuote(String company, ReservationConstraints constraints) throws ReservationException {
        try {
            Quote out = pm.getCarRentalCompany(company).createQuote(constraints, renter);
            quotes.add(out);
            return out;
        } catch(Exception e) {
            throw new ReservationException(e);
        }
    }
    
    //OK
    @Override
    public List<Quote> getCurrentQuotes() {
        return quotes;
    }
    
    //TODO: queries in transaction style??
    @Override
    public List<Reservation> confirmQuotes() throws ReservationException {
        List<Reservation> done = new LinkedList<Reservation>();
        try {
            for (Quote quote : quotes) {
                //TODO: Rewrite in transaction style queries
                done.add(pm.getCarRentalCompany(quote.getRentalCompany()).confirmQuote(quote));
            }
        } catch (Exception e) {
            for(Reservation r:done)
                pm.getCarRentalCompany(r.getRentalCompany()).cancelReservation(r);
            throw new ReservationException(e);
        }
        return done;
    }
    
    //OK
    @Override
    public void setRenterName(String name) {
        if (renter != null) {
            throw new IllegalStateException("name already set");
        }
        renter = name;
    }
    
    //OK
    @Override
    public void checkForAvailableCarTypes(Date start, Date end) {
        List<CarType> carTypesList = this.getAvailableCarTypes(start, end);
        this.availableCarTypes.clear();
        this.availableCarTypes.addAll(carTypesList);
    }
    
    //OK
    @Override
    public String getCheapestCarType(Date start, Date end, String region) {
        List<CarType> carTypes = pm.getCarTypesOrderedByPrice();
        return carTypes.get(0).getName();
    }
}