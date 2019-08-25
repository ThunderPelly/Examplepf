package ch.hftm.persistence;

import ch.hftm.model.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class CompanyPersistence {

	private static CompanyPersistence singleton;
	private EntityManager entityManager;

	public static CompanyPersistence getInstance() {
		if (singleton == null) {
			singleton = new CompanyPersistence();
		}
		return singleton;
	}

	public CompanyPersistence() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("company");
		entityManager = emf.createEntityManager();
	}

	public void insert(Company company) {
		entityManager.getTransaction().begin();
		this.entityManager.persist(company);
		entityManager.getTransaction().commit();
	}

	public Company findById(long id) {
		return this.entityManager.find(Company.class, id);
	}

	public void delete(long id) {
		entityManager.getTransaction().begin();
		Company company = findById(id);
		if (company != null) {
			this.entityManager.remove(company);
		}
		entityManager.getTransaction().commit();
	}

	public List<Company> getAll() {
		TypedQuery<Company> query = this.entityManager
				.createQuery("select s from Company s", Company.class);
		return query.getResultList();
	}

}
