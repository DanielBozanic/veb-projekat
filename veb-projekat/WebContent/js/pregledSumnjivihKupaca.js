$(document).ready(function(){

    $.get({
		url: 'rest/administratori/getSumnjiviKupci',
		success: function(sumnjiviKupci) {
			for (let sk of sumnjiviKupci) {
				 dodajSumnjivKupacRed(sk);
			}
			blokirajKorisnika();
			odblokirajKorisnika();
		}
	});
	
	function dodajSumnjivKupacRed(sumnjivKupac) {
		let tr = $('<tr></tr>');
		let korisnickoIme = $('<td>' + sumnjivKupac.korisnickoIme + '</td>');
		let ime = $('<td>' + sumnjivKupac.ime + '</td>');
		let prezime = $('<td>' + sumnjivKupac.prezime + '</td>');
		let datumRodjenja = $('<td>' + sumnjivKupac.datumRodjenja + '</td>');
		let pol = $('<td>' + sumnjivKupac.pol + '</td>');
		let uloga = $('<td>' + sumnjivKupac.uloga + '</td>');
		let brojSakupljenihBodova = $('<td>' + sumnjivKupac.brojSakupljenihBodova + '</td>');
		let status;
		let blokiraj;
		if (sumnjivKupac.blokiran) {
			status = $('<td>Blokiran</td>');
			blokiraj = $('<td><a class="odblokiraj" href="#">Odblokiraj korisnika</a></td>');
		} else {
			status = $('<td>Aktivan</td>');
			blokiraj = $('<td><a class="blokiraj" href="#">Blokiraj korisnika</a></td>');
		}
		tr.append(korisnickoIme).append(ime).append(prezime).append(datumRodjenja).
			append(pol).append(uloga).append(brojSakupljenihBodova).append(status).append(blokiraj);
		$('#tabela tbody').append(tr);
	};
	
	function blokirajKorisnika() {
		
		$('.blokiraj').on('click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var korisnickoIme = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/blokirajSumnjivogKupca',
				type: 'PUT',
				data: korisnickoIme,
	            contentType: 'text/plain',
				success: function(korisnici) {
					if (korisnici !== undefined) {
						$('#tabela tbody').empty();
						for (let k of korisnici) {
							dodajSumnjivKupacRed(k);
						}
						blokirajKorisnika();
						odblokirajKorisnika();
					}
				}
			});
		});
	}
	
	function odblokirajKorisnika() {
		
		$('.odblokiraj').on('click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var korisnickoIme = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/odblokirajSumnjivogKupca',
				type: 'PUT',
				data: korisnickoIme,
	            contentType: 'text/plain',
				success: function(korisnici) {
					if (korisnici !== undefined) {
						$('#tabela tbody').empty();
						for (let k of korisnici) {
							dodajSumnjivKupacRed(k);
						}
						blokirajKorisnika();
						odblokirajKorisnika();
					}
				}
			});
		});
	}
});
