package com.redhat.sample.cicd.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.redhat.sample.cicd.dao.OrderDao;
import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;

public class OrderServiceBeanTest {

    @Test
    public void test_searchOrders() throws ParseException {
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

        List<ReceivedOrder> list = Arrays.asList(entity1, entity2);

        // DAOモックを作成
        OrderDao dao = Mockito.mock(OrderDao.class);
        Mockito.when(dao.search("201607%", 0, 10)).thenReturn(list);
        // テスト対象サービスをインスタンス化
        OrderServiceBean service = new OrderServiceBean();
        Whitebox.setInternalState(service, "orderDao", dao);

        List<ReceivedOrder> result = service.searchOrders("201607%", 0, 10);

        Assert.assertThat(result.size(), CoreMatchers.is(2));
        Assert.assertThat(result.get(0),
                CoreMatchers.is(Matchers.samePropertyValuesAs(entity1)));
        Assert.assertThat(result.get(1),
                CoreMatchers.is(Matchers.samePropertyValuesAs(entity2)));
    }

    //    @Test
    //    public void test_find() {
    //        Product entity = new Product();
    //        entity.setId(1);
    //        entity.setKey("test0");
    //        entity.setValue("value0");
    //        entity.setModified(new Date());
    //
    //        // DAOモックを作成
    //        ProductDao kvStoreDao = Mockito.mock(ProductDao.class);
    //        Mockito.when(kvStoreDao.find(entity.getKey())).thenReturn(entity);
    //        // テスト対象サービスをインスタンス化
    //        KvStoreServiceBean service = new KvStoreServiceBean();
    //        Whitebox.setInternalState(service, "kvStoreDao", kvStoreDao);
    //
    //        Product result = service.find(entity.getKey());
    //
    //        Assert.assertThat(entity, CoreMatchers.is(result));
    //    }
    //
    //    @Test
    //    public void test_create() {
    //        ArgumentCaptor<Product> keyValueStoreCaptor = ArgumentCaptor
    //                .forClass(Product.class);
    //        ArgumentCaptor<Order> operationLogCaptor = ArgumentCaptor
    //                .forClass(Order.class);
    //        ProductDao kvStoreDao = Mockito.mock(ProductDao.class);
    //        Mockito.doNothing().when(kvStoreDao)
    //                .create(keyValueStoreCaptor.capture());
    //        OrderDao operationLogDao = Mockito.mock(OrderDao.class);
    //        Mockito.doNothing().when(operationLogDao)
    //                .create(operationLogCaptor.capture());
    //        KvStoreServiceBean service = new KvStoreServiceBean();
    //        Whitebox.setInternalState(service, "kvStoreDao", kvStoreDao);
    //        Whitebox.setInternalState(service, "operationLogDao", operationLogDao);
    //
    //        Product entity = new Product();
    //        entity.setKey("test1");
    //        entity.setValue("value1");
    //        entity.setModified(new Date());
    //
    //        Order operationLog = new Order();
    //        operationLog.setOperation(
    //                String.format("KeyValueStore[%s] created", entity.getKey()));
    //
    //        service.create(entity);
    //
    //        Assert.assertThat(entity.getKey(),
    //                CoreMatchers.is(keyValueStoreCaptor.getValue().getKey()));
    //        Assert.assertThat(entity.getValue(),
    //                CoreMatchers.is(keyValueStoreCaptor.getValue().getValue()));
    //        Assert.assertThat(entity.getModified(),
    //                CoreMatchers.is(keyValueStoreCaptor.getValue().getModified()));
    //        Assert.assertThat(operationLog.getOperation(),
    //                CoreMatchers.is(operationLogCaptor.getValue().getOperation()));
    //        Assert.assertThat(operationLog.getOperated(),
    //                CoreMatchers.is(operationLogCaptor.getValue().getOperated()));
    //    }
    //
    //    @Test
    //    public void test_update() throws ParseException {
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //
    //        String key = "test2";
    //        String valueBefore = "value2";
    //        String valueAfter = "ばりゅー２";
    //        Date modified = sdf.parse("2016-05-30 00:00:00");
    //
    //        Product keyValueStore = new Product();
    //        keyValueStore.setId(1);
    //        keyValueStore.setKey(key);
    //        keyValueStore.setValue(valueBefore);
    //        keyValueStore.setModified(modified);
    //
    //        ArgumentCaptor<Product> keyValueStoreCaptor = ArgumentCaptor
    //                .forClass(Product.class);
    //        ArgumentCaptor<Order> operationLogCaptor = ArgumentCaptor
    //                .forClass(Order.class);
    //        ProductDao kvStoreDao = Mockito.mock(ProductDao.class);
    //        Mockito.when(kvStoreDao.find(key)).thenReturn(keyValueStore);
    //        Mockito.doNothing().when(kvStoreDao)
    //                .update(keyValueStoreCaptor.capture());
    //        OrderDao operationLogDao = Mockito.mock(OrderDao.class);
    //        Mockito.doNothing().when(operationLogDao)
    //                .create(operationLogCaptor.capture());
    //        KvStoreServiceBean service = new KvStoreServiceBean();
    //        Whitebox.setInternalState(service, "kvStoreDao", kvStoreDao);
    //        Whitebox.setInternalState(service, "operationLogDao", operationLogDao);
    //
    //        keyValueStore.setValue(valueAfter);
    //
    //        Order operationLog = new Order();
    //        operationLog
    //                .setOperation(String.format("KeyValueStore[%s] updated", key));
    //
    //        service.update(keyValueStore);
    //
    //        Assert.assertThat(key,
    //                CoreMatchers.is(keyValueStoreCaptor.getValue().getKey()));
    //        Assert.assertThat(valueAfter,
    //                CoreMatchers.is(keyValueStoreCaptor.getValue().getValue()));
    //        Assert.assertThat(modified,
    //                CoreMatchers.is(keyValueStoreCaptor.getValue().getModified()));
    //        Assert.assertThat(operationLog.getOperation(),
    //                CoreMatchers.is(operationLogCaptor.getValue().getOperation()));
    //        Assert.assertThat(operationLog.getOperated(),
    //                CoreMatchers.is(operationLogCaptor.getValue().getOperated()));
    //    }
    //
    //    @Test
    //    public void test_remove() {
    //        Product entity = new Product();
    //        entity.setKey("test3");
    //        entity.setValue("value3");
    //        entity.setModified(new Date());
    //
    //        ArgumentCaptor<Product> keyValueStoreCaptor = ArgumentCaptor
    //                .forClass(Product.class);
    //        ArgumentCaptor<Order> operationLogCaptor = ArgumentCaptor
    //                .forClass(Order.class);
    //        // DAOモックの振る舞いを定義
    //        ProductDao kvStoreDao = Mockito.mock(ProductDao.class);
    //        Mockito.when(kvStoreDao.find(entity.getKey())).thenReturn(entity);
    //        Mockito.doNothing().when(kvStoreDao)
    //                .remove(keyValueStoreCaptor.capture());
    //        OrderDao operationLogDao = Mockito.mock(OrderDao.class);
    //        Mockito.doNothing().when(operationLogDao)
    //                .create(operationLogCaptor.capture());
    //        KvStoreServiceBean service = new KvStoreServiceBean();
    //        Whitebox.setInternalState(service, "kvStoreDao", kvStoreDao);
    //        Whitebox.setInternalState(service, "operationLogDao", operationLogDao);
    //
    //        Order operationLog = new Order();
    //        operationLog.setOperation(
    //                String.format("KeyValueStore[%s] removed", entity.getKey()));
    //
    //        service.remove(entity.getKey());
    //
    //        Assert.assertThat(entity.getKey(),
    //                CoreMatchers.is(keyValueStoreCaptor.getValue().getKey()));
    //        Assert.assertThat(entity.getValue(),
    //                CoreMatchers.is(keyValueStoreCaptor.getValue().getValue()));
    //        Assert.assertThat(entity.getModified(),
    //                CoreMatchers.is(keyValueStoreCaptor.getValue().getModified()));
    //        Assert.assertThat(operationLog.getOperation(),
    //                CoreMatchers.is(operationLogCaptor.getValue().getOperation()));
    //        Assert.assertThat(operationLog.getOperated(),
    //                CoreMatchers.is(operationLogCaptor.getValue().getOperated()));
    //    }

}
