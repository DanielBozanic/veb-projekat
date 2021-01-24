$(document).ready(function(){

    $.get({
		url: 'rest/administratori/getSumnjiviKupci',
		success: function(sumnjiviKupci) {
			ucitajSumnjiveKupce(sumnjiviKupci);
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
	
	function ucitajSumnjiveKupce(sumnjiviKupci) {
	
		for (let k of sumnjiviKupci) {
			dodajSumnjivKupacRed(k);
		}
		
		$('#tabela').undelegate('.blokiraj', 'click').delegate('.blokiraj', 'click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var korisnickoIme = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/blokirajSumnjivogKupca',
				type: 'PUT',
				data: korisnickoIme,
	            contentType: 'text/plain',
				success: function(sumnjiviKupci) {
					if (sumnjiviKupci !== undefined) {
						$('#tabela tbody').empty();
						ucitajSumnjiveKupce(sumnjiviKupci);
					}
				}
			});
		});
		
		$('#tabela').undelegate('.odblokiraj', 'click').delegate('.odblokiraj', 'click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var korisnickoIme = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/odblokirajSumnjivogKupca',
				type: 'PUT',
				data: korisnickoIme,
	            contentType: 'text/plain',
				success: function(sumnjiviKupci) {
					if (sumnjiviKupci !== undefined) {
						$('#tabela tbody').empty();
						ucitajSumnjiveKupce(sumnjiviKupci);
					}
				}
			});
		});
	}
});
