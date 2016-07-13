package com.redhat.sample.cicd.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.sample.cicd.commons.test.util.archive.ShrinkWrapHelper;
import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;

@RunWith(Arquillian.class)
public class OrderServiceBeanArqTestIT {

    @Inject
    private OrderService orderService;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @UsingDataSet("datasets/case000_searchOrders_init.yml")
    @ShouldMatchDataSet("datasets/case000_searchOrders_expected.yml")
    public void test_searchOrders() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        ReceivedOrder[] entities = new ReceivedOrder[2];
        entities[0] = new ReceivedOrder();
        entities[0].setOrderNo("20160701-00001");
        entities[0].setOrdered(df.parse("20160701000000"));
        entities[0].setDesiredDeliveryDate(df.parse("20160715000000"));
        entities[0].setClaimNo("C20160706001");
        entities[0].setBillingDate(df.parse("20160706000000"));
        entities[0].setTotalAmount(20000);
        entities[0].setTax(1600);
        entities[0].setBillingAmount(21600);
        entities[0].setBilled(true);
        entities[0].setModified(
                new Timestamp(df.parse("20160706143000").getTime()));
        entities[0].setOrderDetails(Collections.emptyList());
        entities[1] = new ReceivedOrder();
        entities[1].setOrderNo("20160701-00002");
        entities[1].setOrdered(df.parse("20160701000000"));
        entities[1].setDesiredDeliveryDate(df.parse("20160720000000"));
        entities[1].setTotalAmount(30000);
        entities[1].setTax(2100);
        entities[1].setBillingAmount(32100);
        entities[1].setDisabled(true);
        entities[1].setModified(
                new Timestamp(df.parse("20160706143000").getTime()));
        entities[1].setOrderDetails(Collections.emptyList());

