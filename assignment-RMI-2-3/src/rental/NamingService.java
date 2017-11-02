package rental;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;


public class NamingService {
	
	private Set<CarRentalCompany> carRentalCompanies;

	NamingService(){
		this.carRentalCompanies = new HashSet<CarRentalCompany>();
	}
	
	public synchronized CarRentalCompany getCarRentalCompanyByName(String name) throws Exception{
		for(CarRentalCompany company : this.carRentalCompanies) {
			if (company.getName().equals(name)) {
				return company;
			}
		} throw new Exception("Could not find the company with the given name " + name);
	}
	
	//registers the car rental company with given name by loading it from a file
	// and adding to the list the CarRentalCompany object created with the loaded data.
	public synchronized void registerCarRentalCompany(String name) throws Exception{
		try {
			CrcData data = this.loadData(name+".csv");
			CarRentalCompany newCarRentalCompany = new CarRentalCompany(data.name, data.regions, data.cars);
			this.carRentalCompanies.add(newCarRentalCompany);
		} catch (NumberFormatException | ReservationException | IOException e) {
			throw new Exception("Could not register the car rental company");
		}
	}
	
	public synchronized void unregisterCarRentalCompany(String name) throws Exception {
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
	

	public synchronized Set<CarRentalCompany> getCarRentalCompanies() {
		return this.carRentalCompanies;
	}

	public synchronized Set<String> getCarRentalCompanyNames() {
		Set<String> companyNames = new HashSet<String>();
		Set<CarRentalCompany> companies = this.getCarRentalCompanies();
		for(CarRentalCompany company : companies) {
			companyNames.add(company.getName());
		}
		return companyNames;
		
	}

}
