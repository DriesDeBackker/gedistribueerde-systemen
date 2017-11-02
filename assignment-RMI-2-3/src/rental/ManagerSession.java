package rental;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class ManagerSession implements IManagerSession {
	
	private String name;
	private String rentalName;
	private NamingService namingService;

	ManagerSession(String name, String rentalName, NamingService agency) {
		this.setName(name);
		this.setRentalName(rentalName);
		this.setNamingService(agency);
	}
	
	@Override
	public Set<String> getAllRegisteredCarCompanies() throws RemoteException {
		try {
			return this.getNamingService().getCarRentalCompanyNames();
		} catch (Exception e) {
			throw new RemoteException("Could not retrieve the names of the registered companies.");
		}
	}
	
	@Override
	public void registerCarRentalCompany(String name) throws RemoteException {
		try {
			this.getNamingService().registerCarRentalCompany(name);
		} catch (Exception e) {
			throw new RemoteException("Could not register company.");
		}
	}

	@Override
	public void unregisterCarRentalCompany(String name) throws RemoteException {
		try {
			this.getNamingService().unregisterCarRentalCompany(name);
		} catch (Exception e) {
			throw new RemoteException("Could not unregister company.");
		}	
	}
	
	@Override
	public int getNumberOfReservationsForCarType(String carRentalName, String carType) throws RemoteException {
		try {
			CarRentalCompany crc = this.namingService.getCarRentalCompanyByName(carRentalName);
			int reservations = crc.getNumberOfReservationsForCarType(carType);
			return reservations;
		} catch (Exception e) {
			throw new RemoteException("Could not get the number of reservations for specified cartype and company");
		}
	}
	
	@Override
	public Set<String> getBestClients() throws RemoteException{
		Set<CarRentalCompany> crcs =getNamingService().getCarRentalCompanies();
		HashMap<String, Integer> clientres = new HashMap<String,Integer>();
		for (CarRentalCompany crc : crcs) {
			Set<String> clients = crc.getClients();
			for (String client: clients) {
				int res = crc.getRenterReservations(client).size();
				if (clientres.containsKey(client)) {
					int val = clientres.get(client);
					clientres.replace(client, val+res);
				}
				else {
					clientres.put(client,res);
				}
			}
		}
	    Set<String> bestClients = new HashSet<String>();
        int maxValueInMap=(Collections.max(clientres.values()));  
        for (Entry<String, Integer> entry : clientres.entrySet()) {  
            if (entry.getValue()==maxValueInMap) {
               bestClients.add(entry.getKey());
            }
        }
	    return bestClients;

		
	}
	
	@Override
	public CarType getMostPopularCarTypeIn(String name, int year) throws RemoteException{
		CarRentalCompany crc;
		try {
			crc = this.namingService.getCarRentalCompanyByName(name);
		} catch (Exception e) {
			throw new RemoteException("No company by the name " + name);
		}
		int max = 0;
		CarType popular = null;
		for (CarType type : crc.getAllCarTypes()) {
			int n = crc.getNumberOfReservationsForCarTypeInYear(type.getName(),year);
			if (n > max) {
				popular = type;
			}
		}
		return popular;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getRentalName() {
		return rentalName;
	}

	private void setRentalName(String rentalName) {
		this.rentalName = rentalName;
	}

	public NamingService getNamingService() {
		return namingService;
	}

	private void setNamingService(NamingService namingService) {
		this.namingService = namingService;
	}

}
