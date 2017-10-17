package session;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateful;
import rental.CarRentalCompany;
import rental.Quote;
import rental.RentalStore;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Stateful
public class CarRentalSession implements CarRentalSessionRemote {

    private Set<Quote> quotes;
    private Set<Reservation> reservations;
    
    @Override
    public Set<String> getAllRentalCompanies() {
        return new HashSet<String>(RentalStore.getRentals().keySet());
    }
    
    @Override
    public Quote createQuote(ReservationConstraints constraints) throws ReservationException{
        Map<String, CarRentalCompany> rentals = RentalStore.getRentals();
        CarRentalCompany company = null;
        Set<String> rentalNames = rentals.keySet();
        boolean found = false;
        Iterator<String> iterator = rentalNames.iterator();
        while(! found && iterator.hasNext()) {
            CarRentalCompany rentalCompany = rentals.get(iterator.next());
            if(rentalCompany.hasRegion(constraints.getRegion())
                && rentalCompany.isAvailable(constraints.getCarType(), constraints.getStartDate(), constraints.getEndDate())) {
                found = true;
                company = rentalCompany;
            }
        }
        if (found == false){
        throw new ReservationException("> No cars available to satisfy the given constraints.");
        }
        Quote quote = company.createQuote(constraints, "guest");
        this.quotes.add(quote);
        return quote;
    }

    
    @Override
    public Set<Quote> getCurrentQuotes(){
        return this.quotes;
    }
    
    @Override
    public void confirmQuotes(Set<Quote> quotes) throws ReservationException {
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
    } 
        
}
