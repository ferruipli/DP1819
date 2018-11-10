
package utilities.aplus.d03;

import java.util.List;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import utilities.internal.DatabaseUtil;
import domain.FixUpTask;

public class LuceneUtil {

	public static void initialiseIndex(final DatabaseUtil databaseUtil) throws Throwable {
		FullTextEntityManager fullTextEntityManager;

		fullTextEntityManager = databaseUtil.getFullTextEntityManager();
		fullTextEntityManager.createIndexer().startAndWait();
	}

	public static List<?> findFixUpTaskByKeyword(final FullTextEntityManager fullTextEntityManager, final String keyword) {
		QueryBuilder qb;
		org.apache.lucene.search.Query query;
		javax.persistence.Query persistenceQuery;

		qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(FixUpTask.class).get();
		query = qb.keyword().onFields("ticker", "description", "address").matching(keyword).createQuery();
		persistenceQuery = fullTextEntityManager.createFullTextQuery(query, FixUpTask.class);

		return persistenceQuery.getResultList();
	}

}
