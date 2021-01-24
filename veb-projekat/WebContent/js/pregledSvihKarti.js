$(document).ready(function(){

    $.get({
		url: 'rest/karte/getKarte',
		success: function(karte) {
			ucitajKarte(karte);
		}
	});
	
	function dodajKartaRed(karta) {
		let tr = $('<tr></tr>');
		let identifikatorKarte = $('<td>' + karta.identifikatorKarte + '</td>');
		let nazivManifestacije = $('<td>' + karta.manifestacija.naziv + '</td>');
		let datumIVremeManifestacije = $('<td>' + karta.datumIVremeManifestacije + '</td>');
		let cena = $('<td>' + karta.cena + '</td>');
		let kupac = $('<td>' + karta.kupac + '</td>');
		let statusKarte = $('<td>' + karta.statusKarte + '</td>');
		let tipKarte = $('<td>' + karta.tipKarte + '</td>');
		let obrisana;
		let linkObrisana;
		if (karta.obrisana) {
			obrisana = $('<td>Da</td>');
			linkObrisana = $('<td></td>');
		} else {
			obrisana = $('<td>Ne</td>');
			linkObrisana = $('<td><a class="obrisi" href="#">Obrisi kartu</a></td>');
		}
		tr.append(identifikatorKarte).append(nazivManifestacije).append(datumIVremeManifestacije).
			append(cena).append(kupac).append(statusKarte).append(tipKarte).append(obrisana).append(linkObrisana);
		$('#tabelaSvihKarti tbody').append(tr);
	};
	
	function ucitajKarte(karte) {
		for (let k of karte) {
			dodajKartaRed(k);
		}

		$('#tabelaSvihKarti').undelegate('.obrisi', 'click').delegate('.obrisi', 'click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var idKarte = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/obrisiKartu',
				type: 'DELETE',
				data: idKarte,
	            contentType: 'text/plain',
				success: function(karte) {
					if (karte !== undefined) {
						$('#tabelaSvihKarti tbody').empty();
						ucitajKarte(karte);
					}
				}
			});
		});
	}
});