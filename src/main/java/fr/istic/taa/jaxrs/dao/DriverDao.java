package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Driver;

public class DriverDao extends AbstractJpaDao<Long, Driver> {
    public DriverDao() {
        super();
        setClazz(Driver.class);
    }
}
