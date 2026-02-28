package fr.istic.taa.jaxrs.dto;

public class PassengerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private double ratingByDrivers;
    private String paymentMethod;

    // getters et setters

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

    public double getRatingByDrivers() {
        return ratingByDrivers;
    }

    public void setRatingByDrivers(double ratingByDrivers) {
        this.ratingByDrivers = ratingByDrivers;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
