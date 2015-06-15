<%@page import="com.uguanjia.o2o.manger.OperatorUtils"%>
<%@page import="com.uguanjia.o2o.Operator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
                </div><!-- /.page-content -->
            </div><!-- /.main-content -->

            <div class="footer">
                <div class="footer-inner">
                    <!-- #section:basics/footer -->
                    <div class="footer-content">
                        <span class="bigger-120">
                            <span class="blue bolder">深圳市优管家商务管理有限公司</span>
                            &copy; 2013-2014
                        </span>

                        &nbsp; &nbsp;
                        <span class="action-buttons">
                        </span>
                    </div>

                    <!-- /section:basics/footer -->
                </div>
            </div>

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div><!-- /.main-container -->

        <!-- basic scripts -->

        <!--[if !IE]> -->
        <script type="text/javascript">
            window.jQuery || document.write("<script src='<%= request.getContextPath() %>/portal/js/jquery.min.js'>"+"<"+"/script>");
        </script>

        <!-- <![endif]-->

        <!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%= request.getContextPath() %>/portal/js/jquery1x.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
        <script type="text/javascript">
            if('ontouchstart' in document.documentElement) document.write("<script src='<%= request.getContextPath() %>/portal/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
        </script>
        <script src="<%= request.getContextPath() %>/portal/js/bootstrap.min.js"></script>

        <!-- ace scripts -->
        <script src="<%= request.getContextPath() %>/portal/js/ace-elements.min.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/ace.min.js"></script>

        <!-- inline scripts related to this page -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace.onpage-help.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/js/themes/sunburst.css" />

        <script type="text/javascript"> ace.vars['base'] = '..'; </script>
        <script src="<%= request.getContextPath() %>/portal/js/ace/ace.onpage-help.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/rainbow.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/language/generic.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/language/html.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/language/css.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/language/javascript.js"></script>
        
        <!-- ugj scripts -->
        <script type="text/javascript">
        	function currentBreadcrumb( menuArr ){
        		
        		if( !menuArr )
        			return;
        		
        		var len = menuArr.length;
        		
        		if( len <= 0 )
        			return;
        		
        		var breadcrumb = $('.breadcrumb');
        		
        		for( var i = 0 ; i < len ; i++ ){
        			var menu = menuArr[i];
        			var breadcrumbMenu = false;
        			
        			if( i == (len-1) ){
        				breadcrumbMenu = $('<li class="active">' + menu.name + '</li>');
        			} else{
        				breadcrumbMenu = $('<li><a href="' + menu.href + '">' + menu.name + '</a></li>');
        			}
        			
        			if( breadcrumbMenu )
        				breadcrumb.append(breadcrumbMenu);
        		}
        		
        	}
        	
        	$(document).ready(function() {
        		
        		var welcomeContent = "<small>欢迎,</small>";
        		
         		<%
         			Operator currentOperator = OperatorUtils.getCurrentOperator();
         			if( currentOperator != null )
         			{
         				out.print("welcomeContent +='" + currentOperator.getUsername() + "';");
         			}
        		%>
        		
        		$('.user-info').html(welcomeContent);
        	});
        </script> 
        
    </body>
