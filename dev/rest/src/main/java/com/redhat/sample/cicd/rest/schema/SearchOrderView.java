package com.redhat.sample.cicd.rest.schema;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrderDetail;

public interface SearchOrderView {

    @JsonIgnore
    List<ReceivedOrderDetail> getOrderDetails();
}
