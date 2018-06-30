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
import jira.JIRACommentOperations_Payload;
import jira.JIRAIssueOperations_Payload;
import jira.JIRABasics;
import junit.framework.Assert;
import resources.ConvertRawFiles;

public class JIRACommentOperations {
	//We first create an issue and add three comments to the issue 1,2, and 3
	//We then update the third comment and delete the second comment
	//We perform a 'GET Comments' request and 'GET Comment' request on the first comment
	
	public static Logger log = LogManager.getLogger(JIRACommentOperations.class.getName());
	
	public Properties prop;
	public String sessionvalue;
	public String issue_id;
	public String comment_id1;
	public String comment_id2;
	public String comment_id3;
	public String comment3;
	public String updatecomment_id;
	public String updatedcomment;
	public int comment_count;
	
	JIRABasics basics = new JIRABasics();
	JIRAIssueOperations_Payload jira = new JIRAIssueOperations_Payload();
	
	@BeforeTest
	public void getData() throws IOException {
		log.info("JIRACommentOperations.getData");
		prop = new Properties();
		FileInputStream fis = new FileInputStream(basics.propertiesFileLocation());
		prop.load(fis);
	}
	
	@Test
	public void a_createSession() {
		log.info("JIRACommentOperations.createSession");
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
		log.info("JIRACommentOperations.createSessionIssue");
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
	public void c_addComment1() {
		log.info("JIRACommentOperations.addComment1");
		
		RestAssured.baseURI=prop.getProperty("APIHost");
		Response response3 = 
			given().header("Cookie", "JSESSIONID=" + sessionvalue).
			header("Content-Type",basics.jsonApplicationType()).
			body(jira.comment1Body()).
			when().post(basics.issueRequest() + "/" + issue_id + "/comment").
			then().statusCode(201).
			extract().response();
		
		JsonPath json3 = (ConvertRawFiles.rawtoJSON(response3));
		comment_id1 = json3.getString("id");
//		log.info("The comment id is is generated");
		log.info("First comment is being added to the issue");
	}
	
	@Test
	public void c_addComment2() {
		log.info("JIRACommentOperations.addComment2");

		RestAssured.baseURI=prop.getProperty("APIHost");
		Response response3 = 
			given().header("Cookie", "JSESSIONID=" + sessionvalue).
			header("Content-Type",basics.jsonApplicationType()).
			body(jira.comment2Body()).
			when().post(basics.issueRequest() + "/" + issue_id + "/comment").
			then().statusCode(201).
			extract().response();
		
		JsonPath json3 = (ConvertRawFiles.rawtoJSON(response3));
		comment_id2 = json3.getString("id");
//		log.info("The comment id is is generated");
		log.info("Second comment is added to the issue");
	}
	
	@Test
	public void c_addComment3() {
		log.info("JIRACommentOperations.addComment3");
		log.info("Third comment is being added to the issue");
		
		RestAssured.baseURI=prop.getProperty("APIHost");
		Response response3 = 
			given().header("Cookie", "JSESSIONID=" + sessionvalue).
			header("Content-Type",basics.jsonApplicationType()).
			body(jira.comment3Body()).
			when().post(basics.issueRequest() + "/" + issue_id + "/comment").
			then().statusCode(201).
			extract().response();
		
		JsonPath json3 = (ConvertRawFiles.rawtoJSON(response3));
		comment_id3 = json3.getString("id");
		comment3    = json3.getString("body");
//		log.info("The comment id is is generated");
		log.info("Third comment is added to the issue");
	}
	
	@Test
	public void d_updateComment() {
		log.info("JIRACommentOperations.updateComment");
		
		RestAssured.baseURI=prop.getProperty("APIHost");
		Response response4 = 
		given().header("Cookie", "JSESSIONID=" + sessionvalue).
		header("Content-Type",basics.jsonApplicationType()).
		body(jira.updatecomment3Body()).
		when().put(basics.issueRequest() + "/" + issue_id + "/comment/" + comment_id3).
		then().statusCode(200).
		extract().response();
		
		JsonPath json4 = (ConvertRawFiles.rawtoJSON(response4));
		updatecomment_id = json4.getString("id");
		updatedcomment   = json4.getString("body");
		if (comment3.contains("Created") && updatedcomment.contains("Updated")) {
			Assert.assertTrue(true);
		}else {
			Assert.assertFalse(true);
		}
		
		log.info("The third comment is updated");
	}
	
	@Test
	public void e_getComments() {
		log.info("JIRACommentOperations.getComments");
		
		RestAssured.baseURI = prop.getProperty("APIHost");
		Response response = 
				given().header("Cookie", "JSESSIONID=" + sessionvalue).
				header("Content-Type",basics.jsonApplicationType()).
				when().get(basics.issueRequest() + "/" + issue_id + "/" + "comment").
				then().statusCode(200).
				extract().response();
		
		JsonPath json = (ConvertRawFiles.rawtoJSON(response));
		comment_count = Integer.parseInt(json.getString("total"));
		
		if (comment_count == 3) {
			Assert.assertTrue(true);
		}else {
			Assert.assertFalse(true);
		}
	}
	
	@Test
	public void f_deleteComment2() {
		log.info("JIRACommentOperations.deleteComment2");
		
		RestAssured.baseURI = prop.getProperty("APIHost");
		Response response = 
				given().header("Cookie", "JSESSIONID=" + sessionvalue).
				header("Content-Type", basics.jsonApplicationType()).
				when().delete(basics.issueRequest() + "/" + issue_id + "/comment/" + comment_id2).
				then().statusCode(204).
				extract().response();
		
		log.info("The second comment is deleted");
	}
	
	@Test
	public void g_getComment() {
		log.info("JIRACommentOperations.getComment");
		
		RestAssured.baseURI = prop.getProperty("APIHost");
		Response response = 
				given().header("Cookie", "JSESSIONID=" + sessionvalue).
				header("Content-Type",basics.jsonApplicationType()).
				when().get(basics.issueRequest() + "/" + issue_id +  "/" +"comment" + "/" + comment_id3).
				then().statusCode(200).
				extract().response();
		
		JsonPath json = (ConvertRawFiles.rawtoJSON(response));
		String comment = json.getString("body");
		
		if (comment.contains("third")) {
			Assert.assertTrue(true);
		}else {
			Assert.assertFalse(true);
		}
	}
	
	@Test
	public void h_getCommentsAgain() {
		log.info("JIRACommentOperations.getCommentsAgain");
		
		RestAssured.baseURI = prop.getProperty("APIHost");
		Response response = 
				given().header("Cookie", "JSESSIONID=" + sessionvalue).
				header("Content-Type",basics.jsonApplicationType()).
				when().get(basics.issueRequest() + "/" + issue_id + "/" + "comment").
				then().statusCode(200).
				extract().response();
		
		JsonPath json = (ConvertRawFiles.rawtoJSON(response));
		comment_count = Integer.parseInt(json.getString("total"));
		
		if (comment_count == 2) {
			Assert.assertTrue(true);
		}else {
			Assert.assertFalse(true);
		}
	}
}