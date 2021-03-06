package rental;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import rental.CarType;
import rental.CarType;
import rental.Reservation;
import rental.Reservation;

@Entity
public class Car implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int carId;
    @ManyToOne
    private CarType type;
    @OneToMany
    private Set<Reservation> reservations;
    @ManyToOne
    private CarRentalCompany crc;

    /***************
     * CONSTRUCTOR *
     ***************/
    
    public Car(){}
    
    public CarRentalCompany getCrc (){
        return crc;
    }
    
    public void setCrc(CarRentalCompany crc){
        this.crc = crc;
    }
    

    /******
     * ID *
     ******/
    
    public int getId() {
    	return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    /************
     * CAR TYPE *
     ************/
    
    public CarType getType() {
        return type;
    }
	
    public void setType(CarType type) {
            this.type = type;
    }
    /****************
     * RESERVATIONS *
     ****************/

    public boolean isAvailable(Date start, Date end) {
        if(!start.before(end))
            throw new IllegalArgumentException("Illegal given period");

        for(Reservation reservation : reservations) {
            if(reservation.getEndDate().before(start) || reservation.getStartDate().after(end))
                continue;
            return false;
        }
        return true;
    }
    
    public void addReservation(Reservation res) {
        reservations.add(res);
    }
    
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }
    
    //TODO query involvement?
    public Set<Reservation> getReservations() {
        return reservations;
    }
    
    public void setCarId(int i) {
        carId = i;
    }
    
    public int getCarId(){
        return carId;
    }
}