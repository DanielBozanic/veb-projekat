$(document).ready(function() {
	
	$.get({
		url: 'rest/korisnici/linkovi',
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
		let a = $('<a class="nav-link" href='+ Object.keys(link)[0] +'></a>').text(Object.values(link)[0]);
		let li = $('<li class="nav-item"></li>');
		li.append(a);
		$('#menu').append(li);
	};
});