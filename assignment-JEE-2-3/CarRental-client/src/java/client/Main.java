package client;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import rental.CarType;
import rental.Reservation;
import rental.ReservationConstraints;
import session.CarRentalSessionRemote;
import session.ManagerSessionRemote;

public class Main extends AbstractTestManagement<CarRentalSessionRemote, ManagerSessionRemote> {

    @EJB
    static CarRentalSessionRemote session;
    @EJB
    static ManagerSessionRemote managerSession;
    
    public Main(String scriptFile) {
        super(scriptFile);
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main("trips");
        main.getNewManagerSession("name", "carRentalName").loadCompanyFromData();
        System.out.println("line3");
        main.run();
    }

    @Override
    protected Set<String> getBestClients(ManagerSessionRemote session) throws Exception {
        return session.getBestClients();
    }

    @Override
    protected String getCheapestCarType(CarRentalSessionRemote session, Date start, Date end, String region) throws Exception {
        return session.getCheapestCarType(start, end, region);
    }

    @Override
    protected CarType getMostPopularCarTypeIn(ManagerSessionRemote session, String carRentalCompanyName, int year) throws Exception {
        return session.getMostPopularCarTypeIn(carRentalCompanyName, year);
    }

    @Override
    protected CarRentalSessionRemote getNewReservationSession(String name) throws Exception {
        InitialContext context = new InitialContext();
        session = (CarRentalSessionRemote) context.lookup(CarRentalSessionRemote.class.getName());
        session.setRenterName(name);
        return session;
    }

    @Override
    protected ManagerSessionRemote getNewManagerSession(String name, String carRentalName) throws Exception {
        InitialContext context = new InitialContext();
        managerSession = (ManagerSessionRemote) context.lookup(ManagerSessionRemote.class.getName());
        managerSession.setName(name);
        managerSession.setCompanyName(carRentalName);
        return managerSession;
    }

    @Override
    protected void checkForAvailableCarTypes(CarRentalSessionRemote session, Date start, Date end) throws Exception {
        session.checkForAvailableCarTypes(start, end);
    }

    @Override
    protected void addQuoteToSession(CarRentalSessionRemote session, String name, Date start, Date end, String carType, String region) throws Exception {
        ReservationConstraints constraints = new ReservationConstraints(start, end,carType, region);
        session.addQuote(constraints);
    }

    @Override
    protected List<Reservation> confirmQuotes(CarRentalSessionRemote session, String name) throws Exception {
        return session.confirmQuotes();
    }

    @Override
    protected int getNumberOfReservationsForCarType(ManagerSessionRemote session, String carRentalName, String carType) throws Exception {
        return session.getNumberOfReservations(carRentalName, carType);
    }
    
    protected void loadCompanyFromData(ManagerSessionRemote session) {
        session.loadCompanyFromData();
    }
    
    protected void registerCompany(ManagerSessionRemote session, String companyName, List<String> regions) {
        session.registerCompany(companyName, regions);
    }
    
    protected void registerCarType(ManagerSessionRemote session, String name, int nbOfSeats, float trunkSpace, double rentalPricePerDay, boolean smokingAllowed, String companyName) {
        CarType type = session.createCarType(name, nbOfSeats, trunkSpace, rentalPricePerDay, smokingAllowed, companyName);
        session.registerCarType(type, companyName);
    }
    
    protected void registerCar(ManagerSessionRemote session, CarType type, int id, String companyName) {
        session.registerCar(type,id,companyName);
    }
}