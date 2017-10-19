package client;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import rental.Reservation;
import rental.ReservationConstraints;
import session.CarRentalSessionRemote;
import session.ManagerSessionRemote;

public class Main extends AbstractTestAgency{
    
    @EJB
    static CarRentalSessionRemote rentalSession;
    @EJB
    static ManagerSessionRemote managerSession;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("found rental companies: "+rentalSession.getAllRentalCompanies());
    }

    public Main(String scriptFile) {
        super(scriptFile);
    }

    @Override
    protected Object getNewReservationSession(String name) throws Exception {
        try {
            InitialContext context = new InitialContext(); 
            Object newReservationSession = context.lookup("???????????????");
            CarRentalSessionRemote rs = (CarRentalSessionRemote)newReservationSession;
            rs.setName(name);
            return newReservationSession;
        }catch(Exception e) {
            throw new UnsupportedOperationException(e.toString());
        }
    }

    @Override
    protected Object getNewManagerSession(String name, String carRentalName) throws Exception {
        try{
            InitialContext context = new InitialContext(); 
            Object newManagerSession = context.lookup("????????????????????");
            ManagerSessionRemote ms = (ManagerSessionRemote)newManagerSession;
            ms.setName(name);
            ms.setCarRentalCompany(carRentalName);
            return newManagerSession;
        }catch(Exception e){
            throw new UnsupportedOperationException(e.toString());
        }
    }

    @Override
    protected void checkForAvailableCarTypes(Object session, Date start, Date end) throws Exception {
        try{
            CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
            rs.checkForAvailableCarTypes(start, end);
        }catch(Exception e) {
            throw new UnsupportedOperationException(e.toString());
        }
    }

    @Override
    protected void addQuoteToSession(Object session, String name, Date start, Date end, String carType, String region) throws Exception {
         try{
            CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
            ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
            rs.createQuote(constraints);
        }catch(Exception e) {
            throw new UnsupportedOperationException(e.toString());
        }
    }

    @Override
    protected List<Reservation> confirmQuotes(Object session, String name) throws Exception {
        try{
            CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
            return rs.confirmQuotes();
        }catch(Exception e) {
            throw new UnsupportedOperationException(e.toString());
        }
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
