package resources;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;


public class ConvertRawFiles {
	
	public static XmlPath rawtoXML(Response response) {
		
		String responseString = response.asString();
		
		XmlPath x = new XmlPath(responseString);
		return x;
//		return response_string;
	}
	
	public static JsonPath rawtoJSON(Response response) {
		String responseString = response.asString();
//		System.out.println(responseString);
		
		//Grab the place_id from the response
		JsonPath json = new JsonPath(responseString);
		return json;
//		String placeid = json.getString("place_id");
//		return placeid;
	}

}