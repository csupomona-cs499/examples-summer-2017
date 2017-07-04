package edu.cpp.l05_http_basics;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.l05_http_basics.data.WeatherResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivityWithRetrofit extends AppCompatActivity {

    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.responseTextView)
    TextView responseTextView;

    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.sendButton)
    public void onSend() {
        Log.i("TEST", "onSend");
        sendButton.setEnabled(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://samples.openweathermap.org")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherResponse> weatherResponseCall = weatherService.getWeatherData();
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                responseTextView.setText(response.body().getWind().getSpeed() + " : " + response.body().getWind().getDeg());
                sendButton.setEnabled(true);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("TEST", "Failed to get the response");
            }
        });
    }
}
