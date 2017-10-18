/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import rental.Car;
import rental.CarRentalCompany;
import rental.CarType;
import rental.RentalStore;
import rental.Reservation;

/**
 *
 * @author Toby
 */
@Stateless
public class ManagerSession implements ManagerSessionRemote {

    @Override
    public List<CarType> getCarTypesCompany(String companyName) {
        try {
          CarRentalCompany company = RentalStore.getRental(companyName);
          List<CarType> carTypes = new ArrayList<CarType>();
          carTypes.addAll(company.getAllCarTypes());
          return carTypes;
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.toString()); 
        }        
    }

    @Override
    public int getNumberReservations(CarType type, String companyName) {
        try {
            CarRentalCompany company = RentalStore.getRental(companyName);
            List<Car> cars = company.getCars();
            int numberOfReservations = 0;
            for(Car car: cars) {
                if (car.getType() == type){
                    numberOfReservations += car.getAllReservations().size();
                }
            }
            return numberOfReservations;
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.toString());
        }
    }

    @Override
    public String getBestClient() {
        try {
            //Add all the rental companies to a list.
            List<CarRentalCompany> companies = new ArrayList();
            companies.addAll(RentalStore.getRentals().values());
            //Add all cars of all companies to a list.
            List<Car> cars = new ArrayList();
            for(CarRentalCompany company : companies) {
                cars.addAll(company.getCars());
            }
            //Add all reservations of all cars to a list.
            List<Reservation> reservations = new ArrayList();
            for(Car car : cars){
                reservations.addAll(car.getAllReservations());
            }
            //Compute for each customer his/her number of total reservations
            // and put that information in a map.
            HashMap<String, Integer> reservationsPerCustomer = new HashMap<String, Integer>();
            for(Reservation reservation : reservations) {
                String customer = reservation.getCarRenter();
                if (reservationsPerCustomer.containsKey(customer)){
                    reservationsPerCustomer.put(customer, reservationsPerCustomer.get(customer)+1);
                }else{
                    reservationsPerCustomer.put(customer,1);
                }
            }
            //Iterate over the map to find the customer with the most reservations.
            String bestCustomer = null;
            int mostReservations = 0;
            for(String customer : reservationsPerCustomer.keySet()){
                if (reservationsPerCustomer.get(customer) > mostReservations){
                    bestCustomer = customer;
                    mostReservations = reservationsPerCustomer.get(customer);
                }
            }
            return bestCustomer;
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.toString()); 
        }
    }

}
