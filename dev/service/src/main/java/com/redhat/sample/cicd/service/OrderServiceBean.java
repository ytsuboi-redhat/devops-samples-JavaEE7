package com.redhat.sample.cicd.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.NotImplementedException;

import com.redhat.sample.cicd.dao.OrderDao;
import com.redhat.sample.cicd.dao.OrderDetailDao;
import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;
import com.redhat.sample.cicd.entity.jpa.ReceivedOrderDetail;

@Stateless
public class OrderServiceBean implements OrderService {

    @Inject
    private OrderDao orderDao;

    @Inject
    private OrderDetailDao orderDetailDao;

    @Override
    public List<ReceivedOrder> searchOrders(String orderNo, int offset,
            int count) {
        return orderDao.search(orderNo, offset, count);
    }

    @Override
    public ReceivedOrder find(String orderNo) {
        return orderDao.find(orderNo);
    }

    @Override
    public ReceivedOrderDetail findDetail(String orderDetailNo) {
        return orderDetailDao.find(orderDetailNo);
    }

    @Override
    public void create(ReceivedOrder order) {
        orderDao.create(order);
    }

    @Override
    public void createDetail(ReceivedOrderDetail orderDetail) {
        throw new NotImplementedException("not implemented");
    }

    @Override
    public void update(ReceivedOrder order) {
        orderDao.update(order);
    }

    @Override
    public void updateDetail(ReceivedOrderDetail orderDetail) {
        throw new NotImplementedException("not implemented");
    }

    @Override
    public void remove(String orderNo) {
        
        orderDao.remove(orderNo);
    }

    @Override
    public void removeDetail(String orderDetailNo) {
        throw new NotImplementedException("not implemented");
    }

}
