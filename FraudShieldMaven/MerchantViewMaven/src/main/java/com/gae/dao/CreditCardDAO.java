package com.gae.dao;

import com.gae.model.CreditCard;

public interface CreditCardDAO extends GenericDAO<CreditCard> {

	CreditCard getCreditCard(Long bin);
	
}
