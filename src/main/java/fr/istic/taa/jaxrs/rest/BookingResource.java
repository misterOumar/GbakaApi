package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.BookingDao;
import fr.istic.taa.jaxrs.domain.Booking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Tag(name = "Bookings", description = "Gestion des réservations")
@Path("bookings")
@Produces("application/json")
@Consumes("application/json")
public class BookingResource {

    private BookingDao bookingDao = new BookingDao();

    @Operation(summary = "Lister toutes les réservations")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<Booking> getAll() {
        return bookingDao.findAll();
    }

    @Operation(summary = "Trouver une réservation par id")
    @ApiResponse(responseCode = "200", description = "Réservation trouvée")
    @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    @GET
    @Path("/{id}")
    public Booking getById(
            @Parameter(description = "ID de la réservation")
            @PathParam("id") Long id) {
        return bookingDao.findOne(id);
    }

    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Réservation créée")
    @POST
    public Response create(Booking booking) {
        bookingDao.save(booking);
        return Response.status(201).entity(booking).build();
    }

    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation modifiée")
    @PUT
    @Path("/{id}")
    public Response update(
            @Parameter(description = "ID de la réservation")
            @PathParam("id") Long id, Booking booking) {
        booking.setId(id);
        bookingDao.update(booking);
        return Response.ok().entity(booking).build();
    }

    @Operation(summary = "Supprimer une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation supprimée")
    @DELETE
    @Path("/{id}")
    public Response delete(
            @Parameter(description = "ID de la réservation")
            @PathParam("id") Long id) {
        bookingDao.deleteById(id);
        return Response.ok().build();
    }

    @Operation(summary = "Lister les réservations d'un passager")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    @Path("/passenger/{passengerId}")
    public List<Booking> getByPassenger(
            @Parameter(description = "ID du passager")
            @PathParam("passengerId") Long passengerId) {
        return bookingDao.findByPassenger(passengerId);
    }

    @Operation(summary = "Lister les réservations d'un trajet")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    @Path("/trip/{tripId}")
    public List<Booking> getByTrip(
            @Parameter(description = "ID du trajet")
            @PathParam("tripId") Long tripId) {
        return bookingDao.findByTrip(tripId);
    }

    @Operation(summary = "Noter un chauffeur", description = "Note de 1 à 5")
    @ApiResponse(responseCode = "200", description = "Note enregistrée")
    @PUT
    @Path("/{id}/rate-driver")
    public Response rateDriver(
            @Parameter(description = "ID de la réservation")
            @PathParam("id") Long id,
            @Parameter(description = "Note de 1 à 5")
            @QueryParam("rating") int rating) {
        bookingDao.rateDriver(id, rating);
        return Response.ok().build();
    }

    @Operation(summary = "Noter un passager", description = "Note de 1 à 5")
    @ApiResponse(responseCode = "200", description = "Note enregistrée")
    @PUT
    @Path("/{id}/rate-passenger")
    public Response ratePassenger(
            @Parameter(description = "ID de la réservation")
            @PathParam("id") Long id,
            @Parameter(description = "Note de 1 à 5")
            @QueryParam("rating") int rating) {
        bookingDao.ratePassenger(id, rating);
        return Response.ok().build();
    }
}