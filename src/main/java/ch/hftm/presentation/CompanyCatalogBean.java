package ch.hftm.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
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
	private List<Company> companies = new ArrayList<Company>();
	private String message;
	private static final int MIN_SEARCH_LENGTH = 2;

	public CompanyCatalogBean() {
		this.companies = companyCatalog.getAllCompanies();
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

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
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

	public String searchCompanies() {
		companies.clear();
		message = null;
		try {
			companies = companyCatalog.searchCompany(name, origin);
			if (companies.isEmpty()) {
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
		companies.clear();
		message = null;
		if (name.length() > MIN_SEARCH_LENGTH || origin.length() > MIN_SEARCH_LENGTH) {
			searchCompanies();
		}
	}

}
