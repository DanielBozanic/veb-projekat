$(document).ready(function(){

    $.get({
		url: 'rest/karte/getKarteKupca',
		success: function(karte) {
			ucitajKarte(karte);
			initEvents();
		}
	});
	
	function dodajKartaRed(karta) {
		let tr = $('<tr></tr>');
		let identifikatorKarte = $('<td>' + karta.identifikatorKarte + '</td>');
		let nazivManifestacije = $('<td>' + karta.manifestacija.naziv + '</td>');
		let datumIVremeManifestacije = $('<td>' + karta.datumIVremeManifestacije + '</td>');
		let cena = $('<td>' + karta.cena + '</td>');
		let kupac = $('<td>' + karta.kupac + '</td>');
		let statusKarte = $('<td>' + karta.statusKarte + '</td>');
		let tipKarte = $('<td>' + karta.tipKarte + '</td>');
		tr.append(identifikatorKarte).append(nazivManifestacije).append(datumIVremeManifestacije).
			append(cena).append(kupac).append(statusKarte).append(tipKarte);
		$('#tabelaKarti tbody').append(tr);
	};
	
	function ucitajKarte(karte) {
		for (let k of karte) {
			dodajKartaRed(k);
		}
	}
	
	function initEvents() {
		$('input[name=filter]').on('keyup', function() {
		 	var filter = $('input[name=filter]').val();
		 	var opcija = $('select[name=kriterijumFiltriranja]').val();
		 	if (opcija === 'tipKarte') {
		 		$('#tabelaKarti tbody tr td:nth-child(7)').filter(function() {
		 			var red = $(this).parent();
	      			$(red).toggle($(this).text().toLowerCase().indexOf(filter) > -1)
	    		});
		 	} else {
		 		$('#tabelaKarti tbody tr td:nth-child(6)').filter(function() {
		 			var red = $(this).parent();
	      			$(red).toggle($(this).text().toLowerCase().indexOf(filter) > -1)
	    		});
		 	}
	  	 });
	  	 
	  	$('#btnPretraga').on('click', function() {
		    var nazivManifestacije = $('input[name=naziv]').val();
		    var datumOd = Date.parse($('input[name=datumOd]').val());
		    var datumDo = Date.parse($('input[name=datumDo]').val());
		    var cenaOd = parseFloat($('input[name=cenaOd]').val());
		    var cenaDo = parseFloat($('input[name=cenaDo]').val());
		
		    $('#tabelaKarti tbody tr').each(function() { 
		        var row = $(this);
				
				var nazivTd;
				var datumTd
				var cenaTd

				nazivTd = $(row).find('td:nth-child(2)').text();
				datumTd = Date.parse($(row).find('td:nth-child(3)').text());
				cenaTd = parseFloat($(row).find('td:nth-child(4)').text());
		        if ((nazivManifestacije === "" || nazivTd.toLowerCase().indexOf(nazivManifestacije.toLowerCase())) >= 0 && 
		        	(isNaN(datumOd) || datumTd >= datumOd) &&
		        	(isNaN(datumDo) || datumTd <= datumDo) &&
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
			$('#tabelaKarti tbody').empty();
			
			$.get({
				url: 'rest/karte/getSortiraneKarte?kriterijumSortiranja=' + kriterijumSortiranja + 
					'&kriterijumSortiranja2=' + kriterijumSortiranja2,
				success: function(karte) {
					ucitajKarte(karte);
				}
			});
		});
		
		$('#btnClear').on('click', function() {
			$('input[name=naziv]').val('');
			$('input[name=datumOd]').val('');
		    $('input[name=datumDo]').val('');
		    $('input[name=cenaOd]').val('');
		    $('input[name=cenaDo]').val('');
			$('#tabelaKarti tbody tr').show();
		});
	}
});