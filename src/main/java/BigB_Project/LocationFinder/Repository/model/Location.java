package BigB_Project.LocationFinder.Repository.model;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Location {

    private String coordinates;
}
