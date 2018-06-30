package jira_api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ConvertRawFiles;

public class TwitterTweetOperations {
	
	String consumer_key    = "FMxLzP7TuRKYTKG2nqFvF7kfe";
	String consumer_secret = "Rwqe6mtXUblZPxR9oMCNsS3iCwNWDJo2GXo7xygQ6yyUdh0czE";
	String access_token    = "889343834865385472-UwjMBFthHWByqzFyZaof60EyHw5s9Lu";
	String access_token_secret = "Rh93aPJczniVCdBGZuSPwQE0VCfUE4H7ZIQg2JsVsbFh9";
	public String tweet_id;
	
	public static Logger log = LogManager.getLogger(JIRACommentOperations.class.getName());
	
	@Test
	public void findLatestTweet() {
		log.info("TwitterTweetOperations.findLatestTweet");
		
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
		log.info("TwitterTweetOperations.createTweet");
		
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
		System.out.println("The tweet is created at " + created_at);
	}
	
	@Test
	public void removeCreatedTweet() {
		log.info("TwitterTweetOperations.removeCreatedTweet");
		
		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		Response response = 
		given().auth().oauth(consumer_key, consumer_secret, access_token, access_token_secret).
		when().post("/destroy/" + tweet_id +".json").
		then().statusCode(200).
		extract().response();
		
		JsonPath json = (ConvertRawFiles.rawtoJSON(response));
		String truncated = json.getString("truncated");
		System.out.println(truncated);
		if (truncated.equalsIgnoreCase("false")) {
			Assert.assertTrue(true);
		}else {
			Assert.assertFalse(true);
		}

	}
}
