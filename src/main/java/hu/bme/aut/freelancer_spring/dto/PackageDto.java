package hu.bme.aut.freelancer_spring.dto;

import hu.bme.aut.freelancer_spring.model.enums.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class PackageDto {

    private Long id;

    private String name;

    private Size size;

    private double weight;

    private double fromLat;

    private double fromLong;

    private double toLat;

    private double toLong;

    private Long senderId;

    private Date dateLimit;

    private Long townId;

    private int value;
}
