package rental;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagerSession implements IManagerSession {
	
	private String name;
	private String rentalName;
	private NamingService namingService;

	ManagerSession(String name, String rentalName, NamingService agency) {
		this.setName(name);
		this.setRentalName(rentalName);
		this.setNamingService(agency);
	}
	
	@Override
	public Set<String> getAllRegisteredCarCompanies() throws RemoteException {
		try {
			return this.getNamingService().getCarRentalCompanyNames();
		} catch (Exception e) {
			throw new RemoteException("Could not retrieve the names of the registered companies.");
		}
	}
	
	@Override
	public void registerCarRentalCompany(String name) throws RemoteException {
		try {
			this.getNamingService().registerCarRentalCompany(name);
		} catch (Exception e) {
			throw new RemoteException("Could not register company.");
		}
	}

	@Override
	public void unregisterCarRentalCompany(String name) throws RemoteException {
		try {
			this.getNamingService().unregisterCarRentalCompany(name);
		} catch (Exception e) {
			throw new RemoteException("Could not unregister company.");
		}	
	}
	
	@Override
	public int getNumberOfReservationsForCarType(String carRentalName, String carType) throws RemoteException {
		CarRentalCompany crc = this.namingService.getCarRentalCompanyByName(carRentalName);
		crc.getNumberOfReservationsForCarType(carType);
		return 0;
	}
	
	@Override
	public Set<String> getBestClients() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year){
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getRentalName() {
		return rentalName;
	}

	private void setRentalName(String rentalName) {
		this.rentalName = rentalName;
	}

	public NamingService getNamingService() {
		return namingService;
	}

	private void setNamingService(NamingService namingService) {
		this.namingService = namingService;
	}

}
