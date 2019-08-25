package ch.hftm.business;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ch.hftm.model.Company;
import ch.hftm.persistence.CompanyPersistence;

public class CompanyCatalog {

	private static CompanyCatalog instance;
	private CompanyPersistence persistence = new CompanyPersistence();
	private List<Company> companies = new ArrayList<Company>();

	public static CompanyCatalog getInstance() {
		if (instance == null) {
			instance = new CompanyCatalog();
		}
		return instance;
	}

	private CompanyCatalog() {
		persistence.insert(new Company(1,"Lamborghini", 1963, "Italy"));

		companies = persistence.getAll();
		System.out.println(companies.size());
//		companies.add(new Company(01, "Lamborghini", 1963, "Italy"));
//		companies.add(new Company(02, "BMW", 1916, "Germany"));
//		companies.add(new Company(03, "Ferrari", 1947, "Italy"));
//		companies.add(new Company(04, "Renault", 1899, "France"));
//		companies.add(new Company(05, "Alfa Romeo", 1910, "Italy"));
//		companies.add(new Company(06, "Maserati", 1914, "Italy"));
	}

	public List<Company> getAllCompanies() {
		return companies;
	}

	public Company findCompany(long companyId) {
		for (Company company : companies) {
			if (company.getId() == companyId) {
				return company;
			}
		}
		return null;
	}

	public synchronized List<Company> searchCompany(String name, String origin) throws Exception {
		if (name.isEmpty() && origin.isEmpty()) {
			throw new Exception();
		}
		name = name.toLowerCase();
		origin = origin.toLowerCase();

		List<Company> results = new ArrayList<Company>();
		for (Company company : companies) {
			if (company.getName().toLowerCase().indexOf(name) >= 0 &&
					company.getOrigin().toLowerCase().indexOf(origin) >= 0
					) {
				results.add(clone(company));
			}
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	private <T> T clone(T object) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			new ObjectOutputStream(os).writeObject(object);
			ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
			return (T) new ObjectInputStream(is).readObject();
		} catch (Exception e) {
			return object;
		}
	}
}
