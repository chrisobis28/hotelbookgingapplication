package com.bookingsiemens.BookingSiemens.Services;

import com.bookingsiemens.BookingSiemens.Commons.Location;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocationService {

    private final String API_KEY = "your_ipstack_api_key";
    private final String URL = "http://api.ipstack.com/";

    public Location getGeolocation(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = URL + ip + "?access_key=" + API_KEY;
        String jsonResponse = restTemplate.getForObject(requestUrl, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Location location = new Location();

        try {
            JsonNode rootNode = mapper.readTree(jsonResponse);
            location.setLatitude(rootNode.path("latitude").asDouble());
            location.setLongitude(rootNode.path("longitude").asDouble());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
      }
}