package edu.cpp.l09_local_storage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.l09_local_storage.data.Course;

public class MainActivityWithSharedPreferences extends AppCompatActivity {


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
        ObjectMapper objectMapper = new ObjectMapper();
        SharedPreferences sharedPreferences = getSharedPreferences("course", MODE_PRIVATE);
        String courseListStr = sharedPreferences.getString("courseList", null);
        List<Course> courseList = null;
        try {
            if (courseListStr == null) {
                courseList = Collections.emptyList();
            } else {
                courseList = objectMapper.readValue(courseListStr, new TypeReference<List<Course>>() {});
                ArrayAdapter<Course> courseAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, courseList);
                courseListView.setAdapter(courseAdapter);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.addButton)
    public void onAddClicked() {
        Course c = new Course();
        c.setCourseName(courseNameEditText.getText().toString());
        c.setCourseId(courseIdEditText.getText().toString());
        c.setCourseCredit(Integer.parseInt(courseCreditEditText.getText().toString()));

        ObjectMapper objectMapper = new ObjectMapper();

        SharedPreferences sharedPreferences = getSharedPreferences("course", MODE_PRIVATE);
        String courseListStr = sharedPreferences.getString("courseList", null);

        List<Course> courseList = null;
        try {
            if (courseListStr == null) {
                courseList = new ArrayList<>();
            } else {
                courseList = objectMapper.readValue(courseListStr, new TypeReference<List<Course>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        courseList.add(c);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putString("courseList", objectMapper.writeValueAsString(courseList));
            editor.commit();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        loadDataLocally();
    }


}
