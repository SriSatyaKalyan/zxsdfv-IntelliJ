package jira;

public class JIRABasics {
	
	public String propertiesFileLocation() {
		String location = "C:\\Users\\satya\\Desktop\\Eclipse Workspace\\Framework57\\environment.properties";
		return location;
	}
	
	public String jsonApplicationType() {
		String jsonapplication = "application/json";
		return jsonapplication;
	}
	
	public String xmlApplicationType() {
		String xmlapplication = "application/xml";
		return xmlapplication;
	}
	
	public String authRequest() {
		String authrequest = "/rest/auth/1/session";
		return authrequest;
	}
	
	public String issueRequest() {
		String issuerequest = "/rest/api/2/issue";
		return issuerequest;
	}
}
