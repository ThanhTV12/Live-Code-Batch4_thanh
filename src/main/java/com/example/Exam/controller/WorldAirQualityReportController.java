package com.example.Exam.controller;

import com.example.Exam.endpoint.IqairClient;
import com.example.Exam.model.City;
import com.example.Exam.model.CountryDTO;
import com.example.Exam.model.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2")
public class WorldAirQualityReportController {


    @Autowired
    private IqairClient iqairClient;

    @RequestMapping(value = "/nearest_city", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<City> availSearchByGeoBox(@RequestParam String lat,
                                                  @RequestParam String lon) {
        System.out.println("lat : " + lat);
        City nearestCityBGeo = iqairClient.getNearestCityBGeo(Double.parseDouble(lat), Double.parseDouble(lon));
        if(nearestCityBGeo == null) {
            return new ResponseData<>("Error system");
        }
        return new ResponseData<>(nearestCityBGeo);
    }

    @RequestMapping(value = "/list/countries", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<List<CountryDTO>> listAllCountries() {
        List<CountryDTO> countryDTOS = iqairClient.listOfCountries();
        if(countryDTOS.isEmpty()) {
            return new ResponseData<>("Error system");
        }
        return new ResponseData<>(countryDTOS);
    }
}
