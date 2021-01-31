$(document).ready(function() {
	
	var posterManifestacije;
	
	$.get({
		url: 'rest/manifestacije/getManifestacijeZaProdavca',
		success: function(manifestacije) {
			azurirajManifestacije(manifestacije, true);
		}
	});
	
	$('select[name="manifestacije"]').on('change', function() {
		let manifestacija = JSON.parse($('select[name="manifestacije"] option:selected').attr("data-val"));
		popuniFormu(manifestacija);
	});
	
	function azurirajManifestacije(manifestacije, prviPut) {
		if (!prviPut) {
			$('select[name="manifestacije"]').empty();
		}
		var selected = false;
		for (let m of manifestacije) {
				 var optionManfestacija = $('<option value = "' + m.naziv + '"' + "data-val='" + JSON.stringify(m) + 
					(selected ? "'>" : "' selected='selected'>") + m.naziv +  '</option>' );
				$('select[name="manifestacije"]').append(optionManfestacija);
				if (!selected) {
					popuniFormu(m);
				}
				selected = true;
			}
	}
	
	function popuniFormu(manifestacija) {
		$("input[name=naziv]").val(manifestacija.naziv);
		$("select[name=tipManifestacije]").val(manifestacija.tipManifestacije);
		$("input[name=brojMesta]").val(manifestacija.brojMesta);
		$("input[name=datumIVremeOdrzavanja]").val(manifestacija.datumIVremeOdrzavanja);
		$("input[name=cenaRegularKarte]").val(manifestacija.cenaRegularKarte);
		$("input[name=geografskaDuzina]").val(manifestacija.lokacija.geografskaDuzina);
		$("input[name=geografskaSirina]").val(manifestacija.lokacija.geografskaSirina);
		$("input[name=ulicaIBroj]").val(manifestacija.lokacija.ulicaIBroj);
		$("input[name=mesto]").val(manifestacija.lokacija.mesto);
		$("input[name=postanskiBroj]").val(manifestacija.lokacija.postanskiBroj);
		posterManifestacije = manifestacija.posterManifestacije;
	}
	
	$('#btnIzmeni').on('click', function() {
		let naziv = $("input[name=naziv]").val();
		var tipManifestacije = $("select[name=tipManifestacije]").val();
		var brojMesta = $("input[name=brojMesta]").val();
		var datumIVremeOdrzavanja = $("input[name=datumIVremeOdrzavanja]").val();
		var cenaRegularKarte = $("input[name=cenaRegularKarte]").val();
		let aktivan = JSON.parse($('select[name="manifestacije"] option:selected').attr("data-val")).aktivan;
		var geografskaDuzina = $("input[name=geografskaDuzina]").val();
		var geografskaSirina = $("input[name=geografskaSirina]").val();
		var ulicaIBroj = $("input[name=ulicaIBroj]").val();
		var mesto = $("input[name=mesto]").val();
		var postanskiBroj =$("input[name=postanskiBroj]").val();
		
		if (validacija()) {
			let lokacija = {
			"geografskaDuzina": geografskaDuzina,
			"geografskaSirina": geografskaSirina,
			"ulicaIBroj": ulicaIBroj,
			"mesto": mesto,
			"postanskiBroj": postanskiBroj
			}
		
			var manifestacija = {
				"naziv": naziv,
				"tipManifestacije": tipManifestacije,
				"brojMesta": brojMesta,
				"datumIVremeOdrzavanja": datumIVremeOdrzavanja,
				"cenaRegularKarte": cenaRegularKarte,
				"lokacija": lokacija,
				"posterManifestacije": posterManifestacije,
				"aktivan": aktivan
			}
			
			$.ajax({
		            url: 'rest/prodavci/izmeniManifestaciju',
		            type: 'PUT',
		            data: JSON.stringify(manifestacija),
		            contentType: 'application/json',
		            success: function(manifestacije) {
		            	if (manifestacije !== undefined) {
		            		alert("Manifestacija je uspesno izmenjena.");
		            		azurirajManifestacije(manifestacije, false);
		            	}
		            	else {
		            		alert("Neuspena izmena manifestacije!");
		            	}
		            }
		        });
		} else {
			return;
		}
		  
	    function validacija() {
	    	let valid = true;
	    
	    	if (tipManifestacije === '') {
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