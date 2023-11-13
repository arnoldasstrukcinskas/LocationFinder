package BigB_Project.LocationFinder.repository.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;

@Entity
@Table(name ="routes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Routes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "route_name")
    private String routeName;

    @Column(name = "route_description")
    private String routeDescription;

    @Column(name = "starting_coordinates")
    private String startingCoordinates;

    @Column(name = "ending_coordinates")
    private String endingCoordinates;

    @Column(name = "optional_coordinates")
    private ArrayList<String> optionalCoordinates;
}
