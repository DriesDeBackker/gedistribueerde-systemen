/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.List;
import javax.ejb.Remote;
import rental.CarType;

/**
 *
 * @author Toby
 */
@Remote
public interface ManagerSessionRemote {
    
    public List<CarType> getCarTypesCompany(String companyName);
    
    public int getNumberReservations(String carType, String companyName);
    
    public String getBestClient(String companyName);
    
    public void setCompanyName(String name);
    
    public void setName(String name);
}
