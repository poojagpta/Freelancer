package com.gae.dao;

import com.gae.model.User;

public interface UserDAO extends GenericDAO<User> {

	User getUser(String userName);
}
