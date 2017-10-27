package rental;

import java.io.Serializable;

public class ManagerSession implements Serializable{
	
	private String name;
	private String rentalName;

	ManagerSession(String name, String rentalName) {
		this.setName(name);
		this.setRentalName(rentalName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRentalName() {
		return rentalName;
	}

	public void setRentalName(String rentalName) {
		this.rentalName = rentalName;
	}

}
