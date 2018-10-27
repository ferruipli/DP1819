
package utilities;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonJava {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final BufferedReader bReader;
		final JsonParser jsonparser;
		final FileReader fileReader;
		final JsonElement jsonElement;

		try {
			final String file = "./src/main/java/utilities/json.txt";
			fileReader = new FileReader(file);
			bReader = new BufferedReader(fileReader);
			jsonparser = new JsonParser();
			jsonElement = jsonparser.parse(fileReader);

			final Gson gson = new GsonBuilder().create();
			final domain.UserExample userExample = gson.fromJson(jsonElement, domain.UserExample.class);
			System.out.println(userExample);
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}
}
