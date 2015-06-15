package com.uguanjia.o2o.dao;

import java.util.List;

import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.StoreAccount;
import com.uguanjia.o2o.StoreServiceAccount;
import com.uguanjia.o2o.User;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.meta.StoreProcessActivity;

/*******************************************
 * @CLASS:OrderDao
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public interface StoreDao {
	
	
	/*
	 * store_main_info
	 */
	public int copyStoreMainInfoFromApply(long oldStoreId, long newStoreId);
	
	public int selectAreaStoreCount(int area);
	
	public int selectStoreCount(QueryCondition condition);
	
	public Store selectStoreMainInfoById(QueryCondition condition);
	
	public List<Store> selectStoreMainInfo(QueryCondition condition);

	/*
	 * store_account_info
	 */
	public int copyStoreAccountInfoFromApply(long oldStoreId, long newStoreId);
	
	public StoreAccount selectStoreAccountInfoById(QueryCondition condition);
	
	/*
	 * store_main_info_apply
	 */
	public Store selectStoreApplyMainInfoById(QueryCondition condition);
	
	public List<Store> selectStoreApplyMainInfo(QueryCondition condition);
	
	public int insertStoreApplyMainInfo(Store store);
	
	public int updateStoreApplyMainInfo(Store store);
	
	public int updateStoreApplyMainInfoStatus(long id, int status);
	
	public int deleteStoreApplyMainInfo(long id);
	
	/*
	 * store_account_info_apply
	 */
	public StoreAccount selectStoreApplyAccountInfoById(QueryCondition condition);
	
	public int replaceStoreApplyAccountInfo(StoreAccount account);
	
	/*
	 * store_process
	 */
	public List<StoreProcessActivity> selectStoreProcessesById(QueryCondition condition);
	
	public int insertStoreProcess(StoreProcessActivity activity);
	
	/*
	 * users
	 */
	public User selectUserByUsername(String username);
	
	public long selectStoreIdByUsername(String username);	
	
	public int insertStoreUser(User user);
	
	public int updateUserStoreId(long oldStoreId, long newStoreId);
	
	public int updateUserPassword(String username, String oldPassword, String newPassword);
	
	
	/*
	 * store_service_account
	 */
	public List<StoreServiceAccount> selectStoreServiceAccounts(QueryCondition condition);
	
	public int replaceStoreServiceAccount(StoreServiceAccount serviceAccount);
	
	/*
	 * store_service_account
	 */
	public List<StoreServiceAccount> selectStoreApplyServiceAccounts(QueryCondition condition);
	
	public int replaceStoreApplyServiceAccount(StoreServiceAccount serviceAccount);
	
	public int copyStoreServiceAccountsFromApply(long oldStoreId, long newStoreId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int createStore(Store store);
	
	public int createUser(String username, String password, long storeId);
	
	public int createRoleForUser(String username, String role);
	
	public Store queryStoreById(long id);
	
	public int queryStoreCount(QueryCondition condition);
	
	public List<Store> queryStores(QueryCondition condition);
	
	public List<Store> queryStoresByCode(long storeCode1, long storeCode2, QueryCondition condition);
	
	public List<String> queryUsers(long storeId);
	
	public int changeStoreInfo(Store store);

}

