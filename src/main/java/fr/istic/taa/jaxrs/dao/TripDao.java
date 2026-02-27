package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Trip;
import fr.istic.taa.jaxrs.domain.enums.TripStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.List;

public class TripDao extends AbstractJpaDao<Long, Trip> {
    public TripDao() {
        super();
        setClazz(Trip.class);
    }

    //NamedQuery
    public List<Trip> findByDate(LocalDateTime date) {
        return entityManager
                .createNamedQuery("Trip.findByDate", Trip.class)
                .setParameter("date", date)
                .getResultList();
    }

    //JPQL
    public List<Trip> findByStatus(TripStatus status) {
        return entityManager
                .createQuery(
                        "SELECT t FROM Trip t WHERE t.tripStatus = :status",
                        Trip.class
                )
                .setParameter("status", status)
                .getResultList();
    }


    //Criteria Query
    public List<Trip> findByDepartureAndArrivalCity(String departure, String arrival) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> tripCriteriaQuery = cb.createQuery(Trip.class);
        Root<Trip> t = tripCriteriaQuery.from(Trip.class);

        tripCriteriaQuery.select(t).where(
                cb.and(
                        cb.equal(t.get("departureCity"), departure),
                        cb.equal(t.get("arrivalCity"), arrival)
                )
        );

        return entityManager.createQuery(tripCriteriaQuery).getResultList();
    }
}
