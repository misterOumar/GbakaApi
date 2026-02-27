package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Vehicle;

public class VehicleDao extends AbstractJpaDao<Long, Vehicle> {
    public VehicleDao() {
        super();
        setClazz(Vehicle.class);
    }
}
