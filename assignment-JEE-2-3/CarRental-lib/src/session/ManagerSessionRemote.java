package session;

import java.util.Set;
import javax.ejb.Remote;
import rental.CarType;
import rental.Reservation;

@Remote
public interface ManagerSessionRemote {
    
    public Set<CarType> getCarTypes(String company);
    
    public Set<Integer> getCarIds(String company,String type);
    
    public int getNumberOfReservations(String company, String type, int carId);
    
    public int getNumberOfReservations(String company, String type);

    public void setCompanyName(String carRentalName);
    
    public void setName(String name);

    public CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year);

    public Set<String> getBestClients();

    public void registerCompany(String companyName);

    public void registerCar(String carTypeName);

    public void registerCarType(String name, int nbOfSeats, float trunkSpace, double rentalPricePerDay, boolean smokingAllowed);

    public void loadCompanyFromData(String companyName);
}