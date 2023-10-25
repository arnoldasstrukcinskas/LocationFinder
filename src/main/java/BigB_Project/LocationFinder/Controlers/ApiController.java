package BigB_Project.LocationFinder.Controlers;

import BigB_Project.LocationFinder.Services.LocationFinderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping(value = "/geocoding")
public class ApiController {

    @Autowired
    LocationFinderServices locationFinderServices;

    // http://localhost:8080/geocoding/home
    @GetMapping(path = "/home")
    public String displayHomePage(){
        return "home";
    }

    // http://localhost:8080/geocoding/coordinates
    @GetMapping(value = "/coordinates")
    public String getCoordinates(Model model) throws IOException, InterruptedException {

        String coordinates = locationFinderServices.connectToApi();
        model.addAttribute("coordinates", coordinates);
        return "result";
    }
}

//Isbandau Branch