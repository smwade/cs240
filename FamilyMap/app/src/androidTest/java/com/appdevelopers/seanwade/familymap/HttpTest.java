package com.appdevelopers.seanwade.familymap;

import android.test.AndroidTestCase;
import android.util.Log;

import com.appdevelopers.seanwade.familymap.web_server_access.HttpClient;

import org.json.JSONObject;


/**
 * This tests getting data from the web service.
 */
public class HttpTest extends AndroidTestCase {

    // Make sure to change to match current server
    private String WEB_SERVER_ADDRESS = "http://10.14.34.218:8000";

    public void testLogin() {
        try {
            HttpClient httpClient = new HttpClient();
            JSONObject jsonObject = httpClient.login("sean", "sean", WEB_SERVER_ADDRESS);
            assertEquals(jsonObject.getString("userName"), "sean");
        }
        catch (Exception e) {
            fail();
        }
    }

    public void testGetEvents() {
        try {
            HttpClient httpClient = new HttpClient();
            JSONObject result = httpClient.login("sean", "sean", WEB_SERVER_ADDRESS);
            JSONObject j = httpClient.getEvents(result.getString("Authorization"), WEB_SERVER_ADDRESS);
            Log.d("testGetEvents", j.toString());
        }
        catch (Exception e) {
            fail();
        }
    }

    public void testGetPeople() {
        try {
            HttpClient httpClient = new HttpClient();
            JSONObject result = httpClient.login("sean", "sean", WEB_SERVER_ADDRESS);
            JSONObject j = httpClient.getPeople(result.getString("Authorization"), WEB_SERVER_ADDRESS);
            Log.d("testGetPeople", j.toString());
        }
        catch (Exception e) {
            fail();
        }
    }
}
