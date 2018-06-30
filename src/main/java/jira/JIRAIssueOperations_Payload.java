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
					  "        \"description\": \"Automated issue in JIRA\",\r\n" + 
					  "        \"issuetype\": {\r\n" + 
				      "            \"name\": \"Bug\"\r\n" + 
					  "        }\r\n" + 
					  "    }\r\n" + 
					  "}";
		return body;
	}
	
	public String commentBody() {
		String body = "{\r\n" + 
					  "    \"body\": \"Created new comment\",\r\n" + 
					  "    \"visibility\": {\r\n" + 
					  "        \"type\": \"role\",\r\n" + 
					  "        \"value\": \"Administrators\"\r\n" + 
					  "    }\r\n" + 
					  "}";
		return body;
	}
	
	public String updatecommentBody() {
		String body = "{\r\n" + 
				      "    \"body\": \"Updated the comment\",\r\n" + 
					  "    \"visibility\": {\r\n" + 
				      "        \"type\": \"role\",\r\n" + 
					  "        \"value\": \"Administrators\"\r\n" + 
					  "    }\r\n" + 
				      "}";
		return body;
	}
}
