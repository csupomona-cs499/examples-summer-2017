package edu.cpp.l07_firebase_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.cpp.l07_firebase_demo.data.Student;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.nameEditText)
    EditText nameEditText;
    @BindView(R.id.majorEditText)
    EditText majorEditText;
    @BindView(R.id.ageEditText)
    EditText ageEditText;

    @BindView(R.id.studentListView)
    ListView studentListView;

    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        loadStudents();
    }

    private void loadStudents() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("student");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("TEST", dataSnapshot.toString());

                students = new ArrayList<Student>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Student student = ds.getValue(Student.class);
                    students.add(student);
                }

                StudentListViewAdapter studentAdapter = new StudentListViewAdapter(MainActivity.this, R.layout.listview_item_student, students);
                studentListView.setAdapter(studentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.addButton)
    public void onAddClicked() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        Student student = new Student();
        student.setName(nameEditText.getText().toString());
        student.setAge(Integer.parseInt(ageEditText.getText().toString()));
        student.setMajor(majorEditText.getText().toString());

        DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("student").push().setValue(student);
    }
}
