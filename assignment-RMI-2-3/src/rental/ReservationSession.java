package rental;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Set;

public class ReservationSession implements Serializable {
	
	private String name;
	private CarRentalAgency carRentalAgency;

	ReservationSession(String name, CarRentalAgency agency) {
		this.setName(name);
		this.setCarRentalAgency(agency);
	}
	
	public Set<CarType> checkForAvailableCarTypes(Date start, Date end) throws RemoteException {
		return this.carRentalAgency.getAvailableCarTypes(start, end);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setCarRentalAgency(CarRentalAgency carRentalAgency) {
		this.carRentalAgency = carRentalAgency;
	}

}
