package edu.cpp.l02_ui_widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by yusun on 6/26/17.
 */

public class MiniWebActivity extends AppCompatActivity {

    private EditText urlEditText;
    private Button goButton;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_browser);

        urlEditText = (EditText) findViewById(R.id.urlEditText);
        goButton = (Button) findViewById(R.id.goButton);
        webView = (WebView) findViewById(R.id.webView);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlEditText.getText().toString();
                webView.loadUrl(url);
            }
        });
    }
}
