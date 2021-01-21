$(document).ready(function() {


    $('#btnLogin').on('click',function(){
        var korisnickoIme = $('input[name="korisnickoIme"]').val();
        var lozinka = $('input[name="lozinka"]').val();

        if (validacija()) {

        	let data = {
            "korisnickoIme": korisnickoIme,
			"lozinka": lozinka
            };

	        $.post({
	            url: 'rest/loginServis/login',
	            data: JSON.stringify(data),
	            contentType: 'application/json',
	            statusCode: {
	            	500: function(message) {
	            		alert(message.responseText);
	            		ocistiLoginFormu();
	            	},
	            	200: function(message) {
	            		alert(message);
	            		window.location.href = "glavna.html";
	            	}
	            }
	        });
        } else {
        	return;
        }
        
        function ocistiLoginFormu() {
        	$('input[name="korisnickoIme"]').val('');
        	$('input[name="lozinka"]').val('');
        }
        
        function validacija() {
	    	let valid = true;
	    
	    	if (korisnickoIme === '') {
	    		alert("Korisnicko ime je obavezno polje!");
	    		valid = false;
	    	} else if (lozinka === '') {
	    		alert("Lozinka je obavezno polje!");
                valid = false;
            }
    		return valid;
        }
    });
});