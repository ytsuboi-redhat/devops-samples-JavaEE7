package com.redhat.sample.cicd.dao;

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

import com.redhat.sample.cicd.entity.jpa.Product;
import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;
import com.redhat.sample.cicd.entity.jpa.ReceivedOrderDetail;
import com.redhat.sample.cicd.exception.TargetNotFoundException;

@RunWith(Arquillian.class)
public class OrderDaoBeanArqTestIT {

    @Inject
    private OrderDao orderDao;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @UsingDataSet("datasets/OrderDaoBeanArqTestIT/case000_search_init.yml")
    @ShouldMatchDataSet("datasets/OrderDaoBeanArqTestIT/case000_search_expected.yml")
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

        List<ReceivedOrder> result = orderDao.search("20160701%", 0, 10);
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
    //    @UsingDataSet("datasets/KvStoreDaoBeanArqTestIT/case000_find_init.yml")
    //    public void test_find() {
    //        assertThat("値１", is(kvStoreDao.find("キー１").getValue()));
    //    }
    //
    //    @Test
    //    @UsingDataSet("datasets/KvStoreDaoBeanArqTestIT/case001_create_init.yml")
    //    public void test_create() throws ParseException {
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //
    //        Product entity = new Product();
    //        entity.setKey("key２");
    //        entity.setValue("value２");
    //        kvStoreDao.create(entity);
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
    //    }
    //
    //    @Test
    //    @UsingDataSet("datasets/KvStoreDaoBeanArqTestIT/case002_update_init.yml")
    //    public void test_update() {
    //        Product entity = entityManager.find(Product.class, 90000001L);
    //        long prevId = entity.getId();
    //        Date prevModified = entity.getModified();
    //        entity.setKey("key更新");
    //        entity.setValue("value更新");
    //        kvStoreDao.update(entity);
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
    //    }
    //
    //    @Test
    //    @UsingDataSet("datasets/KvStoreDaoBeanArqTestIT/case003_remove_init.yml")
    //    public void test_remove() throws ParseException {
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //        Product entity = entityManager.find(Product.class, 90000001L);
    //        kvStoreDao.remove(entity);
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
    //    }

    @Deployment
    public static EnterpriseArchive createDeployment() {
        // テスト対象JARを生成
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test-dao.jar")
                .addClasses(OrderDao.class, OrderDaoBean.class,
                        TargetNotFoundException.class, ReceivedOrder.class,
                        ReceivedOrderDetail.class, Product.class,
                        OrderDaoBeanArqTestIT.class)
                .addAsManifestResource("test-beans.xml", "beans.xml")
                .addAsManifestResource("persistence.xml");

        // EARを生成
        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class,
                "test.ear");
        ear.addAsModule(jar);

        return ear;
    }

}
