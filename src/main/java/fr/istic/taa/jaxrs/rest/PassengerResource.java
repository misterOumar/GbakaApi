package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.PassengerDao;
import fr.istic.taa.jaxrs.domain.Passenger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Tag(name = "Passengers", description = "Gestion des passagers")
@Path("passengers")
@Produces("application/json")
@Consumes("application/json")
public class PassengerResource {

    private PassengerDao passengerDao = new PassengerDao();

    @Operation(summary = "Lister tous les passagers")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<Passenger> getAll() {
        return passengerDao.findAll();
    }

    @Operation(summary = "Trouver un passager par id")
    @ApiResponse(responseCode = "200", description = "Passager trouvé")
    @ApiResponse(responseCode = "404", description = "Passager non trouvé")
    @GET
    @Path("/{id}")
    public Passenger getById(
            @Parameter(description = "ID du passager")
            @PathParam("id") Long id) {
        return passengerDao.findOne(id);
    }

    @Operation(summary = "Créer un passager")
    @ApiResponse(responseCode = "201", description = "Passager créé")
    @POST
    public Response create(Passenger passenger) {
        passengerDao.save(passenger);
        return Response.status(201).entity(passenger).build();
    }

    @Operation(summary = "Modifier un passager")
    @ApiResponse(responseCode = "200", description = "Passager modifié")
    @PUT
    @Path("/{id}")
    public Response update(
            @Parameter(description = "ID du passager")
            @PathParam("id") Long id, Passenger passenger) {
        passenger.setId(id);
        passengerDao.update(passenger);
        return Response.ok().entity(passenger).build();
    }

    @Operation(summary = "Supprimer un passager")
    @ApiResponse(responseCode = "200", description = "Passager supprimé")
    @DELETE
    @Path("/{id}")
    public Response delete(
            @Parameter(description = "ID du passager")
            @PathParam("id") Long id) {
        passengerDao.deleteById(id);
        return Response.ok().build();
    }
}