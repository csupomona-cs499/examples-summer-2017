package edu.cpp.l06_flickr_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.cpp.l06_flickr_demo.data.Photo;

/**
 * Created by yusun on 7/3/17.
 */

public class PhotosAdapter extends BaseAdapter {

    private Context context;
    private List<Photo> photos;

    public PhotosAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int i) {
        return photos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.gridview_item, viewGroup, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(context).load(photos.get(i).getUrl_s()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, photos.get(i).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
