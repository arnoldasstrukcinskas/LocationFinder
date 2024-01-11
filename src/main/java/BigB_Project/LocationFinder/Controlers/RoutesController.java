package BigB_Project.LocationFinder.Controlers;

import BigB_Project.LocationFinder.repository.model.Route;
import BigB_Project.LocationFinder.Services.RoutesService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/routes")
public class RoutesController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RoutesService routesService;


//    http://localhost:8080/routes/home
    @GetMapping(value = "/home")
    public String home(){
        return "home";
    }

//    http://localhost:8080/routes/create
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String saveRoute(Model model){
        model.addAttribute("route", new Route());
        model.addAttribute("apiKey", apiKey);
        return "create_route";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String saveRoute(Model model, @ModelAttribute(value = "route") Route route){
        Integer roudeId = routesService.save(route);
        model.addAttribute("route", route);
        return "create_route";
    }


    //method which handles response from end point search
    @RequestMapping(value = "/handleStartPointResponse", method = RequestMethod.POST)
    @ResponseBody
    public void handleStartPointResponse(Model model, @RequestBody ObjectNode jsonResponse, HttpSession session)
    {
        String locationName = routesService.getLocationName(jsonResponse);
        String coordinates = routesService.getLocationCoordinates(jsonResponse);
        session.setAttribute("startLocationName", locationName);
        session.setAttribute("startLocationCoordinates", coordinates);
        System.out.println("startpoint response: " + coordinates);
    }

    //metodas, kuris ikelia lokacijos pavadinima
    @RequestMapping(value = "/startingLocationName", method = RequestMethod.GET)
    @ResponseBody
    public String getLocationName(HttpSession session) {
        String locationName = (String) session.getAttribute("startLocationName");
        System.out.println("Starting name " + locationName);
        return locationName != null ? locationName : "";
    }

    @RequestMapping(value = "/startingLocationCoordinates", method = RequestMethod.GET)
    @ResponseBody
    public String getCoordinates(HttpSession session, Model model) {
        String coordinates = (String) session.getAttribute("startLocationCoordinates");
        model.addAttribute("coordinates", coordinates);
        System.out.println("Starting coords " + coordinates);
        return coordinates != null ? coordinates : "";
    }

    //method which handles response from Start point search
    @RequestMapping(value = "/handleEndPointResponse", method = RequestMethod.POST)
    @ResponseBody
    public void handleEndPointResponse(Model model, @RequestBody ObjectNode jsonResponse, HttpSession session)
    {
        String locationName = routesService.getLocationName(jsonResponse);
        String coordinates = routesService.getLocationCoordinates(jsonResponse);
        session.setAttribute("endLocationName", locationName);
        session.setAttribute("endLocationCoordinates", coordinates);
        System.out.println("endpoint response: " + coordinates);
    }


    //metodas, kuris ikelia lokacijos pavadinima
    @RequestMapping(value = "/endLocationName", method = RequestMethod.GET)
    @ResponseBody
    public String getEndLocationName(HttpSession session) {
        String locationName = (String) session.getAttribute("endLocationName");
        System.out.println("Ending name " + locationName);
        return locationName != null ? locationName : "";
    }

    @RequestMapping(value = "/endLocationCoordinates", method = RequestMethod.GET)
    @ResponseBody
    public String getEndLocationCoordinates(HttpSession session, Model model) {
        String coordinates = (String) session.getAttribute("endLocationCoordinates");
        model.addAttribute("coordinates", coordinates);
        System.out.println("Ending coords " + coordinates);
        return coordinates != null ? coordinates : "";
    }
}


