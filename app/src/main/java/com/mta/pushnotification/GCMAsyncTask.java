package com.mta.pushnotification;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by anurag.pal on 10/28/2015.
 */
public class GCMAsyncTask extends AsyncTask<String, String, String>{


    private ProgressDialog mProgressDialog;
    private Context mContext;
    private OnRegistrationListener mListener;
    private GoogleCloudMessaging mCloudMessaging;
    public static final String REG_ID = "regId";

    protected static final String APP_VERSION = "appversion";


    GCMAsyncTask(Context context){

        mContext = context;
        mListener = (OnRegistrationListener) context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext, "","Please Wait...");
    }

    @Override
    protected String doInBackground(String... params) {

        String regID = "";

        mCloudMessaging = GoogleCloudMessaging.getInstance(mContext);
        try {
            regID = mCloudMessaging.register(Config.GOOGLE_PROJECT_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return regID;
    }

    @Override
    protected void onPostExecute(String regId) {
        super.onPostExecute(regId);

        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        if (regId != null && !regId.isEmpty()){
            mListener.onRegistratiomSuccess(regId);
        }else{
            mListener.onRegistrationFailed();
        }
    }
}
