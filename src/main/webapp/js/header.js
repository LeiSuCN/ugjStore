;(function($) {
	
	function adjustFooterLocation( id ){
		var bodyHeight = document.documentElement.scrollHeight;
		var clientHeight = document.documentElement.clientHeight;
		
		if( bodyHeight < clientHeight ){
			var h = $('.container').height() + (clientHeight - bodyHeight);
			$('.container').css('min-height', h + 'px');
		}
	}
	
	$(document).ready(function() {
		adjustFooterLocation("footer");
	});
	
})(jQuery);
