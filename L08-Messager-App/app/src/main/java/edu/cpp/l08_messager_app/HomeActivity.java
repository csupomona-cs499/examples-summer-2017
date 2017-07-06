package edu.cpp.l08_messager_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.cpp.l08_messager_app.data.MessagerUser;
import edu.cpp.l08_messager_app.data.MessagerUserAdapter;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.friendsListView)
    ListView friendsListView;

    private FirebaseDatabase firebaseDatabase;
    private MessagerUserAdapter messagerUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<MessagerUser> userList = new ArrayList<MessagerUser>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    MessagerUser messagerUser = ds.getValue(MessagerUser.class);
                    userList.add(messagerUser);
                }
                messagerUserAdapter = new MessagerUserAdapter(
                        HomeActivity.this, R.layout.listview_item_user, userList);
                friendsListView.setAdapter(messagerUserAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Failed to load all the users", Toast.LENGTH_SHORT).show();
            }
        });

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String recipientUid = messagerUserAdapter.getItem(i).getUid();
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                intent.putExtra("RUid", recipientUid);
                startActivity(intent);
            }
        });
    }

}
