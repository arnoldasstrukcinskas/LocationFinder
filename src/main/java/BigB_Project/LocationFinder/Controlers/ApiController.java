package BigB_Project.LocationFinder.Controlers;

import BigB_Project.LocationFinder.Services.LocationFinderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/coordinates", method = RequestMethod.GET)
    public String getCoordinates(Model model, @ModelAttribute(value = "location") String location) throws IOException, InterruptedException {
//        model.addAttribute("location", location);
//        //        locationFinderServices.connectToApi(location);
        model.addAttribute("coordinates", locationFinderServices.connectToApi(location));
        return "result";
    }
}