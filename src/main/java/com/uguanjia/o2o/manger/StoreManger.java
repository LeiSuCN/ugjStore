package com.uguanjia.o2o.manger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.TransactionStatus;

import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.StoreAccount;
import com.uguanjia.o2o.StoreServiceAccount;
import com.uguanjia.o2o.User;
import com.uguanjia.o2o.config.Configration;
import com.uguanjia.o2o.dao.StoreDao;
import com.uguanjia.o2o.meta.Roles;
import com.uguanjia.o2o.meta.StoreProcessActivity;
import com.uguanjia.o2o.meta.StoreStatus;
import com.uguanjia.o2o.service.BaseResult;


/*******************************************
 * @CLASS:StoreManger
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月9日 上午7:52:11
 *******************************************/
public class StoreManger extends BaseManger{
	
	private Configration ugjCfg;
	
	public Store queryStoreByUsername(String username ){
		long storeId = 0;
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			storeId = storeDao.selectStoreIdByUsername(username);
		}
		
		Store store = queryStoreById(storeId);
		
		if( store == null )
		{
			QueryCondition condition = new QueryCondition();
			condition.setStoreId(storeId);
			
			store = queryApplyStoreById(condition);
		}
		
		return store;
	}
	
	public Store queryStoreById(long storeId){
		
		logger.debug("begin to query apply store " + storeId);
		
		QueryCondition condition = new QueryCondition();
		condition.setStoreId(storeId);
		
		Store store = null;
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			
			store = storeDao.selectStoreMainInfoById(condition);
			
			StoreAccount account = storeDao.selectStoreAccountInfoById(condition);
			store.setAccount(account);
			
			List<StoreServiceAccount> serviceAccounts = storeDao.selectStoreServiceAccounts(condition);
			store.setServiceAccounts(serviceAccounts);
		} 
		catch (Exception e) {
			logger.error(e);
		}
		
		if( store == null )
		{
			logger.warn("failure to query store, id is " + storeId );
		}
		else
		{
			logger.info("success to query store, id is " + storeId );
		}
		
		return store;	
	}
	
	/**
	 * @description:查询门店的用户	
	 * @DATE:2015年3月9日 上午11:11:39
	 * @param storeId
	 * @return
	 */
	public List<String> queryUsersByStore(long storeId){
		logger.info("begin to query users for " + storeId);
		
		SqlSession session = sqlSessionFactory.openSession();
		
		StoreDao storeDao = session.getMapper(StoreDao.class);
		
		List<String> users = storeDao.queryUsers(storeId);
		
		session.close();
		
		if( users == null )
		{
			users = new ArrayList<>();
			logger.warn("failure to query users for " + storeId);
		}
		else
		{
			logger.info("success to query users for " + storeId);
		}
		
		return users;
	}
	
	/**
	 * @description:创建门店	
	 * @DATE:2015年3月9日 下午11:46:50
	 * @param store
	 * @return
	 */
	public BaseResult createStore(Store store){
		logger.info("begin to create store " + store.getId());
		
		BaseResult result = new BaseResult();
		
		TransactionStatus transactionStatus = beginTransaction("createStore");
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			
			// 电话作为登录帐号
			String username = store.getPhonenumber();
			
			User user = storeDao.selectUserByUsername(username);
			
			// 帐号不能重复
			if( user != null )
			{
				result.setFailureMsg("用户" + username + "已经存在！");
			}
			else
			{
				// 生成申请记录
				store.setStatus(StoreStatus.APPROVAL);
				
				// 门店主体信息
				storeDao.insertStoreApplyMainInfo(store);
				
				long storeId = store.getId();
				
				/*
				// 门店账号信息
				StoreAccount account = store.getAccount();
				account.setStoreId(storeId);
				storeDao.replaceStoreApplyAccountInfo(account);
					*/
			
				// 门店服务账号
				List<StoreServiceAccount> serviceAccounts = store.getServiceAccounts();
				if( serviceAccounts != null )
				{
					for( StoreServiceAccount serviceAccount : serviceAccounts )
					{
						serviceAccount.setStoreId(storeId);
						storeDao.replaceStoreApplyServiceAccount(serviceAccount);
					}
				}
				
				// 创建登录用户名
				user = new User();
				user.setUsername(username);
				user.setStoreId(storeId);
				/*
				// 密码默认为工商注册号后六位
				String password = String.valueOf(store.getRegistrationNo());
				int len = password.length();
				if( len > 6 )
				{
					password = password.substring(len - 6);
				}
				*/
				// 密码默认为用户名
				String password = username;
				user.setPassword(password);
				storeDao.insertStoreUser(user);
				
				// 创建角色
				storeDao.createRoleForUser(username, Roles.ROLE_STORE);
				
				// 记录申请信息
				StoreProcessActivity activity = new StoreProcessActivity();
				activity.setStoreId(storeId);
				activity.setOperator(store.getPhonenumber());
				activity.setStatus(StoreStatus.APPROVAL);
				activity.setComment("新门店注册");
				storeDao.insertStoreProcess(activity);
				
				result.setSuccessMsg("创建成功");
			}
		} 
		catch (Exception e) {
			logger.error(e);
			result.setFailureMsg("新建门店失败");
		}
		
		if( result.isSuccess() )
		{
			logger.info("success to create store " + store.getId());
			commitTransaction(transactionStatus);
		}
		else
		{
			rollbackTransaction(transactionStatus);
			logger.warn("failure to create store " + store.getId());
		}
		
		return result;
	}
	
	/**
	 * @description:创建门店	
	 * @DATE:2015年3月9日 下午11:46:50
	 * @param store
	 * @return
	 */
	public BaseResult updateStore(Store store){
		logger.info("begin to update store " + store.getId());
		
		BaseResult result = new BaseResult();
		
		TransactionStatus transactionStatus = beginTransaction("updateStore");
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			
			// 门店主体信息
			storeDao.updateStoreApplyMainInfo(store);
			
			long storeId = store.getId();
			
			// 门店账号信息
			StoreAccount account = store.getAccount();
			account.setStoreId(storeId);
			storeDao.replaceStoreApplyAccountInfo(account);
			
			// 门店服务账号
			List<StoreServiceAccount> serviceAccounts = store.getServiceAccounts();
			if( serviceAccounts != null )
			{
				for( StoreServiceAccount serviceAccount : serviceAccounts )
				{
					serviceAccount.setStoreId(storeId);
					storeDao.replaceStoreApplyServiceAccount(serviceAccount);
				}
			}

			
			// 记录申请信息
			StoreProcessActivity activity = new StoreProcessActivity();
			activity.setStoreId(storeId);
			activity.setOperator(store.getPhonenumber());
			activity.setStatus(StoreStatus.APPROVAL);
			activity.setComment("更新门店注册信息");
			storeDao.insertStoreProcess(activity);
			
			result.setSuccessMsg("创建成功");
		} 
		catch (Exception e) {
			logger.error(e);
			result.setFailureMsg("新建门店失败");
		}
		
		if( result.isSuccess() )
		{
			logger.info("success to update store " + store.getId());
			commitTransaction(transactionStatus);
		}
		else
		{
			rollbackTransaction(transactionStatus);
			logger.warn("failure to update store " + store.getId());
		}
		
		return result;
	}
	
	/**
	 * @description: 受理门店申请	
	 * @DATE:2015年4月27日 下午10:57:42
	 * @param store
	 * @return
	 */
	public BaseResult acceptStore(Store store, String operator, String comment){
		long newStoreId = 0;
		
		synchronized (StoreManger.class) {
			try(SqlSession session = sqlSessionFactory.openSession()) 
			{
				StoreDao storeDao = session.getMapper(StoreDao.class);
				int area = store.getArea();
				int count = storeDao.selectAreaStoreCount(area);
				newStoreId = area * 10000l + count + 1;
			}
			catch (Exception e) {
				logger.error(e);
				newStoreId = -1;
			}
		}
		
		
		if( newStoreId <= 0 ){
			BaseResult result = new BaseResult();
			result.setFailureMsg("创建门店编号失败！");
			return result;
		}
		
		return acceptStore(store.getId(), newStoreId, operator, comment);
	}
	
	/**
	 * @description:审核通过门店创建请求	
	 * @DATE:2015年4月8日 下午10:33:16
	 * @param storeId
	 * @return
	 */
	public BaseResult acceptStore(long storeId, long newStoreId, String operator, String comment){
		logger.info("begin to accept store " + storeId);
		
		BaseResult result = new BaseResult();
		
		TransactionStatus transactionStatus = beginTransaction("createStore");
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			
			QueryCondition condition = new QueryCondition();
			condition.setStoreId(newStoreId);
			
			Store store = storeDao.selectStoreMainInfoById(condition);
			
			if( store != null )
			{
				result.setFailureMsg("编号为" + newStoreId + "的门店已经存在！");
			}
			else
			{
				storeDao.copyStoreMainInfoFromApply(storeId, newStoreId);
				storeDao.copyStoreAccountInfoFromApply(storeId, newStoreId);
				storeDao.copyStoreServiceAccountsFromApply(storeId, newStoreId);
				storeDao.deleteStoreApplyMainInfo(storeId);
				
				storeDao.updateUserStoreId(storeId, newStoreId);

				// 记录审批信息
				StoreProcessActivity activity = new StoreProcessActivity();
				activity.setStoreId(storeId);
				activity.setOperator(operator);
				activity.setStatus(StoreStatus.NORMAL);
				if( StringUtils.isEmpty(comment) )
				{
					comment = String.valueOf(newStoreId);
				}
				else
				{
					comment = comment + "(门店编号为:" + newStoreId + ")";
				}
				activity.setComment(comment);
				storeDao.insertStoreProcess(activity);
				
				// 复制文件
				File src = new File(ugjCfg.getValue("store.images.folder") + "/" + storeId);
				File destDir = new File(ugjCfg.getValue("store.images.folder") + "/" + newStoreId);
				FileUtils.moveDirectory(src, destDir);
				
				result.setSuccessMsg(String.valueOf(newStoreId));
			}
			
		} 
		catch (Exception e) {
			logger.error(e);
			result.setFailureMsg("审核门店失败");
		}
		
		if( result.isSuccess() )
		{
			logger.info("success to accept store " + storeId);
			commitTransaction(transactionStatus);
		}
		else
		{
			rollbackTransaction(transactionStatus);
			logger.warn("failure to accept store " + storeId);
		}
		
		return result;
	}
	
	
	/**
	 * @description: 拒绝门店申请	
	 * @DATE:2015年4月27日 下午10:57:42
	 * @param store
	 * @return
	 */
	public BaseResult rejectStore(Store store, String operator, String comment){
		BaseResult result = new BaseResult();
		result.setCode(BaseResult.CODE_SUCCESS);
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			storeDao.updateStoreApplyMainInfoStatus(store.getId(), StoreStatus.REJECT);
			
			// 记录审批信息
			StoreProcessActivity activity = new StoreProcessActivity();
			activity.setStoreId(store.getId());
			activity.setOperator(operator);
			activity.setStatus(StoreStatus.REJECT);
			activity.setComment(comment);
			storeDao.insertStoreProcess(activity);
			
		}
		catch (Exception e) {
			logger.error(e);
			result.setFailureMsg("更新状态异常");
		}
		
		return result;
	}
	
	/**
	 * @description:查询待审核门店（门店注册）	
	 * @DATE:2015年4月10日 上午7:07:51
	 * @param condition
	 * @return
	 */
	public List<Store> queryApplyStores(QueryCondition condition){
		
		logger.debug("begin to query apply stores");
		
		List<Store> stores = null;
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			
			stores = storeDao.selectStoreApplyMainInfo(condition);
		} 
		catch (Exception e) {
			logger.error(e);
		}
		
		if( stores == null )
		{
			stores = new ArrayList<>();
		}
		
		logger.info("success to query apply stores, total is " + stores.size() );
		
		return stores;
	}	
	
	/**
	 * @description:查询待审核门店（门店注册）	
	 * @DATE:2015年4月10日 上午7:07:51
	 * @param condition
	 * @return
	 */
	public Store queryApplyStoreById(QueryCondition condition){
		
		logger.debug("begin to query apply store " + condition.getStoreId());
		
		Store store = null;
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			
			store = storeDao.selectStoreApplyMainInfoById(condition);
			
			StoreAccount account = storeDao.selectStoreApplyAccountInfoById(condition);
			
			store.setAccount(account);
			
			List<StoreServiceAccount> serviceAccounts = storeDao.selectStoreApplyServiceAccounts(condition);
			store.setServiceAccounts(serviceAccounts);
			
			List<StoreProcessActivity> activities = storeDao.selectStoreProcessesById(condition);
			store.setActivities(activities);
		} 
		catch (Exception e) {
			logger.error(e);
		}
		
		return store;
	}	
	
	/**
	 * @description:查询门店的扫描件	
	 * @DATE:2015年4月10日 上午10:01:59
	 * @param storeId
	 * @param type
	 * @return
	 */
	public File queryStoreScanning(long storeId, String type){
		
		String filename = null;
		
		String fullFilecode = "store.images.name." + type;
		if( ugjCfg.getValue(fullFilecode) != null )
		{
			filename = ugjCfg.getValue(fullFilecode).toString();
		}
		else
		{
			filename = type;
		}
		
		
		String path = ugjCfg.getValue("store.images.folder") 
						+ "/" + storeId
						+ "/" + filename + ".jpg";
		
		File file = new File(path);
		
		return file;
	}
	
	/**
	 * @description:新建或更新第三方服务账号	
	 * @DATE:2015年4月10日 下午1:30:08
	 * @param storeId
	 * @param serviceAccount
	 * @return
	 */
	public BaseResult updateServiceAccount(StoreServiceAccount serviceAccount){
		logger.debug("begin to update store service account " + serviceAccount.getType());
		
		BaseResult result = new BaseResult();
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			storeDao.replaceStoreServiceAccount(serviceAccount);
		}
		catch (Exception e) 
		{
			logger.error(e);
			result.setFailureMsg("更新账号失败");
		}
		
		if( result.isSuccess() )
		{
			logger.debug("success to update store service account " + serviceAccount.getType());
		}
		else
		{
			logger.warn("failure to update store service account " + serviceAccount.getType());
		}
		
		return result;
	}
	
	/**
	 * @description:新建或更新第三方服务账号	
	 * @DATE:2015年4月10日 下午1:30:08
	 * @param storeId
	 * @param serviceAccount
	 * @return
	 */
	public BaseResult updateApplyServiceAccount(StoreServiceAccount serviceAccount){
		logger.debug("begin to update store service account " + serviceAccount.getType());
		
		BaseResult result = new BaseResult();
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			storeDao.replaceStoreServiceAccount(serviceAccount);
		}
		catch (Exception e) 
		{
			logger.error(e);
			result.setFailureMsg("更新账号失败");
		}
		
		if( result.isSuccess() )
		{
			logger.debug("success to update store service account " + serviceAccount.getType());
		}
		else
		{
			logger.warn("failure to update store service account " + serviceAccount.getType());
		}
		
		return result;
	}	
	
	
	/**
	 * @description:查询所有门店（带有分页条件）	
	 * @DATE:2015年3月9日 上午7:53:41
	 * @param condition
	 * @return
	 */
	public List<Store> queryStores(QueryCondition condition){
		
		logger.info("begin to query stores");
		
		List<Store> stores = null; 
		
		SqlSession session = sqlSessionFactory.openSession();
		
		// 分页条件设置
		int pageNo = condition.getPageNo();
		int pageSize = condition.getPageSize();
		if( pageNo <= 0 ){
			pageNo = 1;
		}
		if( pageSize <= 0 ){
			pageSize = 25;
		}
		condition.setLimit((pageNo - 1)*pageSize);
		
		StoreDao storeDao = session.getMapper(StoreDao.class);
		
		// 查询分页 总数
		int total = storeDao.selectStoreCount(condition);
		condition.setTotal(total);
		if( total > 0 )
		{
			stores = storeDao.selectStoreMainInfo(condition);
		}
		else 
		{
			stores = new ArrayList<>();
		}
		
		session.close();
		
		logger.info("success to query stores, total is " + total);
		
		return stores;
	}
	
	
	/**
	 * @description:根据编号范围查询所有门店（带有分页条件）	
	 * @DATE:2015年3月9日 上午7:53:41
	 * @param condition
	 * @return
	 */
	public List<Store> queryStoresByCode(QueryCondition condition){
		
		List<Store> stores = null; 
		
		long storeId = condition.getStoreId();
		long storeCode1 = storeId - (storeId%10000);
		long storeCode2 = storeCode1 + 9999;
		
		try( SqlSession session = sqlSessionFactory.openSession() )
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			
			stores = storeDao.queryStoresByCode(storeCode1, storeCode2, condition);
		}
		catch (Exception e) {
			logger.error(e);
		}
		
		return stores;
	}
	
	/**
	 * @description:修改门店信息	
	 * @DATE:2015年3月9日 下午10:19:28
	 * @param store
	 * @return
	 */
	public int changeStoreInfo(Store store){
		
		logger.info("begin to change the info of store " + store.getId());
		
		// ---- ----> 开始事务
		TransactionStatus status = beginTransaction("changeStoreInfo");

		int result = 0;
		
		try(SqlSession session = sqlSessionFactory.openSession();)
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			result = storeDao.changeStoreInfo(store);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(e);
		}
		
		if( result > 0 ){
			commitTransaction(status);
			logger.info("success to change the info of store " + store.getId());
		}
		else
		{
			rollbackTransaction(status);
			logger.warn("failure to change the info of store " + store.getId());
		}

		return result;
	}
	
	/**
	 * @description:修改门店信息	
	 * @DATE:2015年3月9日 下午10:19:28
	 * @param store
	 * @return
	 */
	public int changeUserPassword( String username, String oldPwd, String newPwd){
		
		logger.info("begin to change the password of user " + username);
		
		// ---- ----> 开始事务
		TransactionStatus status = beginTransaction("changeUserPassword");

		int result = 0;
		
		try(SqlSession session = sqlSessionFactory.openSession();)
		{
			StoreDao storeDao = session.getMapper(StoreDao.class);
			result = storeDao.updateUserPassword(username, oldPwd, newPwd);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(e);
		}
		
		if( result > 0 ){
			commitTransaction(status);
			logger.info("success to change the password of user " + username);
		}
		else
		{
			rollbackTransaction(status);
			logger.warn("failure to change the password of user " + username);
		}

		return result;
	}

	public void setUgjCfg(Configration ugjCfg) {
		this.ugjCfg = ugjCfg;
	}
}

