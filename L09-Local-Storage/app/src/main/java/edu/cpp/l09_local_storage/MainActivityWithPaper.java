package edu.cpp.l09_local_storage;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.l09_local_storage.data.Course;
import edu.cpp.l09_local_storage.service.CourseService;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivityWithPaper extends AppCompatActivity {


    @BindView(R.id.courseNameEditText)
    EditText courseNameEditText;
    @BindView(R.id.courseIdEditText)
    EditText courseIdEditText;
    @BindView(R.id.courseCreditEditText)
    EditText courseCreditEditText;
    @BindView(R.id.courseListView)
    ListView courseListView;

    private CourseService courseService;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://02f34b31.ngrok.io")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        courseService = retrofit.create(CourseService.class);

        loadDataLocally();

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadDateRemotely();
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter("sync"));


//        scheduler.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pd = new ProgressDialog(MainActivityWithPaper.this);
//                        pd.setMessage("loading data...");
//                        pd.show();
//                    }
//                });
//
//                Log.i("TEST", "Executed!");
//                loadDateRemotely();
//            }
//        }, 1, 10, TimeUnit.SECONDS);
    }

    private void loadDateRemotely() {
        pd = new ProgressDialog(MainActivityWithPaper.this);
        pd.setMessage("loading data...");
        pd.show();
        Log.i("TEST", "Start loading");

        Call<List<Course>> call = courseService.getAllCourses();
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                Log.i("TEST", "Loaded courses!");
                if (response.isSuccessful()) {
                    List<Course> courseList = response.body();
                    ArrayAdapter<Course> courseAdapter = new ArrayAdapter<Course>(
                            MainActivityWithPaper.this, android.R.layout.simple_list_item_1, courseList);
                    courseListView.setAdapter(courseAdapter);

                    Paper.book().write("newCourseList", courseList);
                } else {
                    Log.e("TEST", "Failed to get the list of courses");
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.e("TEST", "Failed to get the list of courses", t);
                pd.dismiss();
            }
        });
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
