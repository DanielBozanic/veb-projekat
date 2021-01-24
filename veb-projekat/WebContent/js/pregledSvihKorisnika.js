$(document).ready(function(){

    $.get({
		url: 'rest/korisnici/getKorisnici',
		success: function(korisnici) {
			ucitajTabelu(korisnici);
			initEvents();
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
		let blokiraj;
		if (korisnik.uloga === 'kupac' || korisnik.uloga === 'prodavac') {
			if (korisnik.obrisan) {
				status = $('<td>Obrisan</td>');
				obrisi = $('<td></td>');
				blokiraj = $('<td></td>');
			} else if (korisnik.blokiran) {
				status = $('<td>Blokiran</td>');
				obrisi = $('<td><a class="obrisi" href="#">Obrisi korisnika</a></td>');
				blokiraj = $('<td><a class="odblokiraj" href="#">Odblokiraj korisnika</a></td>');
			} else {
				status = $('<td>Aktivan</td>');
				obrisi = $('<td><a class="obrisi" href="#">Obrisi korisnika</a></td>');
				blokiraj = $('<td><a class="blokiraj" href="#">Blokiraj korisnika</a></td>');
			}
		} else {
			status = $('<td>Aktivan</td>');
			obrisi = $('<td></td>');
			blokiraj = $('<td></td>');
		}
		tr.append(korisnickoIme).append(ime).append(prezime).append(datumRodjenja).
			append(pol).append(uloga).append(brojSakupljenihBodova).append(status).append(obrisi).append(blokiraj);
		$('#tabela tbody').append(tr);
	};
	
	function initEvents() {
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
		    var korisnickoIme = $('input[name=korisnickoIme]').val();
		    var ime = $('input[name=ime]').val();
		    var prezime = $('input[name=prezime]').val();
		
		    $('#tabela tbody tr').each(function() { 
		        var row = $(this);
				
				var korisnickoImeTd;
				var imeTd
				var prezimeTd;
				

				korisnickoImeTd = $(row).find('td:first').text();
				imeTd = $(row).find('td:nth-child(2)').text();
				prezimeTd = $(row).find('td:nth-child(3)').text();
		        if ((korisnickoIme === "" || korisnickoImeTd.toLowerCase().indexOf(korisnickoIme.toLowerCase())) >= 0 && 
		        	(ime === "" || imeTd.toLowerCase().indexOf(ime.toLowerCase()) >= 0) &&
		        	(prezime === "" || prezimeTd.toLowerCase().indexOf(prezime.toLowerCase()) >= 0)) {
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
					ucitajTabelu(korisnici);
				}
			});
		});
		
		$('#btnClear').on('click', function() {
			$('input[name=korisnickoIme]').val('');
			$('input[name=ime]').val('');
			$('input[name=prezime]').val('');
			$('#tabela tbody tr').show();
		});
	}
	
	function ucitajTabelu(korisnici) {
		for (let k of korisnici) {
			dodajKorisnikRed(k);
		}
		
		$('#tabela').undelegate('.obrisi', 'click').delegate('.obrisi', 'click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var korisnickoIme = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/obrisiKorisnika',
				type: 'DELETE',
				data: korisnickoIme,
	            contentType: 'text/plain',
				success: function(korisnici) {
					if (korisnici !== undefined) {
						$('#tabela tbody').empty();
						ucitajTabelu(korisnici);
					}
				}
			});
		});
		
		$('#tabela').undelegate('.blokiraj', 'click').delegate('.blokiraj', 'click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var korisnickoIme = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/blokirajKorisnika',
				type: 'PUT',
				data: korisnickoIme,
	            contentType: 'text/plain',
				success: function(korisnici) {
					if (korisnici !== undefined) {
						$('#tabela tbody').empty();
						ucitajTabelu(korisnici);
					}
				}
			});
		});
		
		$('#tabela').undelegate('.odblokiraj', 'click').delegate('.odblokiraj', 'click', function() {
			var self = this;
			var tdHref = $(self).parent();
			var row = $(tdHref).parent();
			var korisnickoIme = $(row).find("td:first").text();
			
			$.ajax({
				url: 'rest/administratori/odblokirajKorisnika',
				type: 'PUT',
				data: korisnickoIme,
	            contentType: 'text/plain',
				success: function(korisnici) {
					if (korisnici !== undefined) {
						$('#tabela tbody').empty();
						ucitajTabelu(korisnici);
					}
				}
			});
		});
	}
});
