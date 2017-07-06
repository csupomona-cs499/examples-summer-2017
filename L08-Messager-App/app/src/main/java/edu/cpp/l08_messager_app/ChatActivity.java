package edu.cpp.l08_messager_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.l08_messager_app.data.MessageRecord;
import edu.cpp.l08_messager_app.data.MessageRecordAdapter;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.chatHistoryListView)
    ListView chatHistoryListView;
    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.messageEditText)
    EditText messageEditText;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private String myUid;
    private String recipientUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        myUid = firebaseAuth.getCurrentUser().getUid();

        recipientUid = getIntent().getStringExtra("RUid");

        firebaseDatabase.getReference(
                "chathistory/" + getSortedIdCombination(myUid, recipientUid))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<MessageRecord> messageRecords = new ArrayList<MessageRecord>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            MessageRecord mr = ds.getValue(MessageRecord.class);
                            messageRecords.add(mr);
                        }
                        MessageRecordAdapter messageRecordAdapter = new MessageRecordAdapter(
                                ChatActivity.this, R.layout.listview_item_message_record, messageRecords, myUid);
                        chatHistoryListView.setAdapter(messageRecordAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @OnClick(R.id.sendButton)
    public void sendClicked() {
        String message = messageEditText.getText().toString();
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setText(message);
        messageRecord.setSenderUid(myUid);
        messageRecord.setTimestamp(System.currentTimeMillis());

        firebaseDatabase.getReference(
                getChatHistoryReferenceId(messageRecord))
                .setValue(messageRecord);
    }

    private String getChatHistoryReferenceId(MessageRecord messageRecord) {
        return "chathistory/"
                + getSortedIdCombination(myUid, recipientUid)
                + "/" + messageRecord.getTimestamp();
    }

    private static String getSortedIdCombination(String id1, String id2) {
        if (id1.compareTo(id2) > 0)  {
            return id2 + id1;
        } else {
            return id1 + id2;
        }
    }

}
