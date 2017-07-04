package edu.cpp.l06_flickr_demo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by yusun on 7/3/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {

    private String id;
    private String title;
    private String url_s;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_s() {
        return url_s;
    }

    public void setUrl_s(String url_s) {
        this.url_s = url_s;
    }
}
