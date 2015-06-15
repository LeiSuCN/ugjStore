package com.uguanjia.o2o.manger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.meta.Roles;

/*******************************************
 * @CLASS:OperatorUtils
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月10日 下午10:05:12
 *******************************************/
public class OperatorUtils {
	
	public static CurrentOperatorFinder finder = new CurrentOperatorFinder(){

		@SuppressWarnings("unchecked")
		@Override
		public Operator current() {
			Operator operator = null;
			
			if( SecurityContextHolder.getContext() == null || 
					SecurityContextHolder.getContext().getAuthentication() == null || 
					SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null ){
				return operator;
			}
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if( principal instanceof UserDetails ){
				operator = new Operator();
				UserDetails userDetails = (UserDetails)principal;
				operator.setUsername(userDetails.getUsername());;
			} else{
				return operator;
			}
			
			Collection<GrantedAuthority> authorities = 
					(Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			
			if( authorities != null ){
				List<String> roles = new ArrayList<>(authorities.size());
				for( GrantedAuthority gauthority : authorities ){
					roles.add(gauthority.getAuthority());
				}
				
				operator.setRoles(roles);
			}
			
			return operator;
		}
	};
	
	public static Operator getCurrentOperator(){
		return finder.current();
	}
	
	public static Store getCurrentStore(HttpServletRequest request){
		
		Operator operator = getCurrentOperator();
		
		if( operator != null && operator.hasRole(Roles.ROLE_STORE) ){
			return (Store)request.getSession().getAttribute("STORE_INFO");
		}
		
		return null;
	}
	
	public static interface CurrentOperatorFinder{
		public Operator current();
	}

}

