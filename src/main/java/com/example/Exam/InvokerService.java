package com.example.Exam;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


public class InvokerService {
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String RETRY = "RETRY";

    private static RestTemplate restTemplate = new RestTemplate();


    public static  <T> Optional<T>  get(String url, HttpHeaders headers, Class<T> responseType){
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String responseStr;
        long start = System.currentTimeMillis();
        try {
            responseStr = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
            Optional res = Optional.ofNullable(JsonUtil.convert(responseStr, responseType));
            return res;
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static <T> Optional<T> postHeader(String url, HttpEntity<Object> header, Class<T> responseType){
        String responseStr;
        long start = System.currentTimeMillis();
        try {
            responseStr = restTemplate.exchange(url, HttpMethod.POST, header, String.class).getBody();
            Optional res = Optional.ofNullable(JsonUtil.convert(responseStr, responseType));
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
