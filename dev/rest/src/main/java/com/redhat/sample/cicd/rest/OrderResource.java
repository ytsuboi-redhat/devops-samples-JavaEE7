package com.redhat.sample.cicd.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;
import com.redhat.sample.cicd.rest.schema.OrderResponse;

@Path("/")
public interface OrderResource {

    @GET
    @Path("/orders")
    @Produces({ MediaType.APPLICATION_JSON })
    OrderResponse<List<ReceivedOrder>> search(
            @QueryParam("filter") String orderNo,
            @QueryParam("offset") String offset,
            @QueryParam("count") String count);

    @GET
    @Path("/order/{key}")
    @Produces({ MediaType.APPLICATION_JSON })
    OrderResponse<ReceivedOrder> get(@PathParam("key") String key);

    @POST
    @Path("/order/{key}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    OrderResponse<Void> post(@PathParam("key") String key, ReceivedOrder order);

    @PUT
    @Path("/order/{key}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    OrderResponse<Void> put(@PathParam("key") String key, ReceivedOrder order);

    @DELETE
    @Path("/order/{key}")
    @Produces({ MediaType.APPLICATION_JSON })
    OrderResponse<Void> remove(@PathParam("key") String key);
}
