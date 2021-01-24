$(document).ready(function(){

    $.get({
		url: 'rest/komentari/getKomentari',
		success: function(komentari) {
			ucitajKomentare(komentari);
		}
	});
	
	function dodajKomentarRed(komentar) {
		let tr = $('<tr></tr>');
		let idKomentara = $('<td>' + komentar.idKomentara + '</td>');
		let kupacKarte = $('<td>' + komentar.kupacKarte + '</td>');
		let manifestacijaNaziv = $('<td>' + komentar.manifestacija.naziv + '</td>');
		let tekstKomentara = $('<td>' + komentar.tekstKomentara + '</td>');
		let ocena = $('<td>' + komentar.ocena + '</td>');
		let odobren;
		let link;
		if (komentar.odobren) {
			odobren = $('<td>Odobren</td>');
			link = $('<td></td>');
		} else {
			odobren = $('<td>Nije odobren</td>');
			link = $('<td><a class="odobri" href="#">Odobri komentar</a></td>');
		}
		tr.append(idKomentara).append(kupacKarte).append(manifestacijaNaziv).append(tekstKomentara).
			append(ocena).append(odobren).append(link);
		$('#komentari tbody').append(tr);
	};
	
	function ucitajKomentare(komentari) {
		for (let k of komentari) {
			dodajKomentarRed(k);
		}
		
		$('#komentari').undelegate('.odobri', 'click').delegate('.odobri', 'click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var idKomentara = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/komentari/odobriKomentar',
				type: 'PUT',
				data: idKomentara,
	            contentType: 'text/plain',
				success: function(komentari) {
					$('#komentari tbody').empty();
					ucitajKomentare(komentari);
				}
			});
		});
	}
});