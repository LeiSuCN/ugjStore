package o2o;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uguanjia.o2o.Contract;
import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.document.ExcelReportCreator;
import com.uguanjia.o2o.document.OrderSheetHandler;
import com.uguanjia.o2o.manger.ContractManger;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.service.xiaobaitu.XiaobaituContract;

/*******************************************
 * @CLASS:ContactTest
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/application.xml"})
public class ContractTest {
	
	@Inject
	@Named("contractManger")
	private ContractManger contractManger;
	

	
	@Test
	public void test_query_all_contract(){
		
		QueryCondition condition = new QueryCondition();
		condition.setPageNo(1);
		
		List<Contract> contracts = contractManger.queryContracts(condition);
		
		assertNotNull(contracts);
		
		assertTrue(contracts.size() > 0 );
		
		for( Contract contract : contracts ){
			System.out.println("store:" + contract.getStoreId() 
					+ ", type:" + contract.getType() 
					+ ", time:" + contract.getTime()
					+ ", status:" + contract.getStatus());
		}
	}	
	
	@Test
	public void test_create_xbt_contract(){
		
		XiaobaituContract xbtContract = new XiaobaituContract();
		
		xbtContract.setStoreId(1);
		
		xbtContract.setYiFang("7-11");
		xbtContract.setGongShangZhuCeHao("123123132");
		xbtContract.setFaDingDaiBiaoRen("����");
		xbtContract.setDianHua("13811111111");
		xbtContract.setDiZhi("�����и�����");
		xbtContract.setYouBian("518000");
		
		contractManger.createContract( xbtContract );
		
		System.out.println(xbtContract.getId());
	}
	
	@Test
	public void test_create_excel_for_orders() throws Exception{
		Order order = null;
		List<Order> orders = new ArrayList<>();
		order = new Order();
		order.setId(1);
		order.setCreateTime(new Date());
		order.setCustomerName("����");
		order.setStore(10010);
		order.setStatus(1);
		orders.add(order);
		order = new Order();
		order.setId(2);
		order.setCreateTime(new Date());
		order.setCustomerName("����");
		order.setStore(10011);
		order.setStatus(2);
		orders.add(order);
		OrderSheetHandler osh = new OrderSheetHandler();
		osh.setOrders(orders);
		
		OutputStream os = new FileOutputStream("D:\\zfile\\test.xls");
		
		ExcelReportCreator rc = new ExcelReportCreator();
		rc.setSheetHandler(osh);
		
		String filepath = rc.create(os);
		
		os.close();
		
		assertNotNull(filepath);
		
		System.out.println(filepath);
		
	}

}

