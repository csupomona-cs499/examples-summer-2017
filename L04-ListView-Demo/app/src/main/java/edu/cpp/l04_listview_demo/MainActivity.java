package edu.cpp.l04_listview_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.l04_listview_demo.data.Friend;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.generateFriendButton)
    Button generateFriendButton;
    @BindView(R.id.friendListView)
    ListView friendListView;

    private List<Friend> friendList;
    private FriendListAdapter friendListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        initFriendList();
        friendListAdapter = new FriendListAdapter(this, R.layout.listview_item_friend, friendList);
        friendListView.setAdapter(friendListAdapter);
    }

    @OnClick(R.id.generateFriendButton)
    public void onGenerateFriendButtonClick() {
        Friend friend = new Friend();
        friend.setName("New Friend");
        friend.setNumMutualFriends(new Random().nextInt(50));
        friend.setProfileImageRes(R.drawable.user_4_icon);
        friendList.add(friend);

        // this line notifies the changes and refresh the view
        friendListAdapter.notifyDataSetChanged();
    }

    private void initFriendList() {
        friendList = new ArrayList<>();
        friendList.add(new Friend("Jake", 22, R.drawable.user_1_icon));
        friendList.add(new Friend("Jessica", 13, R.drawable.user_2_icon));
        friendList.add(new Friend("Amy", 18, R.drawable.user_3_icon));
        friendList.add(new Friend("Lauren", 44, R.drawable.user_4_icon));
        friendList.add(new Friend("Garen", 8, R.drawable.user_5_icon));
        friendList.add(new Friend("Andrew", 6, R.drawable.user_6_icon));
    }
}
