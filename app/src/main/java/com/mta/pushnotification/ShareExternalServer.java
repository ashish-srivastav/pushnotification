package com.mta.pushnotification;

import android.content.Context;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by anurag.pal on 10/28/2015.
 */
public class ShareExternalServer {

    private Context mContext;
    private String mRegId;



    public String shareRegIdWithAppServer(Context context, String regId){

        mContext = context;
        mRegId = regId;

        String result = "";
        Map paramsMap = new HashMap();
        paramsMap.put("regId",regId);
        try{
            URL serverUrl = null;
            try{
                serverUrl = new URL(Config.APP_SERVER_URL);
            }catch(MalformedURLException e){
                result = "Invalid URL : " + Config.APP_SERVER_URL;
            }
            StringBuilder postBody = new StringBuilder();
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry param = (Map.Entry) iterator.next();
                postBody.append(param.getKey()).append("=").append(param.getValue());
                if (iterator.hasNext()){
                    postBody.append("&");
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();
            HttpURLConnection httpURLConnection = null;
            try{
                httpURLConnection = (HttpURLConnection) serverUrl.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setFixedLengthStreamingMode(bytes.length);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                int status = httpURLConnection.getResponseCode();
                if (status == 200) {
                    result = "RegId shared with Application Server. RegId: "
                            + regId;
                } else {
                    result = "Post Failure." + " Status: " + status;
                }
            }finally {
                if (httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
        }catch (IOException e){
            result = "Post Failure. Error in sharing with App Server.";
        }
        return result;
    }
}
