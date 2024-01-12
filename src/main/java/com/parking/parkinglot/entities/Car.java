package com.parking.parkinglot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Collection;

@Entity
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Basic
    private String licensePlate;

    @Size(min = 7, max = 10)
    @Column(unique = true, nullable = false, length = 10)
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Basic
    private String parkingSpot;

    @Size(min = 3, max=100)
    @Column(unique = true, nullable = false, length = 100)
    public String getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    @ManyToOne
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CarPhoto photo;

    public CarPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(CarPhoto photo) {
        this.photo = photo;
    }
}
