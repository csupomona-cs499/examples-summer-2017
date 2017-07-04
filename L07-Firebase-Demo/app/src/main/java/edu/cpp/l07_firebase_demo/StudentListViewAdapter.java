package edu.cpp.l07_firebase_demo;

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

import edu.cpp.l07_firebase_demo.data.Student;

/**
 * Created by yusun on 7/3/17.
 */

public class StudentListViewAdapter extends ArrayAdapter<Student> {

    private Context context;
    private List<Student> students;

    public StudentListViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.students = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_item_student, parent, false);

        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        nameTextView.setText(students.get(position).getName());
        TextView ageTextView = (TextView) view.findViewById(R.id.ageTextView);
        ageTextView.setText(students.get(position).getAge() + "");
        TextView majorTextView = (TextView) view.findViewById(R.id.majorTextView);
        majorTextView.setText(students.get(position).getMajor());

        return view;
    }
}
