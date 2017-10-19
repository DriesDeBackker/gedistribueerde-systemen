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
    
    private String name;
    private String companyName;
    
    public void setName(String newName){
        name = newName;
    }
    
    public void setCarRentalCompany(String companyName){
        company = RentalStore.getRentals().get(companyName);
    }

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
    public int getNumberReservations(String carType, String companyName) {
        try {
            
            CarRentalCompany company = RentalStore.getRental(companyName);
            List<Car> cars = company.getCars();
            int numberOfReservations = 0;
            for(Car car: cars) {
                if (car.getType().getName().equals(carType)){
                    numberOfReservations += car.getAllReservations().size();
                }
            }
            return numberOfReservations;
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.toString());
        }
    }

    @Override
    public String getBestClient(String companyName) {
        try {
            CarRentalCompany company = RentalStore.getRental(companyName);
            Set<String> clients = company.getClients();
            String bestClient = null;
            int reservations = 0;
            Iterator<String> iterator = clients.iterator();
            while(iterator.hasNext()) {
                String client = iterator.next();
                int res = company.getReservationsBy(client).size();
                if (res > reservations){
                    bestClient = client;
                    }
            }
            return bestClient;
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.toString()); 
        }
    }

}
