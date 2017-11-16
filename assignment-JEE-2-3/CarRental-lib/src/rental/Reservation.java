package rental;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Reservation extends Quote {
    
    @Id
    private int reservationId;
    private int carId;
    
    /***************
     * CONSTRUCTOR *
     ***************/

    public Reservation(){}
    
    public Reservation(Quote quote, int carId) {
    	super(quote.getCarRenter(), quote.getStartDate(), quote.getEndDate(), 
    		quote.getRentalCompany(), quote.getCarType(), quote.getRentalPrice());
        this.carId = carId;
    }
    
    public void initReservationId() {
        this.reservationId = this.hashCode();
    }
    
    public int getReservationId() {
        return this.reservationId;
    }
    
    public int getCarId() {
    	return carId;
    }
    
    /*************
     * TO STRING *
     *************/
    
    @Override
    public String toString() {
        return String.format("Reservation for %s from %s to %s at %s\nCar type: %s\tCar: %s\nTotal price: %.2f", 
                getCarRenter(), getStartDate(), getEndDate(), getRentalCompany(), getCarType(), getCarId(), getRentalPrice());
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((carRenter == null) ? 0 : carRenter.hashCode());
	result = prime * result + ((carType == null) ? 0 : carType.hashCode());
	result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
	result = prime * result + ((rentalCompany == null) ? 0 : rentalCompany.hashCode());
        result = prime * result + carId;
        
	long temp;
	temp = Double.doubleToLongBits(rentalPrice);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (this.carId != other.carId) {
            return false;
        }
        return true;
    }
}