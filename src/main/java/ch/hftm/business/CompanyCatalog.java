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
		persistence.insert(new Company("Lamborghini", 1963, "Italy"));
		persistence.insert(new Company("BMW", 1916, "Germany"));
		persistence.insert(new Company("Ferrari", 1947, "Italy"));
		persistence.insert(new Company("Renault", 1899, "France"));
		persistence.insert(new Company("Alfa Romeo", 1910, "Italy"));
		persistence.insert(new Company("Maserati", 1914, "Italy"));

		companies = persistence.getAll();
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

	public synchronized List<Company> addCompany(Company company){
		companies.add(company);
		persistence.insert(company);
		companies = persistence.getAll();
		return companies;
	}

	public synchronized List<Company> searchCompany(String name, String origin) throws Exception {
		if (name.isEmpty() && origin.isEmpty()) {
			throw new Exception();
		}
		name = name.toLowerCase();
		origin = origin.toLowerCase();

		List<Company> results = new ArrayList<Company>();
		for (Company company : companies) {
			if (company.getName().toLowerCase().contains(name) &&
					company.getOrigin().toLowerCase().contains(origin)
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
