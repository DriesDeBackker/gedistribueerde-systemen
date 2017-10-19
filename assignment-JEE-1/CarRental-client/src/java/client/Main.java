package client;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import session.CarRentalSessionRemote;
import session.ManagerSessionRemote;

public class Main extends AbstractTestAgency{
    
    @EJB
    static CarRentalSessionRemote session;
    static ManagerSessionRemote managerSession;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("found rental companies: "+session.getAllRentalCompanies());
    }

    public Main(String scriptFile) {
        super(scriptFile);
    }

    @Override
    protected Object getNewReservationSession(String name) throws Exception {
       return new CarRentalSession():
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Object getNewManagerSession(String name, String carRentalName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void checkForAvailableCarTypes(Object session, Date start, Date end) throws Exception {
        CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
        rs.checkForAvailableCarTypes(start, end);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void addQuoteToSession(Object session, String name, Date start, Date end, String carType, String region) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List confirmQuotes(Object session, String name) throws Exception {
        CarRentalSessionRemote rs = (CarRentalSessionRemote)session;
        rs.confirmQuotes();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected int getNumberOfReservationsForCarType(Object session, String carRentalName, String carType) throws Exception {
        try {
            ManagerSessionRemote ms = (ManagerSessionRemote)session;
            return ms.getNumberReservations(carType, carRentalName);
        }catch(Exception e) {
            throw new UnsupportedOperationException(e.toString());
        }
         //To change body of generated methods, choose Tools | Templates.
    }
}
