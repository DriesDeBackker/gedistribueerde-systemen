package rental;

import java.io.Serializable;
import java.rmi.RemoteException;
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
	public void registerCarRentalCompany(String name) throws RemoteException {
		CarRentalCompany newCompany = new CarRentalCompany(name, null, null);
		
	}

	@Override
	public void unregisterCarRentalCompany(String name) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getNumberOfReservationsForCarType(String carType) throws RemoteException {
		return this.getCarRentalAgency().getNumberOfReservationsForCarType(carType);
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
