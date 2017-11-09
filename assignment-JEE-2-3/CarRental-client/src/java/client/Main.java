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
        // TODO: use updated manager interface to load cars into companies
        new Main("trips").run();
    }

    @Override
    protected Set<String> getBestClients(ManagerSessionRemote session) throws Exception {
        ManagerSessionRemote ms = (ManagerSessionRemote)session;
        return ms.getBestClients();
    }

    @Override
    protected String getCheapestCarType(CarRentalSessionRemote session, Date start, Date end, String region) throws Exception {
        CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
        return rs.getCheapestCarType(start, end, region);
    }

    @Override
    protected CarType getMostPopularCarTypeIn(ManagerSessionRemote session, String carRentalCompanyName, int year) throws Exception {
        ManagerSessionRemote ms = (ManagerSessionRemote)session;
        return ms.getMostPopularCarTypeIn(carRentalCompanyName, year);
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
        CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
        rs.checkForAvailableCarTypes(start, end);
    }

    @Override
    protected void addQuoteToSession(CarRentalSessionRemote session, String name, Date start, Date end, String carType, String region) throws Exception {
        CarRentalSessionRemote rs = (CarRentalSessionRemote)session;  
        ReservationConstraints constraints = new ReservationConstraints(start, end,carType, region);
        rs.addQuote(constraints);
    }

    @Override
    protected List<Reservation> confirmQuotes(CarRentalSessionRemote session, String name) throws Exception {
        CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
        return rs.confirmQuotes();
    }

    @Override
    protected int getNumberOfReservationsForCarType(ManagerSessionRemote session, String carRentalName, String carType) throws Exception {
        ManagerSessionRemote ms = (ManagerSessionRemote)session;
        return ms.getNumberOfReservations(carRentalName, carType);
    }
}