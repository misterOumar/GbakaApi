package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.VehicleDao;
import fr.istic.taa.jaxrs.domain.Vehicle;
import fr.istic.taa.jaxrs.dto.VehicleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Vehicles", description = "Gestion des véhicules")
@Path("vehicles")
@Produces("application/json")
@Consumes("application/json")
public class VehicleResource {

    private VehicleDao vehicleDao = new VehicleDao();

    @Operation(summary = "Lister tous les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<VehicleDto> getAll() {
        return vehicleDao
                .findAll()
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Trouver un véhicule par id")
    @ApiResponse(responseCode = "200", description = "Véhicule trouvé")
    @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    @GET
    @Path("/{id}")
    public VehicleDto getById(
            @Parameter(description = "ID du véhicule")
            @PathParam("id") Long id) {
        return  toDto(vehicleDao.findOne(id));

    }

    @Operation(summary = "Créer un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule créé")
    @POST
    public Response create(VehicleDto dto) {

        Vehicle vehicle = new Vehicle();
        vehicle.setMarque(dto.getMarque());
        vehicle.setModel(dto.getModel());
        vehicle.setColor(dto.getColor());
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setYear(dto.getYear());
        vehicle.setMaxSeats(dto.getMaxSeats());
        vehicle.setHasAirConditioning(dto.isHasAirConditioning());

        vehicleDao.save(vehicle);
        return Response.status(201).entity(toDto(vehicle)).build();
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule modifié")
    @PUT
    @Path("/{id}")
    public Response update(
            @Parameter(description = "ID du véhicule")
            @PathParam("id") Long id, VehicleDto dto) {

        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setMarque(dto.getMarque());
        vehicle.setModel(dto.getModel());
        vehicle.setColor(dto.getColor());
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setYear(dto.getYear());
        vehicle.setMaxSeats(dto.getMaxSeats());
        vehicle.setHasAirConditioning(dto.isHasAirConditioning());


        vehicleDao.update(vehicle);
        return Response.ok().entity(toDto(vehicle)).build();
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

    private VehicleDto toDto(Vehicle vehicle){
        VehicleDto dto = new VehicleDto();
        dto.setId(vehicle.getId());
        dto.setMarque(vehicle.getMarque());
        dto.setModel(vehicle.getModel());
        dto.setColor(vehicle.getColor());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setYear(vehicle.getYear());
        dto.setMaxSeats(vehicle.getMaxSeats());
        dto.setHasAirConditioning(dto.isHasAirConditioning());

        return dto;
    }
}