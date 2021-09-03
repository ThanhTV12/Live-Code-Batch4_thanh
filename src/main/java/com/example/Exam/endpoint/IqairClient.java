package com.example.Exam.endpoint;

import com.example.Exam.InvokerService;
import com.example.Exam.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class IqairClient {


    private static final String HOST = "http://api.airvisual.com";
    private static final String NEAREST_CITY = "/v2/nearest_city";
    private static final String COUNTRIES = "/v2/countries";
    private static final String STATES = "/v2/states"; //?country={{COUNTRY_NAME}}&key={{YOUR_API_KEY}}

    //v2/cities?state={{STATE_NAME}}&country={{COUNTRY_NAME}}&key={{YOUR_API_KEY}}
    private static final String CITIES = "/v2/cities";

    private static final String KEY = "7c90744e-de78-4997-a289-3a340528db41";

    public City getNearestCityBGeo(double lat,  double lon) {
        log.debug("getNearestCityBGeo");
        String url = HOST + NEAREST_CITY + "?lat=" + lat + "&lon=" + lon +"&key=" + KEY;
        Optional<NearestCityBGeoRS> optional = InvokerService.get(url, getHeaders(), NearestCityBGeoRS.class);
        if(optional.isPresent()) {
            NearestCityBGeoRS rs = optional.get();
            if("success".equals(rs.getStatus())) {
                return rs.getData();
            }
        }
        return null;
    }

    public List<CountryDTO> listOfCountries() {
        List<CountryDTO> result = new ArrayList<>();
        List<Country> allCountries = getAllCountries();
        if(allCountries.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        allCountries.parallelStream().forEach(c -> {
            CountryDTO countryDTO = new CountryDTO();
            List<State> states = getAllStates(c.getCountry());
            countryDTO.setCountryName(c.getCountry());
            for(State state : states) {
                countryDTO.getStates().add(new CountryDTO.StateDTO(state.getState()));
            }
            result.add(countryDTO);
        });

        result.parallelStream().forEach(c -> {
            List<CountryDTO.StateDTO> states = c.getStates();
            states.parallelStream().forEach(s -> {
                List<CityRS> cityRSs = getAllCityByState(s.getStateName(), c.getCountryName());
                for(CityRS cityRS : cityRSs) {
                    s.getCities().add(new CountryDTO.CityDTO(cityRS.getCity()));
                }
            });
        });

        return result;
    }

    public List<Country> getAllCountries() {
        log.debug("getNearestCityBGeo");
        String url = HOST + COUNTRIES + "?&key=" + KEY;
        Optional<CountriesDataRS> optional = InvokerService.get(url, getHeaders(), CountriesDataRS.class);
        if(optional.isPresent()) {
            CountriesDataRS rs = optional.get();
            if("success".equals(rs.getStatus())) {
                return rs.getData();
            }
        }
        return Collections.EMPTY_LIST;
    }


    public List<State> getAllStates(String countryName) {
        log.debug("getNearestCityBGeo");
        //?country={{COUNTRY_NAME}}&key={{YOUR_API_KEY}}
        String url = HOST + STATES + "?country=" + countryName + "&key=" + KEY;
        Optional<StateDataRS> optional = InvokerService.get(url, getHeaders(), StateDataRS.class);
        if(optional.isPresent()) {
            StateDataRS rs = optional.get();
            if("success".equals(rs.getStatus())) {
                return rs.getData();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public List<CityRS> getAllCityByState(String stateName, String countryName) {
        log.debug("getNearestCityBGeo");
        //?country={{COUNTRY_NAME}}&key={{YOUR_API_KEY}}
        String url = HOST + CITIES + "?state=" + stateName + "&country=" + countryName + "&key=" + KEY;
        Optional<CitiesDataRS> optional = InvokerService.get(url, getHeaders(), CitiesDataRS.class);
        if(optional.isPresent()) {
            CitiesDataRS rs = optional.get();
            if("success".equals(rs.getStatus())) {
                return rs.getData();
            }
        }
        return Collections.EMPTY_LIST;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        return headers;
    }
}
