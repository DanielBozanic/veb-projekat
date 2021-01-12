$(document).ready(function(){

    $.get({
		url: 'rest/karte/getKarte',
		success: function(karte) {
			for (let k of karte) {
				 dodajKartaRed(k);
			}
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
		tr.append(identifikatorKarte).append(nazivManifestacije).append(datumIVremeManifestacije).
			append(cena).append(kupac).append(statusKarte).append(tipKarte);
		$('#tabelaSvihKarti tbody').append(tr);
	};
});