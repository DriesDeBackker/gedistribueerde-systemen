package rental;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CarType implements Serializable{
    
    @Id //signifies the primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int nbOfSeats;
    private boolean smokingAllowed;
    private double rentalPricePerDay;
    //trunk space in liters
    private float trunkSpace;
    private String crcName;
    
    
    /***************
     * CONSTRUCTOR *
     ***************/
    
    public CarType() {}
    

    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCrcName() {
    	return crcName;
    }
    
    public void setCrcName(String name) {
        this.crcName = name;
    }
    
    public int getNbOfSeats() {
        return nbOfSeats;
    }
    
    public void setNbOfSeats(int n){
        nbOfSeats = n;
    }
    
        public void setSmokingAllowed(boolean smokingAllowed) {
		this.smokingAllowed = smokingAllowed;
	}

	public void setRentalPricePerDay(double rentalPricePerDay) {
		this.rentalPricePerDay = rentalPricePerDay;
	}

	public void setTrunkSpace(float trunkSpace) {
		this.trunkSpace = trunkSpace;
	}
    
    public boolean isSmokingAllowed() {
        return smokingAllowed;
    }

    public double getRentalPricePerDay() {
        return rentalPricePerDay;
    }
    
    public float getTrunkSpace() {
    	return trunkSpace;
    }
    
    /*************
     * TO STRING *
     *************/
    
    @Override
    public String toString() {
    	return String.format("Car type: %s \t[seats: %d, price: %.2f, smoking: %b, trunk: %.0fl]" , 
                getName(), getNbOfSeats(), getRentalPricePerDay(), isSmokingAllowed(), getTrunkSpace());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
	if (obj == null)
            return false;
	if (getClass() != obj.getClass())
            return false;
	CarType other = (CarType) obj;
	if (name == null) {
            if (other.name != null)
                return false;
        } else if (id != other.id)
            return false;
	return true;
    }
}