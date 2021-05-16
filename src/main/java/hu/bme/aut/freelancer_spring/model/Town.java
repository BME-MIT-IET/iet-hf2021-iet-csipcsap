package hu.bme.aut.freelancer_spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

////////////////////////////////////////////////////////////////////////
// The town of the deliveries
// Contains information about the transfers going on in the town
// and the packages registered there.
////////////////////////////////////////////////////////////////////////

@Entity
@Table(name = "town_entity")
@Getter @Setter
public class Town {

    //Identifier, unique to every town.
    @Id
    @GeneratedValue(generator = "town_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "town_id_seq",
            sequenceName = "town_id_seq"
    )
    @Column(name = "id")
    private Long id;

    //The name of the town.
    @Column(name = "name")
    private String name;

    //A list of transfers going on in the town. Custom class, detailed in Transfer.java .
    @JsonIgnore
    @OneToMany(mappedBy = "town")
    private List<Transfer> transfers;

    //A list of packages registered in the town. Custom class, detailed in Package.java .
    @JsonIgnore
    @OneToMany(mappedBy = "town")
    private List<Package> packages;
}
