package com.example.Exam.model;

import lombok.Data;

import java.util.List;

@Data
public class CountriesDataRS {
    public String status;
    public List<Country> data;
}

