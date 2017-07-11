package edu.cpp.l09_local_storage.data;

import android.provider.BaseColumns;

/**
 * Created by yusun on 7/10/17.
 */

public class CourseContract {

    private CourseContract() {

    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CourseEntry.TABLE_NAME + " (" +
                    CourseEntry._ID + " INTEGER PRIMARY KEY," +
                    CourseEntry.COLUMN_NAME_COURSENAME + " TEXT," +
                    CourseEntry.COLUMN_NAME_COURSEID + " TEXT," +
                    CourseEntry.COLUMN_NAME_COURSECREDIT + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CourseEntry.TABLE_NAME;

    public static class CourseEntry implements BaseColumns {
        public static final String TABLE_NAME = "course";
        public static final String COLUMN_NAME_COURSENAME = "courseName";
        public static final String COLUMN_NAME_COURSEID = "courseId";
        public static final String COLUMN_NAME_COURSECREDIT = "courseCredit";
    }

}
