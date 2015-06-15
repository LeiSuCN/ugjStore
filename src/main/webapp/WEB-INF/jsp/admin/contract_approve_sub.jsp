<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div class="approve-area">
	<label for="approve_comment">审核意见:</label>
	<textarea name="approve_comment" id="approve_comment" ></textarea>
	<br>
	<button id="approve_btn_yes">同意</button><button id="approve_btn_no">拒绝</button>
	<script type="text/javascript">
	
		(function(){
			
			function approve( result, comment){
				
				var url = root + '/admin/contract/' + contractId + '/accept/';
				
				$.post(url + result, {comment: comment}, function(resp){
					if( resp && resp.code == 0 ){
						window.location.href = root + '/admin/contract/list'
					} else{
						if( resp && resp.message ){
							alert( resp.message )
						} else{
							alert( '更新协议状态失败！' );
						}
					}
				})
				
			}
			
			
			var commentTxt = $('#approve_comment');
			
			$('#approve_btn_yes').click(function(){
				approve(1, commentTxt.val());
				return false;
			});
			
			$('#approve_btn_no').click(function(){
				approve(0, commentTxt.val());
				return false;
			});
			
		})();
		
	
	</script>
</div>