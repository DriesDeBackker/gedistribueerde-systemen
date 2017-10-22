package rental;

import java.io.Serializable;
import java.util.Objects;



public class Reservation extends Quote implements Serializable {

    private int carId;
    
    /***************
	 * CONSTRUCTOR *
	 ***************/

    public Reservation(Quote quote, int carId) {
    	super(quote.getCarRenter(), quote.getStartDate(), quote.getEndDate(), 
    			quote.getRentalCompany(), quote.getCarType(), quote.getRentalPrice());
        this.carId = carId;
    }
    
    /******
     * ID *
     ******/
    
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
    
    /*
    @Override
    public boolean equals(Object o) {
    // self check
    if (this == o)
        return true;
    // null check
    if (o == null)
        return false;
    // type check and cast
    if (getClass() != o.getClass())
        return false;
    Reservation reservation = (Reservation) o;
    // field comparison
    return Objects.equals(carId, reservation.getCarId())
            && Objects.equals(this.getCarRenter(), reservation.getCarRenter())
            && Objects.equals(this.getCarType(), reservation.getCarType())
            && Objects.equals(this.getStartDate(), reservation.getStartDate())
            && Objects.equals(this.getEndDate(), reservation.getEndDate());
}

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.carId;
        return hash;
    }
    */
}