package edu.cpp.l05_http_basics;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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
//        progress = ProgressDialog.show(this, "Loading the URL",
//                "Please wait ...", true);
        sendButton.setEnabled(false);


        AsyncTask<Void, Void, Void> loadURLTask = new AsyncTask<Void, Void, Void>() {

            private String totalStr;


            @Override
            protected Void doInBackground(Void... voids) {
                Log.i("TEST", "start background work");
                URL url = null;
                try {
                    url = new URL("http://www.google.com/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String line;
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    while ((line = r.readLine()) != null) {
                        total.append(line).append('\n');
                    }
                    totalStr = total.toString();
                    Log.i("TEST", "total: " + totalStr);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                responseTextView.setText(totalStr);
                //progress.dismiss();
                sendButton.setEnabled(true);
            }
        };

        loadURLTask.execute();
    }
}
