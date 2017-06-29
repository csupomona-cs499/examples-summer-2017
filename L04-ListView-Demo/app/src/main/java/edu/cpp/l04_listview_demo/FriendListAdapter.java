package edu.cpp.l04_listview_demo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.cpp.l04_listview_demo.data.Friend;

/**
 * Created by yusun on 6/28/17.
 */

public class FriendListAdapter extends ArrayAdapter<Friend> {

    private Context context;
    private List<Friend> friends;

    public FriendListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Friend> objects) {
        super(context, resource, objects);
        this.context = context;
        this.friends = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_item_friend, parent, false);

        ImageView profileImageView = (ImageView) view.findViewById(R.id.profileImageView);
        profileImageView.setImageResource(friends.get(position).getProfileImageRes());

        TextView nameTextView = (TextView) view.findViewById(R.id.friendNameTextView);
        nameTextView.setText(friends.get(position).getName());

        TextView numTextView = (TextView) view.findViewById(R.id.numTextView);
        numTextView.setText(friends.get(position).getNumMutualFriends() + " mutual friends");

        Button addFriendButton = (Button) view.findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friends.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
