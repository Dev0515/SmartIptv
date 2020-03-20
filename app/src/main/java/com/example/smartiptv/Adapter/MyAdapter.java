package com.example.smartiptv.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartiptv.Activity.PlayingLive;
import com.example.smartiptv.Activity.PlayliveVideo;
import com.example.smartiptv.Fragment.LiveSubCategory;
import com.example.smartiptv.Fragment.PlayLiveFragment;
import com.example.smartiptv.R;
import com.example.smartiptv.model.Live;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Live> dataSet;
    List<Live> dataSet1 = new ArrayList<Live>();
    Context ctx;
    int value;
    String name,id;
    private ArrayList<Live> arraylist;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName,textViewName1;
        ImageView img,img1;
        CardView gridlayout;
        View line;

        RelativeLayout rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.streamname);
            this.img=(ImageView)itemView.findViewById(R.id.img);
            this.gridlayout=(CardView)itemView.findViewById(R.id.card_viewalbum);
             this.rel=(RelativeLayout)itemView.findViewById(R.id.relative);
            this.textViewName1 = (TextView) itemView.findViewById(R.id.streamname1);
            this.img1=(ImageView)itemView.findViewById(R.id.img1);
           // this.line=(View)itemView.findViewById(R.id.line);



        }
    }

    public MyAdapter(List<Live> data, Context ctx,int value,List<Live>dataSet1,String name,String id ){
        this.dataSet = data;
        this.ctx=ctx;
        this.value=value;
        this.dataSet1=dataSet1;
        this.name=name;
        this.id=id;
        this.arraylist = new ArrayList<Live>();
        this.arraylist.addAll(dataSet);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listinfo, parent, false);
        MyViewHolder myViewHolder   = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;

        if(value==0)
        {
            holder.gridlayout.setVisibility(View.VISIBLE);
            holder.rel.setVisibility(View.GONE);
          //  holder.line.setVisibility(View.GONE);

            String namepass[] = dataSet.get(listPosition).getStreamname().split(":");
            String name8 = namepass[0];
            final String pass = namepass[1];
            textViewName.setText(pass);

            if(!isNullOrEmpty(dataSet.get(listPosition).getStream_icon()))
            {
                Picasso.get()
                        .load(dataSet.get(listPosition).getStream_icon())
                        .placeholder(R.drawable.logo1)
                        .error(R.drawable.logo1)
                        .into(holder.img);
            }
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent a=new Intent(ctx, PlayingLive.class);
                    a.putExtra("id", id);
                    a.putExtra("name",name);
                    a.putExtra("streamid", dataSet.get(listPosition).getStream_id());
                   a.putExtra("streamname",pass);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("valuesArray1",  (Serializable) dataSet1);
                    a.putExtras(bundle);
                    ctx.startActivity(a);



//                    Fragment fragment = new PlayLiveFragment();
//                    Bundle args = new Bundle();
//                    args.putString("id", id);
//                    args.putString("name",name);
//                    args.putSerializable("valuesArray1", (Serializable) dataSet1);
//                    args.putString("streamid", dataSet.get(listPosition).getStream_id());
//                    args.putString("streamname",pass);
//                    fragment.setArguments(args);
//                    FragmentManager fragmentManager = ((AppCompatActivity)ctx).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frame_container, fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();

                }
            });
            holder.gridlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Fragment fragment = new PlayLiveFragment();
//                    Bundle args = new Bundle();
//                    args.putString("id", id);
//                    args.putString("name",name);
//                    args.putSerializable("valuesArray1", (Serializable) dataSet1);
//                    args.putString("streamid", dataSet.get(listPosition).getStream_id());
//                    args.putString("streamname",pass);
//                    fragment.setArguments(args);
//                    FragmentManager fragmentManager = ((AppCompatActivity)ctx).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frame_container, fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();

                }
            });

        }
        else if(value==1)
        {
            holder.gridlayout.setVisibility(View.GONE);
            holder.rel.setVisibility(View.VISIBLE);
           // holder.line.setVisibility(View.VISIBLE);
            String namepass[] = dataSet.get(listPosition).getStreamname().split(":");
            String namei8 = namepass[0];
            final String pass1 = namepass[1];

            holder.textViewName1.setText(pass1);

            if(!isNullOrEmpty(dataSet.get(listPosition).getStream_icon()))
            {
                Picasso.get()
                        .load(dataSet.get(listPosition).getStream_icon())
                        .placeholder(R.drawable.logo1)
                        .error(R.drawable.logo1)
                        .into(holder.img1);
            }

            holder.rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Fragment fragment = new PlayLiveFragment();
                    Bundle args = new Bundle();
                    args.putString("id", id);
                    args.putString("name",name);
                    args.putSerializable("valuesArray1", (Serializable) dataSet1);
                    args.putString("streamid", dataSet.get(listPosition).getStream_id());
                    args.putString("streamname",pass1);
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = ((AppCompatActivity)ctx).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
//                    Intent intent = new Intent(ctx, PlayliveVideo.class);
//                    intent.putExtra("LIST", (Serializable) dataSet1);
//                    intent.putExtra("name",name);
//                    intent.putExtra("id",id);
//                    intent.putExtra("streamid",dataSet.get(listPosition).getStream_id());
//                    intent.putExtra("streamname",pass1);
//                    ctx.  startActivity(intent);

                }
            });
        }





//
//        holder.rel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ctx, ""+dataSet.get(listPosition).getCategory_id(), Toast.LENGTH_SHORT).show();
//                Fragment fragment = new LiveSubCategory(dataSet.get(listPosition).getCategory_id(),dataSet.get(listPosition).getCategory_name());
//                FragmentManager fragmentManager = ((AppCompatActivity)ctx).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frame_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                }
//
//        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        dataSet.clear();
        if (charText.length() == 0) {
            dataSet.addAll(arraylist);
        } else {
            for (Live wp : arraylist) {
                if (wp.getStreamname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    dataSet.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
