package com.stern.fraudshields.service;

import com.stern.fraudshields.model.Customer;

public interface UserService {

	Customer getUser(String userName);
}
