package com.redhat.sample.cicd.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "received_order_detail")
public class ReceivedOrderDetail implements Serializable {

    // ---------------------------------------------------- Instance Variables

    @Id
    @Column(name = "order_detail_no", nullable = false)
    private String orderDetailNo;

    @NotNull
    @JoinColumn(name = "order_no")
    @ManyToOne
    private ReceivedOrder order;

    @NotNull
    @JoinColumn(name = "product_no")
    @ManyToOne
    private Product product;

    @NotNull
    @Column(name = "unit_price")
    private int unitPrice;

    @NotNull
    @Column
    private int quantity;

    @NotNull
    @Column(name = "total_amount")
    private int totalAmount;

    @Column
    private String summary;

    @NotNull
    @Column
    private boolean disabled;

    @NotNull
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    // ------------------------------------------------------------- Accessors

    public String getOrderDetailNo() {
        return orderDetailNo;
    }

    public void setOrderDetailNo(String orderDetailNo) {
        this.orderDetailNo = orderDetailNo;
    }

    public ReceivedOrder getOrder() {
        return order;
    }

    public void setOrder(ReceivedOrder order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

}
