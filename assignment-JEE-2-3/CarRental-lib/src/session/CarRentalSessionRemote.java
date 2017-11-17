package session;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Remote
public interface CarRentalSessionRemote {
    
    public void setRenterName(String name);
    
    public Set<String> getAllRentalCompaniesNames();
    
    public Set<CarType> checkForAvailableCarTypes(Date start, Date end);
    
    public Quote addQuote(ReservationConstraints constraints) throws ReservationException;
    
    public List<Quote> getCurrentQuotes();
    
    public List<Reservation> confirmQuotes() throws ReservationException, Exception;

    public String getCheapestCarType(Date start, Date end, String region);
    
}