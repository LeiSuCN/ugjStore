package com.uguanjia.o2o.web.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/*******************************************
 * @CLASS:SaveLoginRecoderHandler
 * @DESCRIPTION:记录用户登录的信息	
 * @VERSION:v1.0
 * @DATE:2015年4月27日 下午7:20:12
 *******************************************/
public class SaveLoginRecordHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private DataSource dataSource;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		
		// 保存登录用户名和ip地址
		Object principal = authentication.getPrincipal();
		
		if( principal != null && principal instanceof UserDetails ){
			UserDetails userDetails = (UserDetails)principal;
			String username = userDetails.getUsername();
			String address = request.getRemoteAddr();
			
			try (Connection conn = dataSource.getConnection())
			{
				PreparedStatement statement = conn.prepareStatement("insert into login_history(username, address) values(?,?)");
				
				statement.setString(1, username);
				statement.setString(2, address);
				
				statement.execute();
				
				statement.close();
			} 
			catch (Exception e)
			{
				logger.error(e);
			}
			
		}

		
		// 根据spring security文档，默认的的hanler为SavedRequestAwareAuthenticationSuccessHandler
		// 所以要保留SavedRequestAwareAuthenticationSuccessHandler的默认处理
		super.onAuthenticationSuccess(request, response, authentication);
		
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}

