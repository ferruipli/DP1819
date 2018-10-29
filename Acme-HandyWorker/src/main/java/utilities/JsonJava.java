
package utilities;

import java.io.BufferedReader;
import java.io.FileReader;

import utilities.internal.SchemaPrinter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import domain.DomainEntity;

public class JsonJava {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final BufferedReader bReader;
		final JsonParser jsonparser;
		final FileReader fileReader;
		final JsonElement jsonElement;
		final DomainEntity object;

		try {
			final String file = "./src/main/java/utilities/json.txt";
			fileReader = new FileReader(file);
			bReader = new BufferedReader(fileReader);
			jsonparser = new JsonParser();
			jsonElement = jsonparser.parse(fileReader);
			final JsonObject jsonObject = jsonElement.getAsJsonObject();
			final Gson gson = new GsonBuilder().create();

			final java.util.Set<java.util.Map.Entry<String, JsonElement>> inputs = jsonObject.entrySet();
			for (final java.util.Map.Entry<String, JsonElement> map : inputs)
				if (map.getValue().isJsonArray()) {
					final JsonArray array = map.getValue().getAsJsonArray();
					final java.util.Iterator<JsonElement> iter = array.iterator();
					while (iter.hasNext()) {
						final JsonElement input = iter.next();
						final domain.UserExample userExample = gson.fromJson(input, domain.UserExample.class);
						System.out.println(userExample);
						SchemaPrinter.print(userExample);
					}
				}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
