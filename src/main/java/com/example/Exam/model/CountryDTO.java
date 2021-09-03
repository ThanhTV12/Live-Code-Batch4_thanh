package com.example.Exam.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CountryDTO {

    private String countryName;
    private List<StateDTO> states = new ArrayList<>();

    @Data
    public static class StateDTO {
        private String stateName;
        List<CityDTO> cities = new ArrayList<>();

        public StateDTO(String stateName) {
            this.stateName = stateName;
        }
    }

    @Data
    public static class CityDTO {
        String name;

        public CityDTO(String name) {
            this.name = name;
        }
    }
}
