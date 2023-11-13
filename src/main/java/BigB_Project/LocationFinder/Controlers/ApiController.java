package BigB_Project.LocationFinder.Controlers;

import BigB_Project.LocationFinder.Services.LocationFinderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping(value = "/geocoding")
public class ApiController {

    @Value("${api.key}")
    private String apiKey;

//    http://localhost:8080/geocoding/map
    @GetMapping("/map")
    public String mapPage(Model model) {
        model.addAttribute("apiKey", apiKey);
        return "mapviewoption"; // Mano sprendimo būdas, kaip dar panaudoti directions API
//        return "mapview";
    }

    @Autowired
    LocationFinderServices locationFinderServices;

    // http://localhost:8080/geocoding/home
    @GetMapping(path = "/home")
    public String displayHomePage(){
        return "home";
    }

    // http://localhost:8080/geocoding/coordinates
    @RequestMapping(value = "/coordinates", method = RequestMethod.GET)
    public String getCoordinates(Model model,
                                 @RequestParam(value = "startPoint", required = true) String startPoint,
                                 @RequestParam(value = "endPoint", required = true) String endPoint) throws IOException, InterruptedException {
            model.addAttribute("startPointModel", locationFinderServices.startPointCoordinates(startPoint));
            model.addAttribute("endPointModel", locationFinderServices.endPointCoordinates(endPoint));
            model.addAttribute("directions", locationFinderServices.getDirections(startPoint, endPoint));
        return "result";
    }


}