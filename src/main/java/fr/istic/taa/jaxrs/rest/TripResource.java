package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.DriverDao;
import fr.istic.taa.jaxrs.dao.TripDao;
import fr.istic.taa.jaxrs.domain.Trip;
import fr.istic.taa.jaxrs.domain.enums.TripStatus;
import fr.istic.taa.jaxrs.dto.TripDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Trips", description = "Gestion des trajets")
@Path("trips")
@Produces("application/json")
@Consumes("application/json")
public class TripResource {

    private TripDao tripDao = new TripDao();
    private DriverDao driverDao = new DriverDao();

    // GET /trips
    @Operation(
            summary = "Lister tous les trajets",
            description = "Retourne la liste complète des trajets"
    )
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<Trip> getAll() {
        return tripDao.findAll();
    }

    // GET /trips/1
    @Operation(
            summary = "Rechercher un trajet par id",
            description = "Retourne un trajet selon son identifiant"
    )
    @ApiResponse(responseCode = "200", description = "Trajet trouvé")
    @ApiResponse(responseCode = "404", description = "Trajet non trouvé")
    @GET
    @Path("/{id}")
    public Trip getById(@PathParam("id") Long id) {
        return tripDao.findOne(id);
    }

    // POST /trips
    @Operation(
            summary = "Creer un trajet",
            description = "Retourne le trajet cree"
    )
    @ApiResponse(responseCode = "200", description = "Trajet Cree")
    @POST
    public Response create(Trip trip) {
        tripDao.save(trip);
        return Response.status(201).entity(trip).build();
    }

    // PUT /trips/1
    @Operation(
            summary = "Modifier un trajet par id",
            description = "Retourne le trajet modifie"
    )
    @ApiResponse(responseCode = "200", description = "Trajet trouvé")
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Trip trip) {
        trip.setId(id);
        tripDao.update(trip);
        return Response.ok().entity(trip).build();
    }

    // DELETE /trips/1
    @Operation(
            summary = "Supprimer un trajet par id"
    )
    @ApiResponse(responseCode = "200", description = "Trajet supprime")
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
    @Operation(
            summary = "Rechercher un trajet par sa date",
            description = "Retourne les trajets actuels et futurs"
    )
    @ApiResponse(responseCode = "200", description = "Trajet trouvé")
    @GET
    @Path("/date")
    public List<Trip> getByDate(@QueryParam("date") String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return tripDao.findByDate(dateTime);
    }

    // GET /trips/search?dep=Paris&arr=Marseille
    @Operation(
            summary = "Rechercher un trajet par le depart et la destination",
            description = "Retourne les trajets trouves"
    )
    @ApiResponse(responseCode = "200", description = "Trajet trouvé")
    @GET
    @Path("/search")
    public List<TripDto> search(
            @QueryParam("dep") String departure,
            @QueryParam("arr") String arrival) {
        List<Trip> trips = tripDao.findByDepartureAndArrivalCity(departure, arrival);
        return trips.stream().map(this::toDto).collect(Collectors.toList());
    }

    // GET /trips/1/driver/rating
    @Operation(
            summary = "Calcul de la moyenne des notations",
            description = "Retourne la note"
    )
    @ApiResponse(responseCode = "200", description = "Trajet trouvé")
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