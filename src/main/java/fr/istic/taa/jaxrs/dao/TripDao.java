package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Trip;

public class TripDao extends AbstractJpaDao<Long, Trip> {
    public TripDao() {
        super();
        setClazz(Trip.class);
    }
}
