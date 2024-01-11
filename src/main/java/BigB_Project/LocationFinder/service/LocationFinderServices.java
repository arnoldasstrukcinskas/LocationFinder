package BigB_Project.LocationFinder.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class LocationFinderServices {

    @Value("${api.key}")
    private String apiKey;

    HttpClient client = HttpClient.newHttpClient();

    public String startPointCoordinates(String startPoint) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        String apiUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/";

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl + apiParameters(startPoint)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.body();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode data = objectMapper.readTree(jsonResponse);

        JsonNode geometry = data.at("/features/0/geometry");
        JsonNode coordinates = geometry.at("/coordinates");

            double latitude = coordinates.get(1).asDouble();
            double longitude = coordinates.get(0).asDouble();

        return longitude + "," + latitude + ";";
    }

    public String endPointCoordinates(String endPoint) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        String apiUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/";

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl + apiParameters(endPoint)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.body();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode data = objectMapper.readTree(jsonResponse);

        JsonNode geometry = data.at("/features/0/geometry");
        JsonNode coordinates = geometry.at("/coordinates");

        double latitude = coordinates.get(1).asDouble();
        double longitude = coordinates.get(0).asDouble();

        return longitude + "," + latitude + "?";
    }

    public String apiParameters(String parameters){
        StringBuilder urlAddParams = new StringBuilder();

        ////sukuriu stringa taip, kad ivestas adresas gautusi taip: -> ......mapbox.places/kaunas%20elniaragiu%20takas%207.json?prox.....
        String urlParameters = parameters.replace(" ", "%20");
        urlAddParams.append(urlParameters);
        urlAddParams.append(".json?");
        urlAddParams.append("access_token=");
        urlAddParams.append(apiKey);
        return urlAddParams.toString();
    }

    public String getDirections(String startPoint, String endPoint) throws IOException, InterruptedException {

        String startCoordinates = startPointCoordinates(startPoint);
        String endCoordinates = endPointCoordinates(endPoint);

        HttpClient client = HttpClient.newHttpClient();

        String apiUrl = "https://api.mapbox.com/directions/v5/mapbox/cycling/";

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl + startCoordinates + endCoordinates + "geometries=geojson&access_token=" + apiKey))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.body();

        return jsonResponse;
    }
//    Pvz: https://api.mapbox.com/directions/v5/mapbox/cycling/-84.518641,39.134270;-84.512023,39.102779?geometries=geojson&access_token=pk.eyJ1IjoiYXJub2xkYXNzdHJ1a2NpbnNrYXMiLCJhIjoiY2xuNGVtbTRpMHZmcTJqbDd4ZW90Z2k4bSJ9.91LzqGuR4nmhfnrpxsX5Qw
}