        List<ReceivedOrder> result = orderService.searchOrders("20160701%", 0,
                10);
        assertThat(result.size(), is(2));
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i), Matchers.hasProperty("orderNo",
                    is(entities[i].getOrderNo())));
            assertThat(result.get(i), Matchers.hasProperty("ordered",
                    is(entities[i].getOrdered())));
            assertThat(result.get(i),
                    Matchers.hasProperty("desiredDeliveryDate",
                            is(entities[i].getDesiredDeliveryDate())));
            assertThat(result.get(i), Matchers.hasProperty("claimNo",
                    is(entities[i].getClaimNo())));
            assertThat(result.get(i), Matchers.hasProperty("billingDate",
                    is(entities[i].getBillingDate())));
            assertThat(result.get(i), Matchers.hasProperty("totalAmount",
                    is(entities[i].getTotalAmount())));
            assertThat(result.get(i),
                    Matchers.hasProperty("tax", is(entities[i].getTax())));
            assertThat(result.get(i), Matchers.hasProperty("billingAmount",
                    is(entities[i].getBillingAmount())));
            assertThat(result.get(i),
                    Matchers.hasProperty("billed", is(entities[i].isBilled())));
            assertThat(result.get(i), Matchers.hasProperty("disabled",
                    is(entities[i].isDisabled())));
            assertThat(result.get(i), Matchers.hasProperty("modified",
                    is(entities[i].getModified())));
        }
    }

    //    @Test
    //    @UsingDataSet("datasets/case000_find_init.yml")
    //    public void test_find() {
    //        assertThat("値１", is(sandboxService.find("キー１").getValue()));
    //
    //        assertThat(0L,
    //                is(entityManager
    //                        .createQuery("select count(id) from OperationLog opl")
    //                        .getSingleResult()));
    //    }
    //
    //    @Test
    //    @UsingDataSet("datasets/case001_create_init.yml")
    //    public void test_create() throws ParseException {
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //
    //        Product entity = new Product();
    //        entity.setKey("key２");
    //        entity.setValue("value２");
    //        sandboxService.create(entity);
    //        entityManager.flush();
    //
    //        @SuppressWarnings("unchecked")
    //        List<Product> list = entityManager
    //                .createQuery("select kvs from KeyValueStore kvs")
    //                .getResultList();
    //        assertThat(2, is(list.size()));
    //        assertThat(90000001L, is(list.get(0).getId()));
    //        assertThat("key1", is(list.get(0).getKey()));
    //        assertThat("value1", is(list.get(0).getValue()));
    //        assertThat(sdf.parse("2016-05-24 12:00:00"),
    //                is(list.get(0).getModified()));
    //        assertThat("key２", is(list.get(1).getKey()));
    //        assertThat("value２", is(list.get(1).getValue()));
    //
    //        @SuppressWarnings("unchecked")
    //        List<Order> oplList = entityManager
    //                .createQuery("select opl from OperationLog opl")
    //                .getResultList();
    //        assertThat(2, is(oplList.size()));
    //        assertThat(90000001L, is(oplList.get(0).getId()));
    //        assertThat("test", is(oplList.get(0).getOperation()));
    //        assertThat(sdf.parse("2016-05-24 12:00:00"),
    //                is(oplList.get(0).getOperated()));
    //        assertThat(String.format("KeyValueStore[%s] created", entity.getKey()),
    //                is(oplList.get(1).getOperation()));
    //    }
    //
    //    @Test
    //    @UsingDataSet("datasets/case002_update_init.yml")
    //    public void test_update() throws ParseException {
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //
    //        Product entity = entityManager.find(Product.class,
    //                90000001L);
    //        long prevId = entity.getId();
    //        Date prevModified = entity.getModified();
    //        entity.setKey("key更新");
    //        entity.setValue("value更新");
    //        sandboxService.update(entity);
    //        entityManager.flush();
    //
    //        Assert.assertThat(prevId, CoreMatchers.is(entity.getId()));
    //        Assert.assertThat("key更新", CoreMatchers.is(entity.getKey()));
    //        Assert.assertThat("value更新", CoreMatchers.is(entity.getValue()));
    //        Assert.assertThat(prevModified,
    //                CoreMatchers.not(entity.getModified().getTime()));
    //
    //        Query query = entityManager
    //                .createNativeQuery("select count(1) from kv_store");
    //        Assert.assertThat("2",
    //                CoreMatchers.is(String.valueOf(query.getSingleResult())));
    //
    //        @SuppressWarnings("unchecked")
    //        List<Order> oplList = entityManager
    //                .createQuery("select opl from OperationLog opl")
    //                .getResultList();
    //        assertThat(2, is(oplList.size()));
    //        assertThat(90000001L, is(oplList.get(0).getId()));
    //        assertThat("test", is(oplList.get(0).getOperation()));
    //        assertThat(sdf.parse("2016-05-24 12:00:00"),
    //                is(oplList.get(0).getOperated()));
    //        assertThat(String.format("KeyValueStore[%s] updated", entity.getKey()),
    //                is(oplList.get(1).getOperation()));
    //    }
    //
    //    @Test
    //    @UsingDataSet("datasets/case003_remove_init.yml")
    //    public void test_remove() throws ParseException {
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //        Product entity = entityManager.find(Product.class,
    //                90000001L);
    //        sandboxService.remove(entity.getKey());
    //        entityManager.flush();
    //
    //        @SuppressWarnings("unchecked")
    //        List<Product> list = entityManager
    //                .createQuery("select kvs from KeyValueStore kvs")
    //                .getResultList();
    //        assertThat(1, is(list.size()));
    //        assertThat(90000002L, is(list.get(0).getId()));
    //        assertThat("イエロー", is(list.get(0).getKey()));
    //        assertThat("黄色", is(list.get(0).getValue()));
    //        assertThat(sdf.parse("2016-05-31 00:00:01"),
    //                is(list.get(0).getModified()));
    //
    //        @SuppressWarnings("unchecked")
    //        List<Order> oplList = entityManager
    //                .createQuery("select opl from OperationLog opl")
    //                .getResultList();
    //        assertThat(2, is(oplList.size()));
    //        assertThat(90000001L, is(oplList.get(0).getId()));
    //        assertThat("test", is(oplList.get(0).getOperation()));
    //        assertThat(sdf.parse("2016-05-24 12:00:00"),
    //                is(oplList.get(0).getOperated()));
    //        assertThat(String.format("KeyValueStore[%s] removed", entity.getKey()),
    //                is(oplList.get(1).getOperation()));
    //    }
    //
    //    @Test
    //    @UsingDataSet("datasets/case001_create_init.yml")
    //    @ShouldMatchDataSet("datasets/case001_create_init.yml")
    //    public void test_tx_rollback_create() {
    //        Product entity = new Product();
    //        entity.setKey("key1");
    //        entity.setValue("失敗");
    //        try {
    //            sandboxService.create(entity);
    //            entityManager.flush();
    //            assertThat("It does not violate unique constraint", true,
    //                    is(false));
    //        } catch (PersistenceException e) {
    //            assertThat(true, is(true));
    //        }
    //    }
    //
    //    @Test
    //    @UsingDataSet("datasets/case002_update_init.yml")
    //    @ShouldMatchDataSet("datasets/case002_update_init.yml")
    //    public void test_tx_rollback_update() throws ParseException {
    //        Product entity = entityManager.find(Product.class,
    //                90000001L);
    //        entity.setKey("key2");
    //        entity.setValue("value更新");
    //        try {
    //            sandboxService.update(entity);
    //            entityManager.flush();
    //            assertThat("It does not violate unique constraint", true, is(false));
    //        } catch (EJBTransactionRolledbackException e) {
    //            assertThat(true, is(true));
    //        }
    //    }
    //
    //    @Test
    //    @UsingDataSet("datasets/case003_remove_init.yml")
    //    @ShouldMatchDataSet("datasets/case003_remove_init.yml")
    //    public void test_tx_rollback_remove() throws ParseException {
    //        try {
    //            sandboxService.remove("グレー");
    //            entityManager.flush();
    //            assertThat("It is not exist", true, is(false));
    //        } catch (EJBTransactionRolledbackException e) {
    //            assertThat(true, is(true));
    //        }
    //    }

    @Deployment
    public static EnterpriseArchive createDeployment() {
        // テスト対象JARを生成
        JavaArchive jar = ShrinkWrap
                .create(JavaArchive.class, "test-service.jar")
                .addClasses(OrderService.class, OrderServiceBean.class,
                        OrderServiceBeanArqTestIT.class);
        // EARを生成（daoとentityはpom.xmlから取得して生成）
        EnterpriseArchive ear = ShrinkWrapHelper.archiveWithLibs(
                EnterpriseArchive.class, "test.ear", "pom.xml");
        ear.addAsModule(jar);

        return ear;
    }

}
