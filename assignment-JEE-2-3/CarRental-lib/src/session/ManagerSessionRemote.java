package session;

import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import rental.CarType;
import rental.Reservation;

@Remote
public interface ManagerSessionRemote {
    
    public Set<CarType> getCarTypes(String company);
    
    public int getNumberOfReservations(String company, String type);

    public void setCompanyName(String carRentalName);
    
    public void setName(String name);

    public CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year);

    public Set<String> getBestClients();

    public void registerCompany(String companyName, List<String> regions);

    public void registerCar(CarType type,int id, String crcName);

    public void registerCarType(CarType type,String crcName);
    
    public CarType createCarType(String name, int nbOfSeats, float trunkSpace, double rentalPricePerDay, boolean smokingAllowed, String crcName);
    
    public void loadCompanyFromData();
}