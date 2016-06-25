package com.appdevelopers.seanwade.familymap.web_server_access;

import android.util.Log;

import com.appdevelopers.seanwade.familymap.FMS;
import com.appdevelopers.seanwade.familymap.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpClient {

    public JSONObject login(String userName, String password, String WEB_SERVER_ADDRESS) throws LoginFailedException{
        try {
            JSONObject requestBodyJson = new JSONObject();
            requestBodyJson.put("username", userName);
            requestBodyJson.put("password", password);
            String requestBodyString = requestBodyJson.toString();

            URL url = new URL(WEB_SERVER_ADDRESS + "/user/login/");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();

            // Write post data to request body
            OutputStream requestBody = connection.getOutputStream();
            requestBody.write(requestBodyString.getBytes());
            requestBody.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject responseBodyJson = new JSONObject(responseBodyData);
                if (responseBodyJson.has("message")) {
                    responseBodyJson = null;
                }
                return responseBodyJson;
            }
            else {
                Log.e("HTTP", "The Login request did not work");
                throw new LoginFailedException();
            }
        }
        catch (Exception e) {
            Log.e("IO", "There was an io error (or JSON) in the http login function.");
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getEvents(String authorization, String WEB_SERVER_ADDRESS) throws HttpFailedException {
        try {
            URL url = new URL(WEB_SERVER_ADDRESS + "/event/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Set HTTP request header
            connection.addRequestProperty("Authorization", authorization);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject responseBodyJson = new JSONObject(responseBodyData);
                return responseBodyJson;
            } else {
                Log.e("HTTP", "The event http request did not work.");
                throw new HttpFailedException();
            }
        } catch (Exception e) {
            Log.e("IO", "The event io http did not work");
            return null;
        }
    }

    public JSONObject getPeople(String authorization, String WEB_SERVER_ADDRESS) throws HttpFailedException {
        try {
            URL url = new URL(WEB_SERVER_ADDRESS + "/person/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Set HTTP request header
            connection.addRequestProperty("Authorization", authorization);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject responseBodyJson = new JSONObject(responseBodyData);
                return responseBodyJson;
            } else {
                Log.e("HTTP", "The people http request did not work.");
                throw new HttpFailedException();
            }
        } catch (Exception e) {
            Log.e("IO", "The people io http did not work");
            return null;
        }
    }

    public class HttpFailedException extends Exception {}

    public class LoginFailedException extends Exception {}
}
