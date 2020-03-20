package com.example.smartiptv.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NavUtils;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartiptv.Activity.LiveTvActivity;
import com.example.smartiptv.Activity.PlayliveVideo;
import com.example.smartiptv.Adapter.EPGadapter;
import com.example.smartiptv.Adapter.livevideoscreenAdapter;
import com.example.smartiptv.POJO.ApiLiveinfo;
import com.example.smartiptv.R;
import com.example.smartiptv.model.Live;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlayLiveFragment extends Fragment  implements VideoRendererEventListener {

    private static final String TAG = "JavaActivity";

    private static final String SAMPLE_URL="http://m3ulink.com:7899/live/saumyamohan831/BeNHLLIL/";

    String id,name,streamid,streamname;
    private static livevideoscreenAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView,recyclerView1,epggrecycle;
    public List<Live> listDataList1;
    public List<Live> listDataList;
    ImageView left,right,back;
    TextView titlename,vlc_overlay_title;
    Spinner spinner;
    int value=0,j;
    private PlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    BottomNavigationView navBar;
    TextView text_appname;
    EPGadapter groceryAdapter;
    private List<Live> groceryList = new ArrayList<>();

    public PlayLiveFragment() {
//        this.name=name;
//        this.id=id;
//        this.streamid=streamid;
//        this.streamname=streamname;
//        this.listDataList1=listDataList1;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle b = this.getArguments();
        if(b != null){
            id =b.getString("id");
            streamname=b.getString("streamname");
            name=b.getString("name");
            streamid=b.getString("streamid");
            listDataList1=
                    (List<Live>)b.getSerializable("valuesArray1");

        }

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_play_live, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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



                Fragment fragment = new LiveSubCategory();
                Bundle args = new Bundle();
                args.putString("id", id);
                args.putString("name",name);
                args.putSerializable("valuesArray", (Serializable) listDataList1);

                fragment.setArguments(args);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        text_appname=(TextView)view1.findViewById(R.id.text_appname);
        text_appname.setText(streamname);

//
//         navBar = getActivity().findViewById(R.id.nav_view);

        simpleExoPlayerView = new SimpleExoPlayerView(getContext());
        simpleExoPlayerView = (SimpleExoPlayerView)view. findViewById(R.id.player_view);

        left=(ImageView)view.findViewById(R.id.left);
        right=(ImageView)view.findViewById(R.id.right);

        titlename=(TextView)view.findViewById(R.id.titlename);
        recyclerView1 = (RecyclerView)view.findViewById(R.id.allsubcategory);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        epggrecycle = view.findViewById(R.id.allsubcategory1);
        epggrecycle.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL));
        groceryAdapter = new EPGadapter(groceryList, getContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        epggrecycle.setLayoutManager(horizontalLayoutManager);
        epggrecycle.setAdapter(groceryAdapter);
        populategroceryList();

        checkplayer(streamid,streamname);
       // getseriescategory(id,name);
        spinner = (Spinner) view.findViewById(R.id.spinner);
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

        right.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            for (int i=0;i<listDataList1.size()-1;i++)
            {
                if(titlename.getText().toString().equalsIgnoreCase(listDataList1.get(i).getCategory_name()))
                {
                    j=i+1;
                }
            }
            getseriescategory(listDataList1.get(j).getCategory_id(),listDataList1.get(j).getCategory_name());
            titlename.setText(listDataList1.get(j).getCategory_name());

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
                    getseriescategory(listDataList1.get(j).getCategory_id(),listDataList1.get(j).getCategory_name());
                    titlename.setText(listDataList1.get(j).getCategory_name());


                } }
        });
        titlename.setText(""+name);



//        int currentOrientation = getResources().getConfiguration().orientation;
//        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//            // Landscape
//            Log.e(TAG, "onViewCreated: landd" );
//            ((LiveTvActivity)getActivity()).navView.setVisibility(View.GONE);
//        }
//        else {
//            Log.e(TAG, "onViewCreated: potrait" );
//        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
         getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void populategroceryList(){
        Live potato = new Live("12:15-2:00  afganistan Friends\nvampire diaries");
        Live onion = new Live("12:15-2:00  afganistan Friends\n");
        Live cabbage = new Live("12:15-2:00  afganistan Vampire Diaries\n");
        Live cauliflower = new Live("12:15-2:00  afganistan Friends\n");
        groceryList.add(potato);
        groceryList.add(onion);
        groceryList.add(cabbage);
        groceryList.add(cauliflower);
        groceryAdapter.notifyDataSetChanged();
    }

    private void displayUserData(Live user) {
        String name = user.getCategory_name();
        String idd=user.getCategory_id();
        titlename.setText(name);
        getseriescategory(user.getCategory_id(),name);

    }
    private void getseriescategory(final String id, final String n) {

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


                adapter=new livevideoscreenAdapter(listDataList,getContext(),listDataList1,n,id,PlayLiveFragment.this,text_appname.getText().toString());
                recyclerView1.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Live>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//
//        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
//
//
//
//    }

    public void checkplayer(String streamid,String streamname)
    {
        if(player==null)
        {

            text_appname.setText(streamname);

            intializeplayer(streamid);

        }
        else{

            player.stop();
           player.setPlayWhenReady(false);


            text_appname.setText(streamname);


            intializeplayer(streamid);
        }
    }
    public void intializeplayer(String id)
    {
      String url="http://m3ulink.com:7899/live/saumyamohan831/BeNHLLIL/"+id+".m3u8";
            Log.e(TAG, "intializeplayer: "+url );
            Uri mp4VideoUri =Uri.parse(url);


            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter(); //test

            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);


            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);


            simpleExoPlayerView.setUseController(false);
            simpleExoPlayerView.requestFocus();
            simpleExoPlayerView.setPlayer(player);

            final DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "Degoo"), bandwidthMeter);
            MediaSource videoSource = new HlsMediaSource(mp4VideoUri, dataSourceFactory, 1, null, null);
            final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
            player.prepare(videoSource);
//            Log.e(TAG, "intializeplayer: "+ player.getPlaybackState());
        player.addListener(new ExoPlayer.EventListener() {


            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.e(TAG, "Listener-onTracksChanged... ");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.e(TAG, "onLoadingChanged: "+isLoading );

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //Log.e(TAG, "Listener-onPlayerStateChanged..." + playbackState+"|||isDrawingCacheEnabled():"+simpleExoPlayerView.isDrawingCacheEnabled());
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.e(TAG, "Listener-onPlayerError...");
                Toast.makeText(getContext(), "File not found on server, error in playing", Toast.LENGTH_SHORT).show();
                player.stop();
                player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        player.setPlayWhenReady(true); //run file/link when ready to play.
        player.setVideoDebugListener(this);
    }

    @Override
    public void onVideoEnabled(DecoderCounters counters) {

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

    }

    @Override
    public void onVideoInputFormatChanged(Format format) {

    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        Log.e(TAG, "onVideoSizeChanged [" + " width: " + width + " height: " + height + "]");
        //resolutionTextView.setText("RES:(WxH):" + width + "X" + height + "\n           " + height + "p");//shows video info
    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }

    @Override
    public void onStop() {
        super.onStop();
        player.stop();
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.layout.customactionbar, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//
//
//
//    }
@Override
public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if(isVisibleToUser) {
        Log.e(TAG, "setUserVisibleHint: " );
        Activity a = getActivity();
        if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }
}

}
