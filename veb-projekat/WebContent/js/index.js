$(document).ready(function() {

	$.get({
            url: 'rest/korisnici/trenutniKorisnik',
            dataType: 'json',
           	success: function(korisnik) {
	            	if (korisnik !== null) {
	            		let a = $('<a class="nav-link" href="glavna.html">Glavna stranica</a>');
						let li = $('<li class="nav-item"></li>');
						li.append(a);
						$('#menu').append(li);
	            	}
	            }
	     });
	
	$.get({
		url: 'rest/manifestacije/getAktivneManifestacije',
		success: function(aktivneManifestacije) {
			for (let m of aktivneManifestacije) {
				 dodajManifestacijaRed(m);
			}
			selektovanaManifestacija();
		}
	});
	
	function dodajManifestacijaRed(manifestacija) {
		let tr = $('<tr></tr>');
		let naziv = $('<td><a class="selektovana" href="#">' + manifestacija.naziv + '</a></td>');
		let datumIVremeOdrzavanja = $('<td>' + manifestacija.datumIVremeOdrzavanja + '</td>');
		tr.append(naziv).append(datumIVremeOdrzavanja);
		$('#manifestacije tbody').append(tr);
	};
	
	function selektovanaManifestacija(){
		
		$(".selektovana").on("click", function() {
			var self = this;
			var naziv = $(self).text();
			
			$.post({
				url: 'rest/manifestacije/setOdabranaManifestacija',
				data: naziv,
	            contentType: 'text/plain',
				success: function() {
					window.location.href = "detaljanPrikazManifestacije.html";
				}
			});
		});
	}
});