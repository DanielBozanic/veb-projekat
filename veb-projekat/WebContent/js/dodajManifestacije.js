$(document).ready(function() {

	var posterManifestacije;

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
            "lokacija": lokacija,
            "posterManifestacije": posterManifestacije
            };
    
	        $.post({
	            url: 'rest/prodavci/dodajManifestaciju',
	            data: JSON.stringify(data),
	            contentType: 'application/json',
	            success: function(valid) {
	            	if (valid === "true") {
	            		alert("Manifestacija je uspesno dodata.");
	            		ocistiFormu();
	            	}
	            	else {
	            		alert("Manifestacija je neuspesno dodata!");
	            	}
	            }
	        });
        } else {
        	return;
        }
        
        function ocistiFormu() {
        	$('input[name="naziv"]').val('');
			$('input[name="brojMesta"]').val('');
			$('input[name="datumIVremeOdrzavanja"]').val('');
			$('input[name="cenaRegularKarte"]').val('');
	    	$('input[name="geografskaDuzina"]').val('');
			$('input[name="geografskaSirina"]').val('');
	    	$('input[name="ulicaIBroj"]').val('');
			$('input[name="mesto"]').val('');
			$('input[name="postanskiBroj"]').val('');
			$('input[name="posterManifestacije"]').val('');
			posterManifestacije = '';
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
	    	} else if (brojMesta < 0) {
	    		alert("Broj mesta ne sme biti negativna vrednost!");
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
	    	} else if (cenaRegularKarte < 0) {
	    		alert("Cena regular karte ne sme biti negativna vrednost!");
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
    		} else if (Date.parse(datumIVremeOdrzavanja) < Date.now()) {
    			alert("Datum manifestacije ne sme biti pre danasnjeg!");
    			valid = false;
    		}
    		return valid;
    	}
  	});
  
  	$('input[name="posterManifestacije"]').on('change', function() {
  		var image = this.files[0];
	    var reader = new FileReader();
	    reader.onloadend = function() {
	 		posterManifestacije = reader.result;
	      }
	    reader.readAsDataURL(image);
  	});
  	
});