package rental;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


public class CarRentalAgency {
	
	private Set<CarRentalCompany> carRentalCompanies;

	CarRentalAgency(){
		this.carRentalCompanies = new HashSet<CarRentalCompany>();
	}
	
	public CarRentalCompany getCarRentalCompanyByName(String name) {
		for(CarRentalCompany company : this.carRentalCompanies) {
			if (company.getName() == name) {
				return company;
			}
		} return null;
	}
	
	//registers the car rental company with given name by loading it from a file
	// and adding to the list the CarRentalCompany object created with the loaded data.
	public void registerCarRentalCompany(String name) throws Exception{
		try {
			CrcData data = this.loadData(name+".csv");
			CarRentalCompany newCarRentalCompany = new CarRentalCompany(name, data.regions, data.cars);
			this.carRentalCompanies.add(newCarRentalCompany);
		} catch (NumberFormatException | ReservationException | IOException e) {
			throw new Exception("Could not register the car rental company");
		}
	}
	
	public void unregisterCarRentalCompany(String name) throws Exception {
		CarRentalCompany company = this.getCarRentalCompanyByName(name);
		if (company == null) {
			throw new Exception("Could not unregister the car rental company as it is not present.");
		} else {
			this.carRentalCompanies.remove(company);
		}
		
	}
	
	//loads data of a rental company from the data file bearing its name.
	private CrcData loadData(String datafile)
			throws ReservationException, NumberFormatException, IOException {

		CrcData out = new CrcData();
		int nextuid = 0;

		// open file
		BufferedReader in = new BufferedReader(new FileReader(datafile));
		StringTokenizer csvReader;
		
		try {
			// while next line exists
			while (in.ready()) {
				String line = in.readLine();
				
				if (line.startsWith("#")) {
					// comment -> skip					
				} else if (line.startsWith("-")) {
					csvReader = new StringTokenizer(line.substring(1), ",");
					out.name = csvReader.nextToken();
					out.regions = Arrays.asList(csvReader.nextToken().split(":"));
				} else {
					// tokenize on ,
					csvReader = new StringTokenizer(line, ",");
					// create new car type from first 5 fields
					CarType type = new CarType(csvReader.nextToken(),
							Integer.parseInt(csvReader.nextToken()),
							Float.parseFloat(csvReader.nextToken()),
							Double.parseDouble(csvReader.nextToken()),
							Boolean.parseBoolean(csvReader.nextToken()));
					System.out.println(type);
					// create N new cars with given type, where N is the 5th field
					for (int i = Integer.parseInt(csvReader.nextToken()); i > 0; i--) {
						out.cars.add(new Car(nextuid++, type));
					}
				}
			}
		} finally {
			in.close();
		}

		return out;
	}
	
	static class CrcData {
		public List<Car> cars = new LinkedList<Car>();
		public String name;
		public List<String> regions =  new LinkedList<String>();
	}
	
	private void addCarRentalCompany(CarRentalCompany newCarRentalCompany) {
		this.carRentalCompanies.add(newCarRentalCompany);
	}
	

	public Set<CarType> getAvailableCarTypes(Date start, Date end) {
		Set<CarType> availableCarTypes = new HashSet<CarType>();
		for (CarRentalCompany company : this.carRentalCompanies) {
			availableCarTypes.addAll(company.getAvailableCarTypes(start, end));
		}
		return availableCarTypes;
	}

	/*
	@Override
	public Quote createQuote(ReservationConstraints constraints, String client)
			throws RemoteException, ReservationException {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	public boolean canCreateQuote(ReservationConstraints constraint, String client) {
		// TODO implement this
		return true;
	}
	
	/*
	@Override
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException {
		// TODO implement this
		return null;
	}*/
	
	/*
	@Override
	public void cancelReservation(Reservation res) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	*/
	
	public List<Reservation> getRenterReservations(String clientname) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getNumberOfReservationsForCarType(String carRentalName, String carType) {
		CarRentalCompany crc = this.getCarRentalCompanyByName(carRentalName);
		crc.getNumberOfReservationsForCarType(carType);
		return 0;
	}

	public CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Set<String> getBestClients(){
		// TODO Auto-generated method stub
		return null;
	}

	public String getCheapestCarType(Date start, Date end, String region) {
		double lowestPrice = 999999999;
		String cheapestCar = null;
		Map<String, Double> carTypeWithPrice = this.getAvailableCarTypesWithPrice(start, end, region);
		for(String carType : carTypeWithPrice.keySet()) {
			double price = carTypeWithPrice.get(carType);
			if (cheapestCar == null || price < lowestPrice) {
				cheapestCar = carType;
				lowestPrice = price;
			}
		}
		return cheapestCar;
	}

	private Map<String, Double> getAvailableCarTypesWithPrice(Date start, Date end, String region) {
		Set<CarType> availableCarTypes = this.getAvailableCarTypes(start, end, region);
		Map<String, Double> availableCarTypesWithPrice = new HashMap<String, Double>();
		for (CarType carType : availableCarTypes) {
			availableCarTypesWithPrice.put(carType.getName(), carType.getRentalPricePerDay());
		}
		return availableCarTypesWithPrice;
	}

	private Set<CarType> getAvailableCarTypes(Date start, Date end, String region) {
		Set<CarType> availableCarTypes = new HashSet<CarType>();
		for (CarRentalCompany company : this.carRentalCompanies) {
			if (company.hasRegion(region)) {
				availableCarTypes.addAll(company.getAvailableCarTypes(start, end));
			}
		}
		return availableCarTypes;
	}

	public Set<CarRentalCompany> getCarRentalCompanies() {
		return this.carRentalCompanies;
	}

	public Set<String> getCarRentalCompanyNames() {
		Set<String> companyNames = new HashSet<String>();
		Set<CarRentalCompany> companies = this.getCarRentalCompanies();
		for(CarRentalCompany company : companies) {
			companyNames.add(company.getName());
		}
		return companyNames;
		
	}

}
