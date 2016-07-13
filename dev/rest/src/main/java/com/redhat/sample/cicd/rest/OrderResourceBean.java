package com.redhat.sample.cicd.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;
import com.redhat.sample.cicd.rest.schema.OrderResponse;
import com.redhat.sample.cicd.service.OrderService;

public class OrderResourceBean implements OrderResource {

    @Inject
    OrderService service;

    @Override
    public OrderResponse<List<ReceivedOrder>> search(String orderNo,
            String offset, String count) {
        try {
            int _offset = Integer.parseInt(offset);
            int _count = Integer.parseInt(count);

            List<ReceivedOrder> list = service.searchOrders(orderNo, _offset,
                    _count);
            return new OrderResponse<List<ReceivedOrder>>(
                    OrderResponse.RESPONSE_CODE_SUCCESS, null, list);
        } catch (NumberFormatException e) {
            throw new BadRequestException(
                    "offset or(and) count are not numeric");
        }
    }

    @Override
    public OrderResponse<ReceivedOrder> get(String key) {
        ReceivedOrder entity = service.find(key);
        if (entity == null) {
            throw new javax.ws.rs.NotFoundException();
        }
        return new OrderResponse<ReceivedOrder>(
                OrderResponse.RESPONSE_CODE_SUCCESS, null, entity);
    }

    @Override
    public OrderResponse<Void> post(String key, ReceivedOrder order) {
        if (!key.equals(order.getOrderNo())) {
            throw new BadRequestException("posted entity is not same as path");
        }
        service.create(order);
        return new OrderResponse<Void>(OrderResponse.RESPONSE_CODE_SUCCESS);
    }

    @Override
    public OrderResponse<Void> put(String key, ReceivedOrder order) {
        if (!key.equals(order.getOrderNo())) {
            throw new BadRequestException("posted entity is not same as path");
        }
        return new OrderResponse<Void>(OrderResponse.RESPONSE_CODE_SUCCESS);
    }

    @Override
    public OrderResponse<Void> remove(String key) {
        service.remove(key);
        return new OrderResponse<Void>(OrderResponse.RESPONSE_CODE_SUCCESS);
    }

}
