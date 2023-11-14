package BigB_Project.LocationFinder.controller;

import BigB_Project.LocationFinder.repository.model.Route;
import BigB_Project.LocationFinder.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        return "create_route";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String saveKlientas(Model model, @ModelAttribute(value = "route") Route route){
        Integer roudeId = routesService.save(route);
        model.addAttribute("route", route);
        return "redirect:/routes/home";
    }
}
