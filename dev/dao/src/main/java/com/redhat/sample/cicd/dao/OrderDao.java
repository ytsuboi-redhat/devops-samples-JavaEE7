package com.redhat.sample.cicd.dao;

import java.util.List;

import javax.ejb.Local;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;

@Local
public interface OrderDao {

    List<ReceivedOrder> search(String orderNo, int offset, int count);

    ReceivedOrder find(String orderNo);

    void create(ReceivedOrder entity);

    void update(ReceivedOrder entity);

    void remove(String orderNo);
}
