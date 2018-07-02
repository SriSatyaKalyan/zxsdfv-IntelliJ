package twitter_api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ConvertRawFiles;

public class TwitterUserOperations {
	
	Properties prop = new Properties();
	
	String consumer_key    = prop.getProperty("consumer_key");
	String consumer_secret = prop.getProperty("consumer_secret");
	String access_token    = prop.getProperty("access_token");
	String access_token_secret = prop.getProperty("access_token_secret");
	public String tweet_id;
	
	public static Logger log = LogManager.getLogger(TwitterUserOperations.class.getName());
	
	@Test
	public void findLatestTweet() {
		log.info("TwitterUserOperations.findLatestTweet");
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		Response response = 
		given().auth().oauth(consumer_key, consumer_secret, access_token, access_token_secret).
		queryParam("count", "1").when().
		get("/user_timeline.json").then().extract().response();
		
		JsonPath json = (ConvertRawFiles.rawtoJSON(response));
		String text = json.getString("text");
		System.out.println("The text is " + text);
	}
	
	@Test
	public void createTweet() {
		log.info("TwitterUserOperations.createTweet");
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		Response response = 
		given().auth().oauth(consumer_key, consumer_secret, access_token, access_token_secret).
		//Keep on changing the tweet text as API does not allow the same tweet to be posted on and on forever
		queryParam("status", "This is an automated tweeter programmed using Java").when().
		post("/update.json").
		then().statusCode(200).
		extract().response();
		
		JsonPath json = (ConvertRawFiles.rawtoJSON(response));
		String created_at = json.getString("created_at");
		tweet_id   = json.getString("id");
		log.info("The tweet is created at " + created_at);
	}
	
	@Test
	public void removeCreatedTweet() {
		log.info("TwitterUserOperations.removeCreatedTweet");
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		Response response = 
		given().auth().oauth(consumer_key, consumer_secret, access_token, access_token_secret).
		when().post("/destroy/" + tweet_id +".json").
		then().statusCode(200).
		extract().response();
		
		JsonPath json = (ConvertRawFiles.rawtoJSON(response));
		String truncated = json.getString("truncated");
//		log.info(truncated);
		if (truncated.equalsIgnoreCase("false")) {
			Assert.assertTrue(true);
		}else {
			Assert.assertFalse(true);
		}
	}
}
