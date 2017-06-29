package edu.cpp.l03_adapter_views;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.cpp.l03_adapter_views.model.PhoneCompatibility;

/**
 * Created by yusun on 6/28/17.
 */

public class PhoneModelDataAdapter extends ArrayAdapter<PhoneCompatibility> {

    private Context context;
    private List<PhoneCompatibility> data;

    public PhoneModelDataAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PhoneCompatibility> objects) {
        super(context, resource, objects);

        this.context = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_item_phone_model, parent, false);

        ImageView iconImageView = (ImageView) view.findViewById(R.id.compatibilityImageView);
        if (data.get(position).isCompatible()) {
            iconImageView.setImageResource(R.drawable.yes_icon);
        } else {
            iconImageView.setImageResource(R.drawable.no_icon);
        }
        TextView phoneModelTextView = (TextView) view.findViewById(R.id.phoneModelTextView);
        phoneModelTextView.setText(data.get(position).getPhoneModel());

        return view;
    }
}
