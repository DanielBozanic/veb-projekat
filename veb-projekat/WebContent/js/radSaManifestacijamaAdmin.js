$(document).ready(function(){

    $.get({
		url: 'rest/manifestacije/getManifestacije',
		success: function(manifestacije) {
			ucitajManifestacije(manifestacije);
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
		let obrisana;
		let linkOdobrena;
		let linkObrisana;
		if (manifestacija.aktivan) {
			odobrena = $('<td>Da</td>');
			linkOdobrena = $('<td></td>');
		} else {
			if (Date.now() > Date.parse(manifestacija.datumIVremeOdrzavanja)) {
				linkOdobrena = $('<td></td>');
			} else {
				linkOdobrena = $('<td><a class="odobri" href="#">Odobri manifestaciju</a></td>');
			}
			odobrena = $('<td>Ne</td>');
		}
		if (manifestacija.obrisana) {
			obrisana = $('<td>Da</td>');
			linkObrisana = $('<td></td>');
			linkOdobrena = $('<td></td>');
		} else {
			obrisana = $('<td>Ne</td>');
			linkObrisana = $('<td><a class="obrisi" href="#">Obrisi manifestaciju</a></td>');
			linkOdobrena = $('<td><a class="odobri" href="#">Odobri manifestaciju</a></td>');
		}
		tr.append(naziv).append(tipManifestacije).append(brojMesta).append(datumIVremeOdrzavanja).
			append(cenaRegularKarte).append(odobrena).append(obrisana).append(linkOdobrena).append(linkObrisana);
		$('#manifestacije tbody').append(tr);
	};
	
	function ucitajManifestacije(manifestacije) {
		for (let m of manifestacije) {
			dodajManifestacijaRed(m);
		}

		$('#manifestacije').undelegate('.odobri', 'click').delegate('.odobri', 'click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var naziv = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/odobriManifestaciju',
				type: 'PUT',
				data: naziv,
	            contentType: 'text/plain',
				success: function(manifestacije) {
					if (manifestacije !== undefined) {
						$('#manifestacije tbody').empty();
						ucitajManifestacije(manifestacije);
					}
				}
			});
		});
		
		$('#manifestacije').undelegate('.obrisi', 'click').delegate('.obrisi', 'click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var nazivManifestacije = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/obrisiManifestaciju',
				type: 'DELETE',
				data: nazivManifestacije,
	            contentType: 'text/plain',
				success: function(manifestacije) {
					if (manifestacije !== undefined) {
						$('#manifestacije tbody').empty();
						ucitajManifestacije(manifestacije);
					}
				}
			});
		});
	}
});
