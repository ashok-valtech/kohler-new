/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

function GetMap() {
	var dataSource = $(".koh-bingdatasource").data("bingdatasource");
	var masterKey = $(".koh-bingdatasourcekeys").data("bingdatasourcekeys");
	var languageCode = $(".koh-bingdatasourcelanguage").data("bingdatasourcelanguage");
	
	//var dataSource = dataSource.split("_",2);
	//var countryCode = dataSource[0];
	//var dataSourceURL = dataSource[1];
    function getUrlVars(url) {
        var vars = [],
        hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for (var i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            vars.push(hash[1]);
        }
        return vars;
    }
    
    var url = (window.location.href)
    var lat = getUrlVars(url)[2];
    var lon = getUrlVars(url)[1];

   /* if(countryCode == 'APAC'){
    	masterKey = 'AtBo4eNjApjhBRLJIIn3crGV-J8M_saJbxuSeQU_Jx68ardh0B8WnDwiew5x6n50';
    }else if(countryCode == 'SG'){
    	masterKey = 'Av8by0e-sNM9wb5lf8ipY0d0iGc14lZvoBN4kVU25-xr1wYtt_Z5hM7mTXoHSg4H';
    }*/
    
	var map = new Microsoft.Maps.Map(document.getElementById('myMap'), {
            credentials: masterKey,
            center: new Microsoft.Maps.Location(lat, lon),
            zoom: 15
    });

	
	
    var sdsDataSourceUrl = dataSource;
    Microsoft.Maps.loadModule('Microsoft.Maps.SpatialDataService', function() {
			var queryOptions = {
							queryUrl: 	sdsDataSourceUrl,
							spatialFilter: {
								spatialFilterType: 'nearby',
								location: map.getCenter(),
								radius: 20
							},
				};
            
				Microsoft.Maps.SpatialDataService.QueryAPIManager.search(queryOptions, map, function(data) {
						var obj = data[0];
						var pushpin = new Microsoft.Maps.Pushpin(map.getCenter(), {
							icon: $('#redpin').val(),
							text: "1"
						});
						map.entities.push(pushpin);
				 
						var infobox = new Microsoft.Maps.Infobox(map.getCenter(), {
							visible: false
						});
						infobox.setMap(map);
						
						Microsoft.Maps.Events.addHandler(pushpin, 'click', function(e) {
							if(languageCode == 'en'){
								/*for (var key in obj.metadata)
								{								 
								 console.log ('key: ' + key + " :-  Value: " + obj.metadata[key]);
								}*/
								displayInfobox_EnglishSite(obj);
							}
							else{
								displayInfobox_LanguageSite(obj)
								/*for (var key in obj.metadata)
								{								 
								 console.log ('key: ' + key + " :-  Value: " + obj.metadata[key]);
								}*/
							}
						});

						function displayInfobox_EnglishSite(obj) {	
							
							var addressTable = "<table>";
							var displayName = "";
							var addressLine = "";
							if(obj!=undefined){
							if(obj.metadata.LocationName != undefined && obj.metadata.LocationName != ""){
								displayName += '<tr><td>' + obj.metadata.LocationName + '</td></tr>';
							}							
							if(obj.metadata.AddressLine != undefined && obj.metadata.AddressLine != "" ){
								addressLine += obj.metadata.AddressLine + ", ";
							}
							if(obj.metadata.Locality != undefined && obj.metadata.Locality != ""){
								addressLine += obj.metadata.Locality + ", ";
							}
							if(obj.metadata.AdminDistrict != undefined && obj.metadata.AdminDistrict != ""){
								
								if(obj.metadata.Locality != obj.metadata.AdminDistrict)
								{
									addressLine += obj.metadata.AdminDistrict + ", ";
								}
								
							}
							if(obj.metadata.PostalCode != undefined && obj.metadata.PostalCode != ""){
								addressLine += obj.metadata.PostalCode+ ", ";
							}
							
							addressTable +=  displayName;
							addressTable += '<tr><td>' + addressLine.slice(0, addressLine.length-2) +'</td></tr>';
							}
							addressTable +=	'</table>';
							infobox.setOptions({
									visible: true,
									description: addressTable
								});
						}
						
						function displayInfobox_LanguageSite(obj) {
							
							var addressTable = "<table>";
							var displayName = "";
							var addressLine = "";
							if(obj!=undefined){
							if(obj.metadata.LocationNameAlternate != undefined && obj.metadata.LocationNameAlternate != "" ){
								displayName += '<tr><td>' + obj.metadata.LocationNameAlternate + '</td></tr>';
							}							
							if(obj.metadata.AddressLineAlternate != undefined && obj.metadata.AddressLineAlternate != ""){
								addressLine += obj.metadata.AddressLineAlternate + ", ";
							}
							if(obj.metadata.LocalityAlternate != undefined && obj.metadata.LocalityAlternate != ""){
								addressLine += obj.metadata.LocalityAlternate + ", ";
							}
							if(obj.metadata.AdminDistrictAlternate != undefined && obj.metadata.AdminDistrictAlternate != ""){
								
								if(obj.metadata.LocalityAlternate != obj.metadata.AdminDistrictAlternate)
								{
									addressLine += obj.metadata.AdminDistrictAlternate + ", ";
								}
								
							}
							if(obj.metadata.PostalCode != undefined && obj.metadata.PostalCode != ""){
								addressLine += obj.metadata.PostalCode+ ", ";
							}
							
							addressTable +=  displayName;
							addressTable += '<tr><td>' + addressLine.slice(0, addressLine.length-2) +'</td></tr>';
							}
							addressTable +=	'</table>';							
							
							infobox.setOptions({
									visible: true,
									description: addressTable
								});
						}
						
					}, null, false, function(status, message) {
							document.getElementById('printoutPanel').innerHTML = 'Search failure. NetworkStatus: ' + status;
				});
        });
}            