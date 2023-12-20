package BigB_Project.LocationFinder.repository;

import BigB_Project.LocationFinder.repository.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoutesRepository extends JpaRepository<Route, Integer> {
}
