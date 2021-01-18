$(document).ready(function() {
	
	$.get({
		url: 'rest/manifestacije/getOdabranaManifestacija',
		success: function(manifestacija) {
			if (manifestacija !== null){
				popuniFormu(manifestacija);
			}
		}
	});
	
	function popuniFormu(manifestacija) {
		$("input[name=naziv]").val(manifestacija.naziv);
		$("input[name=tipManifestacije]").val(manifestacija.tipManifestacije);
		$("input[name=brojMesta]").val(manifestacija.brojMesta);
		$("input[name=datumIVremeOdrzavanja]").val(manifestacija.datumIVremeOdrzavanja);
		$("input[name=cenaRegularKarte]").val(manifestacija.cenaRegularKarte);
		$("input[name=geografskaDuzina]").val(manifestacija.lokacija.geografskaDuzina);
		$("input[name=geografskaSirina]").val(manifestacija.lokacija.geografskaSirina);
		$("input[name=ulicaIBroj]").val(manifestacija.lokacija.ulicaIBroj);
		$("input[name=mesto]").val(manifestacija.lokacija.mesto);
		$("input[name=postanskiBroj]").val(manifestacija.lokacija.postanskiBroj);
		$("img[name=posterManifestacije]").attr('src', manifestacija.posterManifestacije);
	}
})