package edu.cpp.l09_local_storage.service;

import java.util.List;

import edu.cpp.l09_local_storage.data.Course;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by yusun on 7/12/17.
 */

public interface CourseService {

    @GET("/courses")
    public Call<List<Course>> getAllCourses();
}
