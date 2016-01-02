package com.mta.pushnotification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, OnRegistrationListener {

    private Button btGCMreg;
    private Button btServer;
    private Context mContext;
    SharedPreferences preferences;
    ArrayList<Chat> arrayList = new ArrayList<Chat>();
    public static final String REG_ID = "regId";

    protected static final String APP_VERSION = "appversion";

    private String regId;
    private RecyclerView listView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter recyclerAdapter;
   private static  RegistrationActivity context;
  private EditText text;

public  static  RegistrationActivity getInstance(){
    return context;
}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_registration);
        mContext = getApplicationContext();
        preferences = getSharedPreferences(RegistrationActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        initView();
        listView = (RecyclerView) findViewById(R.id.list_item);

        text=(EditText)findViewById(R.id.msg);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)



  /*      arrayList.add(new Chat("a","dd"));
        arrayList.add(new Chat("a","dd"));
        arrayList.add(new Chat("a","dd"));
        arrayList.add(new Chat("a","dd"));*/
       recyclerAdapter= new RecyclerAdapter(this,arrayList,recyclerAdapter);

        listView.setAdapter(recyclerAdapter);


    }


    public  void addChat(final String type,final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerAdapter.add(arrayList.size()+1,new Chat(type,msg));

            }
        });

      }



    private void initView() {
        btGCMreg = (Button) findViewById(R.id.btGcm);
        btGCMreg.setOnClickListener(this);
        btServer = (Button) findViewById(R.id.btServer);
        btServer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btGcm:

                regId = getRegistrationId(mContext);
                if (regId.isEmpty()){
                    GCMAsyncTask task =new GCMAsyncTask(this);
                    task.execute();
                }else {
                    Toast.makeText(RegistrationActivity.this, "Already Register!!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btServer:
                final String msg=text.getText().toString();
                addChat("usr",msg);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (regId == null || regId.isEmpty()){
//                            Toast.makeText(this,"Device Not Register or Reg Id Not found",Toast.LENGTH_SHORT).show();
                        }else {
                            RestFulMethods.makeServiceCall(msg);
//                            Toast.makeText(this,RestFulMethods.makeServiceCall(),Toast.LENGTH_SHORT).show();

/*                    Intent intent = new Intent(this,MainActivity.class);
                    intent.putExtra(REG_ID,regId);
                    startActivity(intent);
                    finish();*/
                        }
                    }
                }).start();

                break;
        }
    }

    private String getRegistrationId(Context context){
        String regID = null;
        regID = preferences.getString(REG_ID,"");
        return regID;
    }

    @Override
    public void onRegistratiomSuccess(String regId) {
        if (!regId.isEmpty()){
            this.regId = regId;
            saveGCMID(regId);
        }
    }

    @Override
    public void onRegistrationFailed() {
        Toast.makeText(RegistrationActivity.this, "GCM Registration Failed" , Toast.LENGTH_SHORT).show();
    }

    private void saveGCMID(String regId){
        SharedPreferences.Editor  pref = preferences.edit();
        pref.putString(REG_ID,regId);
        pref.commit();
    }
}
