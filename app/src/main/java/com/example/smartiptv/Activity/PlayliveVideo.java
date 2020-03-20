package com.example.smartiptv.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;


import com.example.smartiptv.Adapter.MyAdapter;
import com.example.smartiptv.Adapter.livevideoscreenAdapter;
import com.example.smartiptv.POJO.ApiLiveinfo;
import com.example.smartiptv.R;
import com.example.smartiptv.model.Live;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayliveVideo extends AppCompatActivity implements IVLCVout.Callback {
    private static final String TAG = "JavaActivity";

    //private static final String SAMPLE_URL = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
    private static final String SAMPLE_URL="http://m3ulink.com:7899/live/saumyamohan831/BeNHLLIL/";
    private static final int SURFACE_BEST_FIT = 0;
    private static final int SURFACE_FIT_HORIZONTAL = 1;
    private static final int SURFACE_FIT_VERTICAL = 2;
    private static final int SURFACE_FILL = 3;
    private static final int SURFACE_16_9 = 4;
    private static final int SURFACE_4_3 = 5;
    private static final int SURFACE_ORIGINAL = 6;
    private static int CURRENT_SIZE = SURFACE_BEST_FIT;
    private FrameLayout mVideoSurfaceFrame = null;
    private SurfaceView mVideoSurface = null;
    private final Handler mHandler = new Handler();

    private LibVLC mLibVLC = null;
    private MediaPlayer mMediaPlayer = null;
    private int mVideoHeight = 0;
    private int mVideoWidth = 0;
    private int mVideoVisibleHeight = 0;
    private int mVideoVisibleWidth = 0;
    private int mVideoSarNum = 0;
    private int mVideoSarDen = 0;

    String id,name,streamid,streamname;
    private static RecyclerView.Adapter adapter,adapter1;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView,recyclerView1;
    public List<Live> listDataList1;
    public List<Live> listDataList;
    ImageView left,right;
    TextView titlename,vlc_overlay_title;
    ImageView listview1,gridview1;
    RecyclerView.LayoutManager mLayoutManager;
    int value=0,j;
    IVLCVout vlcVout;
    Spinner spinner;
    private MediaController controller;
    private SurfaceHolder mSurfaceHolderVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlive_video);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        final ArrayList<String> args = new ArrayList<>();
        args.add("-vvv");
        mLibVLC = new LibVLC(this, args);
        mMediaPlayer = new MediaPlayer(mLibVLC);
        mVideoSurfaceFrame = (FrameLayout) findViewById(R.id.video_surface_frame);
        mVideoSurface = (SurfaceView) findViewById(R.id.video_surface);
        left=(ImageView)findViewById(R.id.left);
        right=(ImageView)findViewById(R.id.right);
        titlename=(TextView)findViewById(R.id.titlename);
        recyclerView1 = (RecyclerView)findViewById(R.id.allsubcategory);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(PlayliveVideo.this);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        Intent i = getIntent();
        listDataList1 = (List<Live>) i.getSerializableExtra("LIST");
        name=i.getStringExtra("name");
        id=i.getStringExtra("id");
        streamid=i.getStringExtra("streamid");
        streamname=i.getStringExtra("streamname");
        titlename.setText(""+name);

        intializeplayer(SAMPLE_URL,streamid);

;
        //  getseriescategory(id,name);
        right.setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View v) {
                for (int i=0;i<listDataList1.size()-1;i++)
                {
                    if(titlename.getText().toString().equalsIgnoreCase(listDataList1.get(i).getCategory_name()))
                    {
                        j=i+1;
                    }
                }
                titlename.setText(listDataList1.get(j).getCategory_name());
                getseriescategory(listDataList1.get(j).getCategory_id(),listDataList1.get(j).getCategory_name());
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
                   getseriescategory(listDataList1.get(j).getCategory_id(),listDataList1.get(j).getCategory_name());

                } }
        });

        vlc_overlay_title=(TextView)findViewById(R.id.vlc_overlay_title);
      //  vlc_overlay_title.setText(""+streamname);



    }

    public void intializeplayer(String sampleUrl, String id) {
        String url=sampleUrl+id+".m3u8";
        Log.e(TAG, "onStart: "+url );
        Log.e(TAG, "intializeplayer: "+mMediaPlayer.getPlayerState() );
        if(mMediaPlayer.getPlayerState()==0)
        {
              vlcVout = mMediaPlayer.getVLCVout();

            vlcVout.setVideoView(mVideoSurface);

            vlcVout.attachViews();

            mMediaPlayer.getVLCVout().addCallback(this);

            Media media = new Media(mLibVLC, Uri.parse(url));

            mMediaPlayer.setMedia(media);

            media.release();
            mMediaPlayer.play();
        }
        else
        {
           Log.e("2","check"+ mMediaPlayer.isPlaying()+mMediaPlayer.getMedia());
            vlcVout.detachViews();
            mMediaPlayer.getVLCVout().removeCallback(this);
            mMediaPlayer.setMedia(null);
            Log.e("2","check afterrrrrrr"+ mMediaPlayer.isPlaying()+mMediaPlayer.getMedia());
            vlcVout = mMediaPlayer.getVLCVout();

            vlcVout.setVideoView(mVideoSurface);

            vlcVout.attachViews();

            mMediaPlayer.getVLCVout().addCallback(this);

            Media media = new Media(mLibVLC, Uri.parse(url));

            mMediaPlayer.setMedia(media);

            media.release();
            mMediaPlayer.play();
            Log.e("2","check after attach\t\t"+ mMediaPlayer.isPlaying()+mMediaPlayer.getMedia());

        }

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


//                    adapter=new livevideoscreenAdapter(listDataList,PlayliveVideo.this,listDataList1,n,id);
//                    recyclerView1.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Live>> call, Throwable t) {
                Toast.makeText(PlayliveVideo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mLibVLC.release();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        String url=SAMPLE_URL+streamid+".m3u8";
//        Log.e(TAG, "onStart: "+url );
//        Log.e(TAG, "intializeplayer: "+mMediaPlayer.getPlayerState() );
//
//            vlcVout = mMediaPlayer.getVLCVout();
//
//            vlcVout.setVideoView(mVideoSurface);
//
//            vlcVout.attachViews();
//
//            mMediaPlayer.getVLCVout().addCallback(this);
//
//            Media media = new Media(mLibVLC, Uri.parse(url));
//
//            mMediaPlayer.setMedia(media);
//
//            media.release();
//            mMediaPlayer.play();
//
//        Log.e(TAG, "mnmnmnm: "+mMediaPlayer.getPlayerState() );
//
////        controller.setAnchorView(mVideoSurfaceFrame);
////        controller = new MediaController(this);
////        controller.setMediaPlayer(mVideoSurfaceFrame);
////        mVideoSurfaceFrame.setOnClickListener(new View.OnClickListener() {
////            public void onClick(View v) {
////                controller.show(10000);
////            }});
//
//
//    }

    @Override
    protected void onStop() {
        super.onStop();

        mMediaPlayer.stop();
        mMediaPlayer.getVLCVout().detachViews();
        mMediaPlayer.getVLCVout().removeCallback(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
        mVideoWidth = width;
        mVideoHeight = height;
        mVideoVisibleWidth = visibleWidth;
        mVideoVisibleHeight = visibleHeight;
        mVideoSarNum = sarNum;
        mVideoSarDen = sarDen;
    }

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {
    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {
    }
}

