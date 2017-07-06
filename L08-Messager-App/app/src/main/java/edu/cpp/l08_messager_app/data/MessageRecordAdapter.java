package edu.cpp.l08_messager_app.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.cpp.l08_messager_app.R;
import edu.cpp.l08_messager_app.UserProfileActivity;

/**
 * Created by yusun on 7/5/17.
 */

public class MessageRecordAdapter extends ArrayAdapter<MessageRecord> {

    private Context context;
    private List<MessageRecord> messageRecords;
    private String myUid;

    public MessageRecordAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MessageRecord> objects, String myUid) {
        super(context, resource, objects);
        this.context = context;
        this.messageRecords = objects;
        this.myUid = myUid;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (messageRecords.get(position).getSenderUid().equals(myUid)) {
            view = layoutInflater.inflate(R.layout.listview_item_message_record, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.listview_item_message_record_rec, parent, false);
        }
        TextView messageTextView = (TextView) view.findViewById(R.id.messageTextView);
        messageTextView.setText(messageRecords.get(position).getText());
        TextView timeDateTextView = (TextView) view.findViewById(R.id.timeDateTextView);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(messageRecords.get(position).getTimestamp());
        timeDateTextView.setText(sf.format(date));

        ImageView userImageView = (ImageView) view.findViewById(R.id.iconImageView);
        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("uid", messageRecords.get(position).getSenderUid());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
