package com.example.smartiptv.POJO;




import com.example.smartiptv.model.Live;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiLive {


    String BASE_URL = "http://iptv-line.com:6969/";

    @GET("player_api.php?username=saumyamohan831&password=BeNHLLIL&action=get_live_categories")
   // Call<List<Live>> getSeries();
    Call<String> getString();
}
