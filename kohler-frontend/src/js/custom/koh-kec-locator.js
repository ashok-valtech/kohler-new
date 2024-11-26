/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

function GetMap() {
	var dataSource = $(".koh-bingdatasource").data("bingdatasource");
	var masterKey = $(".koh-bingdatasourcekeys").data("bingdatasourcekeys");
	var languageCode = $(".koh-bingdatasourcelanguage").data("bingdatasourcelanguage");


	var lat = $('.koh-latitude').data("latitude");
	var lon = $('.koh-longitude').data("longitude");

	var map = new Microsoft.Maps.Map(document.getElementById('myMap'), {
		credentials: masterKey,
		center: new Microsoft.Maps.Location(lat, lon),
		disableScrollWheelZoom: true,
		zoom: 15
	});

	var sdsDataSourceUrl = dataSource;
	Microsoft.Maps.loadModule('Microsoft.Maps.SpatialDataService', function () {
		var queryOptions = {
			queryUrl: sdsDataSourceUrl,
			spatialFilter: {
				spatialFilterType: 'nearby',
				location: map.getCenter(),
				radius: 20
			},
		};

		var base64Image = 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiA/PjwhRE9DVFlQRSBzdmcgIFBVQkxJQyAnLS8vVzNDLy9EVEQgU1ZHIDEuMS8vRU4nICAnaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkJz48c3ZnIGVuYWJsZS1iYWNrZ3JvdW5kPSJuZXcgMCAwIDI0IDI0IiBoZWlnaHQ9IjI0cHgiIGlkPSJMYXllcl8xIiB2ZXJzaW9uPSIxLjEiIHZpZXdCb3g9IjAgMCAyNCAyNCIgd2lkdGg9IjI0cHgiIHhtbDpzcGFjZT0icHJlc2VydmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiPjxwYXRoIGQ9Ik0xMS41MTMsMTIuMzhjLTIuMTE3LDAtMy44MzUtMS43MjktMy44MzUtMy44NjJjMC0yLjEzNSwxLjcxOC0zLjg2MywzLjgzNS0zLjg2M3MzLjgzNSwxLjcyOSwzLjgzNSwzLjg2MyAgQzE1LjM0OCwxMC42NSwxMy42MywxMi4zOCwxMS41MTMsMTIuMzggTTExLjUxMywwQzYuODI1LDAsMy4wMjUsMy44MjcsMy4wMjUsOC41NDljMCw0LjQ2LDMuODQ0LDEwLjIxMyw2LjQxMSwxMy4wMTQgIGMwLjk1OSwxLjA0NSwyLjA3NiwyLjQ1NCwyLjA3NiwyLjQ1NHMxLjItMS40MTcsMi4yMjktMi40OTNDMTYuMzA2LDE4Ljg0LDIwLDEzLjQ1MSwyMCw4LjU0OUMyMCwzLjgyNywxNi4yLDAsMTEuNTEzLDAiLz48L3N2Zz4=';
		Microsoft.Maps.SpatialDataService.QueryAPIManager.search(queryOptions, map, function (data) {
			var obj = data[0];
			var pushpin = new Microsoft.Maps.Pushpin(map.getCenter(), {
				icon: base64Image

			});
			map.entities.push(pushpin);
			
			var offset = new Microsoft.Maps.Point(0,-25);

			var p = map.tryLocationToPixel(map.getCenter());
			p.x -= offset.x;
			p.y += offset.y;
			map.setView({center: map.tryPixelToLocation(p)});

			var infobox = new Microsoft.Maps.Infobox(map.getCenter(), {
				visible: false								
			});
			infobox.setMap(map);

			Microsoft.Maps.Events.addHandler(pushpin, 'click', function (e) {
				if (languageCode == 'en') {
					for (var key in obj.metadata) {
						console.log('key: ' + key + " :-  Value: " + obj.metadata[key]);
					}
					displayInfobox_EnglishSite(obj);
				} else {
					displayInfobox_LanguageSite(obj)
					for (var key in obj.metadata) {
						console.log('key: ' + key + " :-  Value: " + obj.metadata[key]);
					}
				}
			});

			function displayInfobox_EnglishSite(obj) {

				var addressTable = "<table>";
				var displayName = "";
				var addressLine = "";
				if (obj != undefined) {
					if (obj.metadata.LocationName != undefined && obj.metadata.LocationName != "") {
						displayName += '<tr><td>' + obj.metadata.LocationName + '</td></tr>';
					}
					if (obj.metadata.AddressLine != undefined && obj.metadata.AddressLine != "") {
						addressLine += obj.metadata.AddressLine + ", ";
					}
					if (obj.metadata.Locality != undefined && obj.metadata.Locality != "") {
						addressLine += obj.metadata.Locality + ", ";
					}
					if (obj.metadata.AdminDistrict != undefined && obj.metadata.AdminDistrict != "") {

						if (obj.metadata.Locality != obj.metadata.AdminDistrict) {
							addressLine += obj.metadata.AdminDistrict + ", ";
						}

					}
					if (obj.metadata.PostalCode != undefined && obj.metadata.PostalCode != "") {
						addressLine += obj.metadata.PostalCode + ", ";
					}

					addressTable += displayName;
					addressTable += '<tr><td>' + addressLine.slice(0, addressLine.length - 2) + '</td></tr>';
				}
				addressTable += '</table>';
				infobox.setOptions({
					visible: true,
					description: addressTable
				});
			}

			function displayInfobox_LanguageSite(obj) {

				var addressTable = "<table>";
				var displayName = "";
				var addressLine = "";
				if (obj != undefined) {
					if (obj.metadata.LocationNameAlternate != undefined && obj.metadata.LocationNameAlternate != "") {
						displayName += '<tr><td>' + obj.metadata.LocationNameAlternate + '</td></tr>';
					}
					if (obj.metadata.AddressLineAlternate != undefined && obj.metadata.AddressLineAlternate != "") {
						addressLine += obj.metadata.AddressLineAlternate + ", ";
					}
					if (obj.metadata.LocalityAlternate != undefined && obj.metadata.LocalityAlternate != "") {
						addressLine += obj.metadata.LocalityAlternate + ", ";
					}
					if (obj.metadata.AdminDistrictAlternate != undefined && obj.metadata.AdminDistrictAlternate != "") {

						if (obj.metadata.LocalityAlternate != obj.metadata.AdminDistrictAlternate) {
							addressLine += obj.metadata.AdminDistrictAlternate + ", ";
						}

					}
					if (obj.metadata.PostalCode != undefined && obj.metadata.PostalCode != "") {
						addressLine += obj.metadata.PostalCode + ", ";
					}

					addressTable += displayName;
					addressTable += '<tr><td>' + addressLine.slice(0, addressLine.length - 2) + '</td></tr>';
				}
				addressTable += '</table>';

				infobox.setOptions({
					visible: true,
					description: addressTable
				});
			}

		}, null, false, function (status, message) {
			document.getElementById('printoutPanel').innerHTML = 'Search failure. NetworkStatus: ' + status;
		});
	});
}