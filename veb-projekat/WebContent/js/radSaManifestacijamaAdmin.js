$(document).ready(function(){

    $.get({
		url: 'rest/manifestacije/getManifestacije',
		success: function(manifestacije) {
			for (let m of manifestacije) {
				 dodajManifestacijaRed(m);
			}
			promenaStatusa();
		}
	
	});
	
	function dodajManifestacijaRed(manifestacija) {
		let tr = $('<tr></tr>');
		let naziv = $('<td>' + manifestacija.naziv + '</td>');
		let tipManifestacije = $('<td>' + manifestacija.tipManifestacije + '</td>');
		let brojMesta = $('<td>' + manifestacija.brojMesta + '</td>');
		let datumIVremeOdrzavanja = $('<td>' + manifestacija.datumIVremeOdrzavanja + '</td>');
		let cenaRegularKarte = $('<td>' + manifestacija.cenaRegularKarte + '</td>');
		let odobrena;
		let link;
		if (manifestacija.aktivan) {
			odobrena = $('<td>Da</td>');
			link = $('<td></td>');
		} else {
			if (Date.now() > Date.parse(manifestacija.datumIVremeOdrzavanja)) {
				link = $('<td></td>');
			} else {
				link = $('<td><a class="odobri" href="#">Odobri manifestaciju</a></td>');
			}
			odobrena = $('<td>Ne</td>');
		}
		tr.append(naziv).append(tipManifestacije).append(brojMesta).append(datumIVremeOdrzavanja).
			append(cenaRegularKarte).append(odobrena).append(link);
		$('#manifestacije tbody').append(tr);
	};
	
	function promenaStatusa(){
		
		$(".odobri").on("click", function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var naziv = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/odobriManifestaciju',
				type: 'PUT',
				data: naziv,
	            contentType: 'text/plain',
				success: function(odobreno) {
					if (odobreno === "true")
						$(tdHref).prev().text("Da");
				}
			});
		});
	}
});
