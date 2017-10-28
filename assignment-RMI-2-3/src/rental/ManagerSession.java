package rental;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagerSession implements IManagerSession {
	
	private String name;
	private String rentalName;
	private CarRentalAgency carRentalAgency;

	ManagerSession(String name, String rentalName, CarRentalAgency agency) {
		this.setName(name);
		this.setRentalName(rentalName);
		this.setCarRentalAgency(agency);
	}
	
	@Override
	public Set<String> getAllRegisteredCarCompanies() throws RemoteException {
		try {
			return this.getCarRentalAgency().getCarRentalCompanyNames();
		} catch (Exception e) {
			throw new RemoteException("Could not retrieve the names of the registered companies.");
		}
	}
	
	@Override
	public void registerCarRentalCompany(String name) throws RemoteException {
		try {
			this.getCarRentalAgency().registerCarRentalCompany(name);
		} catch (Exception e) {
			throw new RemoteException("Could not register company.");
		}
	}

	@Override
	public void unregisterCarRentalCompany(String name) throws RemoteException {
		try {
			this.getCarRentalAgency().unregisterCarRentalCompany(name);
		} catch (Exception e) {
			throw new RemoteException("Could not unregister company.");
		}	
	}
	
	@Override
	public int getNumberOfReservationsForCarType(String carRentalName, String carType) throws RemoteException {
		return this.getCarRentalAgency().getNumberOfReservationsForCarType(carRentalName, carType);
	}
	
	@Override
	public Set<String> getBestClients() {
		return this.getCarRentalAgency().getBestClients();
	}
	
	@Override
	public CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year){
		return this.getCarRentalAgency().getMostPopularCarTypeIn(carRentalCompanyName, year);
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

	public CarRentalAgency getCarRentalAgency() {
		return carRentalAgency;
	}

	private void setCarRentalAgency(CarRentalAgency carRentalAgency) {
		this.carRentalAgency = carRentalAgency;
	}

}
