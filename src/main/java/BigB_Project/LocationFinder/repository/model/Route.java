package BigB_Project.LocationFinder.repository.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.dialect.PostgreSQLJsonPGObjectJsonbType;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="routes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Route {

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


    @ElementCollection
    @CollectionTable(name ="optional_coordinates", joinColumns = @JoinColumn(name = "route_id"))
    @Column(name = "optional_coordinates")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> optionalCoordinates = new ArrayList<>();
}
