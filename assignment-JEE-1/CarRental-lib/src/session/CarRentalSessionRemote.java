package session;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;

import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Remote
public interface CarRentalSessionRemote {

    public void setName(String name);
    
    public Set<String> getAllRentalCompanies();
    
    public void checkForAvailableCarTypes(Date start, Date end);
    
    public void createQuote(ReservationConstraints constraints)throws ReservationException;
    
    public Set<Quote> getCurrentQuotes();
    
    public List<Reservation> confirmQuotes() throws ReservationException;
    
}
