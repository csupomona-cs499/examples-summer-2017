package edu.cpp.l05_http_basics.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by yusun on 7/3/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private Coord coord;
    private List<WeatherDescription> weather;
    private String base;

    @JsonProperty(value = "main")
    private MainData mainData;

    private int visibility;
    private WindData wind;

    private CloudsData clouds;

//    private long dt;
//    private SysData sys;

    private int id;
    private String name;
    private int cod;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<WeatherDescription> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDescription> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public WindData getWind() {
        return wind;
    }

    public void setWind(WindData wind) {
        this.wind = wind;
    }

    public CloudsData getClouds() {
        return clouds;
    }

    public void setClouds(CloudsData clouds) {
        this.clouds = clouds;
    }

//    public long getDt() {
//        return dt;
//    }
//
//    public void setDt(long dt) {
//        this.dt = dt;
//    }
//
//    public SysData getSys() {
//        return sys;
//    }
//
//    public void setSys(SysData sys) {
//        this.sys = sys;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public MainData getMainData() {
        return mainData;
    }

    public void setMainData(MainData mainData) {
        this.mainData = mainData;
    }
}
