package twitter_api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ConvertRawFiles;
import twitter.TwitterBasics;

public class TwitterTweetOperations {
	
	Properties prop = new Properties();
	
	String consumer_key    = "DjuHXSQqLtx3U9DlocjJWeaaK";//prop.getProperty("consumer_key");
	String consumer_secret = "aM2ZQ9sLO4LDgimHAKLSsFdD2QE10rjci9dWX5Q5qa8tGJjQri";//prop.getProperty("consumer_secret");
	String access_token    = "889343834865385472-hGZ2xMEu5YSF1sICdf4VcxSZTzpw5mC";//prop.getProperty("access_token");
	String access_token_secret = "4Qxyz7gLe0w2pFvJqXszpotEUQeIfQlI6tadkOrmfoHH5";//prop.getProperty("access_token_secret");
	public String tweet_id;
	
	TwitterBasics basics = new TwitterBasics();
	public static Logger log = LogManager.getLogger(TwitterTweetOperations.class.getName());
	
//	@BeforeTest
//	public void getData() throws IOException {
//		log.info("TwitterTweetOperations.getData");
//		prop = new Properties();
//		FileInputStream fis = new FileInputStream(basics.propertiesFileLocation());
//		prop.load(fis);
//	}
	
	@Test
	public void findLatestTweet() {
		log.info("TwitterTweetOperations.findLatestTweet");
		
		RestAssured.baseURI =  basics.twitterBaseURI();
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
		log.info("TwitterTweetOperations.createTweet");
		
		RestAssured.baseURI = basics.twitterBaseURI();
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
		log.info("TwitterTweetOperations.removeCreatedTweet");
		
		RestAssured.baseURI = basics.twitterBaseURI();
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
