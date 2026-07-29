/**
 * 
 */

ADMIN =(function(){
	
	return {
		showMessage: function(cssclass,message){
			
			return '<div class="alert '+cssclass+'" role="alert">'+
			message+' <a href="#" class="pull-right"><span class="glyphicon glyphicon-remove"></span></a>'+
			'</div>';
		}
		
	}
	
	
})();