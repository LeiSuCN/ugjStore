package com.uguanjia.o2o.web.filter;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Contract;
import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.manger.ContractManger;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.meta.ContractStatus;
import com.uguanjia.o2o.meta.Roles;
import com.uguanjia.o2o.service.Service;

/*******************************************
 * @CLASS:ServiceContractInerceptor
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public class ServiceContractInerceptor implements HandlerInterceptor{
	
	private Service service;
	
	private ContractManger contractManger;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		
		Operator operator = OperatorUtils.getCurrentOperator();
		
		// 管理员不需要签订合同
		if( operator != null && operator.hasRole(Roles.ROLE_ADMIN) )
		{
			return true;
		}
		
		String contractSessionKey = "contract_" + service.CODE;
		
		if( session.getAttribute(contractSessionKey) != null ){
			return true;
		}
		
		Store store = OperatorUtils.getCurrentStore(request);
		
		Contract serviceContract = null;
		
		QueryCondition condition = new QueryCondition();
		condition.setStoreId(store.getId());
		condition.setType(service.ID);
		
		List<Contract> contracts = this.contractManger.queryContracts(condition);
		if( contracts != null && contracts.size() > 0 ){
			serviceContract = contracts.get(0);
		}
		
		if( serviceContract == null )
		{
			response.sendRedirect(request.getContextPath() + "/store/contract/alert/" + service.ID);
			return false;
		}
		else if( serviceContract.getStatus() != ContractStatus.ACCEPT )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			request.setAttribute("error_msg", "您已于" + sdf.format(serviceContract.getTime()) + "提交申请，请耐心等待管理员审核");
			request.getRequestDispatcher("/WEB-INF/jsp/store/error.jsp").forward(request, response);
			return false;
		}
		else
		{
			session.setAttribute(contractSessionKey, serviceContract);
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	public void setContractManger(ContractManger contractManger) {
		this.contractManger = contractManger;
	}

	public void setService(Service service) {
		this.service = service;
	}
}

