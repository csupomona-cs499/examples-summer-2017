package edu.cpp.l09_local_storage.data;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by yusun on 7/10/17.
 */

@Table(name = "course")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {

    private String id;
    @Column(name = "courseName")
    private String courseName;
    @Column(name = "courseId")
    private String courseId;
    @Column(name = "courseCredit")
    private int courseCredit;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String toString() {
        return id + ", " + courseName + ", " + courseId + ", " + courseCredit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
