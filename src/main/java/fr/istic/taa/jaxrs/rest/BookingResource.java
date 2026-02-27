package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.BookingDao;
import fr.istic.taa.jaxrs.domain.Booking;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("bookings")
@Produces("application/json")
@Consumes("application/json")
public class BookingResource {

    private BookingDao bookingDao = new BookingDao();

    // GET /bookings
    @GET
    public List<Booking> getAll() {
        return bookingDao.findAll();
    }

    // GET /bookings/1
    @GET
    @Path("/{id}")
    public Booking getById(@PathParam("id") Long id) {
        return bookingDao.findOne(id);
    }

    // POST /bookings
    @POST
    public Response create(Booking booking) {
        bookingDao.save(booking);
        return Response.status(201).entity(booking).build();
    }

    // PUT /bookings/1
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Booking booking) {
        booking.setId(id);
        bookingDao.update(booking);
        return Response.ok().entity(booking).build();
    }

    // DELETE /bookings/1
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        bookingDao.deleteById(id);
        return Response.ok().build();
    }


    // GET /bookings/passenger/1
    @GET
    @Path("/passenger/{passengerId}")
    public List<Booking> getByPassenger(@PathParam("passengerId") Long passengerId) {
        return bookingDao.findByPassenger(passengerId);
    }

    // GET /bookings/trip/1
    @GET
    @Path("/trip/{tripId}")
    public List<Booking> getByTrip(@PathParam("tripId") Long tripId) {
        return bookingDao.findByTrip(tripId);
    }

    // PUT /bookings/1/rate-driver?rating=5
    @PUT
    @Path("/{id}/rate-driver")
    public Response rateDriver(
            @PathParam("id") Long id,
            @QueryParam("rating") int rating) {
        bookingDao.rateDriver(id, rating);
        return Response.ok().build();
    }

    // PUT /bookings/1/rate-passenger?rating=5
    @PUT
    @Path("/{id}/rate-passenger")
    public Response ratePassenger(
            @PathParam("id") Long id,
            @QueryParam("rating") int rating) {
        bookingDao.ratePassenger(id, rating);
        return Response.ok().build();
    }

}
