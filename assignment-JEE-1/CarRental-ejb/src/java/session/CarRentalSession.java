package session;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateful;
import rental.CarRentalCompany;

import rental.Quote;
import rental.RentalStore;
import rental.ReservationConstraints

@Stateful
public class CarRentalSession implements CarRentalSessionRemote {

    private Set<Quote> quotes;

    @Override
    public Set<String> getAllRentalCompanies() {
        return new HashSet<String>(RentalStore.getRentals().keySet());
    }
    
    @Override
    public Quote createQuote(ReservationConstraints constraints){
        Map<String, CarRentalCompany> rentals = RentalStore.getRentals();
        Set<String> rentalNames = rentals.keySet();
        int length = rentalNames.size();
        boolean found = false;
        int i = 0;
        Iterator<String> iterator = rentalNames.iterator();
        while(! found && iterator.hasNext()) {
            CarRentalCompany rentalCompany = rentals.get(iterator.next());
            if(rentalCompany.hasRegion(constraints.getRegion())
                && rentalCompany.isAvailable(constraints.getCarType(), constraints.getStartDate(), constraints.getEndDate())) {
                found = true;            
        }
        Quote newQuote =  new Quote(carRenter, constraints.getStartDate(), 
                constraints.getEndDate(),constraints, rentalCompany,
                constraints.getCarType(),constraints.getRegion());
        this.quotes.add(newQuote);
    };
    
    @Override
    public Set<Quote> getCurrentQuotes(){
        
    };
    
    @Override
    public void confirmQuotes(Set<Quote> quotes) throws ReservationException {
        
    };

    
    
}
