package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.DriverDao;
import fr.istic.taa.jaxrs.dao.TripDao;
import fr.istic.taa.jaxrs.domain.Driver;
import fr.istic.taa.jaxrs.domain.Trip;
import fr.istic.taa.jaxrs.domain.enums.TripStatus;
import fr.istic.taa.jaxrs.dto.TripCreateDto;
import fr.istic.taa.jaxrs.dto.TripDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Lister tous les trajets", description = "Retourne la liste complète des trajets")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<TripDto> getAll() {
        return tripDao.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Rechercher un trajet par id", description = "Retourne un trajet selon son identifiant")
    @ApiResponse(responseCode = "200", description = "Trajet trouvé")
    @ApiResponse(responseCode = "404", description = "Trajet non trouvé")
    @GET
    @Path("/{id}")
    public TripDto getById(
            @Parameter(description = "ID du trajet")
            @PathParam("id") Long id) {
        Trip trip = tripDao.findOne(id);
        if (trip == null) return null;
        return toDto(trip);
    }

    @Operation(summary = "Créer un trajet", description = "Retourne le trajet créé")
    @ApiResponse(responseCode = "201", description = "Trajet créé")
    @POST
    public Response create(TripCreateDto dto) {
        Trip trip = new Trip();
        trip.setDepartureCity(dto.getDepartureCity());
        trip.setArrivalCity(dto.getArrivalCity());
        trip.setDepartureAddress(dto.getDepartureAddress());
        trip.setArrivalAddress(dto.getArrivalAddress());
        trip.setDepartureTime(LocalDateTime.parse(dto.getDepartureTime()));
        trip.setEstimatedArrivalTime(LocalDateTime.parse(dto.getEstimatedArrivalTime()));
        trip.setPricePerSeat(dto.getPricePerSeat());
        trip.setTotalSeats(dto.getTotalSeats());
        trip.setAvailableSeats(dto.getTotalSeats());
        trip.setSmokingAllowed(dto.isSmokingAllowed());
        trip.setPetsAllowed(dto.isPetsAllowed());
        trip.setDescription(dto.getDescription());
        trip.setTripStatus(TripStatus.PLANNED);
        Driver driver = driverDao.findOne(dto.getDriverId());
        trip.setDriver(driver);
        tripDao.save(trip);
        return Response.status(201).entity(toDto(trip)).build();
    }

    @Operation(summary = "Modifier un trajet", description = "Retourne le trajet modifié")
    @ApiResponse(responseCode = "200", description = "Trajet modifié")
    @PUT
    @Path("/{id}")
    public Response update(
            @Parameter(description = "ID du trajet")
            @PathParam("id") Long id, TripCreateDto dto) {
        Trip trip = new Trip();
        trip.setId(id);
        trip.setDepartureCity(dto.getDepartureCity());
        trip.setArrivalCity(dto.getArrivalCity());
        trip.setDepartureAddress(dto.getDepartureAddress());
        trip.setArrivalAddress(dto.getArrivalAddress());
        trip.setDepartureTime(LocalDateTime.parse(dto.getDepartureTime()));
        trip.setEstimatedArrivalTime(LocalDateTime.parse(dto.getEstimatedArrivalTime()));
        trip.setPricePerSeat(dto.getPricePerSeat());
        trip.setTotalSeats(dto.getTotalSeats());
        trip.setSmokingAllowed(dto.isSmokingAllowed());
        trip.setPetsAllowed(dto.isPetsAllowed());
        trip.setDescription(dto.getDescription());
        Driver driver = driverDao.findOne(dto.getDriverId());
        trip.setDriver(driver);
        tripDao.update(trip);
        return Response.ok().entity(toDto(trip)).build();
    }

    @Operation(summary = "Supprimer un trajet")
    @ApiResponse(responseCode = "200", description = "Trajet supprimé")
    @DELETE
    @Path("/{id}")
    public Response delete(
            @Parameter(description = "ID du trajet")
            @PathParam("id") Long id) {
        tripDao.deleteById(id);
        return Response.ok().build();
    }

    @Operation(summary = "Lister les trajets par statut")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    @Path("/status/{status}")
    public List<TripDto> getByStatus(
            @Parameter(description = "Statut du trajet : PLANNED, IN_PROGRESS, COMPLETED, CANCELLED")
            @PathParam("status") TripStatus status) {
        return tripDao.findByStatus(status)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Rechercher un trajet par date", description = "Retourne les trajets à partir d'une date")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    @Path("/date")
    public List<TripDto> getByDate(
            @Parameter(description = "Date au format 2026-03-01T00:00:00")
            @QueryParam("date") String date) {
        return tripDao.findByDate(LocalDateTime.parse(date))
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Rechercher un trajet par départ et destination")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    @Path("/search")
    public List<TripDto> search(
            @Parameter(description = "Ville de départ") @QueryParam("dep") String departure,
            @Parameter(description = "Ville d'arrivée") @QueryParam("arr") String arrival) {
        return tripDao.findByDepartureAndArrivalCity(departure, arrival)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Note moyenne du chauffeur d'un trajet")
    @ApiResponse(responseCode = "200", description = "Note retournée")
    @ApiResponse(responseCode = "404", description = "Trajet non trouvé")
    @GET
    @Path("/{id}/driver/rating")
    public Response getDriverRating(
            @Parameter(description = "ID du trajet")
            @PathParam("id") Long tripId) {
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
            Double rating = driverDao.calculateAverageRating(trip.getDriver().getId());
            dto.setDriverRating(rating != null ? rating : 0.0);
        }
        return dto;
    }
}