
package utilities.aplus;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import utilities.internal.ConsoleReader;
import utilities.internal.DatabaseUtil;
import utilities.internal.EclipseConsole;
import utilities.internal.SchemaPrinter;
import utilities.internal.ThrowablePrinter;
import domain.FixUpTask;

public class FullTextSearchFixUpTask {

	public static void main(final String[] args) {
		final ConsoleReader reader;
		String keyword, title;
		final String guideText;
		List<?> resultQuery;
		boolean quit;
		DatabaseUtil databaseUtil;
		FullTextEntityManager fullTextEntityManager;

		EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);
		title = "FullTextSearchFixUpTask 1.0";
		databaseUtil = null;

		try {
			// Creating an initial Lucene index for the data already present in the database

			reader = new ConsoleReader();
			databaseUtil = new DatabaseUtil();
			quit = false;

			// TODO: Detectar de alguna forma que los índices están inicializados??
			databaseUtil.initialise();
			//			initialiseIndex();

			System.out.println(title);
			System.out.println(String.format("%1$" + title.length() + "s", "").replace(" ", "-"));
			System.out.println();

			while (!quit) {
				System.out.println("Enter the keyword:");

				// Start transaction

				fullTextEntityManager = databaseUtil.getFullTextEntityManager();

				databaseUtil.startTransaction();

				// Reading the keyword from the keyboard

				keyword = reader.readLine();
				quit = FullTextSearchFixUpTask.interpretWord(keyword);

				if (!quit) {
					// Running the query with the keyword given

					final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(FixUpTask.class).get();
					final org.apache.lucene.search.Query query = qb.keyword().onFields("ticker", "description", "address").matching(keyword).createQuery();

					final javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, FixUpTask.class);

					resultQuery = persistenceQuery.getResultList();

					// Printing results of the query

					FullTextSearchFixUpTask.printResults(resultQuery);
					System.out.println("--------------------------------------------------------------------------------");
				}
				// Commit transaction

				databaseUtil.commitTransaction();
			}
		} catch (final Throwable oops) {
			ThrowablePrinter.print(oops);
		} finally {
			System.out.println("\nClosing persistence context.");
			databaseUtil.shutdown();
		}
	}

	private static void printResults(final List<?> resultQuery) {
		for (final Object r : resultQuery)
			SchemaPrinter.print(r);
	}

	private static boolean interpretWord(final String word) {
		return word.isEmpty();
	}

	//	public static List<?> searchByKeyword(final String keyword) throws Throwable {
	//		DatabaseUtil databaseUtil;
	//		FullTextEntityManager fullTextEntityManager;
	//
	//		databaseUtil = new DatabaseUtil();
	//		databaseUtil.initialise();
	//
	//		fullTextEntityManager = databaseUtil.getFullTextEntityManager();
	//		databaseUtil.startTransaction();
	//
	//		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(FixUpTask.class).get();
	//		final org.apache.lucene.search.Query query = qb.keyword().onFields("ticker", "description", "address").matching(keyword).createQuery();
	//
	//		final javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, FixUpTask.class);
	//
	//		final List<?> result = persistenceQuery.getResultList();
	//
	//		databaseUtil.commitTransaction();
	//
	//		return result;
	//	}

	public static void initialiseIndex() throws Throwable {
		DatabaseUtil databaseUtil;
		FullTextEntityManager fullTextEntityManager;

		databaseUtil = new DatabaseUtil();
		databaseUtil.initialise();

		fullTextEntityManager = databaseUtil.getFullTextEntityManager();
		fullTextEntityManager.createIndexer().startAndWait();
	}

}
