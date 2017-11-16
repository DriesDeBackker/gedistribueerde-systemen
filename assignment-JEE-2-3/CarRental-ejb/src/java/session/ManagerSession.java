package session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import rental.Car;
import rental.CarRentalCompany;
import rental.CarType;
import rental.Reservation;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ManagerSession implements ManagerSessionRemote {

    private String name;
    private String carRentalName;
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction userTransaction;
    
    
    @Override
    public Set<CarType> getCarTypes(String company) {
        CarRentalCompany crc = em.find(CarRentalCompany.class, company);
        return crc.getCarTypes();
    }

    @Override
    public int getNumberOfReservations(String company, String type, int id) {
        Query query = em.createQuery("SELECT c FROM Car c WHERE c.crc = :company AND c.type.name = :type AND c.carId = :id");
        List<Car> list=(List<Car>)query.getResultList( );
        int n = 0;
        for(Car c : list){
           n += c.getReservations().size();
        }
        return n;
    }

    @Override
    public int getNumberOfReservations(String company, String type) {
        Query query = em.createQuery("SELECT c FROM Car c WHERE c.crc = :company AND c.type.name = :type");
        Car car =  (Car) query.getSingleResult();
        return car.getReservations().size();
    }

    @Override
    public void setCompanyName(String carRentalName) {
        this.carRentalName = carRentalName;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> getBestClients() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerCompany(String companyName, List<String> regions) {
        CarRentalCompany crc = new CarRentalCompany();
        crc.setName(companyName);
        crc.setRegions(regions);
        try {
            userTransaction.begin();
            em.persist(crc);
            userTransaction.commit();
        }
        catch(Exception e){
            throw new EJBException(e);    
        }
    }
    
    public Car createCar(CarType carType, int id){
        Car car = new Car();
        car.setCarId(id);
        car.setType(carType);
        try {
            userTransaction.begin();
            em.persist(car);
            userTransaction.commit();
        }
        catch(Exception e){
            throw new EJBException(e);    
        }
        return car;
    }
    
    @Override
    public CarType createCarType(String name, int nbOfSeats, float trunkSpace, double rentalPricePerDay, boolean smokingAllowed, String crcName){
        CarType type = new CarType();
        type.setName(name);
        type.setNbOfSeats(nbOfSeats);
        type.setTrunkSpace(trunkSpace);
        type.setRentalPricePerDay(rentalPricePerDay);
        type.setSmokingAllowed(smokingAllowed);
        type.setCrcName(crcName);
        try {
            userTransaction.begin();
            em.persist(type);
            userTransaction.commit();
        }
        catch(Exception e){
            throw new EJBException(e);    
        }
        return type;
    }
    
    @Override
    public void registerCar(CarType type, int id,String crcName) {
        Car car = createCar(type,id);
        CarRentalCompany crc = em.find(CarRentalCompany.class, crcName);
        crc.addCar(car);
        try {
            userTransaction.begin();
            em.persist(crc);
            userTransaction.commit();
        }
        catch(Exception e){
            throw new EJBException(e);    
        }
    }

    @Override
    public void registerCarType(CarType type, String crcName) {  
        CarRentalCompany crc = em.find(CarRentalCompany.class, crcName);
        crc.addCarType(type);
        try {
            userTransaction.begin();
            em.persist(crc);
            userTransaction.commit();
        }
        catch(Exception e){
            throw new EJBException(e);    
        }
    }

    @Override
    public void loadCompanyFromData() {  
        loadRental("hertz.csv");
        loadRental("dockx.csv");
    }
    
    public void loadRental(String datafile) {
        try {
            loadData(datafile);
        } catch (NumberFormatException ex) {
            Logger.getLogger(ManagerSession.class.getName()).log(Level.SEVERE, "bad file", ex);
        } catch (IOException ex) {
            Logger.getLogger(ManagerSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadData(String datafile)
            throws NumberFormatException, IOException {
        StringTokenizer csvReader;
        int nextuid = 0;
       
        //open file from jar
        BufferedReader in = new BufferedReader(new InputStreamReader(ManagerSession.class.getClassLoader().getResourceAsStream(datafile)));
        String crcName = null;
        try {
            while (in.ready()) {
                String line = in.readLine();
                
                if (line.startsWith("#")) {
                    // comment -> skip					
                } else if (line.startsWith("-")) {
                    csvReader = new StringTokenizer(line.substring(1), ",");
                    crcName = csvReader.nextToken();
                    List<String> crcRegions = Arrays.asList(csvReader.nextToken().split(":"));
                    registerCompany(crcName,crcRegions);
                } else {
                    csvReader = new StringTokenizer(line, ",");
                    //create new car type from first 5 fields
                    CarType type = createCarType(csvReader.nextToken(),
                    Integer.parseInt(csvReader.nextToken()),
                    Float.parseFloat(csvReader.nextToken()),
                    Double.parseDouble(csvReader.nextToken()),
                    Boolean.parseBoolean(csvReader.nextToken()),crcName);
                    registerCarType(type,crcName);
                    //create N new cars with given type, where N is the 5th field
                    for (int i = Integer.parseInt(csvReader.nextToken()); i > 0; i--) {
                        registerCar(type,nextuid++,crcName);
                    }        
                }
            } 
        } finally {
            in.close();
        }
    }
}