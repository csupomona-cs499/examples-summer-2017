package edu.cpp.l02_ui_widget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yusun on 6/26/17.
 */

public class LoginActivity extends AppCompatActivity {

    private TextView appNameTextView;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView errorMessageTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appNameTextView = (TextView) findViewById(R.id.appNameTextView);
        appNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "TextView has been clicked.", Toast.LENGTH_SHORT).show();
                appNameTextView.setTextColor(Color.BLUE);
            }
        });

        errorMessageTextView = (TextView) findViewById(R.id.errorMessageTextView);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("TEST", "beforeTextChanged: " + charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("TEST", "onTextChanged: " + charSequence);
                String str1 = charSequence.toString().toLowerCase();
                if (str1.equals(charSequence.toString())) {
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    errorMessageTextView.setText("Please use uppercase in your password.");
                } else {
                    errorMessageTextView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("TEST", "afterTextChanged: " + editable.toString());
            }
        });

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorMessageTextView.setVisibility(View.INVISIBLE);

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessageTextView.setVisibility(View.VISIBLE);
                } else {
                    // go to home screen
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("username", emailEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
