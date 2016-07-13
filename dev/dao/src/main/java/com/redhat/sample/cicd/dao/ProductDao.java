package com.redhat.sample.cicd.dao;

import javax.ejb.Local;

import com.redhat.sample.cicd.entity.jpa.Product;

@Local
public interface ProductDao {

    Product find(String productNo);

}
