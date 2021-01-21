$(document).ready(function(){

    $.get({
		url: 'rest/kupci/getKarteValidneZaOdustanak',
		success: function(karte) {
			for (let k of karte) {
				 dodajKartaRed(k);
			}
			odustanakRezervacije();
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
		let link = $('<td><a class="odustanak" href="#">Odustanak</a></td>');
		tr.append(identifikatorKarte).append(nazivManifestacije).append(datumIVremeManifestacije).
			append(cena).append(kupac).append(statusKarte).append(tipKarte).append(link);
		$('#tabelaKarti tbody').append(tr);
	};
		
	function odustanakRezervacije() {
		$(".odustanak").on("click", function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var id = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/kupci/odustanakRezervacije',
				type: 'PUT',
				data: id,
	            contentType: 'text/plain',
				success: function(karte) {
					if (karte !== undefined) {
						$('#tabelaKarti tbody').empty();
						for (let k of karte) {
				 			dodajKartaRed(k);
						}
						alert("Uspesan odustanak rezervacije.");
						odustanakRezervacije();
					} else {
						alert("Neuspesan odustanak rezervacije.");
					}
				}
			});
		});
	}
});
