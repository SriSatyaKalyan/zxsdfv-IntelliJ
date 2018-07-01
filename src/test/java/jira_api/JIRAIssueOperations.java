package jira_api;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jira.JIRAIssueOperations_Payload;
import jira.JIRABasics;
import resources.ConvertRawFiles;

public class JIRAIssueOperations {
	//This 
	
	public static Logger log = LogManager.getLogger(JIRAIssueOperations.class.getName());
	
	public Properties prop;
	public String sessionvalue;
	public String issue_id;
	public String comment_id;
	public String updatecomment_id;
	
	JIRABasics basics = new JIRABasics();
	JIRAIssueOperations_Payload jira = new JIRAIssueOperations_Payload();
	
	@BeforeTest
	public void getData() throws IOException {
		log.info("JIRA1.getData");
		prop = new Properties();
		FileInputStream fis = new FileInputStream(basics.propertiesFileLocation());
		prop.load(fis);
	}
	
	@Test
	public void a_createSession() {
		log.info("JIRAIssueOperations.createSession");
		log.info("A session is being created");
		
		//Creating a session
		RestAssured.baseURI = prop.getProperty("APIHost");
		Response response1 = 
			given().header("Content-Type",basics.jsonApplicationType()).
			body(jira.loginDetailsBody()).
			when().
			post(basics.authRequest()).
			then().statusCode(200).
			extract().response();
		
		//Grab the response 
		JsonPath json1 = (ConvertRawFiles.rawtoJSON(response1));
		sessionvalue = json1.getString("session.value");
		log.info("The session key has been generated");
		//createSessionIssue(sessionvalue);
	}
	
	@Test
	public void b_createSessionIssue() {
		log.info("JIRAIssueOperations.createSessionIssue");
		log.info("An issue is being created in the session");
		
		//Creating an issue
		Response response2 = 
			given().header("Content-Type",basics.jsonApplicationType()).
			header("Cookie","JSESSIONID=" + sessionvalue).
			body(jira.issueBody()).
			when().
			post(basics.issueRequest()).
			then().statusCode(201).
			extract().response();
		
		JsonPath json2 = (ConvertRawFiles.rawtoJSON(response2));
		issue_id   = json2.get("id");
		log.info("The issue id is generated");
	}
			
	@Test
	public void c_addComment() {
		log.info("JIRAIssueOperations.addComment");
		log.info("A comment is being added in the issue");
		
		RestAssured.baseURI=prop.getProperty("APIHost");
		Response response3 = 
		given().header("Cookie", "JSESSIONID=" + sessionvalue).
		header("Content-Type",basics.jsonApplicationType()).
		body(jira.commentBody()).
		when().post(basics.issueRequest() + "/" + issue_id + "/comment").
		then().statusCode(201).
		extract().response();
		
		JsonPath json3 = (ConvertRawFiles.rawtoJSON(response3));
		comment_id = json3.getString("id");
		log.info("The comment id is is generated");
	}
	
	@Test
	public void d_updateComment() {
		log.info("JIRAIssueOperations.updateComment");
		log.info("The comment created is being updated");
		
		RestAssured.baseURI=prop.getProperty("APIHost");
		Response response4 = 
		given().header("Cookie", "JSESSIONID=" + sessionvalue).
		header("Content-Type",basics.jsonApplicationType()).
		body(jira.updatecommentBody()).
		when().put(basics.issueRequest() + "/" + issue_id + "/comment/" + comment_id).
		then().statusCode(200).
		extract().response();
		
		JsonPath json4 = (ConvertRawFiles.rawtoJSON(response4));
		updatecomment_id = json4.getString("id");
		log.info("The updated comment id is generated");
	}
}