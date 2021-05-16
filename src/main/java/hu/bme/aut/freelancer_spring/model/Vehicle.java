package hu.bme.aut.freelancer_spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/////////////////////////////////////////////////////
// The vehicles up to deliver the packages. Contains information
// about the cargo hold of the truck, its owner, and the
// maximum weight in can carry.
/////////////////////////////////////////////////////

@Entity
@Table(name = "vehicle_entity")
@Getter @Setter
public class Vehicle {

    //Identifier, unique to every Vehicle.
    @Id
    @GeneratedValue(generator = "vehicle_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "vehicle_id_seq",
            sequenceName = "vehicle_id_seq"
    )
    @Column(name = "id")
    private Long id;

    //The name of the vehicle.
    @Column(name = "name")
    private String name;

    //The X dimension of the vehicle's cargo hold.
    @Column(name = "x")
    private int x;

    //The Y dimension of the vehicle's cargo hold.
    @Column(name = "y")
    private int y;

    //The Z dimension of the vehicle's cargo hold.
    @Column(name = "z")
    private int z;

    //The maximum weight of the packages the vehicle can carry in kilogramms.
    @Column(name = "weight_limit")
    private double weightLimit;

    //The owner of the vehicle. Custom class, detailed in User.java .
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    //A list of transfers this vehicle executes. Custom class, detailed in Transfer.java .
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<Transfer> transfers;

    //Returns the volume of the cargo hold.
    public int getCC() {
        return x * y * z;
    }

    //Returns if the weight given as the parameter is below the weight limit of the vehicle.
    //weight: the weight we want to check in kilogramms
    public boolean isBelowWeightLimit(double weight) {
        return weight <= weightLimit;
    }

    //Returns if the volume given as the parameter is below the volume of the vehicle's cargo hold.
    //cc: the volume of the entity we want to store in the cargo hold
    public boolean isBelowCCLimit(int cc) {
        return cc <= getCC();
    }

}
