package twitter_api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.javascript.json.JsonParser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.internal.path.json.JSONAssertion;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import resources.ConvertRawFiles;
import twitter.TwitterBasics;
import twitter.TwitterUserOperations_Payload;

public class TwitterUserOperations {
	
	public Properties prop;
	public String consumer_key;
	public String consumer_secret;
	public String access_token;
	public String access_token_secret;
	public String tweet_id;
	
	TwitterBasics basics = new TwitterBasics();
	TwitterUserOperations_Payload userop = new TwitterUserOperations_Payload();
	public static Logger log = LogManager.getLogger(TwitterUserOperations.class.getName());
	
	@BeforeTest
	public void getData() throws IOException {
		log.info("TwitterUserOperations.getData");
		
		prop = new Properties();
		FileInputStream fis = new FileInputStream(basics.propertiesFileLocation());
		prop.load(fis);
		
		consumer_key = prop.getProperty("consumer_key");
		consumer_secret = prop.getProperty("consumer_secret");
		access_token    = prop.getProperty("access_token");
		access_token_secret = prop.getProperty("access_token_secret");
	}
	
	//This test is to check if the response is returning a cursored collection 
	//of user objects for users following the specified user.
	@Test
	public void findFollowersList() {
		log.info("TwitterUserOperations.findFollowersList");
		
		RestAssured.baseURI =  basics.twitterFollowersURI();
		Response response = 
		given().auth().oauth(consumer_key, consumer_secret, access_token, access_token_secret).
		when().
		get("/list.json").then().extract().response();
		
		JsonPath json = (ConvertRawFiles.rawtoJSON(response));
//		String json_data = json.prettify();
//		System.out.println("The text is " + json_data);
		String numberoffollowers = json.getString("users.size");
//		log.info("The number of followers are " + numberoffollowers);
		
		if (Integer.parseInt(numberoffollowers) == userop.numberOfFollowers()) {
			Assert.assertTrue(true);
		}
	}
	
	//This test is to check if the response is returning a cursored collection 
	//of user objects for every user the specified user is following
	@Test
	public void findFriendsList() {
		log.info("TwitterUserOperations.findFriendsList");
		
		RestAssured.baseURI =  basics.twitterFriendsURI();
		Response response = 
		given().auth().oauth(consumer_key, consumer_secret, access_token, access_token_secret).
		when().
		get("/list.json").then().extract().response();
		
		JsonPath json = (ConvertRawFiles.rawtoJSON(response));
//		String json_data = json.prettify();
//		System.out.println("The text is " + json_data);
		String numberoffriends = json.getString("users.size");
//		log.info("The number of friends are " + numberoffriends);
		
		if (Integer.parseInt(numberoffriends) == userop.numberOfFriends()) {
			Assert.assertTrue(true);
		}
	}
	
	
}