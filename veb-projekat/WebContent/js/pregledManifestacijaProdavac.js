$(document).ready(function(){

    $.get({
		url: 'rest/manifestacije/getManfestacijeZaProdavca',
		success: function(manifestacije) {
			for (let m of manifestacije) {
				 dodajManifestacijaRed(m);
			}
		}
	
	});
	
	function dodajManifestacijaRed(manifestacija) {
		let tr = $('<tr></tr>');
		let naziv = $('<td>' + manifestacija.naziv + '</td>');
		let tipManifestacije = $('<td>' + manifestacija.tipManifestacije + '</td>');
		let brojMesta = $('<td>' + manifestacija.brojMesta + '</td>');
		let cenaRegularKarte = $('<td>' + manifestacija.cenaRegularKarte + '</td>');
		let status;
		if (manifestacija.aktivan)
			status = $('<td>Odobrena</td>');
		else
			status = $('<td>Nije odobrena</td>');
		tr.append(naziv).append(tipManifestacije).append(brojMesta).append(cenaRegularKarte).
			append(status)
		$('#tabelaManifestacija tbody').append(tr);
	};
});