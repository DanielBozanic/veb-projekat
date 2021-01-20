$(document).ready(function(){

    $.get({
		url: 'rest/korisnici/getKorisnici',
		success: function(korisnici) {
			for (let k of korisnici) {
				 dodajKorisnikRed(k);
			}
			obrisiKorisnika();
		}
	
	});
	
	function dodajKorisnikRed(korisnik) {
		let tr = $('<tr></tr>');
		let korisnickoIme = $('<td>' + korisnik.korisnickoIme + '</td>');
		let ime = $('<td>' + korisnik.ime + '</td>');
		let prezime = $('<td>' + korisnik.prezime + '</td>');
		let datumRodjenja = $('<td>' + korisnik.datumRodjenja + '</td>');
		let pol = $('<td>' + korisnik.pol + '</td>');
		let uloga = $('<td>' + korisnik.uloga + '</td>');
		let brojSakupljenihBodova = $('<td>' + korisnik.brojSakupljenihBodova + '</td>');
		let status;
		let obrisi;
		if (korisnik.uloga === 'kupac' || korisnik.uloga === 'prodavac') {
			if (korisnik.obrisan) {
				status = $('<td>Obrisan</td>');
				obrisi = $('<td></td>');
			} else {
				status = $('<td>Aktivan</td>');
				obrisi = $('<td><a class="obrisi" href="#">Obrisi korisnika</a></td>');
			}
		} else {
			status = $('<td>Aktivan</td>');
			obrisi = $('<td></td>');
		}
		tr.append(korisnickoIme).append(ime).append(prezime).append(datumRodjenja).
			append(pol).append(uloga).append(brojSakupljenihBodova).append(status).append(obrisi);
		$('#tabela tbody').append(tr);
	};
	
	function obrisiKorisnika() {
		
		$('.obrisi').on('click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var korisnickoIme = $(row).find("td:first").text();
			
			$.post({
				url: 'rest/administratori/obrisiKorisnika',
				data: korisnickoIme,
	            contentType: 'text/plain',
				success: function(korisnici) {
					if (korisnici !== undefined) {
						$('#tabela tbody').empty();
						for (let k of korisnici) {
							dodajKorisnikRed(k);
						}
						obrisiKorisnika();
						alert("Uspesno brisanje korisnika.");
					} else {
						alert("Neuspesno brisanje korisnika.");
					}
				}
			});
		});
	}
	
	 $('#filter').on('keyup', function() {
	 	var filter = $('#filter').val();
	 	var opcija = $('#kriterijumFilter').val();
	 	if (opcija === 'uloga') {
	 		$('#tabela tbody tr td:nth-child(6)').filter(function() {
	 			var red = $(this).parent();
      			$(red).toggle($(this).text().toLowerCase().indexOf(filter) > -1)
    		});
	 	}
  	 });

  	$('#btnPretraga').on('click', function() {
		    var vrednost = $('#pretraga').val();
		
		    $('#tabela tbody tr').each(function() { 
		        var row = $(this);
				
				var kriterijumPretrage = $('#kriterijumPretrage').val();
				
				var opcija;
				
				if (kriterijumPretrage === 'korisnickoIme'){
					opcija = $(row).find('td:first').text();
				} else if (kriterijumPretrage === 'ime'){
					opcija = $(row).find('td:nth-child(2)').text();
				} else if (kriterijumPretrage === 'prezime'){
					opcija = $(row).find('td:nth-child(3)').text();
				}
				
		        if (opcija.toLowerCase().indexOf(vrednost.toLowerCase()) >= 0) {
		            $(row).show();
		        }
		        else {
		            $(row).hide();
		        }
		    });
		});
	
	
	$('#btnSortiraj').on('click', function() {
		let kriterijumSortiranja = $('#kriterijumSortiranja').val();
		let kriterijumSortiranja2 = $('#kriterijumSortiranja2').val();
		$('#tabela tbody').empty();
		
		$.get({
		url: 'rest/korisnici/getSortiraneKorisnike?kriterijumSortiranja=' + kriterijumSortiranja + 
			'&kriterijumSortiranja2=' + kriterijumSortiranja2,
		success: function(korisnici) {
			for (let k of korisnici) {
				 dodajKorisnikRed(k);
				}
			}
		});
		
	});
	
	$('#btnClear').on('click', function() {
		$('#pretraga').val('');
		$('#tabela tbody tr').show();
	});
});
