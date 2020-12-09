package com.example.tutorial;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Helper methods related to requesting and receiving data from backend service.
*/
public final class QueryUtils {
    //JSON keys for the form
    private final static String field1 = "username";
    private final static String field2 = "password";
    private final static String field3 = "email";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils(){
    }

    /**
     * Returns new URL object from the given string URL.
    */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Problem", "Problem building the URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP POST request containing data request to the given URL and return a String as the response.
     * This method is used if user tries to register, login or logout.
     */
    private static String makeHttpRequest(URL url, String username, String password, String email) throws IOException {
        String response = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setDoOutput(true);

            //Data to send to the server
            String postData = "{\""+field1+"\":\""+username+"\",\""+field2+"\":\""+password+"\",\""+field3+"\":\""+email+"\"}";
            Log.e("Problem", postData);
            OutputStreamWriter os = new OutputStreamWriter(urlConnection.getOutputStream());
            os.write(postData);
            os.close();

            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.e("Problem", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Problem", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies that an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return response;
    }

    /**
     * Make an HTTP DELETE request to delete all the users from the database and return a String as the response.
     * This method is used if user tries to delete all the users
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String response = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.e("Problem", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Problem", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return response;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
    * Driver method for performing a successful network call.
    * This method is used if user tries to register, login or logout.
    */
    public static String fetchTutorialData(String requestUrl, String username, String password, String email) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String response = null;
        try {
            response = makeHttpRequest(url, username, password, email);
        } catch (IOException e) {
            Log.e("Problem", "Problem making the HTTP request.", e);
        }

        // Return the response
        return response;
    }

    /**
    * Driver method for performing a successful network call.
    * This method is used if user tries to delete all users from the database.
    */
    public static String fetchTutorialData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String response = null;
        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Problem", "Problem making the HTTP request.", e);
        }

        // Return the response
        return response;
    }
}
