$(document).ready(function(){

    $.get({
		url: 'rest/komentari/getKomentari',
		success: function(komentari) {
			for (let k of komentari) {
				 dodajKomentarRed(k);
			}
			obrisiKomentar();
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
		let obrisan;
		let link;
		if (komentar.odobren) {
			odobren = $('<td>Odobren</td>');
		} else {
			odobren = $('<td>Nije odobren</td>');
		}
		if (komentar.obrisan) {
			obrisan = $('<td>Da</td>');
			link = $('<td></td>');
			
		} else {
			obrisan = $('<td>Ne</td>');
			link = $('<td><a class="obrisi" href="#">Obrisi komentar</a></td>');
		}
		tr.append(idKomentara).append(kupacKarte).append(manifestacijaNaziv).append(tekstKomentara).
			append(ocena).append(odobren).append(obrisan).append(link);
		$('#komentari tbody').append(tr);
	};
	
	function obrisiKomentar(){
		
		$(".obrisi").on("click", function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var idKomentara = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/komentari/obrisiKomentar',
				type: 'DELETE',
				data: idKomentara,
	            contentType: 'text/plain',
				success: function(komentari) {
					$('#komentari tbody').empty();
					for (let k of komentari) {
						dodajKomentarRed(k);
					}
					obrisiKomentar();
				}
			});
		});
	}
});