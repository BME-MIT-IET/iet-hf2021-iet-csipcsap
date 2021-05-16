package hu.bme.aut.freelancer_spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.bme.aut.freelancer_spring.model.enums.Size;
import hu.bme.aut.freelancer_spring.model.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.dom4j.tree.FlyweightText;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;


///////////////////////////////////////
// The Package entity.
// Contains information about the package,
// its sender, and the transfer it is
// transfered in.
// It also contains information about
// the time of transfer, as the system
// works with a First In First Out
// model.
///////////////////////////////////////

@Entity
@Table(name = "package_entity")
@Getter @Setter
public class Package {

    //If the package's price is below this, it doesn't need insurance.
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private static final int NO_INSURANCE_LIMIT = 30000;

    //Identifier of the package, unique to every package.
    @Id
    @GeneratedValue(generator = "package_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "package_id_seq",
            sequenceName = "package_id_seq"
    )
    @Column(name = "id")
    private Long id;

    //Name of the package.
    @Column(name = "name")
    private String name;

    //The size and price of the package, stored in a custom class, detailed in enums/Size.java .
    @Enumerated(EnumType.STRING)
    private Size size;

    //The weight of the package, in kilogramms.
    @Column(name = "weight")
    private double weight;

    @Column(name = "from_lat")
    private double fromLat;

    @Column(name = "from_long")
    private double fromLong;

    @Column(name = "to_lat")
    private double toLat;

    @Column(name = "to_long")
    private double toLong;

    //The value of the package.
    @Column(name = "value")
    private int value;

    //The latest date the customer wants their package delivered.
    @Column(name = "date_limit")
    @Temporal(TemporalType.DATE)
    private Date dateLimit;

    //The time their package arrives.
    @Column(name = "arrive_time")
    private LocalTime arriveTime;

    //The time the driver picks up the package.
    @Column(name = "pickup_time")
    private LocalTime pickupTime;

    //The time the package was signed into the system.
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    //The status of the package, stored in a custom enum, detailed in enums/Status.java .
    @Enumerated(EnumType.STRING)
    private Status status;

    //Contains the town of the delivery, in a custom class, detailed in Town.java .
    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    //Contains the sender, in a custom class, detailed in User.java .
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    //Contains the transfer, in a custom class, detailed in Transfer.java .
    @ManyToOne
    @JoinColumn(name = "transfer_id", referencedColumnName = "id")
    private Transfer transfer;

    //Constructor without parameters
    public Package() {
        status = Status.WAITING;
        createdAt = new Date();
    }

    //Constructor with parameters
    //name: The name of the package
    //value: The value of the package
    //weight: The weight of the package, in kilogramms
    public Package(String name, int value, int weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    //Sets the Status of the package to the one set in the parameter.
    //status: The status we want to set on the package, WAITING by default
    public void setStatus(Status status) {
        this.status = Objects.requireNonNullElse(status, Status.WAITING);
    }

    //Returns if the package requires insurance, by checking if its value is above the limit.
    public boolean needInsurance() {
        return value > NO_INSURANCE_LIMIT;
    }
}
