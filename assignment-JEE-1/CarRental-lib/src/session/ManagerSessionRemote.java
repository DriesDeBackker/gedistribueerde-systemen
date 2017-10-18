/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.Set;
import javax.ejb.Remote;
import rental.CarType;

/**
 *
 * @author Toby
 */
@Remote
public interface ManagerSessionRemote {
    public Set<CarType> getCarTypesCompany(String companyName);
    
    public int getNumberReservations(CarType type, String companyName);
    
    public String getBestClient();
}
