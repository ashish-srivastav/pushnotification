package com.mta.pushnotification;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class RestFulMethods {

    public final static int GET = 1;
    public final static int POST = 2;
    public static String customerCameFrom = "";
    public static long soldToCustomerId = 0;

    public static String makeServiceCall(String msg) {//String url, int method, List<NameValuePair> params) throws UnsupportedEncodingException, IOException {
        // http client

        JSONObject jGcmData = new JSONObject();
        JSONObject jData = new JSONObject();
        try {
            jData.put("message", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
// Where to send GCM message.
        try {
            jGcmData.put("to", "APA91bF0nig0PgniXmatbvQr9yBZIrvM_a5Wy9yNCdUC_LI2-nsNqCFo2rky4Cd7csJbwoog8SZHUQXjjBiP6jOP03umIqHB6viXZrVYj5ioGrlJaSRmilbz3SCWa-DG52MU6zt6sWaB");
        } catch (JSONException e) {
            e.printStackTrace();
        }

// What to send in GCM message.
        try {
            jGcmData.put("data", jData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

// Create connection to send GCM Message request.
        URL url = null;
        try {
            url = new URL("https://android.googleapis.com/gcm/send");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty("Authorization", "key=" + "AIzaSyAwf38DJS4RFdoPke2JQ-VbdLk_LSgwluw");
        conn.setRequestProperty("Content-Type", "application/json");
        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setDoOutput(true);


// Send GCM message content.
        OutputStream outputStream = null;
        try {
            outputStream = conn.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.write(jGcmData.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return String.valueOf(conn.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(0);
    }

}
