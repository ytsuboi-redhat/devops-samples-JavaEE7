package com.redhat.sample.cicd.service;

import java.util.List;

import javax.ejb.Local;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;
import com.redhat.sample.cicd.entity.jpa.ReceivedOrderDetail;

@Local
public interface OrderService {

    List<ReceivedOrder> searchOrders(String orderNo, int offset, int count);

    ReceivedOrder find(String orderNo);

    ReceivedOrderDetail findDetail(String orderDetailNo);

    void create(ReceivedOrder order);

    void createDetail(ReceivedOrderDetail orderDetail);

    void update(ReceivedOrder order);

    void updateDetail(ReceivedOrderDetail orderDetail);

    void remove(String orderNo);

    void removeDetail(String orderDetailNo);

}
