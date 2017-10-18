package session;

import java.util.Set;
import javax.ejb.Remote;

import rental.Quote;
import rental.ReservationConstraints;
import rental.ReservationException;

@Remote
public interface CarRentalSessionRemote {

    public void setName(String name);
    
    public Set<String> getAllRentalCompanies();
    
    public Quote createQuote(ReservationConstraints constraints)throws ReservationException;
    
    public Set<Quote> getCurrentQuotes();
    
    public void confirmQuotes(Set<Quote> quotes) throws ReservationException;
    
}
