
package utilities.aplus;

import java.util.List;

import utilities.internal.ConsoleReader;
import utilities.internal.SchemaPrinter;
import utilities.internal.ThrowablePrinter;

public class FixUpTaskKeywordQueries {

	public static void main(final String[] args) {
		final ConsoleReader reader;
		String keyword;
		final LuceneIndex luceneIndex;
		List<?> resultQuery;

		try {
			// Creating an initial Lucene index for the data already present in your database

			luceneIndex = new LuceneIndex();
			//			luceneIndex.initializeIndex();

			// Reading the keyword from the keyboard

			System.out.println("Enter the keyword:");
			reader = new ConsoleReader();
			keyword = reader.readLine();

			// TODO: Running the query with the keyword given

			resultQuery = luceneIndex.searchByKeyword(keyword);

			// TODO: Printing results of the query

			for (final Object r : resultQuery)
				SchemaPrinter.print(r);

		} catch (final Throwable oops) {
			ThrowablePrinter.print(oops);
		}
	}

}
