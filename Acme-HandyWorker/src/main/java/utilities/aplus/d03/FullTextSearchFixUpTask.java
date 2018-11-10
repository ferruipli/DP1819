
package utilities.aplus.d03;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.hibernate.search.jpa.FullTextEntityManager;

import utilities.internal.ConsoleReader;
import utilities.internal.DatabaseUtil;
import utilities.internal.EclipseConsole;
import utilities.internal.SchemaPrinter;
import utilities.internal.ThrowablePrinter;

public class FullTextSearchFixUpTask {

	public static void main(final String[] args) {
		final ConsoleReader reader;
		String keyword, title;
		List<?> resultQuery;
		boolean quit;
		DatabaseUtil databaseUtil;
		FullTextEntityManager fullTextEntityManager;

		EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);
		title = "FullTextSearchFixUpTask 1.0";
		databaseUtil = null;
		quit = false;

		try {
			reader = new ConsoleReader();
			databaseUtil = new DatabaseUtil();

			databaseUtil.initialise();
			fullTextEntityManager = databaseUtil.getFullTextEntityManager();

			/*
			 * If you have already executed this application before and you have
			 * the Lucene indexes created in Acme-HandyWorker/var/lucene/indexes,
			 * you can comment this line.
			 */
			LuceneUtil.initialiseIndex(databaseUtil);

			System.out.println(title);
			System.out.println(String.format("%1$" + title.length() + "s", "").replace(" ", "-"));
			System.out.println();

			while (!quit) {
				System.out.println("Enter the keyword:");

				databaseUtil.startTransaction();

				keyword = reader.readLine();
				quit = keyword.isEmpty();

				if (!quit) {
					resultQuery = LuceneUtil.findFixUpTaskByKeyword(fullTextEntityManager, keyword);
					FullTextSearchFixUpTask.printResults(resultQuery);
					System.out.println("--------------------------------------------------------------------------------");
				}

				databaseUtil.commitTransaction();
			}
		} catch (final Throwable oops) {
			ThrowablePrinter.print(oops);
		} finally {
			System.out.println("\nClosing persistence context.");
			databaseUtil.shutdown();
		}
	}

	// Ancillary methods ------------------------------------------------------

	private static void printResults(final List<?> resultQuery) {
		for (final Object r : resultQuery)
			SchemaPrinter.print(r);
	}

}
