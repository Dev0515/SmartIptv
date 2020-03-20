package com.example.smartiptv.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.smartiptv.Fragment.LiveSubCategory;
import com.example.smartiptv.R;
import com.example.smartiptv.model.Live;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Livecategoryadapter extends RecyclerView.Adapter<Livecategoryadapter.MyViewHolder> {

    private List<Live> dataSet;
    Context ctx;

    private ArrayList<Live> arraylist;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        RelativeLayout rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.rel=(RelativeLayout)itemView.findViewById(R.id.rel);

        }
    }

    public Livecategoryadapter(List<Live> data, Context ctx ){
        this.dataSet = data;
        this.ctx=ctx;
        this.arraylist = new ArrayList<Live>();
        this.arraylist.addAll(dataSet);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorylayout, parent, false);

     //   view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;


        textViewName.setText(dataSet.get(listPosition).getCategory_name());
        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, ""+dataSet.get(listPosition).getCategory_id(), Toast.LENGTH_SHORT).show();
                Fragment fragment = new LiveSubCategory();
                Bundle args = new Bundle();
                args.putString("id", dataSet.get(listPosition).getCategory_id());
                args.putString("name",dataSet.get(listPosition).getCategory_name());
                args.putSerializable("valuesArray", (Serializable) dataSet);
               // LiveSubCategory f = new LiveSubCategory();
                fragment.setArguments(args);
                FragmentManager fragmentManager = ((AppCompatActivity)ctx).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                }

        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void filter(String charText) {
       charText = charText.toLowerCase(Locale.getDefault());

        dataSet.clear();
        if (charText.length() == 0) {
            dataSet.addAll(arraylist);
        } else {
            for (Live wp : arraylist) {
                if (wp.getCategory_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    dataSet.add(wp);
                }
            }
        }
        charText=null;
        notifyDataSetChanged();
    }
}
