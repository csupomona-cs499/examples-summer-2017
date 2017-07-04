package edu.cpp.l06_flickr_demo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by yusun on 7/3/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetRecentPhotosResponse {

    private Photos photos;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }
}
