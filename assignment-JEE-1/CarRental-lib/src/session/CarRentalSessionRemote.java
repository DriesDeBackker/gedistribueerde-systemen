package session;

import java.util.Date;
import java.util.Set;
import javax.ejb.Remote;

import rental.Quote;
import rental.ReservationConstraints;
import rental.ReservationException;

@Remote
public interface CarRentalSessionRemote {

    public void setName(String name);
    
    public Set<String> getAllRentalCompanies();
    
    public void checkForAvailableCarTypes(Date start, Date end);
    
    public Quote createQuote(ReservationConstraints constraints)throws ReservationException;
    
    public Set<Quote> getCurrentQuotes();
    
    public void confirmQuotes() throws ReservationException;
    
}
