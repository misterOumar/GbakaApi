package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Booking;

import java.util.List;

public class BookingDao extends AbstractJpaDao<Long, Booking> {
    public BookingDao() {
        super();
        setClazz(Booking.class);
    }

    //Trouver les Bookings d'un passager
    public List<Booking> findByPassenger(Long passengerId) {
        return entityManager
                .createQuery(
                        "SELECT b FROM Booking b WHERE b.passenger.id = :passengerId",
                        Booking.class
                )
                .setParameter("passengerId", passengerId)
                .getResultList();
    }

    //Trouver les bookings d'un voyage
    public List<Booking> findByTrip(Long tripId) {
        return entityManager
                .createQuery(
                        "SELECT b FROM Booking b WHERE b.trip.id = :tripId",
                        Booking.class
                )
                .setParameter("tripId", tripId)
                .getResultList();
    }

    //Noter un chauffeur
    public void rateDriver(Long bookingId, int rating) {
        Booking booking = findOne(bookingId);
        if (booking != null) {
            booking.setDriverRating(rating);
            update(booking);
        }
    }

    //Noter un Passenger
    public void ratePassenger(Long bookingId, int rating) {
        Booking booking = findOne(bookingId);
        if (booking != null) {
            booking.setPassengerRating(rating);
            update(booking);
        }
    }


}
