package edu.cpp.l09_local_storage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.l09_local_storage.data.Course;
import io.paperdb.Paper;

public class MainActivityWithPaper extends AppCompatActivity {


    @BindView(R.id.courseNameEditText)
    EditText courseNameEditText;
    @BindView(R.id.courseIdEditText)
    EditText courseIdEditText;
    @BindView(R.id.courseCreditEditText)
    EditText courseCreditEditText;
    @BindView(R.id.courseListView)
    ListView courseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        loadDataLocally();
    }

    private void loadDataLocally() {
        List<Course> courseList = Paper.book().read("newCourseList", new ArrayList<Course>());
        ArrayAdapter<Course> courseAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(courseAdapter);
    }

    @OnClick(R.id.addButton)
    public void onAddClicked() {
        Course c = new Course();
        c.setCourseName(courseNameEditText.getText().toString());
        c.setCourseId(courseIdEditText.getText().toString());
        c.setCourseCredit(Integer.parseInt(courseCreditEditText.getText().toString()));

        List<Course> courseList = Paper.book().read("newCourseList", new ArrayList<Course>());
        courseList.add(c);
        Paper.book().write("newCourseList", courseList);

        loadDataLocally();
    }


}
