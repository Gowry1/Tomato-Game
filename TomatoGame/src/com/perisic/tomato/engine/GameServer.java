package com.perisic.tomato.engine;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.json.JSONObject;

/**
 * Game that interfaces to an external Server to retrieve a game. 
 * A game consists of an image and an integer that denotes the solution of this game. 
 * 
 * @author Marc Conrad
 *
 */
public class GameServer {

	/**
	 * Basic utility method to read string for URL.
	 */
	
	  public static String getPublicIPAddress() {
	        String apiUrl = "https://api.ipify.org?format=json";

	        try {
	            @SuppressWarnings("deprecation")
				URL url = new URL(apiUrl);
	            InputStream inputStream = url.openStream();
	            JSONObject jsonResponse = readJsonResponse(inputStream);

	            return jsonResponse.getString("ip");
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	   
	    public static String getWorldTimeInfo(String timezone) {
	        String apiUrl = "http://worldtimeapi.org/api/timezone/" + timezone;

	        try {
	            @SuppressWarnings("deprecation")
				URL url = new URL(apiUrl);
	            InputStream inputStream = url.openStream();
	            JSONObject jsonResponse = readJsonResponse(inputStream);

	            return jsonResponse.getString("datetime");
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    
	    private static JSONObject readJsonResponse(InputStream inputStream) throws IOException {
	        ByteArrayOutputStream result = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int length;

	        while ((length = inputStream.read(buffer)) != -1) {
	            result.write(buffer, 0, length);
	        }

	        String jsonStr = result.toString("UTF-8");
	        return new JSONObject(jsonStr);
	    }


	private static String readUrl(String urlString)  {
		try {
			@SuppressWarnings("deprecation")
			URL url = new URL(urlString);
			InputStream inputStream = url.openStream();

			
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toString("UTF-8");
		} catch (Exception e) {
			System.out.println("An Error occured: " + e.toString());
			e.printStackTrace();
			return null;
		}

	}

	 
	public Game getRandomGame() {
		// See http://marconrad.com/uob/tomato for details of usage of the api. 
		
		String tomatoapi = "https://marcconrad.com/uob/tomato/api.php?out=csv&base64=yes";
		
		String dataraw = readUrl(tomatoapi);
		String[] data = dataraw.split(",");

		byte[] decodeImg = Base64.getDecoder().decode(data[0]);
		ByteArrayInputStream quest = new ByteArrayInputStream(decodeImg);

		int solution = Integer.parseInt(data[1]);

		BufferedImage img = null;
		try {
			img = ImageIO.read(quest);
			return new Game(img, solution);
		} catch (IOException e1) {
			
			e1.printStackTrace();
			return null;
		}
	}

}
