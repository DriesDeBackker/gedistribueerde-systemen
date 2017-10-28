package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.IManagerSession;
import rental.IReservationSession;
import rental.ISessionManager;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;

public class Client extends AbstractTestManagement{
	
	/********
	 * MAIN *
	 ********/
	
	public static void main(String[] args) throws Exception {
		
		String sessionManagerName = "sessionManager";
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		ISessionManager smgr = (ISessionManager) registry.lookup(sessionManagerName);
		
		Client client = new Client("simpleTrips", smgr);
		client.run();
	}

	private ISessionManager smgr;

	
	/***************
	 * CONSTRUCTOR *
	 ***************/
	
	public Client(String scriptFile, ISessionManager smgr) {
		super(scriptFile);
		this.smgr = smgr;
	}

	@Override
	protected CarType getMostPopularCarTypeIn(Object ms, String carRentalCompanyName, int year) throws Exception {
		IManagerSession managerSession = (IManagerSession)ms;
		return managerSession.getMostPopularCarTypeIn(carRentalCompanyName, year);
	}

	@Override
	protected Object getNewReservationSession(String name) throws Exception {
		try {
			IReservationSession newReservationSession = this.smgr.getNewReservationSession(name);
			return newReservationSession;
		} catch(Exception e) {
			throw new UnsupportedOperationException("Could not create a reservation session");
		}
	}

	@Override
	protected Object getNewManagerSession(String name, String carRentalName) throws Exception {
		try {
			IManagerSession newManagerSession = this.smgr.getNewManagerSession(name, carRentalName);
			return newManagerSession;
		} catch(Exception e) {
			throw new UnsupportedOperationException("Could not create a manager session");
		}
	}
	
	@Override
	protected void checkForAvailableCarTypes(Object session, Date start, Date end) throws Exception {
		try {
			IReservationSession reservationSession = (IReservationSession)session;
			Set<CarType> carTypes = reservationSession.getAvailableCarTypes(start, end);
			carTypes.forEach(carType -> System.out.println(carType.toString()));
		} catch(Exception e) {
			throw new UnsupportedOperationException();
		}
		
	}

	@Override
	protected void addQuoteToSession(Object session, String name, Date start, Date end, String carType, String region)
			throws Exception {
			ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
			try {
				IReservationSession reservationSession = (IReservationSession)session;
				Quote quote = reservationSession.createQuote(constraints, name);
				System.out.println(quote.toString());
			} catch(Exception e) {
				throw new UnsupportedOperationException(e.toString());
			}
	}

	@Override
	protected List<Reservation> confirmQuotes(Object session, String name) throws Exception {
		IReservationSession reservationSession = (IReservationSession)session;
		return reservationSession.confirmQuotes(name);
	}

	@Override
	protected int getNumberOfReservationsForCarType(Object ms, String carRentalName, String carType) throws Exception {
		// TODO incorporate session
		try {
			IManagerSession managerSession = (IManagerSession)ms;
			int number = managerSession.getNumberOfReservationsForCarType(carType);
			return number;
		} catch (Exception e) {
			throw new UnsupportedOperationException();
		}		
	}

	@Override
	protected Set<String> getBestClients(Object ms) throws Exception {
		try {
			IManagerSession managerSession = (IManagerSession)ms;
			return managerSession.getBestClients();
		} catch (Exception e) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected String getCheapestCarType(Object session, Date start, Date end, String region) throws Exception {
		try {
			IReservationSession reservationSession = (IReservationSession)session;
			return reservationSession.getCheapestCarType(start, end, region);
		} catch (Exception e) {
			throw new UnsupportedOperationException();
		}	
	}
}