package com.redhat.sample.cicd.dao;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrderDetail;
import com.redhat.sample.cicd.exception.TargetNotFoundException;

@Stateless
public class OrderDetailDaoBean implements OrderDetailDao {

    @PersistenceContext(unitName = "OrderPU")
    private EntityManager entityManager;

    @Override
    public ReceivedOrderDetail find(String orderNo) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager
                    .getCriteriaBuilder();
            CriteriaQuery<ReceivedOrderDetail> criteriaQuery = criteriaBuilder
                    .createQuery(ReceivedOrderDetail.class);

            Root<ReceivedOrderDetail> root = criteriaQuery
                    .from(ReceivedOrderDetail.class);
            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get("orderNo"), orderNo));

            TypedQuery<ReceivedOrderDetail> typedQuery = entityManager
                    .createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new TargetNotFoundException(e);
        }
    }

    @Override
    public void create(ReceivedOrderDetail entity) {
        entity.setModified(new Date());
        entityManager.persist(entity);
    }

    @Override
    public void update(ReceivedOrderDetail entity) {
        entity.setModified(new Date());
        entityManager.merge(entity);
    }

    @Override
    public void remove(String orderDetailNo) {
        try {
            ReceivedOrderDetail entity = entityManager
                    .getReference(ReceivedOrderDetail.class, orderDetailNo);
            entityManager.remove(entity);
        } catch (EntityNotFoundException e) {
            throw new TargetNotFoundException(e);
        }
    }

}
