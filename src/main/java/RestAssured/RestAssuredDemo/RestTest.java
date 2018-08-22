package RestAssured.RestAssuredDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestTest {
	// HashMap to store details from API
	private static Map<String, State> stateMap = new HashMap<String, State>();

	public static void main(String args[]){
		// Get state data
		boolean fetched = getStateData();

		// State data not fetched, exiting the program.
		if (!fetched) {
			System.out.println("Exiting the program. Try Again later!");
			return;
		}

		// State data populated, lets take user inputs now.
		try{
		while (true) {
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter State Name / Abbreviation : (Type quit to Exit)");
			String input = bufferReader.readLine().trim();
			if (!input.equalsIgnoreCase("quit")) {
				// Search the Map for statename as key
				if (stateMap.containsKey(input)) {
					State enteredState = stateMap.get(input);
					System.out.println("The Largest city of " + input + " is " + enteredState.getLargestCity());
					System.out.println("The Capital of " + input + " is " + enteredState.getCapital());

				} else {
					System.out.println("Please enter a valid state name or abbreviation!!");
				}
			} else {
				System.out.println("Exiting the program. See you later!");
				return;
			}
		}
		}catch(IOException ioe){
			System.out.println("Error while getting user input."+ ioe.getMessage());
		}
		
	}

	public static boolean getStateData() {
		System.out.println("Fetching state data.");
		try{
			RestAssured.baseURI = Constants.API_BASE_URI;
			RequestSpecification httpRequest = RestAssured.given();
			Response resp = httpRequest.contentType(ContentType.JSON).get(Constants.API_PATH);

			if (resp != null && resp.getStatusCode() == 200) {
				String response = resp.getBody().asString();
				JsonPath js = new JsonPath(response);
				return parseAndPopulate(js);
			}
			else{
				System.out.println("Unable to fetch state data.!!");
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Make sure URI is correct");
			return false;
		}

	}

	private static boolean parseAndPopulate(JsonPath js) {
		int size = js.get("RestResponse.result.size()");

		System.out.println("size : " + size);

		for (int i = 0; i < size; i++) {

			String stateKey = "RestResponse.result[" + i + "]";

			String nameKey = stateKey + "." + Constants.STATE_NAME;
			String capKey = stateKey + "." + Constants.STATE_CAPITAL;
			String abbrKey = stateKey + "." + Constants.STATE_ABBR;
			String cityKey = stateKey + "." + Constants.STATE_LARGESTCITY;

			String name = js.get(nameKey);
			String capital = js.get(capKey);
			String abbr = js.get(abbrKey);
			String largestCity = "";
			try {
				largestCity = js.get(cityKey);
			} catch (Exception e) {
				largestCity = "Not Specified";
			}
			State state = new State(abbr, name, largestCity, capital);

			// put entry corresponding to the complete name.
			stateMap.put(name, state);
			// put entry corresponding to the abbrev.
			stateMap.put(abbr, state);

		}
		return true;
	}

}
