package edu.cpp.l08_messager_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @BindView(R.id.chatImageView)
    ImageView chatImageView;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private String myUid;
    private String recipientUid;
    private String mCurrentPhotoPath;


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

    @OnClick(R.id.takePhotoAndSendButton)
    public void takePhotoAndSend() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "edu.cpp.l08_messager_app.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            messageEditText.setVisibility(View.GONE);
//            chatImageView.setVisibility(View.VISIBLE);
//            chatImageView.setImageBitmap(imageBitmap);
            File imageFile = new File(mCurrentPhotoPath);
            Log.i("TEST", "file size: " + imageFile.length());
            messageEditText.setVisibility(View.GONE);
            chatImageView.setVisibility(View.VISIBLE);
            Picasso.with(this).load(imageFile).into(chatImageView);

            int size = (int) imageFile.length();
            byte[] bytes = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(imageFile));
                buf.read(bytes, 0, bytes.length);
                buf.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference reference = firebaseStorage.getReference("chat/" + imageFile.getName());
            UploadTask uploadTask = reference.putBytes(bytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle unsuccessful uploads
                    Log.e("TEST", "Failed to upload the image.", e);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.i("TEST", "Success. URL: " + downloadUrl);

                    MessageRecord messageRecord = new MessageRecord();
                    //messageRecord.setText(downloadUrl.toString());
                    messageRecord.setSenderUid(myUid);
                    messageRecord.setTimestamp(System.currentTimeMillis());
                    messageRecord.setImageUrl(downloadUrl.toString());

                    firebaseDatabase.getReference(
                            getChatHistoryReferenceId(messageRecord))
                            .setValue(messageRecord);

                    messageEditText.setVisibility(View.VISIBLE);
                    chatImageView.setVisibility(View.GONE);
                }
            });

            //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);



        }
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
