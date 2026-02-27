package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.VehicleDao;
import fr.istic.taa.jaxrs.domain.Vehicle;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("vehicles")
@Produces("application/json")
@Consumes("application/json")
public class VehicleResource {

    private VehicleDao vehicleDao = new VehicleDao();

    // GET /vehicles
    @GET
    public List<Vehicle> getAll() {
        return vehicleDao.findAll();
    }

    // GET /vehicles/1
    @GET
    @Path("/{id}")
    public Vehicle getById(@PathParam("id") Long id) {
        return vehicleDao.findOne(id);
    }

    // POST /vehicles
    @POST
    public Response create(Vehicle vehicle) {
        vehicleDao.save(vehicle);
        return Response.status(201).entity(vehicle).build();
    }

    // PUT /vehicles/1
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Vehicle vehicle) {
        vehicle.setId(id);
        vehicleDao.update(vehicle);
        return Response.ok().entity(vehicle).build();
    }

    // DELETE /vehicles/1
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        vehicleDao.deleteById(id);
        return Response.ok().build();
    }
}