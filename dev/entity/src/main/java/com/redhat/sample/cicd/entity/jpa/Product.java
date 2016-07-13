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
@Table(name = "product")
public class Product implements Serializable {

    // ---------------------------------------------------- Instance Variables

    @Id
    @Column(name = "product_no", nullable = false)
    private String productNo;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String category;

    @NotNull
    @Column
    private int unit;

    @NotNull
    @Column
    private int price;

    @Column
    private String introduction;

    @NotNull
    @Column
    private boolean disabled;

    @NotNull
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ReceivedOrderDetail> orderDetails;

    // ------------------------------------------------------------- Accessors

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
