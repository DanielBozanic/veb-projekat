$(document).ready(function(){

    $.get({
		url: 'rest/korisnici/getKorisnici',
		success: function(korisnici) {
			for (let k of korisnici) {
				 dodajKorisnikRed(k);
			}
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
		tr.append(korisnickoIme).append(ime).append(prezime).append(datumRodjenja).
			append(pol).append(uloga).append(brojSakupljenihBodova);
		$('#tabela tbody').append(tr);
	};
	
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
  	 
  	$('#pretraga').on('change', function(){
  		let pretraga = $('#pretraga').val();
  		if (pretraga === '') {
  			$('#tabela tbody tr').each(function() {
  				$(this).show();
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
				
		        if (opcija === vrednost) {
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
});
