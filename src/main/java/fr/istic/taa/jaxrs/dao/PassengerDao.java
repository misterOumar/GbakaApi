package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Passenger;

public class PassengerDao extends AbstractJpaDao<Long, Passenger> {
    public PassengerDao() {
        super();
        setClazz(Passenger.class);
    }
}
