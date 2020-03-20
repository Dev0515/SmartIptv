package com.example.smartiptv.POJO;





import com.example.smartiptv.model.Live;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiLiveinfo {

  //  http://api.themoviedb.org/3/movie/top_rated?api_key=INSERT_YOUR_API_KEY)
  String BASE_URL = "  http://iptv-line.com:6969/";

    @GET(" player_api.php?username=saumyamohan831&password=BeNHLLIL&action=get_live_streams")
   // Call<List<Live>> getSeries();
    Call<List<Live>> getSeries1(@Query("category_id") String apiKey);
  //Call<String> getString(@Query("category_id") String apiKey);
}