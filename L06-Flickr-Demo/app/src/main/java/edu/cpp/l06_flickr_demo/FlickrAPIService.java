package edu.cpp.l06_flickr_demo;

import edu.cpp.l06_flickr_demo.data.GetRecentPhotosResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yusun on 7/3/17.
 */

public interface FlickrAPIService {

    @GET("/services/rest/?method=flickr.photos.getRecent")
    public Call<GetRecentPhotosResponse> getRecentPhotos(
            @Query("api_key") String apiKey,
            @Query("extras") String extras,
            @Query("format") String format,
            @Query("nojsoncallback") String nojsoncallback
    );
}
