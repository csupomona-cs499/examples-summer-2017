package edu.cpp.l06_flickr_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.cpp.l06_flickr_demo.data.GetRecentPhotosResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.gridview)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        FlickrAPIService flickrAPIService = retrofit.create(FlickrAPIService.class);
        Call<GetRecentPhotosResponse> getRecentPhotosResponseCall =
                flickrAPIService.getRecentPhotos("633ee7a944094df21b915c681c1788ea", "url_s", "json", "1");

        getRecentPhotosResponseCall.enqueue(new Callback<GetRecentPhotosResponse>() {
            @Override
            public void onResponse(Call<GetRecentPhotosResponse> call, Response<GetRecentPhotosResponse> response) {
                Log.i("TEST", "Get recent photos: " + response.body().getPhotos().getPhoto().size());
                PhotosAdapter photosAdapter = new PhotosAdapter(MainActivity.this, response.body().getPhotos().getPhoto());
                gridView.setAdapter(photosAdapter);
            }

            @Override
            public void onFailure(Call<GetRecentPhotosResponse> call, Throwable t) {
                Log.e("TEST", "Failed to get recent photos.");
            }
        });
    }
}
