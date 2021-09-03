package com.example.Exam.model;

import lombok.Data;

import java.util.List;

@Data
public class CitiesDataRS {
    public String status;
    public List<CityRS> data;
}
