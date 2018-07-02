package twitter;

import io.restassured.path.json.JsonPath;

public class TwitterBasics {
	
	public String propertiesFileLocation() {
		String location = "C:\\Users\\satya\\Desktop\\Eclipse Workspace\\Framework57\\environment.properties";
		return location;
	}
	
	public String twitterBaseURI() {
		String baseuri = "https://api.twitter.com/1.1/statuses";
		return baseuri;
	}
	
	public String twitterBaseURIUser() {
		String baseuri = "https://api.twitter.com/1.1/followers";
		return baseuri;
	}
	
    /**
     * Returns the string representation of this JsonPath
     *
     * @return path as String
     */
    public String getPath(JsonPath path) {
        return this.path.toString();
    }
	
}
