package o2o;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.StoreAccount;
import com.uguanjia.o2o.StoreServiceAccount;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.manger.StoreManger;
import com.uguanjia.o2o.service.BaseResult;
import com.uguanjia.o2o.service.Service;

/*******************************************
 * @CLASS:StoreTest
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月9日 上午7:46:54
 *******************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/application.xml"})
public class StoreTest {
	
	@Inject
	@Named("storeManger")
	private StoreManger storeManger;
	
	
	@Test
	/**
	 * @description:更新门店信息	
	 * @DATE:2015年3月9日 下午10:13:49
	 */
	public void test_create_store(){
		
		Store store = new Store();
		store.setId(4403010005l);
		store.setName("樟坑三区阿里之门");
		store.setRegistrationNo(440301106001666l);
		store.setLegalPerson("张三");
		store.setPhonenumber("18665819713");
		store.setAlipay("518000");
		store.setAddress("宝安区龙华新区民治街道樟坑三区2栋130铺");
		store.setArea(440301);
		store.setLat(22.617229f);
		store.setLon(114.044987f);
		
		StoreAccount account = new StoreAccount();
		account.setStoreId(store.getId());
		account.setName("张三");
		account.setBank("中国银行");
		account.setNumber("4367420110318077111");
		store.setAccount(account);
		
		StoreServiceAccount maowuAccount = new StoreServiceAccount();
		maowuAccount.setType(Service.MAOWU.ID);
		maowuAccount.setAccount("123456");
		maowuAccount.setPassword(StringUtils.EMPTY);
		List<StoreServiceAccount> serviceAccounts = new ArrayList<>();
		serviceAccounts.add(maowuAccount);
		store.setServiceAccounts(serviceAccounts);

		
		BaseResult result = storeManger.createStore( store );
		
		System.out.println(result.getMessage());
		
		assertTrue( result.getCode() == BaseResult.CODE_SUCCESS );
		
		boolean clear = true;
		
		if( !clear )
			return;
		
		try(Connection connection = storeManger.getSqlSessionFactory().openSession().getConnection()){
			
			String deleteDataSql = 
					"delete from store_main_info_apply where id = ?";
			
			PreparedStatement s1 = connection.prepareStatement(deleteDataSql);
			s1.setLong(1, store.getId());
			s1.execute();
			s1.close();
			
			deleteDataSql = 
					"delete from store_apply_info where id = ?";
			
			PreparedStatement s2 = connection.prepareStatement(deleteDataSql);
			s2.setLong(1, store.getId());
			s2.execute();
			s2.close();
			
			deleteDataSql = 
					"delete from users where store = ?";
			
			PreparedStatement s3 = connection.prepareStatement(deleteDataSql);
			s3.setLong(1, store.getId());
			s3.execute();
			s3.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @description:通过门店审核	
	 * @DATE:2015年4月8日 下午10:19:34
	 */
	@Test
	public void test_update_store_apply(){
		Store store = new Store();
		store.setId(4403010005l);
		store.setName("樟坑三区阿里之门");
		store.setRegistrationNo(440301106351666l);
		store.setLegalPerson("张三");
		store.setPhonenumber("18665819713");
		store.setAlipay("518000");
		store.setAddress("宝安区龙华新区民治街道樟坑三区2栋130铺");
		store.setArea(440301);
		store.setLat(22.617229f);
		store.setLon(114.044987f);
		
		StoreAccount account = new StoreAccount();
		account.setStoreId(store.getId());
		account.setName("张三");
		account.setBank("中国银行");
		account.setNumber("4367420110318077111");
		store.setAccount(account);
		
		StoreServiceAccount maowuAccount = new StoreServiceAccount();
		maowuAccount.setType(Service.MAOWU.ID);
		maowuAccount.setAccount("123456");
		maowuAccount.setPassword(StringUtils.EMPTY);
		List<StoreServiceAccount> serviceAccounts = new ArrayList<>();
		serviceAccounts.add(maowuAccount);
		store.setServiceAccounts(serviceAccounts);
		
		BaseResult result = storeManger.createStore( store );
		
		
		System.out.println(result.getMessage());
		
		assertTrue( result.getCode() == BaseResult.CODE_SUCCESS );
		
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		store.setName("阿里之A");
		
		result = storeManger.updateStore( store );
		
		System.out.println(result.getMessage());
		
		assertTrue( result.getCode() == BaseResult.CODE_SUCCESS );
		
		boolean clear = true;
		
		if( !clear )return;
		
		try(Connection connection = storeManger.getSqlSessionFactory().openSession().getConnection()){
			
			String deleteDataSql = 
					"delete from store_main_info where id = ?";
			
			PreparedStatement s1 = connection.prepareStatement(deleteDataSql);
			s1.setLong(1, store.getId());
			s1.execute();
			s1.close();
			
			deleteDataSql = 
					"delete from store_apply_info where id = ?";
			
			PreparedStatement s2 = connection.prepareStatement(deleteDataSql);
			s2.setLong(1, store.getId());
			s2.execute();
			s2.close();
			
			deleteDataSql = 
					"delete from users where store = ?";
			
			PreparedStatement s3 = connection.prepareStatement(deleteDataSql);
			s3.setLong(1, store.getId());
			s3.execute();
			s3.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @description:通过门店审核	
	 * @DATE:2015年4月8日 下午10:19:34
	 */
	@Test
	public void test_accept_store_apply(){
		Store store = new Store();
		store.setId(4403010005l);
		store.setName("樟坑三区阿里之门");
		store.setRegistrationNo(440301106351666l);
		store.setLegalPerson("张三");
		store.setPhonenumber("18665819712");
		store.setAlipay("518000");
		store.setAddress("宝安区龙华新区民治街道樟坑三区2栋130铺");
		store.setArea(440301);
		store.setLat(22.617229f);
		store.setLon(114.044987f);
		
		StoreAccount account = new StoreAccount();
		account.setStoreId(store.getId());
		account.setName("张三");
		account.setBank("中国银行");
		account.setNumber("4367420110318077111");
		store.setAccount(account);
		
		StoreServiceAccount maowuAccount = new StoreServiceAccount();
		maowuAccount.setType(Service.MAOWU.ID);
		maowuAccount.setAccount("123456");
		maowuAccount.setPassword(StringUtils.EMPTY);
		List<StoreServiceAccount> serviceAccounts = new ArrayList<>();
		serviceAccounts.add(maowuAccount);
		store.setServiceAccounts(serviceAccounts);
		
		BaseResult result = storeManger.createStore( store );
		
		
		System.out.println(result.getMessage());
		
		assertTrue( result.getCode() == BaseResult.CODE_SUCCESS );
		
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		result = storeManger.acceptStore( store.getId(), 4403010005l, "admin", "好" );
		
		System.out.println(result.getMessage());
		
		assertTrue( result.getCode() == BaseResult.CODE_SUCCESS );
		
		
		store.setId(Long.valueOf(result.getMessage()));
		
		boolean clear = false;
		
		if( !clear )return;
		
		try(Connection connection = storeManger.getSqlSessionFactory().openSession().getConnection()){
			
			String deleteDataSql = 
					"delete from store_main_info where id = ?";
			
			PreparedStatement s1 = connection.prepareStatement(deleteDataSql);
			s1.setLong(1, store.getId());
			s1.execute();
			s1.close();
			
			deleteDataSql = 
					"delete from store_apply_info where id = ?";
			
			PreparedStatement s2 = connection.prepareStatement(deleteDataSql);
			s2.setLong(1, store.getId());
			s2.execute();
			s2.close();
			
			deleteDataSql = 
					"delete from users where store = ?";
			
			PreparedStatement s3 = connection.prepareStatement(deleteDataSql);
			s3.setLong(1, store.getId());
			s3.execute();
			s3.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @description:查询全部门店注册信息	
	 * @DATE:2015年4月10日 上午7:05:25
	 */
	@Test
	public void test_query_all_store_apply(){
		
		Store store = new Store();
		store.setId(4403010005l);
		store.setName("樟坑三区阿里之门");
		store.setRegistrationNo(440301106351666l);
		store.setPhonenumber("18665819711");
		store.setAlipay("518000");
		store.setAddress("宝安区龙华新区民治街道樟坑三区2栋130铺");
		store.setArea(440301);
		store.setLat(22.617229f);
		store.setLon(114.044987f);
		
		StoreAccount account = new StoreAccount();
		account.setStoreId(store.getId());
		account.setName("张三");
		account.setBank("中国银行");
		account.setNumber("4367420110318077111");
		store.setAccount(account);
		
		BaseResult result = storeManger.createStore( store );
		
		assertTrue(result.isSuccess());
		
		QueryCondition condition = new QueryCondition();
		
		List<Store> stores = storeManger.queryApplyStores(condition);
		
		assertNotNull(stores);
		assertTrue( stores.size() > 0 );
		
		try(Connection connection = storeManger.getSqlSessionFactory().openSession().getConnection()){
			
			String deleteDataSql = 
					"delete from store_main_info_apply where id = ?";
			
			PreparedStatement s1 = connection.prepareStatement(deleteDataSql);
			s1.setLong(1, store.getId());
			s1.execute();
			s1.close();
			
			deleteDataSql = 
					"delete from store_apply_info where id = ?";
			
			PreparedStatement s2 = connection.prepareStatement(deleteDataSql);
			s2.setLong(1, store.getId());
			s2.execute();
			s2.close();
			
			deleteDataSql = 
					"delete from users where store = ?";
			
			PreparedStatement s3 = connection.prepareStatement(deleteDataSql);
			s3.setLong(1, store.getId());
			s3.execute();
			s3.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @description:查询门店的详细信息（申请）	
	 * @DATE:2015年4月10日 上午9:35:57
	 */
	@Test
	public void test_query_store_by_id_apply(){
	
		QueryCondition condition = new QueryCondition();
		condition.setStoreId(40);
		
		Store store = storeManger.queryApplyStoreById(condition);
		
		assertNotNull(store);
		assertNotNull(store.getAccount());
	}
	
	/**
	 * @description:查询门店的详细信息（审核通过）	
	 * @DATE:2015年4月10日 上午9:35:57
	 */
	@Test
	public void test_query_store_by_id(){
	
		
		Store store = storeManger.queryStoreById(4403010001l);
		
		assertNotNull(store);
		assertNotNull(store.getAccount());
	}
		
	/**
	 * @description:创建服务密码	
	 * @DATE:2015年4月10日 下午1:11:54
	 */
	@Test
	public void test_replace_store_service_account(){
		
		long storeId = 4403010001l;
		StoreServiceAccount serviceAccount = new StoreServiceAccount();
		serviceAccount.setStoreId(storeId);
		serviceAccount.setType( Service.SHUNFENG.ID );
		serviceAccount.setAccount("123456");
		serviceAccount.setPassword("65432111");
		
		this.storeManger.updateServiceAccount(serviceAccount);
		
	}
	
	@Test
	/**
	 * @description:查询全部门店	
	 * @DATE:2015年3月9日 上午7:48:13
	 */
	public void test_query_all_stores(){
		
		QueryCondition condition = new QueryCondition();
		
		condition.setPageNo(1);
		condition.setPageSize(20);
		
		List<Store> stores = storeManger.queryStores( condition );
		
		assertNotNull(stores);
		assertTrue( stores.size() > 0 );
		
		for( Store store : stores ){
			System.out.println("******** ******** ******** ********");
			System.out.println("id: " + store.getId());
			System.out.println("name: " + store.getName());
			System.out.println("address: " + store.getAddress());
		}
		
	}
	
	@Test
	/**
	 * @description:根据门店编号查询全部门店	
	 * @DATE:2015年3月9日 上午7:48:13
	 */
	public void test_query_stores_by_code(){
		
		QueryCondition condition = new QueryCondition();
		
		condition.setStoreId(4403010874L);
		
		List<Store> stores = storeManger.queryStoresByCode( condition  );
		
		assertNotNull(stores);
		assertTrue( stores.size() > 0 );
		
		for( Store store : stores ){
			System.out.println("******** ******** ******** ********");
			System.out.println("id: " + store.getId());
			System.out.println("name: " + store.getName());
			System.out.println("address: " + store.getAddress());
		}
		
	}
	
	@Test
	/**
	 * @description:更新门店信息	
	 * @DATE:2015年3月9日 下午10:13:49
	 */
	public void test_update_store_info(){
		
		Store store = new Store();
		store.setId(1);
		store.setName("365艺术生活超市");
		store.setAddress("深圳市宝安区");
		store.setLat(113.916394f);
		store.setLon(22.577226f);
		
		int result = storeManger.changeStoreInfo(store);
		
		assertTrue( result > 0 );
		
	}
}

