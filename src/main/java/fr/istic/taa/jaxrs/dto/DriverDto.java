package fr.istic.taa.jaxrs.dto;

public class DriverDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String licenceNumber;
    private double ratingAverage;
    private int totalTripsOffered;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public int getTotalTripsOffered() {
        return totalTripsOffered;
    }

    public void setTotalTripsOffered(int totalTripsOffered) {
        this.totalTripsOffered = totalTripsOffered;
    }
}