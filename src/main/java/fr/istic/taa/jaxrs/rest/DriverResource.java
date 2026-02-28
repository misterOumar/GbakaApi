package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.DriverDao;
import fr.istic.taa.jaxrs.domain.Driver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Tag(name = "Drivers", description = "Gestion des chauffeurs")
@Path("drivers")
@Produces("application/json")
@Consumes("application/json")
public class DriverResource {

    private DriverDao driverDao = new DriverDao();

    @Operation(summary = "Lister tous les chauffeurs")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<Driver> getAll() {
        return driverDao.findAll();
    }

    @Operation(summary = "Trouver un chauffeur par id")
    @ApiResponse(responseCode = "200", description = "Chauffeur trouvé")
    @ApiResponse(responseCode = "404", description = "Chauffeur non trouvé")
    @GET
    @Path("/{id}")
    public Driver getById(
            @Parameter(description = "ID du chauffeur")
            @PathParam("id") Long id) {
        return driverDao.findOne(id);
    }

    @Operation(summary = "Créer un chauffeur")
    @ApiResponse(responseCode = "201", description = "Chauffeur créé")
    @POST
    public Response create(Driver driver) {
        driverDao.save(driver);
        return Response.status(201).entity(driver).build();
    }

    @Operation(summary = "Modifier un chauffeur")
    @ApiResponse(responseCode = "200", description = "Chauffeur modifié")
    @PUT
    @Path("/{id}")
    public Response update(
            @Parameter(description = "ID du chauffeur")
            @PathParam("id") Long id, Driver driver) {
        driver.setId(id);
        driverDao.update(driver);
        return Response.ok().entity(driver).build();
    }

    @Operation(summary = "Supprimer un chauffeur")
    @ApiResponse(responseCode = "200", description = "Chauffeur supprimé")
    @DELETE
    @Path("/{id}")
    public Response delete(
            @Parameter(description = "ID du chauffeur")
            @PathParam("id") Long id) {
        driverDao.deleteById(id);
        return Response.ok().build();
    }
}