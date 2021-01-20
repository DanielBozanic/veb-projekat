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
		if (!barJednaKarta) {
			$('#objava').hide();
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
	            success: function() {
	            	alert("Komentar je uspesno objavljen.");
	            }
		});
	});
	
	function ucitajListuKomentara(korisnik) {
		if (korisnik === undefined) {
			if (trenutnaManifestacija.posterManifestacije === null) {
				$('#slikaIObjava').hide();
				$('#listaKomentara').removeClass("col-md-5").addClass("col-md-9");
			}
			$.post({
				url: 'rest/komentari/getOdobreniKomentariZaManifestaciju',
				data: trenutnaManifestacija.naziv,
				contentType: 'text/plain',
				success: function(odobreniKomentari) {
						for (let komentar of odobreniKomentari) {
							dodajKomentarUListu(komentar);
						}
					}
				});
		} else if (korisnik.uloga === 'kupac') {
			$.post({
				url: 'rest/komentari/getOdobreniKomentariZaManifestaciju',
				data: trenutnaManifestacija.naziv,
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
				$('#listaKomentara').removeClass("col-md-5").addClass("col-md-9");
			}
			$.post({
				url: 'rest/komentari/getKomentariZaManifestaciju',
				data: trenutnaManifestacija.naziv,
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