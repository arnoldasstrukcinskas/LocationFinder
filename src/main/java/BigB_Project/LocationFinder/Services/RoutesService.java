package BigB_Project.LocationFinder.Services;

import BigB_Project.LocationFinder.repository.RoutesRepository;
import BigB_Project.LocationFinder.repository.model.Route;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutesService {

    @Autowired
    private RoutesRepository routesRepository;

    public Integer save(Route routes){
        return routesRepository.save(routes).getRouteId();
    }

    public List<Route> getAll(){
        return (List<Route>) routesRepository.findAll();
    }

    public String getLocationName(JsonNode jsonResponse){


        JsonNode properties = jsonResponse.at("/features/0/properties");
        JsonNode locationMapBoxName = properties.at("/name");
        JsonNode mapBoxId = properties.at("/mapbox_id");
        JsonNode getLocationFullAddress = properties.at("/full_address");

        String coordinatesId = mapBoxId.asText();
        String locationName = locationMapBoxName.asText();
        String locationFullAddress = getLocationFullAddress.asText();

        return locationName;
    }

    public String getLocationCoordinates(JsonNode jsonResponse){


        JsonNode cordinates = jsonResponse.at("/features/0/geometry/coordinates");

        double longitude = cordinates.get(0).asDouble();
        double latitude = cordinates.get(1).asDouble();

        return longitude + "," + latitude;
    }

}
