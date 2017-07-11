package edu.cpp.l09_local_storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.l09_local_storage.data.Course;
import edu.cpp.l09_local_storage.data.CourseContract;
import edu.cpp.l09_local_storage.data.CourseDBHelper;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.courseNameEditText)
    EditText courseNameEditText;
    @BindView(R.id.courseIdEditText)
    EditText courseIdEditText;
    @BindView(R.id.courseCreditEditText)
    EditText courseCreditEditText;
    @BindView(R.id.courseListView)
    ListView courseListView;

    private CourseDBHelper courseDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        courseDBHelper = new CourseDBHelper(this);
        loadDataLocally();
    }

    private void loadDataLocally() {

        SQLiteDatabase db = courseDBHelper.getReadableDatabase();

        String[] projection = {
                CourseContract.CourseEntry._ID,
                CourseContract.CourseEntry.COLUMN_NAME_COURSENAME,
                CourseContract.CourseEntry.COLUMN_NAME_COURSEID,
                CourseContract.CourseEntry.COLUMN_NAME_COURSECREDIT
        };


        String selection = CourseContract.CourseEntry.COLUMN_NAME_COURSECREDIT + " > ?";
        String[] selectionArgs = { "1" };

//        String sortOrder =
//                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                CourseContract.CourseEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        List courses = new ArrayList<Course>();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CourseContract.CourseEntry._ID));
            String courseName = cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseContract.CourseEntry.COLUMN_NAME_COURSENAME));
            String courseId = cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseContract.CourseEntry.COLUMN_NAME_COURSEID));
            int credit = cursor.getInt(
                    cursor.getColumnIndexOrThrow(CourseContract.CourseEntry.COLUMN_NAME_COURSECREDIT));

            Course c = new Course();
            //c.setId(id);
            c.setCourseName(courseName);
            c.setCourseId(courseId);
            c.setCourseCredit(credit);
            courses.add(c);
        }
        cursor.close();

        ArrayAdapter<Course> courseAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, courses);
        courseListView.setAdapter(courseAdapter);
    }

    @OnClick(R.id.addButton)
    public void onAddClicked() {
        // Gets the data repository in write mode
        SQLiteDatabase db = courseDBHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(CourseContract.CourseEntry.COLUMN_NAME_COURSENAME, courseNameEditText.getText().toString());
        values.put(CourseContract.CourseEntry.COLUMN_NAME_COURSEID, courseIdEditText.getText().toString());
        values.put(CourseContract.CourseEntry.COLUMN_NAME_COURSECREDIT, courseCreditEditText.getText().toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(CourseContract.CourseEntry.TABLE_NAME, null, values);
        Log.i("TEST", "New row id: " + newRowId);

        loadDataLocally();
    }
}
