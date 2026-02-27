package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.DriverDao;
import fr.istic.taa.jaxrs.dao.TripDao;
import fr.istic.taa.jaxrs.domain.Trip;
import fr.istic.taa.jaxrs.domain.enums.TripStatus;
import fr.istic.taa.jaxrs.dto.TripDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Path("trips")
@Produces("application/json")
@Consumes("application/json")
public class TripResource {

    private TripDao tripDao = new TripDao();
    private DriverDao driverDao = new DriverDao();

    // GET /trips
    @GET
    public List<Trip> getAll() {
        return tripDao.findAll();
    }

    // GET /trips/1
    @GET
    @Path("/{id}")
    public Trip getById(@PathParam("id") Long id) {
        return tripDao.findOne(id);
    }

    // POST /trips
    @POST
    public Response create(Trip trip) {
        tripDao.save(trip);
        return Response.status(201).entity(trip).build();
    }

    // PUT /trips/1
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Trip trip) {
        trip.setId(id);
        tripDao.update(trip);
        return Response.ok().entity(trip).build();
    }

    // DELETE /trips/1
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        tripDao.deleteById(id);
        return Response.ok().build();
    }

    // GET /trips/status/PLANNED
    @GET
    @Path("/status/{status}")
    public List<Trip> getByStatus(@PathParam("status") TripStatus status) {
        return tripDao.findByStatus(status);
    }

    // GET /trips/date?date=2026-03-01T00:00:00
    @GET
    @Path("/date")
    public List<Trip> getByDate(@QueryParam("date") String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return tripDao.findByDate(dateTime);
    }

    // GET /trips/search?dep=Cotonou&arr=Parakou  → retourne des DTOs
    @GET
    @Path("/search")
    public List<TripDto> search(
            @QueryParam("dep") String departure,
            @QueryParam("arr") String arrival) {
        List<Trip> trips = tripDao.findByDepartureAndArrivalCity(departure, arrival);
        return trips.stream().map(this::toDto).collect(Collectors.toList());
    }

    // GET /trips/1/driver/rating
    @GET
    @Path("/{id}/driver/rating")
    public Response getDriverRating(@PathParam("id") Long tripId) {
        Trip trip = tripDao.findOne(tripId);
        if (trip == null) return Response.status(404).build();
        Double rating = driverDao.calculateAverageRating(trip.getDriver().getId());
        return Response.ok().entity(rating).build();
    }

    // Conversion Trip → TripDto
    private TripDto toDto(Trip trip) {
        TripDto dto = new TripDto();
        dto.setId(trip.getId());
        dto.setDepartureCity(trip.getDepartureCity());
        dto.setArrivalCity(trip.getArrivalCity());
        dto.setDepartureTime(trip.getDepartureTime());
        dto.setPricePerSeat(trip.getPricePerSeat());
        dto.setAvailableSeats(trip.getAvailableSeats());
        if (trip.getDriver() != null) {
            dto.setDriverFullName(trip.getDriver().getFirstName() + " " + trip.getDriver().getLastName());
            dto.setDriverRating(driverDao.calculateAverageRating(trip.getDriver().getId()) != null ?
                    driverDao.calculateAverageRating(trip.getDriver().getId()) : 0.0);
        }
        return dto;
    }
}