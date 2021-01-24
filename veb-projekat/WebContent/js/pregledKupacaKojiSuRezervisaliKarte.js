$(document).ready(function(){

    $.get({
		url: 'rest/karte/getKupciKojiSuRezervisaliKarte',
		success: function(kupci) {
			for (let k of kupci) {
				 dodajKorisnikRed(k);
			}
		}
	});
	
	function dodajKorisnikRed(kupac) {
		let tr = $('<tr></tr>');
		let korisnickoIme = $('<td>' + kupac.korisnickoIme + '</td>');
		let ime = $('<td>' + kupac.ime + '</td>');
		let prezime = $('<td>' + kupac.prezime + '</td>');
		let datumRodjenja = $('<td>' + kupac.datumRodjenja + '</td>');
		let pol = $('<td>' + kupac.pol + '</td>');
		let uloga = $('<td>' + kupac.uloga + '</td>');
		tr.append(korisnickoIme).append(ime).append(prezime).append(datumRodjenja).
			append(pol).append(uloga);
		$('#tabela tbody').append(tr);
	};
});
