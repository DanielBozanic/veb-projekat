$(document).ready(function(){

    $.get({
		url: 'rest/loginServis/getKorisnici',
		success: function(korisnici) {
			for (let k of korisnici) {
				 dodajKorisnikRed(k);
			}
		}
	
	});
	
	function dodajKorisnikRed(korisnik) {
		let tr = $('<tr></tr>');
		let korisnickoIme = $('<td>' + korisnik.korisnickoIme + '</td>');
		let ime = $('<td>' + korisnik.ime + '</td>');
		let prezime = $('<td>' + korisnik.prezime + '</td>');
		let datumRodjenja = $('<td>' + korisnik.datumRodjenja + '</td>');
		let pol = $('<td>' + korisnik.pol + '</td>');
		let uloga = $('<td>' + korisnik.uloga + '</td>');
		tr.append(korisnickoIme).append(ime).append(prezime).append(datumRodjenja).
			append(pol).append(uloga);
		$('#tabela tbody').append(tr);
	};
});
