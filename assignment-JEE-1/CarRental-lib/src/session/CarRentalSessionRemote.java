package session;

import java.util.Set;
import javax.ejb.Remote;

import rental.Quote;
import rental.ReservationConstraints;
import rental.ReservationException;

@Remote
public interface CarRentalSessionRemote {

    Set<String> getAllRentalCompanies();
    
    Quote createQuote(ReservationConstraints constraints);
    
    Set<Quote> getCurrentQuotes();
    
    void confirmQuotes(Set<Quote> quotes) throws ReservationException;
    
}
