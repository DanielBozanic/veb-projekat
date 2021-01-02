$(document).ready(function(){

    $('#btnRegistruj').on("click", function() {
        let korisnickoIme = $('input[name="korisnickoIme"]').val();
		let lozinka = $('input[name="lozinka"]').val();
        let ime = $('input[name="ime"]').val();
        let prezime = $('input[name="prezime"]').val();
		let pol = $('select[name="pol"]').val();
        let datumRodjenja = $('input[name="datumRodjenja"]').val();

        let data = {
            "korisnickoIme": korisnickoIme,
			"lozinka": lozinka,
            "ime": ime,
            "prezime": prezime,
            "datumRodjenja": datumRodjenja,
            "pol": pol
            };
    
        $.post({
            url: 'rest/kupci/registrujKupca',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function(valid) {
            	if (valid) {
            		window.location.href = "login.html";
            	}
            	else {
            		alert("Ovo korisnicko ime nije dostupno!");
            	}
            }
        });
    });
});