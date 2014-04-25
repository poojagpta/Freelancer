package com.gae.dao;

import com.gae.model.Address;

public interface AddressDAO extends GenericDAO<Address>{

	Address getAddress(Long id);
}
