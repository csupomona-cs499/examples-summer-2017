package edu.cpp.l08_messager_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.cpp.l08_messager_app.data.MessagerUser;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @BindView(R.id.emailProfileEditText)
    EditText emailProfileEditText;
    @BindView(R.id.nameProfileEditText)
    EditText nameProfileEditText;
    @BindView(R.id.usernameProfileEditText)
    EditText usernameProfileEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        String uid = getIntent().getStringExtra("uid");
        firebaseDatabase.getReference("users/" + uid)
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MessagerUser messagerUser = dataSnapshot.getValue(MessagerUser.class);
                    emailProfileEditText.setText(messagerUser.getEmail());
                    nameProfileEditText.setText(messagerUser.getName());
                    usernameProfileEditText.setText(messagerUser.getUsername());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}
