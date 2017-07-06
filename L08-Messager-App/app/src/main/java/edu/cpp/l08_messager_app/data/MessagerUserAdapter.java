package edu.cpp.l08_messager_app.data;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.cpp.l08_messager_app.R;

/**
 * Created by yusun on 7/5/17.
 */

public class MessagerUserAdapter extends ArrayAdapter<MessagerUser> {

    private Context context;
    private List<MessagerUser> userList;

    public MessagerUserAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MessagerUser> objects) {
        super(context, resource, objects);
        this.context = context;
        this.userList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_item_user, parent, false);
        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        nameTextView.setText(userList.get(position).getName());
        return view;
    }
}
