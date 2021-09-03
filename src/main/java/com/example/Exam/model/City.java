package com.example.Exam.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class City {

    private String city;
    private String state;
    private String country;
    private Location location;
    private Current current;

    @Data
    public static class Location{
        private String type;
        private List<Double> coordinates;
    }

    @Data
    public static class Weather{
        private Date ts;
        private int tp;
        private int pr;
        private int hu;
        private double ws;
        private int wd;
        private String ic;
    }

    @Data
    public static class Pollution{
        private Date ts;
        private int aqius;
        private String mainus;
        private int aqicn;
        private String maincn;
    }

    @Data
    public static class Current{
        private Weather weather;
        private Pollution pollution;
    }


}
