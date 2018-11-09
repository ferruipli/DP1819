
package utilities.aplus;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import domain.FixUpTask;

public class LuceneIndex {

	// Attributes -------------------------------------------------------------

	private EntityManager				entityManager;
	private final EntityManagerFactory	entityManagerFactory;


	// Constructor ------------------------------------------------------------

	public LuceneIndex() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("Acme-HandyWorker");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	// Utility methods --------------------------------------------------------

	public void initializeIndex() throws Throwable {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.entityManager);
		fullTextEntityManager.createIndexer().startAndWait();
	}

	public List<?> searchByKeyword(final String keyword) {
		this.entityManager = this.entityManagerFactory.createEntityManager();
		final FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(this.entityManager);
		//this.entityManager.getTransaction().begin();

		// create native Lucene query using the query DSL
		// alternatively you can write the Lucene query using the Lucene query parser
		// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(FixUpTask.class).get();
		final org.apache.lucene.search.Query query = qb.keyword().onFields("ticker", "description", "address").matching(keyword).createQuery();

		// wrap Lucene query in a javax.persistence.Query
		final javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, FixUpTask.class);

		// execute search
		final List<?> result = persistenceQuery.getResultList();

		//		this.entityManager.getTransaction().commit();
		//		this.entityManager.close();

		return result;
	}

}
