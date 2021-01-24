$(document).ready(function() {

	var cenaKarte;
	
	$.get({
		url: 'rest/manifestacije/getAktuelneManifestacije',
		success: function(aktivneManifestacije) {
			var selected = false;
			for (let m of aktivneManifestacije) {
				 var optionManfestacija = $('<option value = "' + m.naziv + '"' + "data-val='" + JSON.stringify(m) + 
					(selected ? "'>" : "' selected='selected'>") + m.naziv +  '</option>' );
				selected = true;
				$('select[name="manifestacije"]').append(optionManfestacija);
			}
		}
	});
	
	$('input[name="brojKarata"]').on('change', function(){
		let brojKarata = $('input[name="brojKarata"]').val();
		if (brojKarata > 0) {
			racunajIznos();
		} 
	});
	
	$('select[name="tipKarte"]').on('change', function(){
		let brojKarata = $('input[name="brojKarata"]').val();
		if (brojKarata > 0) {
			racunajIznos();
		} 
	});
	
	function racunajIznos() {	
		let tipKarte = $('select[name="tipKarte"]').find(":selected").val();
		let brojKarata = $('input[name="brojKarata"]').val();
		cenaKarte = JSON.parse($('select[name="manifestacije"] option:selected').attr('data-val')).cenaRegularKarte;
					
		if (tipKarte === "fanPit"){
			cenaKarte = cenaKarte * 2;
		}
		else if (tipKarte == "vip"){
			cenaKarte = cenaKarte * 4;
		}
		
		let ukupnaCena = (cenaKarte * brojKarata).toFixed(2);
		
		$('#ukupnaCena').text("Ukupna cena: " + ukupnaCena);
	}
	
	
	$('#btnRezervacija').on('click', function(){
		var brojKarata = $('input[name="brojKarata"]').val();
		var manifestacija = JSON.parse($('select[name="manifestacije"] option:selected').attr("data-val"));
		var tipKarte = $('select[name="tipKarte"] option:selected').val();
		
		if (vaidacija()) {
		
			let karta = {
			"manifestacija": manifestacija,
			"datumIVremeManifestacije": manifestacija.datumIVremeOdrzavanja,
			"cena": cenaKarte,
			"statusKarte": "rezervisana",
			"tipKarte": tipKarte,
			"brojKarata": brojKarata
			};
		
			$.post({
				url: 'rest/kupci/rezervacijaKarte',
		            data: JSON.stringify(karta),
		            contentType: 'application/json',
		            success: function(valid) {
		            	 if (valid === "true") {
							alert("Uspesna rezervacija.");
						 } else {
							alert("Rezervacija nije prosla.");	
						 }
		            }
				});
		} else {
			return;
		}
		
		function vaidacija() {
			let valid = true;
			if (brojKarata === '') {
				alert("Broj karti je obavezno polje.")
				valid = false;
			} else if (brojKarata < 1) {
				alert("Broj karti ne moze biti manji od 1.");
				valid = false;
			}
			return valid;
		}
		
	});	
})