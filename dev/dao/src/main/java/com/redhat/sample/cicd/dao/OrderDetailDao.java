package com.redhat.sample.cicd.dao;

import javax.ejb.Local;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrderDetail;

@Local
public interface OrderDetailDao {

    ReceivedOrderDetail find(String orderDetailNo);

    void create(ReceivedOrderDetail entity);

    void update(ReceivedOrderDetail entity);

    void remove(String orderDetailNo);
}
