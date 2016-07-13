package com.redhat.sample.cicd.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;

public class OrderDaoBeanTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test_search() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        ReceivedOrder entity1 = new ReceivedOrder();
        entity1.setOrderNo("20160701-001");
        entity1.setOrdered(df.parse("20160701"));
        entity1.setDesiredDeliveryDate(df.parse("20160710"));
        entity1.setClaimNo("001");
        entity1.setBillingDate(df.parse("20160810"));
        entity1.setTotalAmount(100000);
        entity1.setTax(8000);
        entity1.setBillingAmount(108000);
        entity1.setBilled(true);
        entity1.setModified(new Date());

        ReceivedOrder entity2 = new ReceivedOrder();
        entity2.setOrderNo("20160701-002");
        entity2.setOrdered(df.parse("20160701"));
        entity2.setDesiredDeliveryDate(df.parse("20160720"));
        entity2.setClaimNo("002");
        entity2.setBillingDate(df.parse("20160820"));
        entity2.setTotalAmount(200000);
        entity2.setTax(16000);
        entity2.setBillingAmount(216000);
        entity2.setDisabled(true);
        entity2.setModified(new Date());

        List<ReceivedOrder> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);

        // Rootモックを作成
        Root<ReceivedOrder> root = Mockito.mock(Root.class);
        // Predicateモックの作成
        Predicate predicate = Mockito.mock(Predicate.class);
        // CriteriaBuilderモックの作成
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(criteriaBuilder.like(root.get("orderNo"), "20160701%"))
                .thenReturn(predicate);
        // CriteriaQueryモックの作成
        CriteriaQuery<ReceivedOrder> criteriaQuery = Mockito.mock(CriteriaQuery.class);
        // TypedQueryモックの作成
        TypedQuery<ReceivedOrder> typedQuery = Mockito.mock(TypedQuery.class);
        // EntityManagerモックの作成
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        // CriteriaBuilderモックの振る舞いを定義
        Mockito.when(criteriaBuilder.createQuery(ReceivedOrder.class))
                .thenReturn(criteriaQuery);
        // CriteriaQueryモックの振る舞いを定義
        Mockito.when(criteriaQuery.from(ReceivedOrder.class)).thenReturn(root);
        Mockito.when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
        // TypedQueryモックの振る舞いを定義
        ArgumentCaptor<Integer> setFirstResultCaptor = ArgumentCaptor
                .forClass(Integer.class);
        ArgumentCaptor<Integer> setMaxResultsCaptor = ArgumentCaptor
                .forClass(Integer.class);
        Mockito.when(typedQuery.setFirstResult(setFirstResultCaptor.capture()))
                .thenReturn(typedQuery);
        Mockito.when(typedQuery.setMaxResults(setMaxResultsCaptor.capture()))
                .thenReturn(typedQuery);
        Mockito.when(typedQuery.getResultList()).thenReturn(list);
        // EntityManagerモックの振る舞いを定義
        Mockito.when(entityManager.getCriteriaBuilder())
                .thenReturn(criteriaBuilder);
        Mockito.when(entityManager.createQuery(criteriaQuery))
                .thenReturn(typedQuery);
        // テスト対象サービスをインスタンス化
        OrderDaoBean service = new OrderDaoBean();
        Whitebox.setInternalState(service, "entityManager", entityManager);

        List<ReceivedOrder> result = service.search("20160701%", 10, 100);

        Assert.assertThat(list, CoreMatchers.is(result));
        Assert.assertThat(10, CoreMatchers.is(setFirstResultCaptor.getValue()));
        Assert.assertThat(100, CoreMatchers.is(setMaxResultsCaptor.getValue()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_find() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        ReceivedOrder entity = new ReceivedOrder();
        entity.setOrderNo("20160701-010");
        entity.setOrdered(df.parse("20160701"));
        entity.setDesiredDeliveryDate(df.parse("20160710"));
        entity.setClaimNo("010");
        entity.setBillingDate(df.parse("20160720"));
        entity.setTotalAmount(100000);
        entity.setTax(8000);
        entity.setBillingAmount(108000);
        entity.setBilled(true);
        entity.setModified(new Date());

        // Rootモックを作成
        Root<ReceivedOrder> root = Mockito.mock(Root.class);
        // Predicateモックの作成
        Predicate predicate = Mockito.mock(Predicate.class);
        // CriteriaBuilderモックの作成
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(
                criteriaBuilder.equal(root.get("orderNo"), entity.getOrderNo()))
                .thenReturn(predicate);
        // CriteriaQueryモックの作成
        CriteriaQuery<ReceivedOrder> criteriaQuery = Mockito.mock(CriteriaQuery.class);
        // TypedQueryモックの作成
        TypedQuery<ReceivedOrder> typedQuery = Mockito.mock(TypedQuery.class);
        // EntityManagerモックの作成
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        // CriteriaBuilderモックの振る舞いを定義
        Mockito.when(criteriaBuilder.createQuery(ReceivedOrder.class))
                .thenReturn(criteriaQuery);
        // CriteriaQueryモックの振る舞いを定義
        Mockito.when(criteriaQuery.from(ReceivedOrder.class)).thenReturn(root);
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
        OrderDaoBean service = new OrderDaoBean();
        Whitebox.setInternalState(service, "entityManager", entityManager);

        ReceivedOrder result = service.find(entity.getOrderNo());

        Assert.assertThat(entity, CoreMatchers.is(result));
    }

//    @Test
//    public void test_create() {
//        DateFormat df = new SimpleDateFormat("yyyyMMdd");
//
//        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor
//                .forClass(Order.class);
//        EntityManager entityManager = Mockito.mock(EntityManager.class);
//        Mockito.doNothing().when(entityManager)
//                .persist(argumentCaptor.capture());
//        OrderDaoBean service = new OrderDaoBean();
//        Whitebox.setInternalState(service, "entityManager", entityManager);
//
//        Order entity = new Order();
//        entity.setOrderNo("20160701-010");
//        entity.setOrdered(df.parse("20160701"));
//        entity.setDesiredDeliveryDate(df.parse("20160710"));
//        entity.setClaimNo("010");
//        entity.setBillingDate(df.parse("20160720"));
//        entity.setTotalAmount(100000);
//        entity.setTax(8000);
//        entity.setBillingAmount(108000);
//        entity.setBilled(true);
//        entity.setModified(new Date());
//
//        service.create(entity);
//
//        Assert.assertThat(entity.getKey(),
//                CoreMatchers.is(argumentCaptor.getValue().getKey()));
//        Assert.assertThat(entity.getValue(),
//                CoreMatchers.is(argumentCaptor.getValue().getValue()));
//        Assert.assertThat(entity.getModified(),
//                CoreMatchers.is(argumentCaptor.getValue().getModified()));
//    }
//
//    @Test
//    public void test_update() {
//        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor
//                .forClass(Order.class);
//        EntityManager entityManager = Mockito.mock(EntityManager.class);
//        Mockito.when(entityManager.merge(argumentCaptor.capture()))
//                .thenReturn(null);
//        OrderDaoBean service = new OrderDaoBean();
//        Whitebox.setInternalState(service, "entityManager", entityManager);
//
//        Order entity = new Order();
//        entity.setKey("test2");
//        entity.setValue("value2");
//        entity.setModified(new Date());
//
//        service.update(entity);
//
//        Assert.assertThat(entity.getKey(),
//                CoreMatchers.is(argumentCaptor.getValue().getKey()));
//        Assert.assertThat(entity.getValue(),
//                CoreMatchers.is(argumentCaptor.getValue().getValue()));
//        Assert.assertThat(entity.getModified(),
//                CoreMatchers.is(argumentCaptor.getValue().getModified()));
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void test_remove() {
//        Order entity = new Order();
//        entity.setKey("test3");
//        entity.setValue("value3");
//        entity.setModified(new Date());
//
//        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor
//                .forClass(Order.class);
//
//        // Rootモックを作成
//        Root<Order> root = Mockito.mock(Root.class);
//        // Predicateモックの作成
//        Predicate predicate = Mockito.mock(Predicate.class);
//        // CriteriaBuilderモックの作成
//        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
//        Mockito.when(criteriaBuilder.equal(root.get("key"), entity.getKey()))
//                .thenReturn(predicate);
//        // CriteriaQueryモックの作成
//        CriteriaQuery<Order> criteriaQuery = Mockito.mock(CriteriaQuery.class);
//        // TypedQueryモックの作成
//        TypedQuery<Order> typedQuery = Mockito.mock(TypedQuery.class);
//        // EntityManagerモックの作成
//        EntityManager entityManager = Mockito.mock(EntityManager.class);
//        // CriteriaBuilderモックの振る舞いを定義
//        Mockito.when(criteriaBuilder.createQuery(Order.class))
//                .thenReturn(criteriaQuery);
//        // CriteriaQueryモックの振る舞いを定義
//        Mockito.when(criteriaQuery.from(Order.class)).thenReturn(root);
//        Mockito.when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
//        Mockito.when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
//        // TypedQueryモックの振る舞いを定義
//        Mockito.when(typedQuery.getSingleResult()).thenReturn(entity);
//        // EntityManagerモックの振る舞いを定義
//        Mockito.when(entityManager.getCriteriaBuilder())
//                .thenReturn(criteriaBuilder);
//        Mockito.when(entityManager.createQuery(criteriaQuery))
//                .thenReturn(typedQuery);
//        Mockito.doNothing().when(entityManager)
//                .remove(argumentCaptor.capture());
//        OrderDaoBean service = new OrderDaoBean();
//        Whitebox.setInternalState(service, "entityManager", entityManager);
//
//        service.remove(entity);
//
//        Assert.assertThat(entity.getKey(),
//                CoreMatchers.is(argumentCaptor.getValue().getKey()));
//        Assert.assertThat(entity.getValue(),
//                CoreMatchers.is(argumentCaptor.getValue().getValue()));
//        Assert.assertThat(entity.getModified(),
//                CoreMatchers.is(argumentCaptor.getValue().getModified()));
//    }

}
