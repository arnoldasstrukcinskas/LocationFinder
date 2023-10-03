package BigB_Project.LocationFinder.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class LocationFinderServices {

    @Value("${api.key}")
    private String apiKey;

    HttpClient client = HttpClient.newHttpClient();

    public String connectToApi() throws IOException, InterruptedException {

        String location = "Vilnius";

        HttpClient client = HttpClient.newHttpClient();

        String apiUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/";

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl + apiParameters(location)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String jsonBody = response.body();

        return jsonBody;
    }

    public String apiParameters(String parameters){
        StringBuilder urlAddParams = new StringBuilder();
        String apiKey = "pk.eyJ1IjoiYXJub2xkYXNzdHJ1a2NpbnNrYXMiLCJhIjoiY2xuNGVtbTRpMHZmcTJqbDd4ZW90Z2k4bSJ9.91LzqGuR4nmhfnrpxsX5Qw";

        ////sukurk stringa taip, kad ivestas adresas gautusi taip: -> ......mapbox.places/kaunas%20elniaragiu%20takas%207.json?prox.....
        String urlParameters = parameters.replace(" ", "%20");
        urlAddParams.append(urlParameters);
        urlAddParams.append(".json?");
        urlAddParams.append("access_token=");
        urlAddParams.append(apiKey);
        return urlAddParams.toString();
    }
}
