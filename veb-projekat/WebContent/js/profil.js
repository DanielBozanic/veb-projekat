$(document).ready(function() {

	var trenutniKorisnik;

	$.get({
        url: 'rest/korisnici/trenutniKorisnik',
        dataType: 'json',
       	success: function(korisnik) {
        	if (korisnik !== undefined) {
        		trenutniKorisnik = korisnik;
        		popuniFormu();
        	}
         }
	 });
	     
	     
	 function popuniFormu() {
	 	$("input[name=korisnickoIme]").val(trenutniKorisnik.korisnickoIme);
		$("input[name=lozinka]").val(trenutniKorisnik.lozinka);
		$("input[name=ime]").val(trenutniKorisnik.ime);
		$("input[name=prezime]").val(trenutniKorisnik.prezime);
		$("select[name=pol]").val(trenutniKorisnik.pol);
		$("input[name=datumRodjenja]").val(trenutniKorisnik.datumRodjenja);
	 }
	 
	 
	$('#btnIzmeni').on('click', function() {
	
		let ime = $("input[name=ime]").val();
		let prezime = $("input[name=prezime]").val();
		let datumRodjenja = $("input[name=datumRodjenja]").val();
		let pol = $("select[name=pol]").val();
		
		
		let data = {
			"korisnickoIme": trenutniKorisnik.korisnickoIme,
			"lozinka": trenutniKorisnik.lozinka,
			"ime": ime,
			"prezime": prezime,
			"datumRodjenja": datumRodjenja,
			"pol": pol
		}
		
		$.ajax({
            url: 'rest/korisnici/izmenaPodatakaTrenutnogKorisnika',
            type: 'PUT',
            data: JSON.stringify(data),
	        contentType: 'application/json',
           	success: function(korisnik) {
	            	if (korisnik !== undefined) {
	            		trenutniKorisnik = korisnik;
	            		popuniFormu();
	            		alert("Uspesna izmena.");
	            	}
	            }
	     });
	});
});