package com.redhat.sample.cicd.service;

import javax.ejb.Local;

import com.redhat.sample.cicd.entity.jpa.Product;

@Local
public interface ProductService {

    Product find(String productNo);

}
