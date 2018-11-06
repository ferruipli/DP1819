
package utilities;

import utilities.internal.ConsoleReader;
import utilities.internal.ThrowablePrinter;

public class FixUpTaskKeywordQueries {

	public static void main(final String[] args) {
		final ConsoleReader reader;
		String text;

		try {
			//			System.out.println("Enter the keyword:");
			//			reader = new ConsoleReader();
			//			text = reader.readLine();
			text = "String de prueba";

			//			System.out.println(text);
		} catch (final Throwable oops) {
			ThrowablePrinter.print(oops);
		}
	}

}
