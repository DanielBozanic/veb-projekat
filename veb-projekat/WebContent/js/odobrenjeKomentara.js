$(document).ready(function(){

    $.get({
		url: 'rest/komentari/getKomentari',
		success: function(komentari) {
			for (let k of komentari) {
				 dodajKomentarRed(k);
			}
			promenaStatusa();
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
		if (komentar.odobren)
			odobren = $('<td>Odobren</td>');
		else
			odobren = $('<td>Nije odobren</td>');
        let link = $('<td><a class="odobri" href="#">Odobri komentar</a></td>');
		tr.append(idKomentara).append(kupacKarte).append(manifestacijaNaziv).append(tekstKomentara).
			append(ocena).append(odobren).append(link);
		$('#komentari tbody').append(tr);
	};
	
	function promenaStatusa(){
		
		$(".odobri").on("click", function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var idKomentara = $(row).find("td:first").text();
			
			$.post({
				url: 'rest/komentari/odobriKomentar',
				data: idKomentara,
	            contentType: 'text/plain',
				success: function(odobreno) {
					if (odobreno === "true")
						$(tdHref).prev().text("Odobren");
				}
			});
		});
	}
});