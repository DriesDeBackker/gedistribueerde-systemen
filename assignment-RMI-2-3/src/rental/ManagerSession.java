package rental;

import java.io.Serializable;

public class ManagerSession implements Serializable{
	
	private String name;
	private String rentalName;
	private CarRentalAgency carRentalAgency;

	ManagerSession(String name, String rentalName, CarRentalAgency agency) {
		this.setName(name);
		this.setRentalName(rentalName);
		this.setCarRentalAgency(agency);
	}
	
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
