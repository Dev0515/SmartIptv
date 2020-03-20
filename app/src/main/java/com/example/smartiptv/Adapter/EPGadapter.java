package com.example.smartiptv.Adapter;

import android.content.Context;
import android.os.Bundle;
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

import com.example.smartiptv.Fragment.PlayLiveFragment;
import com.example.smartiptv.R;
import com.example.smartiptv.model.Live;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EPGadapter extends RecyclerView.Adapter<EPGadapter.MyViewHolder> {

    private List<Live> horizontalGrocderyList;
    Context context;

    public EPGadapter(List<Live> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.epghorizontal, parent, false);
        MyViewHolder gvh = new MyViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       // holder.imageView.setImageResource(horizontalGrocderyList.get(position).getProductImage());
        holder.txtview.setText(horizontalGrocderyList.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview;
        public MyViewHolder(View view) {
            super(view);
            //imageView=view.findViewById(R.id.idProductImage);
            txtview=view.findViewById(R.id.idProductName);
        }
    }
}