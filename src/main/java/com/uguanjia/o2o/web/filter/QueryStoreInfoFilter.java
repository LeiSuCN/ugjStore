package com.uguanjia.o2o.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.StoreServiceAccount;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.OrderManger;
import com.uguanjia.o2o.manger.StoreManger;
import com.uguanjia.o2o.meta.Roles;
import com.uguanjia.o2o.meta.StoreProcessActivity;
import com.uguanjia.o2o.meta.StoreStatus;
import com.uguanjia.o2o.service.Service;

/*******************************************
 * @CLASS:QueryStoreInfoFilter
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015 2 13 9:46:14
 *******************************************/
public class QueryStoreInfoFilter extends GenericFilterBean{
	
	private final Logger logger = LogManager.getLogger(OrderManger.class);

	private StoreManger storeManger;
	
	private boolean queryStoreInfo(HttpServletRequest request,ServletResponse response) throws ServletException, IOException{
		
		// 如果session中不存在STORE_INFO
		// ,则为角色为STORE的用户查询门店信息
		if( request.getSession() != null && 
				request.getSession().getAttribute("STORE_INFO") == null ){
			
			Operator operator = OperatorUtils.getCurrentOperator();
			
			if( operator != null && operator.hasRole(Roles.ROLE_STORE) 
					&& !operator.hasRole(Roles.ROLE_ADMIN)){
				String username = operator.getUsername();
				if( !StringUtils.isEmpty(username) ){
					Store store = this.storeManger.queryStoreByUsername(username);
					
					if( store != null ){
						logger.debug("user " + username + " is in the store " + store.getId() );
						request.getSession().setAttribute("STORE_INFO", store);
					} else{
						logger.debug("user " + username + " doesn't have store ");
					}
				}
			}
		}
		
		if( request.getSession().getAttribute("STORE_INFO") != null ){
			Store store = (Store)request.getSession().getAttribute("STORE_INFO");
			// 当store id小于1000000000l时，说明该门店正处于待审核状态
			if( store.getId() < 1000000000l)
			{
				HttpServletRequest httpReq = (HttpServletRequest) request;
				
				String servletpath = httpReq.getServletPath().toLowerCase();
				
				if( servletpath.startsWith("/js") 
					|| servletpath.startsWith("/css") 
					|| servletpath.startsWith("/style") 
					|| servletpath.startsWith("/portal") 
					|| servletpath.startsWith("/public")
					|| servletpath.startsWith("/file")
					|| servletpath.startsWith("/store/detail/scanning")
					|| servletpath.endsWith("html"))
				{
					return true;
				}
				
				httpReq.setAttribute("store", store);
				
				// 待审核状态
				if( StoreStatus.APPROVAL == store.getStatus() )
				{
					httpReq.getRequestDispatcher("/WEB-INF/jsp/public/register_wait.jsp").forward(request, response);
				}
				// 驳回
				else if( StoreStatus.REJECT == store.getStatus() )
				{
					// 申请时填写的菜鸟号
					List<StoreServiceAccount> accounts = store.getServiceAccounts();
					if( accounts != null && accounts.size() > 0 )
					{
						for( StoreServiceAccount account : accounts )
						{
							if( account.getType() == Service.CAINIAO.ID )
							{
								httpReq.setAttribute(Service.CAINIAO.CODE, account.getAccount());
								break;
							}
						}
					}
					
					// 获取驳回理由
					List<StoreProcessActivity> activities = store.getActivities();
					if( activities != null && activities.size() > 0 )
					{
						StoreProcessActivity activity = activities.get(activities.size() - 1);
						httpReq.setAttribute("error_msg", activity.getComment());
					}
					
					httpReq.getRequestDispatcher("/WEB-INF/jsp/public/register_reject.jsp").forward(request, response);
				}
				
				
				return false;
			}
		}
		
		return true;
	}

	public StoreManger getStoreManger() {
		return storeManger;
	}

	public void setStoreManger(StoreManger storeManger) {
		this.storeManger = storeManger;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if( queryStoreInfo((HttpServletRequest)request, response) ){
			chain.doFilter(request, response);
		}
	}

}

