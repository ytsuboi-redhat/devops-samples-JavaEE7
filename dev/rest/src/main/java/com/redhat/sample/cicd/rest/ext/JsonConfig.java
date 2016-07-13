package com.redhat.sample.cicd.rest.ext;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;
import com.redhat.sample.cicd.rest.schema.SearchOrderView;

@Provider
@Produces({ MediaType.APPLICATION_JSON })
public class JsonConfig implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.getSerializationConfig().addMixInAnnotations(ReceivedOrder.class,
                SearchOrderView.class);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return MAPPER;
    }

}
