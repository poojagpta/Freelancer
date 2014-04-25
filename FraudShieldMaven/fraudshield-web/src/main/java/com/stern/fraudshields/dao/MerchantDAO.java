package com.stern.fraudshields.dao;

import com.stern.fraudshields.model.Merchant;

public interface MerchantDAO extends GenericDAO<Merchant>{

	Merchant getMerchant(String siteName);
}
