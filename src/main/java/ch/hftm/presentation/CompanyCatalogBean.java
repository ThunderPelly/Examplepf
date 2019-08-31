package ch.hftm.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.inject.Named;

import ch.hftm.business.CompanyCatalog;
import ch.hftm.model.Company;

@Named
@SessionScoped
public class CompanyCatalogBean implements Serializable {

	private static final long serialVersionUID = -6447813703247980276L;

	private CompanyCatalog companyCatalog = CompanyCatalog.getInstance();
	private Company selectedCompany;

	private String name = "";
	private int founded;
	private String origin ="";
	private List<Company> filteredCompanies = new ArrayList<>();
	private String message;
	private static final int MIN_SEARCH_LENGTH = 2;

	private Company company = new Company();

	public CompanyCatalogBean() {

		this.filteredCompanies = companyCatalog.getAllCompanies();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFounded() {
		return founded;
	}

	public void setFounded(int founded) {
		this.founded = founded;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public List<Company> getFilteredCompanies() {
		return filteredCompanies;
	}

	public void setFilteredCompanies(List<Company> filteredCompanies) {
		this.filteredCompanies = filteredCompanies;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Company> getAllCompanies() {
		return companyCatalog.getAllCompanies();
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void addCompanyBySubmit(AjaxBehaviorEvent ev){
		filteredCompanies = companyCatalog.addCompany(company);
		message = "added company";
		company = new Company();
	}

	public String searchCompanies() {
		message = null;
		try {
			filteredCompanies = companyCatalog.searchCompany(name, origin);
			if (filteredCompanies.isEmpty()) {
				message = "No matching company found";
			} else {
				return null;
			}
		} catch (Exception e) {
			message = "Search criteria are missing";
		}
		return null;
	}

	public void searchCompaniesByTiping(AjaxBehaviorEvent ev) {
		message = null;
		if (name.length() > MIN_SEARCH_LENGTH || origin.length() > MIN_SEARCH_LENGTH) {
			searchCompanies();
		}
		else {
			message = "waiting for more search criteria...";
			filteredCompanies = getAllCompanies();
		}
	}

}
