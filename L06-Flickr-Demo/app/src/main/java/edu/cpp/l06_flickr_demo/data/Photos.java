package edu.cpp.l06_flickr_demo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by yusun on 7/3/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photos {

    private List<Photo> photo;

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }
}
