package fr.istic.taa.jaxrs.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

@Path("/api")
public class SwaggerResource {

    @GET
    public byte[] get() {
        try {
            return Files.readAllBytes(FileSystems.getDefault()
                    .getPath("src/main/webapp/swagger/index.html"));
        } catch (IOException e) {
            return null;
        }
    }

    @GET
    @Path("{path:.*}")
    public byte[] get(@PathParam("path") String path) {
        try {
            return Files.readAllBytes(FileSystems.getDefault()
                    .getPath("src/main/webapp/swagger/" + path));
        } catch (IOException e) {
            return null;
        }
    }
}