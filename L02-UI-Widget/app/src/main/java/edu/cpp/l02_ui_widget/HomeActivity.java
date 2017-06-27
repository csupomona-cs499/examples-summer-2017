package edu.cpp.l02_ui_widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yusun on 6/26/17.
 */

public class HomeActivity extends AppCompatActivity {

    private TextView currentValueTextView;
    private SeekBar seekBar;
    private RatingBar ratingBar;

    private TextView emailTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        emailTextView = (TextView) findViewById(R.id.emailTextView);

        String email = getIntent().getStringExtra("username");
        emailTextView.setText(email);

        currentValueTextView = (TextView) findViewById(R.id.currentValueTextView);
        seekBar = (SeekBar) findViewById(R.id.testSeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentValueTextView.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(
                        HomeActivity.this,
                        "Thank you for your feedback! (" + v + ")",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
