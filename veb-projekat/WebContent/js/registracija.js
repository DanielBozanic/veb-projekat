$(document).ready(function() {

    $('#btnRegistruj').on("click", function() {
    	var korisnickoIme = $('input[name="korisnickoIme"]').val();
		var lozinka = $('input[name="lozinka"]').val();
		var lozinka2 = $('input[name="lozinka2"]').val();
		var ime = $('input[name="ime"]').val();
    	var prezime = $('input[name="prezime"]').val();
		var pol = $('select[name="pol"]').val();
    	var datumRodjenja = $('input[name="datumRodjenja"]').val();
        if (validacija()) {
        	let data = {
            "korisnickoIme": korisnickoIme,
			"lozinka": lozinka,
            "ime": ime,
            "prezime": prezime,
            "datumRodjenja": datumRodjenja,
            "pol": pol
            };
    
	        $.post({
	            url: 'rest/kupci/registrujKupca',
	            data: JSON.stringify(data),
	            contentType: 'application/json',
	            success: function(valid) {
	            	if (valid) {
	            		alert("Registracija je uspesna.");
	            		window.location.href = "login.html";
	            	}
	            	else {
	            		alert("Ovo korisnicko ime nije dostupno!");
	            	}
	            }
	        });
        } else {
        	return;
        }
        
        function validacija() {
	    	let valid = true;
	    
	    	if (korisnickoIme === '') {
	    		alert("Korisnicko ime je obavezno polje!");
	    		valid = false;
	    	} else if (lozinka === '') {
	    		alert("Lozinka je obavezno polje!");
	    		valid = false;
	    	} else if (lozinka2 !== lozinka) {
	    		alert("Niste uneli istu lozinku!");
	    		valid = false;
	    	} else if (ime === '') {
	    		alert("Ime je obavezno polje!");
	    		valid = false;
	    	} else if (!isNaN(ime)) {
	    		alert("Ime ne sme sadrzati brojevne vrednosti!");
	    		valid = false;
	    	} else if (prezime === '') {
	    		alert("Prezime je obavezno polje!");
	    		valid = false;
	    	} else if (!isNaN(prezime)) {
	    		alert("Prezime ne sme sadrzati brojevne vrednosti!");
	    		valid = false;
	    	} else if (pol === '') {
	    		alert("Pol je obavezno polje!");
	    		valid = false;
	    	} else if (datumRodjenja === '') {
	    		alert("Datum rodjenja je obavezno polje!");
	    		valid = false;
	    	}
    	
    		return valid;
    	}
  });  
});