package com.gae.manager;

import java.util.List;

import com.gae.bean.MerchantDataGrid;
import com.gae.bean.SearchInput;
import com.gae.service.DataStoreSearchService;
import com.gae.service.FullTextSearchService;
import com.gae.service.SearchQuery;
import com.quicksale.vo.UserVO;

public class SearchServiceManager {
	
	public static List<MerchantDataGrid> executeDataStoreSearch(SearchInput input, UserVO user){
		
		DataStoreSearchService service = new DataStoreSearchService();
		List<MerchantDataGrid> merchantDataList = null;
		
		switch (input.getSearchOption()) {
			case ADDRESS:
				merchantDataList = service.searchOnAddress(input,user.getId()); // search on address needs to be test.
				break;
			case CUSTOMER:
				merchantDataList = service.searchOnUser(input,user.getId());
				break;
			case MERCHANT:
				merchantDataList = service.searchOnMerchant(input,user.getId());
				break;
				
			case ORDER_DETAIL:
				merchantDataList = service.searchOnOrderDetail(input,user.getId());
				break;
				
			default:
				break;
		}
		
		return merchantDataList;
		
	}
	
	public static List<MerchantDataGrid> executeFullTextSearch(SearchInput input, UserVO user){
		
		List<MerchantDataGrid> merchantDataList = null;
		
		SearchQuery service = new SearchQuery();
		service.initialize(user.getId());
		merchantDataList = service.search(input,user.getId());
		
		return merchantDataList;
		
	}

}
