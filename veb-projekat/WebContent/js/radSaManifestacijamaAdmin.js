$(document).ready(function(){

    $.get({
		url: 'rest/manifestacije/getManifestacijeAdmin',
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
		let status;
		if (manifestacija.aktivan)
			status = $('<td>Aktivna</td>');
		else
			status = $('<td>Nije aktivna</td>');
        let link = $('<td><a class="aktivan" href="#">Aktiviraj manifestaciju</a></td>');
		tr.append(naziv).append(tipManifestacije).append(brojMesta).append(datumIVremeOdrzavanja).
			append(cenaRegularKarte).append(status).append(link);
		$('#manifestacije tbody').append(tr);
	};
	
	function promenaStatusa(){
		
		$(".aktivan").on("click", function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var naziv = $(row).find("td:first").text();
			
			$.post({
				url: 'rest/administratori/odobriManifestaciju',
				data: naziv,
	            contentType: 'text/plain',
				success: function(odobreno) {
					if (odobreno === "true")
						$(tdHref).prev().text("Aktivna");
				}
			});
		});
	}
});
