package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.BookingDao;
import fr.istic.taa.jaxrs.dao.PassengerDao;
import fr.istic.taa.jaxrs.dao.TripDao;
import fr.istic.taa.jaxrs.domain.Booking;
import fr.istic.taa.jaxrs.domain.Passenger;
import fr.istic.taa.jaxrs.domain.Trip;
import fr.istic.taa.jaxrs.domain.enums.BookingStatus;
import fr.istic.taa.jaxrs.dto.BookingCreateDto;
import fr.istic.taa.jaxrs.dto.BookingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Bookings", description = "Gestion des réservations")
@Path("bookings")
@Produces("application/json")
@Consumes("application/json")
public class BookingResource {

    private BookingDao bookingDao = new BookingDao();
    private TripDao tripDao = new TripDao();
    private PassengerDao passengerDao = new PassengerDao();

    @Operation(summary = "Lister toutes les réservations")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<BookingDto> getAll() {
        return bookingDao.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Trouver une réservation par id")
    @ApiResponse(responseCode = "200", description = "Réservation trouvée")
    @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    @GET
    @Path("/{id}")
    public BookingDto getById(
            @Parameter(description = "ID de la réservation")
            @PathParam("id") Long id) {
        return toDto(bookingDao.findOne(id));
    }

    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Réservation créée")
    @POST
    public Response create(BookingCreateDto dto) {
        Trip trip = tripDao.findOne(dto.getTripId());
        Passenger passenger = passengerDao.findOne(dto.getPassengerId());
        Booking booking = new Booking();
        booking.setTrip(trip);
        booking.setPassenger(passenger);
        booking.setSeatBooked(dto.getSeatBooked());
        booking.setTotalPrice(trip.getPricePerSeat() * dto.getSeatBooked());
        booking.setBookedAt(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.PENDING);
        bookingDao.save(booking);
        return Response.status(201).entity(toDto(booking)).build();
    }

    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation modifiée")
    @PUT
    @Path("/{id}")
    public Response update(
            @Parameter(description = "ID de la réservation")
            @PathParam("id") Long id, BookingCreateDto dto) {
        Trip trip = tripDao.findOne(dto.getTripId());
        Passenger passenger = passengerDao.findOne(dto.getPassengerId());
        Booking booking = new Booking();
        booking.setId(id);
        booking.setTrip(trip);
        booking.setPassenger(passenger);
        booking.setSeatBooked(dto.getSeatBooked());
        booking.setTotalPrice(trip.getPricePerSeat() * dto.getSeatBooked());
        bookingDao.update(booking);
        return Response.ok().entity(toDto(booking)).build();
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
    public List<BookingDto> getByPassenger(
            @Parameter(description = "ID du passager")
            @PathParam("passengerId") Long passengerId) {
        return bookingDao.findByPassenger(passengerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Lister les réservations d'un trajet")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    @Path("/trip/{tripId}")
    public List<BookingDto> getByTrip(
            @Parameter(description = "ID du trajet")
            @PathParam("tripId") Long tripId) {
        return bookingDao.findByTrip(tripId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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

    // Conversion Booking → BookingDto
    private BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setSeatBooked(booking.getSeatBooked());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setBookedAt(booking.getBookedAt() != null ? booking.getBookedAt().toString() : null);
        dto.setDriverRating(booking.getDriverRating());
        dto.setPassengerRating(booking.getPassengerRating());
        dto.setDriverComment(booking.getDriverComment());
        dto.setPassengerComment(booking.getPassengerComment());
        dto.setBookingStatus(booking.getBookingStatus() != null ? booking.getBookingStatus().toString() : null);
        dto.setTripId(booking.getTrip() != null ? booking.getTrip().getId() : null);
        dto.setPassengerId(booking.getPassenger() != null ? booking.getPassenger().getId() : null);
        return dto;
    }
}