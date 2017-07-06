package edu.cpp.l08_messager_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.l08_messager_app.data.MessagerUser;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @BindView(R.id.emailSignupEditText)
    EditText emailSignupEditText;
    @BindView(R.id.passwordSingupEditText)
    EditText passwordSignupEditText;

    @BindView(R.id.nameSingupEditText)
    EditText nameSignupEditText;
    @BindView(R.id.usernameSingupEditText)
    EditText usernameSignupEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @OnClick(R.id.createSingupButton)
    public void createClicked() {
        Log.i("TEST", "Create oncliked!");
        String email = emailSignupEditText.getText().toString();
        String password = passwordSignupEditText.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.i("TEST", "User: " + user.getUid());
                            MessagerUser messagerUser = new MessagerUser();
                            messagerUser.setName(nameSignupEditText.getText().toString());
                            messagerUser.setUsername(usernameSignupEditText.getText().toString());
                            messagerUser.setEmail(user.getEmail());
                            messagerUser.setUid(user.getUid());

                            DatabaseReference reference = firebaseDatabase.getReference();
                            reference.child("users/" + user.getUid()).setValue(messagerUser);

                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.e("TEST", "Failed to create the user.", task.getException());
                        }
                    }
                });
    }

}
