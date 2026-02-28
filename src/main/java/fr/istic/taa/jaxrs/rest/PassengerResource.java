package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.PassengerDao;
import fr.istic.taa.jaxrs.domain.Passenger;
import fr.istic.taa.jaxrs.domain.enums.PaymentMethod;
import fr.istic.taa.jaxrs.dto.PassengerCreateDto;
import fr.istic.taa.jaxrs.dto.PassengerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Passengers", description = "Gestion des passagers")
@Path("passengers")
@Produces("application/json")
@Consumes("application/json")
public class PassengerResource {

    private PassengerDao passengerDao = new PassengerDao();

    @Operation(summary = "Lister tous les passagers")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<PassengerDto> getAll() {
        return passengerDao.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Trouver un passager par id")
    @ApiResponse(responseCode = "200", description = "Passager trouvé")
    @ApiResponse(responseCode = "404", description = "Passager non trouvé")
    @GET
    @Path("/{id}")
    public PassengerDto getById(
            @Parameter(description = "ID du passager")
            @PathParam("id") Long id) {
        //return passengerDao.findOne(id);
        return toDto(passengerDao.findOne(id));
    }

    @Operation(summary = "Créer un passager")
    @ApiResponse(responseCode = "201", description = "Passager créé")
    @POST
    public Response create(PassengerCreateDto dto) {
//        passengerDao.save(passenger);
//        return Response.status(201).entity(toDto(passenger)).build();
        Passenger passenger = new Passenger();
        passenger.setFirstName(dto.getFirstName());
        passenger.setLastName(dto.getLastName());
        passenger.setEmail(dto.getEmail());
        passenger.setPassword(dto.getPassword());
        passenger.setPhone(dto.getPhone());
        passenger.setBirthDate(
                dto.getBirthDate() != null ?
                        LocalDate.parse(dto.getBirthDate()) : null
        );
        passenger.setPaymentMethod(dto.getPaymentMethod());
        passengerDao.save(passenger);
        return Response.status(201).entity(toDto(passenger)).build();
    }

    @Operation(summary = "Modifier un passager")
    @ApiResponse(responseCode = "200", description = "Passager modifié")
    @PUT
    @Path("/{id}")
    public Response update(
            @Parameter(description = "ID du passager")
            @PathParam("id") Long id, PassengerCreateDto dto) {
//        passenger.setId(id);
//        passengerDao.update(passenger);
//        return Response.ok().entity(toDto(passenger)).build();
        Passenger passenger = new Passenger();
        passenger.setId(id);
        passenger.setFirstName(dto.getFirstName());
        passenger.setLastName(dto.getLastName());
        passenger.setEmail(dto.getEmail());
        passenger.setPassword(dto.getPassword());
        passenger.setPhone(dto.getPhone());
        passenger.setBirthDate(
                dto.getBirthDate() != null ?
                        LocalDate.parse(dto.getBirthDate()) : null
        );
        passenger.setPaymentMethod(dto.getPaymentMethod());
        passengerDao.update(passenger);
        return Response.ok().entity(toDto(passenger)).build();
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


    // Conversion Passenger → PassengerDto
    private PassengerDto toDto(Passenger passenger) {
        PassengerDto dto = new PassengerDto();
        dto.setId(passenger.getId());
        dto.setFirstName(passenger.getFirstName());
        dto.setLastName(passenger.getLastName());
        dto.setEmail(passenger.getEmail());
        dto.setPhone(passenger.getPhone());
        dto.setRatingByDrivers(passenger.getRatingByDrivers());
        dto.setPaymentMethod(passenger.getPaymentMethod().toString());
        return dto;
    }



}