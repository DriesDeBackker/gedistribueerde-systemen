package session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import rental.Car;
import rental.CarRentalCompany;
import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class CarRentalSession implements CarRentalSessionRemote {

    private String renter;
    private List<Quote> quotes = new ArrayList<Quote>();
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction userTransaction;

    @Override
    public Set<String> getAllRentalCompaniesNames() {
        Query query = em.createQuery("SELECT c.name FROM CarRentalCompany c");
        List<String> list=(List<String>)query.getResultList( );
        Set<String> companies = new HashSet<String>(list);
        return companies ;
    }
    
    public Set<CarRentalCompany> getAllRentalCompanies() {
        Query query = em.createQuery("SELECT c FROM CarRentalCompany c");
        List<CarRentalCompany> list=(List<CarRentalCompany>)query.getResultList( );
        Set<CarRentalCompany> companies = new HashSet<CarRentalCompany>(list);
        return companies ;
    }
    
    public List<CarType> getAvailableCarTypes(Date start, Date end) {
        Query query = em.createQuery("SELECT c FROM Car c");
        List<Car> list=(List<Car>)query.getResultList( );
        Set<CarType> availableCarTypes = new HashSet<CarType>();
        for (Car car : list) {
            if (car.isAvailable(start, end)) {
                availableCarTypes.add(car.getType());
            }
        }
        List<CarType> availableCarTypesList = new ArrayList<CarType>(availableCarTypes);
        return availableCarTypesList;
    }
    
    public boolean isAvailable(CarType type, Date start, Date end) {
        Logger.getLogger(ManagerSession.class.getName()).log(Level.INFO, "Checking availability for car type {0}", new Object[]{type.getName()});
        return getAvailableCarTypes(start, end).contains(type);
    }
    
    private List<Car> getAvailableCars(String companyName, String carType, Date start, Date end) {
        List<Car> availableCars = new ArrayList<Car>();
        Query query = em.createQuery("SELECT c FROM Car c WHERE c.crc.name = :companyName AND c.type.name =:carType")
        .setParameter("companyName", companyName).setParameter("carType", carType);
        List<Car> cars=(List<Car>)query.getResultList( );
        for (Car car : cars) {
            if (car.isAvailable(start, end)) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }
    
    
    @Override
    public Quote addQuote(ReservationConstraints constraints) throws ReservationException {
        Set<String> companies = getAllRentalCompaniesNames();
        for (String company : companies) {
            try {
                  Quote quote = createQuote(company,this.renter, constraints);
                  quotes.add(quote);
                  return quote;
            } catch(ReservationException re) {
                //
            }
        }
        throw new ReservationException("<No cars available in any company to satisfy the given constraints.");
        
    }
    
    
    public Quote createQuote(String companyName, String renter, ReservationConstraints constraints) throws ReservationException {
        Logger.getLogger(ManagerSession.class.getName()).log(Level.INFO, "<{0}> Creating tentative reservation for {1} with constraints {2}",
        new Object[]{companyName, renter, constraints.toString()});
        CarRentalCompany crc = em.find(CarRentalCompany.class, companyName);
        String typeName = constraints.getCarType(); 
        Query query = em.createQuery("SELECT t FROM CarType t WHERE t.crcName = :companyName AND t.name = :typeName")
        .setParameter("companyName",companyName).setParameter("typeName", typeName);
        CarType type;
        try{
        type =(CarType)query.getSingleResult();
        }
        catch(Exception e){
              throw new ReservationException("<" + companyName
              + "> No cars available to satisfy the given constraints.");
        }
        if (!crc.hasRegion(constraints.getRegion()) || !isAvailable(type, constraints.getStartDate(), constraints.getEndDate())) {
            throw new ReservationException("<" + companyName
                    + "> No cars available to satisfy the given constraints.");
        }
        double price = calculateRentalPrice(type.getRentalPricePerDay(), constraints.getStartDate(), constraints.getEndDate());
        return new Quote(renter, constraints.getStartDate(), constraints.getEndDate(), companyName, constraints.getCarType(), price);
    }
    
    // Implementation can be subject to different pricing strategies
    private double calculateRentalPrice(double rentalPricePerDay, Date start, Date end) {
        return rentalPricePerDay * Math.ceil((end.getTime() - start.getTime())
                / (1000 * 60 * 60 * 24D));
    }
    
    @Override
    public List<Quote> getCurrentQuotes() {
        return quotes;
    }
    
    @Override
    public List<Reservation> confirmQuotes() throws ReservationException, Exception {
        List<Reservation> done = new ArrayList<Reservation>();
        try {
            userTransaction.begin();
            for (Quote quote : quotes) {
                done.add(confirmQuote(quote));
            }
            userTransaction.commit();
        } catch (ReservationException e) {
            userTransaction.rollback(); 
        }catch (Exception e1){
            throw new ReservationException(e1);
        }
        return done;
    }
    
    public Reservation confirmQuote(Quote quote) throws ReservationException {
        Logger.getLogger(ManagerSession.class.getName()).log(Level.INFO, "<{0}> Reservation of {1}", new Object[]{quote.getRentalCompany(), quote.toString()});
        List<Car> availableCars = getAvailableCars(quote.getRentalCompany(),quote.getCarType(), quote.getStartDate(), quote.getEndDate());
        if (availableCars.isEmpty()) {
            throw new ReservationException("Reservation failed, all cars of type " + quote.getCarType()
                    + " are unavailable from " + quote.getStartDate() + " to " + quote.getEndDate());
        }
        Car car = availableCars.get((int) (Math.random() * availableCars.size()));
        Reservation res = new Reservation(quote, car.getId());
        em.persist(res);
        car.addReservation(res);
        em.merge(car);
        return res;
    }
    
    @Override
    public void setRenterName(String name) {
        if (renter != null) {
            throw new IllegalStateException("name already set");
        }
        renter = name;
    }
    
    //OK
    @Override
    public Set<CarType> checkForAvailableCarTypes(Date start, Date end) {
        List<CarType> carTypesList = getAvailableCarTypes(start, end);
        Set<CarType> carTypes = new HashSet<CarType>(carTypesList);
        return carTypes;
    }
    
    
    @Override
    public String getCheapestCarType(Date start, Date end, String region) {
        Query query2 = em.createQuery("SELECT t FROM CarType t ORDER BY t.rentalPricePerDay");
        List<CarType> types=(List<CarType>)query2.getResultList( );
        boolean found = false;
        int i= 0;
        String cheapest = null;
        while (found == false && i< types.size()){
            CarRentalCompany crc = em.find(CarRentalCompany.class, types.get(i).getCrcName());
            if((crc.hasRegion(region)) && (isAvailable(types.get(i),start,end))){
                found = true;
                cheapest = types.get(i).getName();
            }
            i = i+1;
        }
        return cheapest;
    }
}