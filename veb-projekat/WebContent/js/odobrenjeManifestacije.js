$(document).ready(function(){

    $.get({
		url: 'rest/manifestacije/getManifestacije',
		success: function(manifestacije) {
			for (let m of manifestacije) {
				 addManifestacijaTr(m);
			}
			promenaStatusa();
		}
	
	});
	
	function addManifestacijaTr(manifestacija) {
		let tr = $('<tr></tr>');
		let naziv = $('<td>' + manifestacija.naziv + '</td>');
		let tipManifestacije = $('<td>' + manifestacija.tipManifestacije + '</td>');
		let brojMesta = $('<td>' + manifestacija.brojMesta + '</td>');
		let datumIVremeOdrzavanja = $('<td>' + manifestacija.datumIVremeOdrzavanja + '</td>');
		let cenaRegularKarte = $('<td>' + manifestacija.cenaRegularKarte + '</td>');
		let status;
		if (manifestacija.aktivan)
			status = $('<td>Odobrena</td>');
		else
			status = $('<td>Nije odobrena</td>');
        let link = $('<td><a class="odobreno" href="#">Odobri manifestaciju</a></td>');
		tr.append(naziv).append(tipManifestacije).append(brojMesta).append(datumIVremeOdrzavanja).
			append(cenaRegularKarte).append(status).append(link);
		$('#manifestacije tbody').append(tr);
	};
	
	function promenaStatusa(){
		
		$(".odobreno").on("click", function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var naziv = $(row).find("td:first").text();
			
			$.post({
				url: 'rest/manifestacije/odobriManifestaciju',
				data: naziv,
	            contentType: 'text/plain',
				success: function(odobreno) {
					if (odobreno === "true")
						$(tdHref).prev().text("Odobrena");
				}
			});
		});
	}
});
