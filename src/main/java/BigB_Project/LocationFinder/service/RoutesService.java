package BigB_Project.LocationFinder.service;

import BigB_Project.LocationFinder.repository.RoutesRepository;
import BigB_Project.LocationFinder.repository.model.Route;
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

    public List<Route> getRouteByNameLike(String name){
        return routesRepository.getRoutesByNameLike("%" + name + "%");
    }
}
