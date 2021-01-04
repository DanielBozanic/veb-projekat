$(document).ready(function() {
	
	$.get({
		url: 'rest/loginServis/linkovi',
		success: function(linkovi) {
			for (let l of linkovi) {
				 addLink(l);
			}
		}
	
	});
	
	$('#logout').on('click', function() {
		$.post({
		url: 'rest/loginServis/logout',
		success: function() {
		}
			
		});
	});
	
	
	function addLink(link) {
		let a = $('<a href='+ Object.keys(link)[0] +'></a>').text(Object.values(link)[0]);
		$('.menu').append(a);
	};
});