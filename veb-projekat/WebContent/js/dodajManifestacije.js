$(document).ready(function() {

    $('#btnDodaj').on("click", function() {
    	var naziv = $('input[name="naziv"]').val();
		var tipManifestacije = $('select[name="tipManifestacije"]').val();
		var brojMesta = $('input[name="brojMesta"]').val();
		var datumIVremeOdrzavanja = $('input[name="datumIVremeOdrzavanja"]').val();
		var cenaRegularKarte = $('input[name="cenaRegularKarte"]').val();
    	var geografskaDuzina = $('input[name="geografskaDuzina"]').val();
		var geografskaSirina = $('input[name="geografskaSirina"]').val();
    	var ulicaIBroj = $('input[name="ulicaIBroj"]').val();
		var mesto = $('input[name="mesto"]').val();
		var postanskiBroj = $('input[name="postanskiBroj"]').val();

        if (validacija()) {
	
			let lokacija = {
				"geografskaDuzina": geografskaDuzina,
				"geografskaSirina": geografskaSirina,
				"ulicaIBroj": ulicaIBroj,
				"mesto": mesto,
				"postanskiBroj": postanskiBroj
			}
			
        	let data = {
            "naziv": naziv,
			"tipManifestacije": tipManifestacije,
            "brojMesta": brojMesta,
            "datumIVremeOdrzavanja": datumIVremeOdrzavanja,
            "cenaRegularKarte": cenaRegularKarte,
            "lokacija": lokacija
            };
    
	        $.post({
	            url: 'rest/manifestacije/dodajManifestaciju',
	            data: JSON.stringify(data),
	            contentType: 'application/json',
	            success: function(valid) {
	            	if (valid === "true") {
	            		alert("Manifestacija je uspesno dodata.");
	            	}
	            	else {
	            		alert("Manifestacija sa ovim nazivom vec postoji!");
	            	}
	            }
	        });
        } else {
        	return;
        }
        
        function validacija() {
	    	let valid = true;
	    
	    	if (naziv === '') {
	    		alert("Naziv je obavezno polje!");
	    		valid = false;
	    	} else if (tipManifestacije === '') {
	    		alert("Tip manifestacije je obavezno polje!");
	    		valid = false;
	    	} else if (brojMesta === '') {
	    		alert("Broj mesta je obavezno polje!");
	    		valid = false;
	    	} else if (datumIVremeOdrzavanja === '') {
	    		alert("Datum i vreme odrzavanja je obavezno polje!");
	    		valid = false;
	    	} else if (cenaRegularKarte === '') {
	    		alert("Cena regular karte je obavezno polje!");
	    		valid = false;
	    	} else if (geografskaDuzina === '') {
	    		alert("Geografska duzina je obavezno polje!");
	    		valid = false;
	    	} else if (geografskaSirina === '') {
	    		alert("Geografska sirina je obavezno polje!");
	    		valid = false;
	    	} else if (ulicaIBroj === '') {
	    		alert("Ulica i broj je obavezno polje!");
	    		valid = false;
	    	} else if (mesto === '') {
	    		alert("Mesto je obavezno polje!");
	    		valid = false;
	    	} else if (postanskiBroj === '') {
	    		alert("Postanski broj je obavezno polje!");
	    		valid = false;
	    	} else if (isNaN(postanskiBroj)) {
	    		alert("Postanski ne sme da se sadrzi nebrojevne vrednosti!");
	    		valid = false;
    		}
    		return valid;
    	}
  });  
});