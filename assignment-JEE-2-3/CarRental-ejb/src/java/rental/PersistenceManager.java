package rental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class PersistenceManager {
    
    EntityManager em;
    
    PersistenceManager() {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("emf");
        this.em = entityManagerFactory.createEntityManager();
    }
    
    public CarRentalCompany getCarRentalCompany(String companyName) {
        return em.find(CarRentalCompany.class, companyName);
    }

    public List<CarRentalCompany> getRentalCompanies() {
        return (List<CarRentalCompany>) em.createQuery(
                "SELECT c FROM CarRentalCompany c");
    }
    
    public List<CarType> getCarTypes() {
        return (List<CarType>) em.createQuery(
                "SELECT c FROM CarType c");
    }
    
    public List<CarType> getCarTypesOrderedByPrice(){
        return (List<CarType>) em.createQuery(
                "SELECT c FROM CarType c ORDER BY c.rentalPricePerDay");
    }
    
    public List<String> getRentalCompanyNames() {
        List<String> names = new ArrayList<String>();
        for(CarRentalCompany company : this.getRentalCompanies()) {
            names.add(company.getName());
        }
        return names;
    }
    
}
