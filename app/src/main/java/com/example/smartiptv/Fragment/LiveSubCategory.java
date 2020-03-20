package com.example.smartiptv.Fragment;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smartiptv.Adapter.MyAdapter;
import com.example.smartiptv.POJO.ApiLiveinfo;
import com.example.smartiptv.R;
import com.example.smartiptv.helper.GridSpacingItemDecoration;
import com.example.smartiptv.model.Live;
import com.nostra13.universalimageloader.utils.L;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LiveSubCategory extends Fragment  {
    String id,name;
    public static MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView,recyclerView1;
    public List<Live> listDataList1;
    public List<Live> listDataList;
    ImageView left,right;
    TextView titlename;
    ImageView listview1,gridview1;
    RecyclerView.LayoutManager mLayoutManager;
    int value=0,j;
    Spinner spinner;
    TextView text_appname;

    public LiveSubCategory(){
//        this.id=id;
//        this.name=name;
//        this.listDataList1=listDataList1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle b = this.getArguments();
        if(b != null){
            id =b.getString("id");
            name=b.getString("name");
         listDataList1=
                    (List<Live>)b.getSerializable("valuesArray");
            Log.e("8", "onCreate: "+id+name+listDataList1 );
        }

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_sub_category, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(
//                "Channels List");
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ((AppCompatActivity) getActivity()). getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()). getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) getActivity()). getSupportActionBar().setCustomView(R.layout.customactionbar);
        View view1 =  ((AppCompatActivity) getActivity()). getSupportActionBar().getCustomView();
        ImageButton buttonSideMenu = (ImageButton)view1.findViewById(R.id.action_add);

        buttonSideMenu.setVisibility(View.VISIBLE);
        buttonSideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

              //  Toast.makeText(getContext(), "Clicked99!",Toast.LENGTH_LONG).show();

                Fragment fragment = new LiveTvfragment();

                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        text_appname=(TextView)view1.findViewById(R.id.text_appname);
        text_appname.setText("Channels List");

        left=(ImageView)view.findViewById(R.id.left);
        right=(ImageView)view.findViewById(R.id.right);
        titlename=(TextView)view.findViewById(R.id.titlename);
//        listview1=(ImageView)view.findViewById(R.id.vieww1);
//        gridview1=(ImageView)view.findViewById(R.id.vieww);
        titlename.setText(""+name);

        recyclerView = (RecyclerView)view. findViewById(R.id.allsubcategory);
        recyclerView1 = (RecyclerView)view. findViewById(R.id.allsubcategory);
        recyclerView.setHasFixedSize(true);
        recyclerView1.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
         spinner = (Spinner) view.findViewById(R.id.spinner);
        // getseriescategory(id);
        String myString = name;
        ArrayAdapter<Live> adapter = new ArrayAdapter<Live>(getContext(),
                android.R.layout.simple_spinner_item, listDataList1);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinner.setAdapter(adapter);

        for(int i = 0; i < spinner.getCount(); i++) {

            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                spinner.setSelection(i);
                break;
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Live user = (Live) parent.getSelectedItem();
                displayUserData(user);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        listview1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listview1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        recyclerView.setVisibility(View.GONE);
//                        listview1.setVisibility(View.GONE);
//                        gridview1.setVisibility(View.VISIBLE);
//                        recyclerView1.setVisibility(View.GONE);
//                        recyclerView.setVisibility(View.VISIBLE);
//                        value=0;
//                        getseriescategory(id);
//                    }
//                });
//            }
//        });
//        gridview1.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  recyclerView1.setVisibility(View.GONE);
//                  recyclerView.setVisibility(View.GONE);
//                  gridview1.setVisibility(View.GONE);
//                  listview1.setVisibility(View.VISIBLE);
//                  recyclerView1.setVisibility(View.VISIBLE);
//                  value=1;
//                  getseriescategory(id);
//              }
//});

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<listDataList1.size()-1;i++)
                {
                    if(titlename.getText().toString().equalsIgnoreCase(listDataList1.get(i).getCategory_name()))
                    {
                        j=i+1;
                    }
                }
                titlename.setText(listDataList1.get(j).getCategory_name());
                getseriescategory(listDataList1.get(j).getCategory_id());
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<listDataList1.size()-1;i++)
                {
                    if(titlename.getText().toString().equalsIgnoreCase(listDataList1.get(i).getCategory_name()))
                    {
                        j=i-1;
                    }
                }
                if(j<0)
                {

                    j=0;
                }
                if(j>=0)
                {
                    titlename.setText(listDataList1.get(j).getCategory_name());
                    getseriescategory(listDataList1.get(j).getCategory_id());

                } }
        });



    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menulivesub, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search1);
        final MenuItem list1 = menu.findItem(R.id.vieww1);
        final MenuItem grid1 = menu.findItem(R.id.vieww);
        list1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                recyclerView.setVisibility(View.GONE);
                list1.setVisible(false);
                grid1.setVisible(true);
                recyclerView1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                value=0;
                getseriescategory(id);

                return false;
            }
        });
        grid1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                recyclerView1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                grid1.setVisible(false);
                list1.setVisible(true);
                recyclerView1.setVisibility(View.VISIBLE);
                value=1;
                getseriescategory(id);

                return false;
            }
        });

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

    private void displayUserData(Live user) {
        String name = user.getCategory_name();
        titlename.setText(name);
        getseriescategory(user.getCategory_id());

    }

    private void getseriescategory(final String id) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLiveinfo.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiLiveinfo api = retrofit.create(ApiLiveinfo.class);
        Call<List<Live>> call = api.getSeries1(id);
        call.enqueue(new Callback<List<Live>>() {
            @Override
            public void onResponse(Call<List<Live>> call, Response<List<Live>> response) {
                listDataList=response.body();
                if(value==1)
                {

                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerView1.setLayoutManager(layoutManager);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                    adapter=new MyAdapter(listDataList,getContext(),value,listDataList1,titlename.getText().toString(),id);
                    recyclerView1.setAdapter(adapter);
                }
                else
                {
                    recyclerView.setLayoutManager(mLayoutManager);
                    adapter=new MyAdapter(listDataList,getContext(),value,listDataList1,titlename.getText().toString(),id);
                    recyclerView.setAdapter(adapter);
                } }

            @Override
            public void onFailure(Call<List<Live>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int dpToPx(int dp) {
        Resources r = getResources();

        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
