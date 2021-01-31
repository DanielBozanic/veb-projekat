$(document).ready(function() {

	var trenutnaManifestacija;
	var trenutniKorisnik;
	
	$.get({
		url: 'rest/manifestacije/getOdabranaManifestacija',
		success: function(manifestacija) {
			if (manifestacija !== undefined){
				popuniFormu(manifestacija);
				trenutnaManifestacija = manifestacija;
				ucitajKorisnika();
			}
		}
	});
	
	function ucitajKorisnika() {
		$.get({
            url: 'rest/korisnici/trenutniKorisnik',
            dataType: 'json',
           	success: function(korisnik) {
	            	if (korisnik !== undefined) {
	            		if (korisnik.uloga === 'kupac') {
	            			mogucnostKomentarisanja(korisnik);
	            		} else {
	            			$('#objava').hide();
	            		}
	            	} else {
	            		$('#objava').hide();
	            	}
	            	ucitajListuKomentara(korisnik);
	            }
	     });
	}

	function popuniFormu(manifestacija) {
		$("input[name=naziv]").val(manifestacija.naziv);
		$("input[name=tipManifestacije]").val(manifestacija.tipManifestacije);
		$("input[name=brojMesta]").val(manifestacija.brojMesta);
		$("input[name=datumIVremeOdrzavanja]").val(manifestacija.datumIVremeOdrzavanja);
		$("input[name=cenaRegularKarte]").val(manifestacija.cenaRegularKarte);
		$("input[name=geografskaDuzina]").val(manifestacija.lokacija.geografskaDuzina);
		$("input[name=geografskaSirina]").val(manifestacija.lokacija.geografskaSirina);
		$("input[name=ulicaIBroj]").val(manifestacija.lokacija.ulicaIBroj);
		$("input[name=mesto]").val(manifestacija.lokacija.mesto);
		$("input[name=postanskiBroj]").val(manifestacija.lokacija.postanskiBroj);
		$("input[name=prosecnaOcena]").val(manifestacija.prosecnaOcena);
		$("img[name=posterManifestacije]").attr('src', manifestacija.posterManifestacije);
	}
	
	function mogucnostKomentarisanja(korisnik) {
		var barJednaKarta = false;
		for (let karta of korisnik.sveKarte) {
			if (karta.manifestacija.naziv === trenutnaManifestacija.naziv &&
				karta.statusKarte === 'rezervisana' && 
				Date.parse(trenutnaManifestacija.datumIVremeOdrzavanja) < Date.now()) {
					barJednaKarta = true;
					trenutniKorisnik = korisnik;
					break;
				}
		}
		if (trenutnaManifestacija.posterManifestacije === null) {
			if (!barJednaKarta) {
				$('#slikaIObjava').hide();
				if ($('#listaKomentara').hasClass("col-md-5")) {
					$('#listaKomentara').removeClass("col-md-5");
				}
				if (!$('#listaKomentara').hasClass("col-md-9")) {
					$('#listaKomentara').addClass("col-md-9");
				}
			} else {
				$('#poster').hide();
			}
		} else {
			if (!barJednaKarta) {
				$('#objava').hide();
			}
		}
	}
	
	$('#btnKomentar').on('click', function() {
		var tekstKomentara = $('#komentar').val();
		var ocena = 0;
		$('input[name=stars]:checked').each(function() {
			ocena = ocena + parseInt($(this).val());
		});
		
		let komentar = {
			"kupacKarte": trenutniKorisnik.korisnickoIme,
			"tekstKomentara": tekstKomentara,
			"manifestacija": trenutnaManifestacija,
			"ocena": ocena
		}
		
		$.post({
			url: 'rest/komentari/objaviKomentar',
	            data: JSON.stringify(komentar),
	            contentType: 'application/json',
	            success: function(valid) {
	            	if (valid === "true") {
	            		alert("Komentar je uspesno objavljen.");
	            	} else {
	            		alert("Ne mozete komentarisati vise!");
	            	}
	            }
		});
	});
	
	function ucitajListuKomentara(korisnik) {
		if (korisnik === undefined) {
			if (trenutnaManifestacija.posterManifestacije === null) {
				$('#slikaIObjava').hide();
				if ($('#listaKomentara').hasClass("col-md-5")) {
					$('#listaKomentara').removeClass("col-md-5");
				}
				if (!$('#listaKomentara').hasClass("col-md-9")) {
					$('#listaKomentara').addClass("col-md-9");
				}
			}
			$.get({
				url: 'rest/komentari/getOdobreniKomentariZaManifestaciju?nazivManifestacije=' + 
					trenutnaManifestacija.naziv,
				contentType: 'text/plain',
				success: function(odobreniKomentari) {
						for (let komentar of odobreniKomentari) {
							dodajKomentarUListu(komentar);
						}
					}
				});
		} else if (korisnik.uloga === 'kupac') {
			$.get({
				url: 'rest/komentari/getOdobreniKomentariZaManifestaciju?nazivManifestacije=' + 
					trenutnaManifestacija.naziv,
				contentType: 'text/plain',
				success: function(odobreniKomentari) {
						for (let komentar of odobreniKomentari) {
							dodajKomentarUListu(komentar);
						}
					}
				});
		} else {
			if (trenutnaManifestacija.posterManifestacije === null) {
				$('#slikaIObjava').hide();
				if ($('#listaKomentara').hasClass("col-md-5")) {
					$('#listaKomentara').removeClass("col-md-5");
				}
				if (!$('#listaKomentara').hasClass("col-md-9")) {
					$('#listaKomentara').addClass("col-md-9");
				}
			} else {
				$('#objava').hide();
			}
			$.get({
				url: 'rest/komentari/getKomentariZaManifestaciju?nazivManifestacije=' + 
					trenutnaManifestacija.naziv,
				contentType: 'text/plain',
				success: function(sviKomentari) {
						for (let komentar of sviKomentari) {
							dodajKomentarUListu(komentar);
						}
					}
				});
		}
	}
	
	function dodajKomentarUListu(komentar){
		let p = $('<p>' + komentar.kupacKarte + ': ' + komentar.tekstKomentara +  ' (Ocena: ' + 
			komentar.ocena + '/5)' + '</p>');
		$('#listaKomentara').append(p);
	}
	
})