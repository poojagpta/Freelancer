package com.gae.dao;

import com.gae.model.Product;

public interface ProductDAO extends GenericDAO<Product>{

	Product getProduct(String productName);
}
