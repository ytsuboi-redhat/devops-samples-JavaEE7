package com.redhat.sample.cicd.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;
import com.redhat.sample.cicd.exception.TargetNotFoundException;

@Stateless
public class OrderDaoBean implements OrderDao {

    @PersistenceContext(unitName = "OrderPU")
    private EntityManager entityManager;

    @Override
    public List<ReceivedOrder> search(String orderNo, int offset, int count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReceivedOrder> criteriaQuery = criteriaBuilder
                .createQuery(ReceivedOrder.class);

        Root<ReceivedOrder> root = criteriaQuery.from(ReceivedOrder.class);
        criteriaQuery.select(root);
        if (!StringUtils.isEmpty(orderNo)) {
            criteriaQuery
                    .where(criteriaBuilder.like(root.get("orderNo"), orderNo));
        }
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("orderNo")));

        TypedQuery<ReceivedOrder> typedQuery = entityManager
                .createQuery(criteriaQuery).setFirstResult(offset)
                .setMaxResults(count);
        return typedQuery.getResultList();
    }

    @Override
    public ReceivedOrder find(String orderNo) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager
                    .getCriteriaBuilder();
            CriteriaQuery<ReceivedOrder> criteriaQuery = criteriaBuilder
                    .createQuery(ReceivedOrder.class);

            Root<ReceivedOrder> root = criteriaQuery.from(ReceivedOrder.class);
            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get("orderNo"), orderNo));

            TypedQuery<ReceivedOrder> typedQuery = entityManager
                    .createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new TargetNotFoundException(e);
        }
    }

    @Override
    public void create(ReceivedOrder entity) {
        entity.setModified(new Date());
        entityManager.persist(entity);
    }

    @Override
    public void update(ReceivedOrder entity) {
        entity.setModified(new Date());
        entityManager.merge(entity);
    }

    @Override
    public void remove(String orderNo) {
        try {
            ReceivedOrder entity = entityManager
                    .getReference(ReceivedOrder.class, orderNo);
            entityManager.remove(entity);
        } catch (EntityNotFoundException e) {
            throw new TargetNotFoundException(e);
        }
    }

}
