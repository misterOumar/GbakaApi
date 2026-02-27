package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.PassengerDao;
import fr.istic.taa.jaxrs.domain.Passenger;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("passengers")
@Produces("application/json")
@Consumes("application/json")
public class PassengerResource {

    private PassengerDao passengerDao = new PassengerDao();

    // GET /passengers
    @GET
    public List<Passenger> getAll() {
        return passengerDao.findAll();
    }

    // GET /passengers/1
    @GET
    @Path("/{id}")
    public Passenger getById(@PathParam("id") Long id) {
        return passengerDao.findOne(id);
    }

    // POST /passengers
    @POST
    public Response create(Passenger passenger) {
        passengerDao.save(passenger);
        return Response.status(201).entity(passenger).build();
    }

    // PUT /passengers/1
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Passenger passenger) {
        passenger.setId(id);
        passengerDao.update(passenger);
        return Response.ok().entity(passenger).build();
    }

    // DELETE /passengers/1
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        passengerDao.deleteById(id);
        return Response.ok().build();
    }
}
