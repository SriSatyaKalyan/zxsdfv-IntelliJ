package jira;

public class JIRAIssueOperations_Payload {
	
	public String loginDetailsBody() {
		
		String logindetails = "{\"username\": \"sparrow\", "
				            + "\"password\": \"L@kshmi123\"}";
		return logindetails;
	}
	
	public String issueBody() {
		String body = "{\r\n" + 
					  "    \"fields\": {\r\n" + 
					  "        \"project\": {\r\n" + 
					  "        	\"key\": \"AUT\"\r\n" + 
					  "        },\r\n" + 
					  "        \"summary\": \"Created issue through automated API request\",\r\n" + 
					  "        \"description\": \"Automated issue in JIRA to perform actions on comments\",\r\n" + 
					  "        \"issuetype\": {\r\n" + 
				      "            \"name\": \"Bug\"\r\n" + 
					  "        }\r\n" + 
					  "    }\r\n" + 
					  "}";
		return body;
	}
	
	public String comment1Body() {
		String body = "{\r\n" + 
					  "    \"body\": \"Created first comment\",\r\n" + 
					  "    \"visibility\": {\r\n" + 
					  "        \"type\": \"role\",\r\n" + 
					  "        \"value\": \"Administrators\"\r\n" + 
					  "    }\r\n" + 
					  "}";
		return body;
	}
	
	public String comment2Body() {
		String body = "{\r\n" + 
					  "    \"body\": \"Created second comment\",\r\n" + 
					  "    \"visibility\": {\r\n" + 
					  "        \"type\": \"role\",\r\n" + 
					  "        \"value\": \"Administrators\"\r\n" + 
					  "    }\r\n" + 
					  "}";
		return body;
	}
	
	public String comment3Body() {
		String body = "{\r\n" + 
					  "    \"body\": \"Created third comment\",\r\n" + 
					  "    \"visibility\": {\r\n" + 
					  "        \"type\": \"role\",\r\n" + 
					  "        \"value\": \"Administrators\"\r\n" + 
					  "    }\r\n" + 
					  "}";
		return body;
	}
	
	public String updatecomment3Body() {
		String body = "{\r\n" + 
				      "    \"body\": \"Updated the third comment\",\r\n" + 
					  "    \"visibility\": {\r\n" + 
				      "        \"type\": \"role\",\r\n" + 
					  "        \"value\": \"Administrators\"\r\n" + 
					  "    }\r\n" + 
				      "}";
		return body;
	}
}
