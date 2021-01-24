$(document).ready(function() {

	$.get({
        url: 'rest/korisnici/trenutniKorisnik',
       	success: function(korisnik) {
        	if (korisnik !== undefined) {
        		let a = $('<a class="nav-link" href="glavna.html">Glavna stranica</a>');
				let li = $('<li class="nav-item"></li>');
				li.append(a);
				$('#menu').append(li);
        	}
         }
	 });
	
	$.get({
		url: 'rest/manifestacije/getAktivneManifestacije',
		success: function(aktivneManifestacije) {
			ucitajManifestacije(aktivneManifestacije);
			initEvents();
		}
	});
	
	function dodajManifestacijaRed(manifestacija) {
		let tr = $('<tr></tr>');
		let naziv = $('<td><a class="selektovana" href="#">' + manifestacija.naziv + '</a></td>');
		let tipManifestacije = $('<td>' + manifestacija.tipManifestacije + '</td>');
		let brojMesta = $('<td>' + manifestacija.brojMesta + '</td>');
		let datumIVremeOdrzavanja = $('<td>' + manifestacija.datumIVremeOdrzavanja + '</td>');;
		let cenaRegularKarte = $('<td>' + manifestacija.cenaRegularKarte + '</td>');
		let mesto = $('<td>' + manifestacija.lokacija.mesto + '</td>');
		tr.append(naziv).append(tipManifestacije).append(brojMesta).append(datumIVremeOdrzavanja)
			.append(cenaRegularKarte).append(mesto);
		$('#manifestacije tbody').append(tr);
	};
	
	function ucitajManifestacije(manifestacije) {
		for (let m of manifestacije) {
			dodajManifestacijaRed(m);
		}
		
		$('#manifestacije').undelegate('.selektovana', 'click').delegate('.selektovana', 'click', function() {
			var self = this;
			var naziv = $(self).text();
			
			$.ajax({
				url: 'rest/manifestacije/setOdabranaManifestacija',
				type: 'PUT',
				data: naziv,
	            contentType: 'text/plain',
				success: function() {
					window.location.href = "detaljanPrikazManifestacije.html";
				}
			});
		});
	}
	
	function initEvents() {
		$('input[name=filter]').on('keyup', function() {
		 	var filter = $('input[name=filter]').val();
		 	var opcija = $('select[name=kriterijumFiltriranja]').val();
		 	if (opcija === 'tipManifestacije') {
		 		$('#manifestacije tbody tr td:nth-child(2)').filter(function() {
		 			var red = $(this).parent();
	      			$(red).toggle($(this).text().toLowerCase().indexOf(filter) > -1)
	    		});
		 	} else {
		 		$('#manifestacije tbody tr td:nth-child(3)').filter(function() {
		 			var red = $(this).parent();
	      			$(red).toggle($(this).text().toLowerCase().indexOf(filter) > -1)
	    		});
		 	}
	  	 });
	  	 
	  	$('#btnPretraga').on('click', function() {
		    var nazivManifestacije = $('input[name=naziv]').val();
		    var datumOd = Date.parse($('input[name=datumOd]').val());
		    var datumDo = Date.parse($('input[name=datumDo]').val());
		    var gradDrzava = $('input[name=gradDrzava]').val();
		    var cenaOd = parseFloat($('input[name=cenaOd]').val());
		    var cenaDo = parseFloat($('input[name=cenaDo]').val());
		
		    $('#manifestacije tbody tr').each(function() { 
		        var row = $(this);
				
				var nazivTd;
				var datumTd
				var gradDrzavaTd;
				var cenaTd

				nazivTd = $(row).find('td:first').text();
				datumTd = Date.parse($(row).find('td:nth-child(4)').text());
				cenaTd = parseFloat($(row).find('td:nth-child(5)').text());
				gradDrzavaTd = $(row).find('td:nth-child(6)').text();
		        if ((nazivManifestacije === "" || nazivTd.toLowerCase().indexOf(nazivManifestacije.toLowerCase())) >= 0 && 
		        	(isNaN(datumOd) || datumTd >= datumOd) &&
		        	(isNaN(datumDo) || datumTd <= datumDo) &&
		        	(gradDrzava === "" || gradDrzavaTd.toLowerCase().indexOf(gradDrzava.toLowerCase()) >= 0) &&
		        	(isNaN(cenaOd) || cenaTd >= cenaOd) &&
		        	(isNaN(cenaDo) || cenaTd <= cenaDo)) {
		            $(row).show();
		        }
		        else {
		            $(row).hide();
		        }
		    });
		});
			
		$('#btnSortiraj').on('click', function() {
			let kriterijumSortiranja = $('select[name=kriterijumSortiranja]').val();
			let kriterijumSortiranja2 = $('select[name=kriterijumSortiranja2]').val();
			$('#manifestacije tbody').empty();
			
			$.get({
				url: 'rest/manifestacije/getSortiraneManifestacije?kriterijumSortiranja=' + kriterijumSortiranja + 
					'&kriterijumSortiranja2=' + kriterijumSortiranja2,
				success: function(manifestacije) {
					ucitajManifestacije(manifestacije);
				}
			});
		});
		
		$('#btnClear').on('click', function() {
			$('input[name=naziv]').val('');
		    $('input[name=datumOd]').val('');
		    $('input[name=datumDo]').val('');
		    $('input[name=gradDrzava]').val('');
		    $('input[name=cenaOd]').val('');
		    $('input[name=cenaDo]').val('');
			$('#manifestacije tbody tr').show();
		});
	}
});