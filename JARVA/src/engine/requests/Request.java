package engine.requests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Request {
	final static private String GoogleURL = "https://sheets.googleapis.com/v4/spreadsheets/1xvITGRaoxRCg5wTRvQBZjY8guHSt0vEOmlaJQ1-TyZQ";
	final static private String SheetdbURL = "https://sheetdb.io/api/v1/zfv8eh7pe00x6";
	
	final static private String API_Key = "AIzaSyCecIcIlVr1Gd5G1Qb8eBDcsLcJjN9QqZY";
	
	public static void main(String[] args) {
		Request.GET(5);
//		Request.POST(new PlayerData());
	}
	
	// POST Request
	public static boolean POST(PlayerData playerData) {
		HttpURLConnection connection = null;
		
		try {	    	
			String message = 
					  "{"
						+ "\"data\":["
							+ "{" 
								+ " \"Username\":" + " \"" + playerData.getName() + "\","
								+ " \"Score\":" + " \"" + playerData.getScore() + "\" "
							+ "}"
						+ "]"
					+ "}";
			
			// Open Connection
			URL url = new URL( SheetdbURL + "?sheet=Raw");
			
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			
			// Send Post Request
			connection.setDoOutput(true);
			OutputStream output = connection.getOutputStream();
			output.write( message.getBytes(StandardCharsets.US_ASCII) );
			output.flush();
			output.close();
			
			// Response Code
			if( connection.getResponseCode() == 201 ) { 
				System.out.println("Successful POST request");
				return true; 
			} else return false;
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("Error making post request");
			return false;
		}
	}
	
	// GET Request
	public static ArrayList<PlayerData> GET(int count) {  
	    // Attempt to Open a Connection
	    HttpURLConnection connection = null;
	    
	    try {
	    	int lastRow = 2 + count;
	    	URL url = new URL(
		    		GoogleURL + 
		    		"/values/Leaderboard!A2:B" + lastRow + 
		    		"/?key=" + API_Key
		    		);
	    	connection = (HttpURLConnection) url.openConnection();
	    	
	    	connection.setRequestMethod("GET");
	    	
	    	connection.setDoInput(true);
	    	connection.setDoOutput(false);
	    	
	    	InputStream inputStream = connection.getInputStream();
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    	
	    	StringBuilder response = new StringBuilder();
	    	String line;
	    	do {
	    		line = reader.readLine();
	    		if(line != null) response.append(line);
	    	} while ( line != null);
	    	
	    	reader.close();
	    	
	    	return parseData(response.toString());
	    } catch(Exception e) {
	    	System.out.println(e);
	    	System.out.println("Unable to make GET request. Error occurred.");
	    	return null;
	    }
	}
	
	// Extract data from JSON string
	public static ArrayList<PlayerData> parseData(String s) {
		s = s.split("\"values\":")[1]; // Remove unneccessary data
		s = s.replaceAll("[\\[\\]\\,\\} ]", ""); // Remove brackets and commas
		
		ArrayList<PlayerData> output = new ArrayList<>();
		String[] split = s.split("\"");
		
		PlayerData temp = new PlayerData();
		int counter = 0;
		for( int i = 0; i < split.length; i++ ) {
			if( split[i].isEmpty() ) continue;
			
			counter++;
			
			if( counter % 2 == 1) {
				temp = new PlayerData();
				temp.setName(split[i]);
			} else {
				temp.setScore( Float.parseFloat(split[i]) );
				output.add(temp);
			}
		}
	
		return output;
	}
	
}