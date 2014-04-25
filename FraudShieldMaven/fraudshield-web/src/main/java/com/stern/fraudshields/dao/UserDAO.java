package com.stern.fraudshields.dao;

import com.stern.fraudshields.model.Customer;

public interface UserDAO extends GenericDAO<Customer> {

	Customer getUser(String userName);
}
