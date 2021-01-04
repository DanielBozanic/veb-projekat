$(document).ready(function() {

	$.get({
            url: 'rest/loginServis/trenutniKorisnik',
            dataType: 'json',
           	success: function(korisnik) {
	            	if (korisnik !== null) {
	            		popuniFormu(korisnik);
	            	}
	            }
	     });
	     
	     
	 function popuniFormu(trenutniKorisnik) {
	 	$("input[name=korisnickoIme]").val(trenutniKorisnik.korisnickoIme);
		$("input[name=lozinka]").val(trenutniKorisnik.lozinka);
		$("input[name=ime]").val(trenutniKorisnik.ime);
		$("input[name=prezime]").val(trenutniKorisnik.prezime);
		$("input[name=pol]").val(trenutniKorisnik.pol);
		$("input[name=datumRodjenja]").val(trenutniKorisnik.datumRodjenja);
	 }
	 
	 
	$('#btnIzmeni').on('click', function() {
		
	});
	
});