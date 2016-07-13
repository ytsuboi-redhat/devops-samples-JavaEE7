package com.redhat.sample.cicd.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.redhat.sample.cicd.entity.jpa.ReceivedOrder;
import com.redhat.sample.cicd.rest.schema.OrderResponse;
import com.redhat.sample.cicd.service.OrderService;

public class OrderResourceBeanTest {

    @Test
    public void test_searchOrders() throws Exception {
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

        OrderService service = Mockito.mock(OrderService.class);
        Mockito.when(service.searchOrders("20160701%", 0, 10)).thenReturn(list);

        OrderResourceBean resource = new OrderResourceBean();
        resource.service = service;

        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(resource);
        MockHttpRequest request = MockHttpRequest
                .get("/orders?filter=20160701%25&offset=0&count=10");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        Assert.assertThat(response.getStatus(),
                CoreMatchers.is(HttpServletResponse.SC_OK));

        ObjectMapper mapper = new ObjectMapper();
        OrderResponse<List<ReceivedOrder>> orderResponse = mapper.readValue(
                response.getOutput(),
                new TypeReference<OrderResponse<List<ReceivedOrder>>>() {
                });
        Assert.assertThat(orderResponse.getCode(), CoreMatchers.is("success"));
        Assert.assertThat(orderResponse.getDescription(),
                CoreMatchers.nullValue());
        Assert.assertThat(orderResponse.getValue().size(), CoreMatchers.is(2));
        Assert.assertThat(orderResponse.getValue().get(0),
                CoreMatchers.is(Matchers.samePropertyValuesAs(entity1)));
        Assert.assertThat(orderResponse.getValue().get(1),
                CoreMatchers.is(Matchers.samePropertyValuesAs(entity2)));
    }
    //    @Ignore
    //    @Test
    //    public void test_POST() throws Exception {
    //        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor
    //                .forClass(Product.class);
    //        OrderService service = Mockito.mock(OrderService.class);
    //        Mockito.doNothing().when(service).create(argumentCaptor.capture());
    //
    //        OrderResourceBean resource = new OrderResourceBean();
    //        resource.service = service;
    //
    //        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
    //        dispatcher.getRegistry().addSingletonResource(resource);
    //        MockHttpRequest request = MockHttpRequest.post("/kvs/1/hoge");
    //        MockHttpResponse response = new MockHttpResponse();
    //        dispatcher.invoke(request, response);
    //
    //        Assert.assertThat(HttpServletResponse.SC_NO_CONTENT,
    //                CoreMatchers.is(response.getStatus()));
    //        Assert.assertThat(StringUtils.EMPTY,
    //                CoreMatchers.is(response.getContentAsString()));
    //        Assert.assertThat("1",
    //                CoreMatchers.is(argumentCaptor.getValue().getKey()));
    //        Assert.assertThat("hoge",
    //                CoreMatchers.is(argumentCaptor.getValue().getValue()));
    //    }
    //
    //    @Ignore
    //    @Test
    //    public void test_PUT() throws Exception {
    //        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor
    //                .forClass(Product.class);
    //        OrderService service = Mockito.mock(OrderService.class);
    //        Mockito.doNothing().when(service).update(argumentCaptor.capture());
    //
    //        OrderResourceBean resource = new OrderResourceBean();
    //        resource.service = service;
    //
    //        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
    //        dispatcher.getRegistry().addSingletonResource(resource);
    //        MockHttpRequest request = MockHttpRequest.put("/kvs/foo/10a");
    //        MockHttpResponse response = new MockHttpResponse();
    //        dispatcher.invoke(request, response);
    //
    //        Assert.assertThat(HttpServletResponse.SC_NO_CONTENT,
    //                CoreMatchers.is(response.getStatus()));
    //        Assert.assertThat(StringUtils.EMPTY,
    //                CoreMatchers.is(response.getContentAsString()));
    //        Assert.assertThat("foo",
    //                CoreMatchers.is(argumentCaptor.getValue().getKey()));
    //        Assert.assertThat("10a",
    //                CoreMatchers.is(argumentCaptor.getValue().getValue()));
    //    }
    //
    //    @Ignore
    //    @Test
    //    public void test_DELETE() throws Exception {
    //        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor
    //                .forClass(String.class);
    //        OrderService service = Mockito.mock(OrderService.class);
    //        Mockito.doNothing().when(service).remove(argumentCaptor.capture());
    //
    //        OrderResourceBean resource = new OrderResourceBean();
    //        resource.service = service;
    //
    //        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
    //        dispatcher.getRegistry().addSingletonResource(resource);
    //        MockHttpRequest request = MockHttpRequest.delete("/kvs/key1");
    //        MockHttpResponse response = new MockHttpResponse();
    //        dispatcher.invoke(request, response);
    //
    //        Assert.assertThat(HttpServletResponse.SC_NO_CONTENT,
    //                CoreMatchers.is(response.getStatus()));
    //        Assert.assertThat(StringUtils.EMPTY,
    //                CoreMatchers.is(response.getContentAsString()));
    //        Assert.assertThat("key1", CoreMatchers.is(argumentCaptor.getValue()));
    //    }
}
