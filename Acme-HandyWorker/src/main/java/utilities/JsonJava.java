
package utilities;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utilities.internal.SchemaPrinter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import domain.DomainEntity;

public class JsonJava {

	public static int				i			= 0;
	static final List<DomainEntity>	objectList	= new ArrayList<DomainEntity>();


	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final String file = "./src/main/java/utilities/json.txt";
		final JsonParser jsonparser;
		final FileReader fileReader;
		final JsonElement jsonElement;
		final JsonObject jsonObject;
		final DomainEntity object;
		final Gson gson;
		final Set<Map.Entry<String, JsonElement>> datas;

		try {

			fileReader = new FileReader(file);
			jsonparser = new JsonParser();
			jsonElement = jsonparser.parse(fileReader);
			jsonObject = jsonElement.getAsJsonObject();
			gson = new GsonBuilder().create();
			datas = jsonObject.entrySet();

			for (final Map.Entry<String, JsonElement> map : datas) {
				final JsonArray jsonArray = map.getValue().getAsJsonArray();
				final java.util.Iterator<JsonElement> iterator;
				if (map.getValue().isJsonArray()) {
					iterator = jsonArray.iterator();
					while (iterator.hasNext()) {
						final JsonElement inputJsonElement = iterator.next();
						final domain.Box box = gson.fromJson(inputJsonElement, domain.Box.class);
						SchemaPrinter.print(box);

					}
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
