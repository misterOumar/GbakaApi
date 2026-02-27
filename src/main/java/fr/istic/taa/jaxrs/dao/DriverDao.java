package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Driver;

public class DriverDao extends AbstractJpaDao<Long, Driver> {
    public DriverDao() {
        super();
        setClazz(Driver.class);
    }

    //Calcul des moyennes des notes d'un chauffeur
    public Double calculateAverageRating(Long driverId) {
        return entityManager
                .createQuery(
                        "SELECT AVG(b.driverRating) FROM Booking b WHERE b.trip.driver.id = :driverId",
                        Double.class
                )
                .setParameter("driverId", driverId)
                .getSingleResult();
    }
}
