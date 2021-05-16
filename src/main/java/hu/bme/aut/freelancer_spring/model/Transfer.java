package hu.bme.aut.freelancer_spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

///////////////////////////////////////////////////////////////
// The transfer of packages
// Contains information about the transfer's date, its route, place, 
// the user delivering, and the vehicle they are delivering with.
///////////////////////////////////////////////////////////////

@Entity
@Table(name = "transfer_entity")
@Getter @Setter
@NoArgsConstructor
public class Transfer {

    //Identifier, unique to every Transfer
    @Id
    @GeneratedValue(generator = "transfer_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "transfer_id_seq",
            sequenceName = "transfer_id_seq"
    )
    @Column(name = "id")
    private Long id;

    //The date of the transfer.
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "from_lat")
    private double fromLat;

    @Column(name = "from_long")
    private double fromLong;

    @Column(name = "to_lat")
    private double toLat;

    @Column(name = "to_long")
    private double toLong;

    //The start of the delivery.
    @Column(name = "start_time")
    private LocalTime startTime;

    //The route of the delivery, encoded into a String.
    @Column(name = "encoded_route")
    private String encodedRoute;

    //The time the transfer was registered at.
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    //The carrier of the transfer. Custom class, detailed in User.java .
    @ManyToOne
    @JoinColumn(name = "carrier_id", referencedColumnName = "id")
    private User carrier;

    //The vehicle of the transfer. Custom class, detailed in Vehicle.java .
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    //The town of the delivery. Custom class, detailed in Town.java .
    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    //A list of packages delivered in this transfer. Custom class, detailed in Package.java .
    @JsonIgnore
    @OneToMany(mappedBy = "transfer")
    private List<Package> packages = new ArrayList<>();

    //A method to delete this transfer from the packages' values, in order to set them up for a different transfer.
    @PreRemove
    private void preRemove() {
        for (var pack : packages) {
            pack.setTransfer(null);
        }
    }

    public boolean fitPackage(Package newPackage) {
        if (date.before(newPackage.getDateLimit()) && suitableInsurance(newPackage)) {
            return vehicle.isBelowWeightLimit(getPackagesWeightSum() + newPackage.getWeight())
                    && vehicle.isBelowCCLimit(getPackagesCCSum() + newPackage.getSize().getCC());
        }
        return false;
    }
    //A method to check if the package given as the parameter fits into the vehicle of the transfer's cargo hold.
    //Returns true if the package fits volume- and weight-wise, returns false otherwise.
    //newPackage: the package we want to fit into the cargo hold

    //A method to check if the package given as the parameter is okay insurance-wise.
    //Returns false if the package needs insurance, and the vehicle's operator doesn't have one.
    //Returns true otherwise.
    private boolean suitableInsurance(Package newPackage) {
        return !newPackage.needInsurance() || carrier.isHasInsurance();
    }

    //Returns the sum of all the packages' weight.
    private double getPackagesWeightSum() {
        return packages.stream()
                .mapToDouble(Package -> Package::getWeight)
                .sum();
    }

    //Returns the sum of all packages' volume.
    private int getPackagesCCSum() {
        return packages.stream()
                .mapToInt(p -> p.getSize().getCC())
                .sum();
    }

    //Adds the package given as the parameter to the list of packages of this transfer.
    public void addPackage(Package pack) {
        packages.add(pack);
    }

    //Constructor with parameters.
    public Transfer(Date createdAt, double fromLat, double fromLong) {
        this.createdAt = createdAt;
        this.fromLat = fromLat;
        this.fromLong = fromLong;
    }
}
