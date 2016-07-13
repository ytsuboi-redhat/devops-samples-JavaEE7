package com.redhat.sample.cicd.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.redhat.sample.cicd.entity.jpa.Product;

public class ProductDaoBeanTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test_find() {
        Product entity = new Product();
        entity.setProductNo("1");
        entity.setName("product-1");
        entity.setCategory("cat1");
        entity.setUnit(10);
        entity.setPrice(100);
        entity.setIntroduction("intro");
        entity.setModified(new Date());

        // Rootモックを作成
        Root<Product> root = Mockito.mock(Root.class);
        // Predicateモックの作成
        Predicate predicate = Mockito.mock(Predicate.class);
        // CriteriaBuilderモックの作成
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(criteriaBuilder.equal(root.get("productNo"),
                entity.getProductNo())).thenReturn(predicate);
        // CriteriaQueryモックの作成
        CriteriaQuery<Product> criteriaQuery = Mockito
                .mock(CriteriaQuery.class);
        // TypedQueryモックの作成
        TypedQuery<Product> typedQuery = Mockito.mock(TypedQuery.class);
        // EntityManagerモックの作成
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        // CriteriaBuilderモックの振る舞いを定義
        Mockito.when(criteriaBuilder.createQuery(Product.class))
                .thenReturn(criteriaQuery);
        // CriteriaQueryモックの振る舞いを定義
        Mockito.when(criteriaQuery.from(Product.class)).thenReturn(root);
        Mockito.when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
        // TypedQueryモックの振る舞いを定義
        Mockito.when(typedQuery.getSingleResult()).thenReturn(entity);
        // EntityManagerモックの振る舞いを定義
        Mockito.when(entityManager.getCriteriaBuilder())
                .thenReturn(criteriaBuilder);
        Mockito.when(entityManager.createQuery(criteriaQuery))
                .thenReturn(typedQuery);
        // テスト対象サービスをインスタンス化
        ProductDaoBean service = new ProductDaoBean();
        Whitebox.setInternalState(service, "entityManager", entityManager);

        Product result = service.find(entity.getProductNo());

        Assert.assertThat(entity, CoreMatchers.is(result));
    }

}
