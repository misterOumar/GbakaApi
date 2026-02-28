package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.DriverDao;
import fr.istic.taa.jaxrs.domain.Driver;
import fr.istic.taa.jaxrs.dto.DriverCreateDto;
import fr.istic.taa.jaxrs.dto.DriverDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Drivers", description = "Gestion des chauffeurs")
@Path("drivers")
@Produces("application/json")
@Consumes("application/json")
public class DriverResource {

    private DriverDao driverDao = new DriverDao();

    @Operation(summary = "Lister tous les chauffeurs")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GET
    public List<DriverDto> getAll() {
        return driverDao.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Trouver un chauffeur par id")
    @ApiResponse(responseCode = "200", description = "Chauffeur trouvé")
    @ApiResponse(responseCode = "404", description = "Chauffeur non trouvé")
    @GET
    @Path("/{id}")
    public DriverDto getById(
            @Parameter(description = "ID du chauffeur")
            @PathParam("id") Long id) {
        return toDto(driverDao.findOne(id));
    }

    @Operation(summary = "Créer un chauffeur")
    @ApiResponse(responseCode = "201", description = "Chauffeur créé")
    @POST
    public Response create(DriverCreateDto dto) {
        Driver driver = new Driver();
        driver.setFirstName(dto.getFirstName());
        driver.setLastName(dto.getLastName());
        driver.setEmail(dto.getEmail());
        driver.setPassword(dto.getPassword());
        driver.setPhone(dto.getPhone());
        driver.setBirthDate(dto.getBirthDate() != null ? LocalDate.parse(dto.getBirthDate()) : null);
        driver.setLicenceNumber(dto.getLicenceNumber());
        driver.setLicenseExpiry(dto.getLicenceExpiry() != null ? LocalDate.parse(dto.getLicenceExpiry()) : null);
        driverDao.save(driver);
        return Response.status(201).entity(toDto(driver)).build();
    }

    @Operation(summary = "Modifier un chauffeur")
    @ApiResponse(responseCode = "200", description = "Chauffeur modifié")
    @PUT
    @Path("/{id}")
    public Response update(
            @Parameter(description = "ID du chauffeur")
            @PathParam("id") Long id, DriverCreateDto dto) {
        Driver driver = new Driver();
        driver.setId(id);
        driver.setFirstName(dto.getFirstName());
        driver.setLastName(dto.getLastName());
        driver.setEmail(dto.getEmail());
        driver.setPassword(dto.getPassword());
        driver.setPhone(dto.getPhone());
        driver.setBirthDate(dto.getBirthDate() != null ? LocalDate.parse(dto.getBirthDate()) : null);
        driver.setLicenceNumber(dto.getLicenceNumber());
        driver.setLicenseExpiry(dto.getLicenceExpiry() != null ? LocalDate.parse(dto.getLicenceExpiry()) : null);
        driverDao.update(driver);
        return Response.ok().entity(toDto(driver)).build();
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

    // Conversion Driver → DriverDto
    private DriverDto toDto(Driver driver) {
        DriverDto dto = new DriverDto();
        dto.setId(driver.getId());
        dto.setFirstName(driver.getFirstName());
        dto.setLastName(driver.getLastName());
        dto.setEmail(driver.getEmail());
        dto.setPhone(driver.getPhone());
        dto.setLicenceNumber(driver.getLicenceNumber());
        dto.setRatingAverage(driver.getRatingAverage());
        dto.setTotalTripsOffered(driver.getTotalTripsOffered());
        return dto;
    }
}