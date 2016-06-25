package com.appdevelopers.seanwade.familymap;

import android.test.AndroidTestCase;
import android.util.Log;

import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.appdevelopers.seanwade.familymap.model.User;
import com.appdevelopers.seanwade.familymap.utilities.JsonResponseParser;
import com.appdevelopers.seanwade.familymap.web_server_access.HttpClient;

import org.json.JSONObject;

import java.util.List;

/**
 * Tests the first piece of data recieved for correctness.  Results are printed to log because
 * so it can be seen.
 */
public class JsonResponseParserTest extends AndroidTestCase {

    private String WEB_SERVER_ADDRESS = "http://192.168.1.237:8000";

    public void testParseUser() {
        try {
            HttpClient httpClient = new HttpClient();
            JSONObject jsonResult = httpClient.login("sean", "sean", WEB_SERVER_ADDRESS);
            User newUser = new User();
            JsonResponseParser.parseUser(jsonResult, newUser);
            Log.d("testParseUser", newUser.toString());
        }
        catch (Exception e) {
            fail();
        }
    }

    public void testParseEvents() {
        try {
            HttpClient httpClient = new HttpClient();
            JSONObject result = httpClient.login("sean", "sean", WEB_SERVER_ADDRESS);
            JSONObject j = httpClient.getEvents(result.getString("Authorization"), WEB_SERVER_ADDRESS);
            List<Event> list = JsonResponseParser.parseEvents(j);
            Log.d("testParseEvent", list.get(0).toString());
        }
        catch (Exception e) {
            fail();
        }
    }

    public void testParsePeople() {
        try {
            HttpClient httpClient = new HttpClient();
            JSONObject result = httpClient.login("sean", "sean", WEB_SERVER_ADDRESS);
            JSONObject j = httpClient.getPeople(result.getString("Authorization"), WEB_SERVER_ADDRESS);
            List<Person> list = JsonResponseParser.parsePeople(j);
            Log.d("testParsePeople", list.get(0).toString());
        }
        catch (Exception e) {
            fail();
        }
    }


}
