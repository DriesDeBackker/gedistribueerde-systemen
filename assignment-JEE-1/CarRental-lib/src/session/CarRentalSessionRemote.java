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
    
    public Quote createQuote(ReservationConstraints constraints)throws ReservationException;
    
    public ReservationConstraints createConstraints( Date start, Date end, String carType, String region);
    
    public Set<Quote> getCurrentQuotes();
    
    public List<Reservation> confirmQuotes() throws ReservationException;
    
}
