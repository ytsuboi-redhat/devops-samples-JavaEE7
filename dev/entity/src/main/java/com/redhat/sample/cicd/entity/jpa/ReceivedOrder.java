package com.redhat.sample.cicd.entity.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "received_order")
public class ReceivedOrder implements Serializable {

    // ---------------------------------------------------- Instance Variables

    @Id
    @Column(name = "order_no", nullable = false)
    private String orderNo;

    @NotNull
    @Column
    @Temporal(TemporalType.DATE)
    private Date ordered;

    @Column(name = "desired_delivery_date")
    @Temporal(TemporalType.DATE)
    private Date desiredDeliveryDate;

    @Column(name = "claim_no")
    private String claimNo;

    @Column(name = "billing_date")
    @Temporal(TemporalType.DATE)
    private Date billingDate;

    @NotNull
    @Column(name = "total_amount")
    private int totalAmount;

    @NotNull
    @Column
    private int tax;

    @NotNull
    @Column(name = "billing_amount")
    private int billingAmount;

    @NotNull
    @Column
    private boolean billed;

    @NotNull
    @Column
    private boolean disabled;

    @NotNull
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<ReceivedOrderDetail> orderDetails;

    // ------------------------------------------------------------- Accessors

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrdered() {
        return ordered;
    }

    public void setOrdered(Date ordered) {
        this.ordered = ordered;
    }

    public Date getDesiredDeliveryDate() {
        return desiredDeliveryDate;
    }

    public void setDesiredDeliveryDate(Date desiredDeliveryDate) {
        this.desiredDeliveryDate = desiredDeliveryDate;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(int billingAmount) {
        this.billingAmount = billingAmount;
    }

    public boolean isBilled() {
        return billed;
    }

    public void setBilled(boolean billed) {
        this.billed = billed;
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

    public List<ReceivedOrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<ReceivedOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
