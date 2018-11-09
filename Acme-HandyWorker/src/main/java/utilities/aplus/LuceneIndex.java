
package utilities.aplus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public class LuceneIndex {

	public static void initializeIndex() throws Throwable {
		EntityManager entityManager;
		EntityManagerFactory entityManagerFactory;

		entityManagerFactory = Persistence.createEntityManagerFactory("Acme-HandyWorker");
		entityManager = entityManagerFactory.createEntityManager();

		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer().startAndWait();
	}

}
