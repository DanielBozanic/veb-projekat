$(document).ready(function(){

    $.get({
		url: 'rest/karte/getRezervisaneKarte',
		success: function(rezervisaneKarte) {
			for (let k of rezervisaneKarte) {
				 dodajRezervisanaKartaRed(k);
			}
		}
	});
	
	function dodajRezervisanaKartaRed(rezervisanaKarta) {
		let tr = $('<tr></tr>');
		let identifikatorKarte = $('<td>' + rezervisanaKarta.identifikatorKarte + '</td>');
		let nazivManifestacije = $('<td>' + rezervisanaKarta.manifestacija.naziv + '</td>');
		let datumIVremeManifestacije = $('<td>' + rezervisanaKarta.datumIVremeManifestacije + '</td>');
		let cena = $('<td>' + rezervisanaKarta.cena + '</td>');
		let kupac = $('<td>' + rezervisanaKarta.kupac + '</td>');
		let statusKarte = $('<td>' + rezervisanaKarta.statusKarte + '</td>');
		let tipKarte = $('<td>' + rezervisanaKarta.tipKarte + '</td>');
		tr.append(identifikatorKarte).append(nazivManifestacije).append(datumIVremeManifestacije).
			append(cena).append(kupac).append(statusKarte).append(tipKarte);
		$('#tabelaRezervisanihKarti tbody').append(tr);
	};
});