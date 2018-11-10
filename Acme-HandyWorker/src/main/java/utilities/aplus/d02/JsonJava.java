
package utilities.aplus.d02;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import security.UserAccount;
import utilities.internal.SchemaPrinter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Box;
import domain.Category;
import domain.Complaint;
import domain.CreditCard;
import domain.Curriculum;
import domain.Customer;
import domain.Customisation;
import domain.DomainEntity;
import domain.EducationRecord;
import domain.Endorsement;
import domain.EndorserRecord;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Message;
import domain.MiscellaneousRecord;
import domain.Note;
import domain.ProfessionalRecord;
import domain.Referee;
import domain.Report;
import domain.Section;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;
import domain.Warranty;

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
		DomainEntity object;
		final Set<Map.Entry<String, JsonElement>> datas;

		try {

			fileReader = new FileReader(file);
			jsonparser = new JsonParser();
			jsonElement = jsonparser.parse(fileReader);
			jsonObject = jsonElement.getAsJsonObject();
			datas = jsonObject.entrySet();

			for (final Map.Entry<String, JsonElement> map : datas) {
				final JsonArray jsonArray = map.getValue().getAsJsonArray();
				final java.util.Iterator<JsonElement> iterator;
				if (map.getValue().isJsonArray()) {
					iterator = jsonArray.iterator();
					while (iterator.hasNext()) {
						final JsonElement jsonElementIter = iterator.next();
						object = JsonJava.returnObject(jsonElementIter, map.getKey());
						SchemaPrinter.print(object);

					}
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	private static DomainEntity returnObject(final JsonElement jsonElement, final String nameJava) {
		final Gson gson;
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create(); //para formatear las fechas, ya que si no se pone da problemas
		DomainEntity res = null;
		switch (nameJava) {
		case "Actor":
			res = gson.fromJson(jsonElement, Actor.class);
			break;
		case "Administrator":
			res = gson.fromJson(jsonElement, Administrator.class);
			break;
		case "Application":
			res = gson.fromJson(jsonElement, Application.class);
			break;
		case "Box":
			res = gson.fromJson(jsonElement, Box.class);
			break;
		case "Category":
			res = gson.fromJson(jsonElement, Category.class);
			break;
		case "Complaint":
			res = gson.fromJson(jsonElement, Complaint.class);
			break;
		case "CreditCard":
			res = gson.fromJson(jsonElement, CreditCard.class);
			break;
		case "Curriculum":
			res = gson.fromJson(jsonElement, Curriculum.class);
			break;
		case "Customer":
			res = gson.fromJson(jsonElement, Customer.class);
			break;
		case "Customisation":
			res = gson.fromJson(jsonElement, Customisation.class);
			break;
		case "DomainEntity":
			res = gson.fromJson(jsonElement, DomainEntity.class);
			break;
		case "EducationRecord":
			res = gson.fromJson(jsonElement, EducationRecord.class);
			break;
		case "Endorsement":
			res = gson.fromJson(jsonElement, Endorsement.class);
			break;
		case "EndorserRecord":
			res = gson.fromJson(jsonElement, EndorserRecord.class);
			break;
		case "Finder":
			res = gson.fromJson(jsonElement, Finder.class);
			break;
		case "FixUpTask":
			res = gson.fromJson(jsonElement, FixUpTask.class);
			break;
		case "HandyWorker":
			res = gson.fromJson(jsonElement, HandyWorker.class);
			break;
		case "Message":
			res = gson.fromJson(jsonElement, Message.class);
			break;
		case "MiscellaneousRecord":
			res = gson.fromJson(jsonElement, MiscellaneousRecord.class);
			break;
		case "Note":
			res = gson.fromJson(jsonElement, Note.class);
			break;
		case "PersonalRecord":
			res = gson.fromJson(jsonElement, Sponsor.class);
			break;
		case "Phase":
			res = gson.fromJson(jsonElement, Sponsorship.class);
			break;
		case "ProfessionalRecord":
			res = gson.fromJson(jsonElement, ProfessionalRecord.class);
			break;
		case "Referee":
			res = gson.fromJson(jsonElement, Referee.class);
			break;
		case "Report":
			res = gson.fromJson(jsonElement, Report.class);
			break;
		case "Section":
			res = gson.fromJson(jsonElement, Section.class);
			break;
		case "SocialProfile":
			res = gson.fromJson(jsonElement, SocialProfile.class);
			break;
		case "Sponsor":
			res = gson.fromJson(jsonElement, Sponsor.class);
			break;
		case "Sponsorship":
			res = gson.fromJson(jsonElement, Sponsorship.class);
			break;
		case "Tutorial":
			res = gson.fromJson(jsonElement, Tutorial.class);
			break;
		case "Warranty":
			res = gson.fromJson(jsonElement, Warranty.class);
			break;
		case "UserAccount":
			res = gson.fromJson(jsonElement, UserAccount.class);
			break;

		}

		return res;
	}

}
