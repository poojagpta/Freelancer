package com.stern.fraudshields.dao;

import com.stern.fraudshields.model.Address;

public interface AddressDAO extends GenericDAO<Address>{

	Address getAddress(Long id);
}
