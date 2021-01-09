$(document).ready(function() {

	var cenaKarte;
	
	$.get({
		url: 'rest/manifestacije/getAktivneManifestacije',
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
		 racunajIznos();
	});
	
	$('select[name="tipKarte"]').on('change', function(){
		 racunajIznos();
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
		let brojKarata = $('input[name="brojKarata"]').val();
		let manifestacija = JSON.parse($('select[name="manifestacije"] option:selected').attr("data-val"));
		let tipKarte = $('select[name="tipKarte"] option:selected').val();
		
		let karta = {
			"manifestacija": manifestacija,
			"datumIVremeManifestacije": manifestacija.datumIVremeOdrzavanja,
			"cena": cenaKarte,
			"statusKarte": "rezervisana",
			"tipKarte": tipKarte,
			"brojKarata": brojKarata
		};
		
		$.post({
			url: 'rest/karte/rezervacijaKarte',
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
	});
	
	
})