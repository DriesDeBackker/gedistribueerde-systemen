package rental;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class RentalServer {
	
	public static void main(String[] args) throws ReservationException,
		NumberFormatException, IOException {
		System.setSecurityManager(null);
		CarRentalAgency cra = new CarRentalAgency();
		SessionManager smgr = new SessionManager(cra);
		ISessionManager stub = (ISessionManager)UnicastRemoteObject.exportObject(smgr, 0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("sessionManager", stub);
	}

}
