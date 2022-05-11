package engine.requests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Request {
	final static private String SpreadsheetID = "1yDXkdtcijqA6ihkR_mVvCsMMgsc8RTEwx4f0cPlWXGI";
	
	final static private String UsernameColumn = "A";
	final static private String ScoreColumn = "B";
	
	public static void main(String[] args) {
		Request.GET(1, 5);
	}
	
	// Request a Sorting of Data
	public static boolean SORT() {
		// POST
		return false;
	}
	
	// POST Request
	public static boolean POST(PlayerData playerData) {
		HttpURLConnection connection = null;
		
		try {
			URL url = new URL(
		    		"https://sheets.googleapis.com/v4/spreadsheets/" + SpreadsheetID + 
		    		":batchUpdate"
		    		);
			
			return true;
		} catch(Exception e) {
			System.out.println("Error making post request");
			return false;
		}
	}
	
	// GET Request for Information
	public static PlayerData GET(int rowBegin, int rowEnd) {   
		// https://sheets.googleapis.com/v4/spreadsheets/1yDXkdtcijqA6ihkR_mVvCsMMgsc8RTEwx4f0cPlWXGI/values/A:B/?key=AIzaSyDatfI3f1pftdRRFbOqOiUG2rsOLjAlAfU
		
		// Pull the header and the member row
		
		
	    final String API_Key = "";
	
	    // Attempt to Open a Connection
	    HttpURLConnection connection = null;
	    
	    try {
	    	URL url = new URL(
		    		"https://sheets.googleapis.com/v4/spreadsheets/" + SpreadsheetID + 
		    		"/values/" + UsernameColumn + rowBegin + ":" + ScoreColumn + rowEnd + "/?key=" + API_Key
		    		);
	    	connection = (HttpURLConnection) url.openConnection();
	    	connection.setRequestMethod("GET");
	    	
	    	
	    	InputStream inputStream = connection.getInputStream();
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    	
	    	StringBuilder response = new StringBuilder();
	    	String line;
	    	do {
	    		line = reader.readLine();
	    		if(line != null) response.append(line);
	    	} while ( line != null);
	    	
	    	reader.close();
	    	
	    	System.out.println(response.toString());
	    	
	    	return null;
	    } catch(Exception e) {
	    	System.out.println("Unable to make GET request. Error occurred.");
	    	return null;
	    }
	}
}