
package utilities.aplus;

import utilities.internal.ConsoleReader;
import utilities.internal.ThrowablePrinter;

public class FixUpTaskKeywordQueries {

	public static void main(final String[] args) {
		final ConsoleReader reader;
		String keyword;

		try {
			// Creating an initial Lucene index for the data already present in your database

			//LuceneIndex.initializeIndex();

			// Reading the keyword from the keyboard

			System.out.println("Enter the keyword:");
			reader = new ConsoleReader();
			keyword = reader.readLine();

			// TODO: Running the query with the keyword given

			// TODO: Printing results of the query

			System.out.println(keyword);

		} catch (final Throwable oops) {
			ThrowablePrinter.print(oops);
		}
	}

}
