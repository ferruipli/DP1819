
package utilities.aplus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public class LuceneIndex {

	// Constructor ------------------------------------------------------------

	public LuceneIndex() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("Acme-HandyWorker");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}


	// Attributes -------------------------------------------------------------

	private final EntityManager			entityManager;
	private final EntityManagerFactory	entityManagerFactory;


	// Utility methods --------------------------------------------------------

	public void initializeIndex() throws Throwable {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.entityManager);
		fullTextEntityManager.createIndexer().startAndWait();
	}

}
