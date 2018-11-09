
package utilities.aplus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public class LuceneIndex {

	@PersistenceContext
	private EntityManager	entityManager;


	// Constructor ------------------------------------------------------------

	public LuceneIndex() {
		super();
	}

	// Utility methods --------------------------------------------------------

	public void initializeIndex() throws Throwable {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.entityManager);
		fullTextEntityManager.createIndexer().startAndWait();
	}

}
