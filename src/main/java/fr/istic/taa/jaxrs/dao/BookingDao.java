package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Booking;

public class BookingDao extends AbstractJpaDao<Long, Booking> {
    public BookingDao() {
        super();
        setClazz(Booking.class);
    }
}
