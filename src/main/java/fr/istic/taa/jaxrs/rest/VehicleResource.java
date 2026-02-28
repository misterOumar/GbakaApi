package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.VehicleDao;
import fr.istic.taa.jaxrs.domain.Vehicle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Tag(name = "Vehicles", description = "Gestion des véhicules")
@Path("vehicles")
@Produces("application/json")
@Consumes("application/json")
public class VehicleResource {

    private VehicleDao vehicleDao = new VehicleDao();

    @Operation(summary = "Lister tous les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<Vehicle> getAll() {
        return vehicleDao.findAll();
    }

    @Operation(summary = "Trouver un véhicule par id")
    @ApiResponse(responseCode = "200", description = "Véhicule trouvé")
    @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    @GET
    @Path("/{id}")
    public Vehicle getById(
            @Parameter(description = "ID du véhicule")
            @PathParam("id") Long id) {
        return vehicleDao.findOne(id);
    }

    @Operation(summary = "Créer un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule créé")
    @POST
    public Response create(Vehicle vehicle) {
        vehicleDao.save(vehicle);
        return Response.status(201).entity(vehicle).build();
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule modifié")
    @PUT
    @Path("/{id}")
    public Response update(
            @Parameter(description = "ID du véhicule")
            @PathParam("id") Long id, Vehicle vehicle) {
        vehicle.setId(id);
        vehicleDao.update(vehicle);
        return Response.ok().entity(vehicle).build();
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule supprimé")
    @DELETE
    @Path("/{id}")
    public Response delete(
            @Parameter(description = "ID du véhicule")
            @PathParam("id") Long id) {
        vehicleDao.deleteById(id);
        return Response.ok().build();
    }
}