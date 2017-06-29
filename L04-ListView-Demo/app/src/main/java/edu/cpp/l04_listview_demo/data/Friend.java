package edu.cpp.l04_listview_demo.data;

/**
 * Created by yusun on 6/28/17.
 */

public class Friend {

    private String name;
    private int numMutualFriends;
    private int profileImageRes;

    public Friend() {
    }

    public Friend(String name, int numMutualFriends, int profileImageRes) {
        this.name = name;
        this.numMutualFriends = numMutualFriends;
        this.profileImageRes = profileImageRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumMutualFriends() {
        return numMutualFriends;
    }

    public void setNumMutualFriends(int numMutualFriends) {
        this.numMutualFriends = numMutualFriends;
    }

    public int getProfileImageRes() {
        return profileImageRes;
    }

    public void setProfileImageRes(int profileImageRes) {
        this.profileImageRes = profileImageRes;
    }
}
