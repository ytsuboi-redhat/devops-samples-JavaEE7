package com.redhat.sample.cicd.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.redhat.sample.cicd.entity.jpa.Product;
import com.redhat.sample.cicd.exception.TargetNotFoundException;

@Stateless
public class ProductDaoBean implements ProductDao {

    @PersistenceContext(unitName = "OrderPU")
    private EntityManager entityManager;

    @Override
    public Product find(String productNo) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager
                    .getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder
                    .createQuery(Product.class);

            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(
                    criteriaBuilder.equal(root.get("productNo"), productNo));

            TypedQuery<Product> typedQuery = entityManager
                    .createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new TargetNotFoundException(e);
        }
    }

}
