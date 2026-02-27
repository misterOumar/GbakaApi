package fr.istic.taa.jaxrs.domain;

import fr.istic.taa.jaxrs.domain.enums.PaymentMethod;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Passenger extends User {

    public Passenger() {}

    private double ratingByDrivers;
    private int totalTripsBooked;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToMany(mappedBy = "passengers")
    private List<Trip> trips;

    @OneToMany(mappedBy = "passenger")
    private List<Booking> bookings;


    public double getRatingByDrivers() {
        return ratingByDrivers;
    }

    public void setRatingByDrivers(double ratingByDrivers) {
        this.ratingByDrivers = ratingByDrivers;
    }

    public int getTotalTripsBooked() {
        return totalTripsBooked;
    }

    public void setTotalTripsBooked(int totalTripsBooked) {
        this.totalTripsBooked = totalTripsBooked;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}