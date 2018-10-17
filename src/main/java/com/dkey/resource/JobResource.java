package com.dkey.resource;


import com.dkey.business.JobBusiness;
import com.dkey.entity.Job;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.net.URI;


@Path("job")
public class JobResource {

    @Inject
    private JobBusiness jobBusiness;

    @GET
    public Response findAll(){

        return Response
                .ok(jobBusiness.findAll())
                .build();
    }


    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        Response response;
        Jsonb jsonb = JsonbBuilder.create();
        String jsonString = jsonb.toJson(jobBusiness.findById(id));
        response = Response.ok(jsonString).build();

        return response;

//        return Response.ok(jobBusiness.findById(id).orElseGet(null)).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response save (@FormParam("companyName") @NotBlank String companyName,
                          @FormParam("description") @NotBlank String description,
                          @FormParam("salary") @NotNull String salary,
                          @FormParam("office") @NotBlank String office){

        Job job = jobBusiness.save( Job.build(companyName, description, salary , office ));

        return Response
                .created(URI.create("job/"+ job.getId()))
                .build();

    }

}
