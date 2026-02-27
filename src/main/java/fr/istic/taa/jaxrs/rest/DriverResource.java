package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.DriverDao;
import fr.istic.taa.jaxrs.dao.VehicleDao;
import fr.istic.taa.jaxrs.domain.Driver;
import fr.istic.taa.jaxrs.domain.Vehicle;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("drivers")
@Produces("application/json")
@Consumes("application/json")
public class DriverResource {

    private DriverDao driverDao = new DriverDao();

    // GET /drivers
    @GET
    public List<Driver> getAll() {
        return driverDao.findAll();
    }

    // GET /drivers/1
    @GET
    @Path("/{id}")
    public Driver getById(@PathParam("id") Long id) {
        return driverDao.findOne(id);
    }

    // POST /drivers
    @POST
    public Response create(Driver driver) {
        driverDao.save(driver);
        return Response.status(201).entity(driver).build();
    }

    // PUT /drivers/1
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Driver driver) {
        driver.setId(id);
        driverDao.update(driver);
        return Response.ok().entity(driver).build();
    }

    // DELETE /drivers/1
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        driverDao.deleteById(id);
        return Response.ok().build();
    }
}
