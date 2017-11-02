package rental;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class RentalServer {
	
	public static void main(String[] args) throws Exception {
		System.setSecurityManager(null);
		NamingService ns = new NamingService();
		ns.registerCarRentalCompany("hertz");
		ns.registerCarRentalCompany("dockx");
		SessionManager smgr = new SessionManager(ns);
		ISessionManager stub = (ISessionManager)UnicastRemoteObject.exportObject(smgr, 0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("sessionManager", stub);
	}

}
