package hu.bme.aut.freelancer_spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

////////////////////////////////////////////////////////////////////////////
// The users of the app, wether customers or deliverers
// Contains personal data, their packages, deliveries, vehicles, and wether
// they have insurance, or not.
////////////////////////////////////////////////////////////////////////////
@Entity
@Table(name = "user_entity")
@Getter @Setter @NoArgsConstructor
public class User {

    //Identifier, unique to every User.
    @Id
    @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "user_id_seq",
            sequenceName = "user_id_seq"
    )
    @Column(name = "id")
    private Long id;

    //The name of the user.
    @Column(name = "name")
    private String name;

    //The e-mail address of the user.
    @Column(name = "email")
    private String email;

    //The phone number of the user.
    @Column(name = "phonenumber")
    private String phonenumber;

    //The password of the user's profile.
    @JsonIgnore
    @Column(name = "password")
    private String password;

    //True, if the user has insurance, false otherwise. False by default.
    @Column(name = "has_insurance")
    private boolean hasInsurance = false;

    //A list of the packages registered by the user. Custom class, detailed in Package.java .
    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<Package> packages;

    //A list of transfers carried out by the user. Custom class, detailed in Transfer.java .
    @JsonIgnore
    @OneToMany(mappedBy = "carrier")
    private List<Transfer> transfers;

    //A list of vehicles the user owns, that are up for delivery. Custom class, detailed in Vehicle.java .
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Vehicle> vehicles;
}
