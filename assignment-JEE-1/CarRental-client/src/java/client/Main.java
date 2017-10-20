package client;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import session.CarRentalSessionRemote;
import session.ManagerSessionRemote;

public class Main extends AbstractTestAgency{
    
    @EJB
    static CarRentalSessionRemote session;
    @EJB
    static ManagerSessionRemote managerSession;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {	
	// An example reservation scenario on car rental company 'Hertz' would be...
	Main main = new Main("simpleTrips");
	main.run();
	}
	
    public Main(String scriptFile) {
	super(scriptFile);
    }

    @Override
    protected Object getNewReservationSession(String name) throws Exception {
        //InitialContext context = new InitialContext();
        //session = (CarRentalSessionRemote) context.lookup(CarRentalSessionRemote.class.getName());
        session.setName(name);
        return session;
    }

    @Override
    protected Object getNewManagerSession(String name, String carRentalName) throws Exception {
        //InitialContext context = new InitialContext();
        //managerSession = (ManagerSessionRemote) context.lookup(ManagerSessionRemote.class.getName());
        managerSession.setName(name);
        managerSession.setCompanyName(carRentalName);
        return managerSession;
    }

    @Override
    protected void checkForAvailableCarTypes(Object session, Date start, Date end) throws Exception {
        CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
        rs.checkForAvailableCarTypes(start, end);
    }

    @Override
    protected void addQuoteToSession(Object session, String name, Date start, Date end, String carType, String region) throws Exception {
        CarRentalSessionRemote rs = (CarRentalSessionRemote)session;  
        rs.createQuote(start, end, carType, region);
    }

    @Override
    protected List confirmQuotes(Object session, String name) throws Exception {
        CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
        return rs.confirmQuotes();
    }

    @Override
    protected int getNumberOfReservationsForCarType(Object session, String carRentalName, String carType) throws Exception {
        try {
            ManagerSessionRemote ms = (ManagerSessionRemote)session;
            return ms.getNumberReservations(carType, carRentalName);
        }catch(Exception e) {
            throw new UnsupportedOperationException(e.toString());
        }
    }
}
