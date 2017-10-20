package session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateful;
import rental.CarRentalCompany;
import rental.CarType;
import rental.Quote;
import rental.RentalStore;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Stateful
public class CarRentalSession implements CarRentalSessionRemote {

    private Set<Quote> quotes;
    private List<Reservation> reservations;
    private String name;
    private Set<CarType> availableCarTypes;
    
    @Override
    public void setName(String name){
        this.name = name;
    }
    
    @Override
    public Set<String> getAllRentalCompanies() {
        return new HashSet<String>(RentalStore.getRentals().keySet());
    }
    
    @Override
    public void createQuote(ReservationConstraints constraints) throws ReservationException{
        Map<String, CarRentalCompany> rentals = RentalStore.getRentals();
        Set<String> rentalNames = rentals.keySet();
        boolean found = false;
        Iterator<String> iterator = rentalNames.iterator();
        while(! found && iterator.hasNext()) {
            CarRentalCompany rentalCompany = rentals.get(iterator.next());
            if(rentalCompany.hasRegion(constraints.getRegion())
                && rentalCompany.isAvailable(constraints.getCarType(), constraints.getStartDate(), constraints.getEndDate())) {
                found = true;
                CarRentalCompany company = rentalCompany;
                Quote quote = company.createQuote(constraints, this.name);
                this.quotes.add(quote);
            }
        }
        if (found == false){
        throw new ReservationException("> No cars available to satisfy the given constraints.");
        }
    }

    
    @Override
    public Set<Quote> getCurrentQuotes(){
        return this.quotes;
    }
    
    @Override
    public List<Reservation> confirmQuotes() throws ReservationException {
        Iterator<Quote> iterator = quotes.iterator();
        while(iterator.hasNext()) {
            Quote quote = iterator.next();
            CarRentalCompany company = RentalStore.getRental(quote.getRentalCompany());
            try{
                reservations.add(company.confirmQuote(quote));
            }
            catch(ReservationException e){
                Iterator<Reservation> iterator2 = reservations.iterator();
                while(iterator2.hasNext()) {
                    Reservation res = iterator2.next();
                    company.cancelReservation(res);
                }
                throw new ReservationException("Error while making reservations, all are cancelled");
            }
        }
        return this.reservations;
    } 

    @Override
    public void checkForAvailableCarTypes(Date start, Date end) throws UnsupportedOperationException {
        List<CarType> carTypes = new ArrayList<CarType>();
        for(CarRentalCompany company : RentalStore.getRentals().values()){
            carTypes.addAll(company.getAvailableCarTypes(start, end));
        }
        availableCarTypes = new HashSet<CarType>(carTypes);
    }
        
}
