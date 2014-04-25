package com.stern.fraudshields.dao;

import com.stern.fraudshields.model.Product;

public interface ProductDAO extends GenericDAO<Product>{

	Product getProduct(String productName);
}
