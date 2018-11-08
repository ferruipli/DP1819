
package utilities.aplus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public class CreateLuceneIndex {

	@PersistenceContext
	private EntityManager	entityManager;

	FullTextEntityManager	fullTextEntityManager	= Search.getFullTextEntityManager(this.entityManager);


	public void createIndex() {
		try {
			this.fullTextEntityManager.createIndexer().startAndWait();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

}
