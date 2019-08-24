package ch.hftm.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


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

}
