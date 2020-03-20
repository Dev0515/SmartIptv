package com.example.smartiptv.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.smartiptv.Adapter.Livecategoryadapter;
import com.example.smartiptv.POJO.ApiLive;
import com.example.smartiptv.R;
import com.example.smartiptv.model.Live;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.videolan.libvlc.Dialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class LiveTvfragment extends Fragment {

    private static Livecategoryadapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    ArrayList<Live> modelRecyclerArrayList = new ArrayList<>();
    ProgressDialog pd;
    TextView text_appname;


    public LiveTvfragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_tvfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((AppCompatActivity) getActivity()). getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()). getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) getActivity()). getSupportActionBar().setCustomView(R.layout.customactionbar);
        View view1 =  ((AppCompatActivity) getActivity()). getSupportActionBar().getCustomView();
        ImageButton buttonSideMenu = (ImageButton)view1.findViewById(R.id.action_add);

        buttonSideMenu.setVisibility(View.GONE);

        buttonSideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


            }
        });
        text_appname=(TextView)view1.findViewById(R.id.text_appname);
        text_appname.setText("Live Tv Categories");
        recyclerView = (RecyclerView)view. findViewById(R.id.allmusics);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        pd = new ProgressDialog(getActivity(), R.style.MyTheme);
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        fetchJSON();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setQuery("", false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.setQuery("", false);
                searchView.setIconified(true);


                searchView.clearFocus();

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if(adapter!=null)
                {
                    adapter.filter(newText);
                }
                return false;
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

        pd.dismiss();
    }

    private void fetchJSON()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLive.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiLive api = retrofit.create(ApiLive.class);
        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        String jsonresponse = response.body().toString();

                        try {

                            JSONArray jsonArrays = new JSONArray(jsonresponse);
                            for (int i = 0; i < jsonArrays.length(); i++) {
                                JSONObject json_data = jsonArrays.getJSONObject(i);

                                Live fish = new Live();
                                fish.category_id=json_data.getString("category_id");
                                fish.category_name=json_data.getString("category_name");
                                modelRecyclerArrayList.add(fish);
                            }
                            adapter = new Livecategoryadapter(modelRecyclerArrayList,getContext());
                            recyclerView.setAdapter(adapter);

                            pd.dismiss();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    } else {
                        Log.e("onEmptyResponse", "Returned empty response");
                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("n", "onFailure: "+t);
                pd.dismiss();

            }
        });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }



}
