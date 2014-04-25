package com.stern.fraudshields.dao;

import com.stern.fraudshields.model.CreditCard;

public interface CreditCardDAO extends GenericDAO<CreditCard> {

	CreditCard getCreditCard(Long bin);
	
}
