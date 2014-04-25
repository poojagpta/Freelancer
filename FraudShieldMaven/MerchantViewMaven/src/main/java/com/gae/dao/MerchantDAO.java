package com.gae.dao;

import java.util.List;

import com.gae.bean.SearchInput;
import com.gae.model.Merchant;

public interface MerchantDAO extends GenericDAO{

	Merchant getMerchant(String siteName);
	List<Merchant> getMerchantList(SearchInput input,Long userId);
	
}
