package com.mta.pushnotification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashish.srivastava on 12/30/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewAdapter> {

     private Context context;
     private ArrayList<Chat> list;
    static RecyclerAdapter adapter1;

    public RecyclerAdapter (Context context1,ArrayList<Chat> list1,RecyclerAdapter adapter){
        context=context;
        adapter1=adapter;
         list=list1;

    }


    public static RecyclerAdapter  getInstance(){
        return adapter1;
    }



    public   void add(int position, Chat item) {
        list.add(position-1, item);
        notifyItemInserted(position);
    }



    @Override
    public MyViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
       View view= inflater.inflate(R.layout.activity_main,parent,false);
       return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(MyViewAdapter holder, int position) {
          if(list.get(position).getMessager().equalsIgnoreCase("usr")){
              holder.send.setText(list.get(position).getMessage());
          }
       else if(list.get(position).getMessager().equalsIgnoreCase("rec")){
              holder.recieve.setText(list.get(position).getMessage());
          }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class MyViewAdapter extends RecyclerView.ViewHolder {
    private TextView send;
        private TextView recieve;

            public MyViewAdapter(View itemView) {
            super(itemView);

            send=(TextView)itemView.findViewById(R.id.send);
            recieve=(TextView)itemView.findViewById(R.id.receive);

        }
    }
}

