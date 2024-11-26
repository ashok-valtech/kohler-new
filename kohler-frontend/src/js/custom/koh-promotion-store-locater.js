/*
 * Copyright © 2000-2018 Kohler Company. All Rights Reserved.
 */

var closeBtnImg = '<img class="infobox-close-img" src="data:image/svg+xml;base64,PHN2ZyBoZWlnaHQ9IjE0cHgiIHdpZHRoPSIxNHB4IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB2ZXJzaW9uPSIxLjEiPjxwYXRoIGQ9Ik03LDBDMy4xMzQsMCwwLDMuMTM0LDAsN2MwLDMuODY3LDMuMTM0LDcsNyw3YzMuODY3LDAsNy0zLjEzMyw3LTdDMTQsMy4xMzQsMTAuODY3LDAsNywweiBNMTAuNSw5LjVsLTEsMUw3LDhsLTIuNSwyLjVsLTEtMUw2LDdMMy41LDQuNWwxLTFMNyw2bDIuNS0yLjVsMSwxTDgsN0wxMC41LDkuNXoiLz48L3N2Zz4=" alt="close infobox">';
var closeBtn = '<a class="infobox-close" href="javascript:closeInfobox()" data-tag="SDK.Infobox.CloseBtn">' + closeBtnImg + '</a>';

/* Define an HTML template for a custom infobox. */
var infoboxTemplate = '<div class="customInfobox"><div class="title">{title}</div>{description}</div>' + closeBtn + '</div>';

var defaultStorelocatorLatId = $('#defaultLatId').val();
var defaultStorelocatorLongId = $('#defaultLongId').val();
var searchErrorMessageId = $('#searchErrorMessageId').val();
var searchSuccessMessageId = $('#searchSuccessMessageId').val();
var bingMapCurrentLocationKeyId = $('#bingMapCurrentLocationKeyId').val();
var searchResultLimitId = $('#searchResultLimitId').val();
var viewDetailsLabelId = $('#viewDetailsLabelId').val();
var kmDigitId = $('#kmDigitId').val();
var dataSource = $(".koh-bingdatasource").data("bingdatasource");
var map, bingMapKey = $(".koh-bingdatasourcekeys").data("bingdatasourcekeys");
var languageCode = $(".koh-bingdatasourcelanguage").data("bingdatasourcelanguage");
var currentLat, currentLong, currentPhone, currentEmail, currentLat, currentLat;
var base_url = window.location.origin;
var searchFlag = true;
var promoStoreLocatorEntityIds = $('#promoStoreLocatorEntityIds').val();
var kecstorecontactclassification = $('#koh-exp-center').val();

/* Document ready function */
$(document).ready(function () {
	// code for ready function
})

/* Check for mobile browsers */
function isMobBrowser() {
	if (navigator.userAgent.match(/Android/i) ||
		navigator.userAgent.match(/webOS/i) ||
		navigator.userAgent.match(/iPhone/i) ||
		navigator.userAgent.match(/iPad/i) ||
		navigator.userAgent.match(/iPod/i) ||
		navigator.userAgent.match(/BlackBerry/i) ||
		navigator.userAgent.match(/Windows Phone/i)
	) {
		return true; // will return true for mobile browsers
	} else {
		return false;
	}
}
$(window).on('load', function () {
	getIP();
	//getLocation();
})

function getIP() {
	$.ajax({
		url: "https://ipinfo.io",
		type: 'GET',
		dataType: "jsonp",
		success: function (response) {
			var ip = response.ip;
			getData(ip);
		},
		error: function (jqXHR, textStatus, errorThrown) {
			console.log("error: " + errorThrown + " || status: " + textStatus + " || data: " + jqXHR);
		}
	});
}

function getData(ip) {
	var url = base_url + '/api/storeservice/getBingResponse/' + ip;
	$.ajax({
		url: url,
		type: 'POST',
		success: function (response) {
			currentLat = response.data.Lt;
			currentLong = response.data.Lg;
			GetMap(currentLat, currentLong)
		},
		error: function (jqXHR, textStatus, errorThrown) {
			console.log("error: " + errorThrown + " || status: " + textStatus + " || data: " + jqXHR);
			currentLat = defaultStorelocatorLatId;
			currentLong = defaultStorelocatorLongId;
			GetMap(currentLat, currentLong)
		}
	});
}

function GetMap(lat, long) {

	/* Initializing Bing Map */
	map = new Microsoft.Maps.Map('#koh-map', {
		credentials: bingMapKey,
		disableScrollWheelZoom: true,
		center: new Microsoft.Maps.Location(lat, long),
	});

	/* Setting center point for the map */
	var center = map.getCenter();
	
	/* Create an infobox at the center of the map but don't show it. */
	infobox = new Microsoft.Maps.Infobox(center, {
		visible: false,
	});

	infobox.setMap(map);

	var currentLocLatLong = lat + ' ' + long;
	$('#defaultLatLong').val(currentLocLatLong);
	$('.koh-store-search-btn').trigger("click");
}

/*function getLocation() {
	  if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(showPosition);
	  } else { 
	    x.innerHTML = "Geolocation is not supported by this browser.";
	  }
	}

function showPosition(position) {
	console.log("Position" + position);
	if(position && position.coords){
		 var currentUserLat = position.coords.latitude;
		 var currentUserLong = position.coords.longitude;
	}else{
		var currentUserLat = defaultStorelocatorLatId;
		var currentUserLong = defaultStorelocatorLongId;
	}
	GetMap(currentUserLat, currentUserLong);
}
*/

/* Left tab click function */
$(document).on('click', '.koh-store-locator-result', function (e) {

	var currentPinId = $(this).closest('li').find('.bingPinId').val();
	var pin = GetEntityByProperty(dataLayer, "id", currentPinId);

	var infoTitle = "<div class='infobox_title'>" + pin.title + "</div>";
	var infoDescription = pin.description;

	if (pin) {

		/* Resetting the center point while each left tab click with animation */
		map.setView({
			center: pin.getLocation(),
			animate: true,
		});
		setTimeout(function () {
			infobox.setOptions({
				visible: true,
				offset: new Microsoft.Maps.Point(-102, 15),
				htmlContent: infoboxTemplate.replace('{title}', infoTitle).replace('{description}', infoDescription)
			});
			infobox.setLocation(pin.getLocation());
		}, 1000);
	}
})

/* Getting clicked pin object function */
function GetEntityByProperty(collection, propertyName, propertyValue) {
	var len = collection.getLength(),
		entity;

	for (var i = 0; i < len; i++) {
		entity = collection.get(i);

		if (entity[propertyName] && entity[propertyName] == propertyValue) {
			return entity;
		} else if (entity.getLength) {

			/* Entity collections have this property and we want to look inside these collections */
			var layer = GetEntityByProperty(entity, propertyName, propertyValue);

			if (layer != null) {
				return layer;
			}
		}
	}
	return null;
}

$(".koh-store-search-btn").click(function () {

	/* Keeping all filters enabled while clicking on search button */
	$("#koh-exp-center").prop('checked', true);
	$("#koh-distributor").prop('checked', true);
	$("#koh-star-dealer").prop('checked', true);
	$("#koh-dealer").prop('checked', true);

	var searchText = $("#koh-search-text").val();
	if (searchText == '') {
		searchText = $('#defaultLatLong').val();
		searchFlag = false;
	} else {
		searchFlag = true;
	}
	mapSeachModule(searchText, $('#koh-exp-center').is(":checked"), $('#koh-distributor').is(":checked"), $("#koh-star-dealer").is(":checked"), $("#koh-dealer").is(":checked"), searchFlag);
});

$('#koh-map-check :checkbox').change(function () {
	var searchText = $("#koh-search-text").val();
	if (searchText == '') {
		searchText = $('#defaultLatLong').val();
		searchFlag = false;
	} else {
		searchFlag = true;
	}
	if (searchText) {
		mapSeachModule(searchText, $('#koh-exp-center').is(":checked"), $('#koh-distributor').is(":checked"), $("#koh-star-dealer").is(":checked"), $("#koh-dealer").is(":checked"), searchFlag);
	}
});

$(document).on('keyup', '#koh-search-text', function (e) {
	e.preventDefault();

	/* Triggering a click event on button while pressing enter key */
	if (e.keyCode === 13) {
		$(".koh-store-search-btn").click();
	}
})

function mapSeachModule(value, kec, distributor, startDealer, dealer, searchFlag) {

	Microsoft.Maps.loadModule('Microsoft.Maps.Search', function () {
		if (searchFlag) {
			map = new Microsoft.Maps.Map('#koh-map', {
				credentials: bingMapKey,
				disableScrollWheelZoom: true,
				center: new Microsoft.Maps.Location(defaultStorelocatorLatId, defaultStorelocatorLongId),
			});
		}
		searchManager = new Microsoft.Maps.Search.SearchManager(map);
		var requestOptions = {
			bounds: map.getBounds(),
			where: value,
			callback: function (answer) {
				loadSpatialDataServices(answer.results[0].location, kec, distributor, startDealer, dealer)
			},
			errorCallback: function () {
				map.entities.clear();
				forNoResults();
			}
		};
		searchManager.geocode(requestOptions);
	});
}

/* Search nearby location for the given input */
function loadSpatialDataServices(center, kec, distributor, startDealer, dealer) {

	Microsoft.Maps.loadModule('Microsoft.Maps.SpatialDataService', function () {

		queryOptions = {
			queryUrl: dataSource,
			top: searchResultLimitId, // Limiting results (maximum results can be 250, default is 25)
			inlineCount: true,
			spatialFilter: {
				spatialFilterType: 'nearby',
				location: center,
				radius: kmDigitId,
			}
		};

		/* Trigger an initial search. */
		getNearByLocations(kec, distributor, startDealer, dealer);
	});
}

function getNearByLocations(kec, distributor, startDealer, dealer) {

	map.entities.clear();
	map.entities.data = [];
	queryOptions.filter = '';

	Microsoft.Maps.SpatialDataService.QueryAPIManager.search(queryOptions, map, function (data) {
		if (data.length > 0) {

			var center = map.getCenter();

			/* Create an infobox at the center of the map but don 't show it. */
			infobox = new Microsoft.Maps.Infobox(center, {
				visible: false
			});

			dataLayer = new Microsoft.Maps.EntityCollection();
			map.entities.push(dataLayer);

			var infoboxLayer = new Microsoft.Maps.EntityCollection();
			map.entities.push(infoboxLayer);

			/* Assign the infobox to a map instance. */
			infobox.setMap(map);
			$('#koh-store-locator-results ul').html('');

			/* For responsive screens enabling tabs */
			var screenWidth = $(window).outerWidth();
			if (screenWidth < 768) {
				$('#koh-response-tab').show();
				$('#koh-map-container').hide();
				$('html, body').animate({
					scrollTop: $("#koh-response-tab").offset().top
				}, 1000);
			} else {
				$('#koh-response-tab').hide();
				$('#koh-map-container').show();
			}
			appendPinsToMap(data, kec, distributor, startDealer, dealer);

		} else {
			forNoResults();
		}
	});
}

/* Appending pins from search response */
function appendPinsToMap(jsonData, kec, distributor, startDealer, dealer) {

	var pin,
		leftContent = '',
		locations = [],
		locationCount = 0,
		kecArray = [],
		distributorArray = [],
		dealerArray = [],
		startDealerArray = [];

	$.each(jsonData, function (ind1, val1) {
		var currentMeta1 = val1.metadata;

		/* Putting KEC Store into different array */
		switch (currentMeta1.ContactClassification.toLowerCase()) {
		    case kecstorecontactclassification:
				kecArray.push(val1);
				break;
			case 'distributor':
				distributorArray.push(val1);
				break;
			case 'star dealer':
				startDealerArray.push(val1);
				break;
			case 'dealer':
				dealerArray.push(val1);
				break;
	    }
	});

	/* Concating other array with KEC Array for making the final array*/
	var finalArray = kecArray.concat(distributorArray, startDealerArray, dealerArray);
	var count = 0;
	$.each(finalArray, function (ind, val) {
		var currentMeta = val.metadata;
		var getEntityID = (currentMeta.EntityID) ? currentMeta.EntityID : '';
		if(promoStoreLocatorEntityIds == '' || promoStoreLocatorEntityIds == null){
			var storeCount = ind + 1;

			/* Setting SVG images according to category */
			if (kec && currentMeta.ContactClassification.toLowerCase() == kecstorecontactclassification) {
				++locationCount;
				var pushpinOptions = {
					icon: '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(132, 166, 72);"></rect><text font-family="HelveticaBold, Helvetica, sans-serif" font-weight="700" x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + storeCount + '</text></svg>',
					anchor: new Microsoft.Maps.Point(12, 12)
				}
				pin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(currentMeta.Latitude, currentMeta.Longitude), pushpinOptions);

				pin = createHtmlToPin(pin, currentMeta, 'expCenter', storeCount);

				/* Mouse over event on pins */
				if (isMobBrowser()) {
					Microsoft.Maps.Events.addHandler(pin, 'click', displayInfobox);
				} else {
					Microsoft.Maps.Events.addHandler(pin, 'mouseover', displayInfobox);
				}

				dataLayer.push(pin);

				leftContent = createLeftHtmlContent(currentMeta, leftContent, 'expCenter', storeCount);

				locations.push(val.getLocation());
				map.entities.push(val);

			}
			if (distributor && currentMeta.ContactClassification.toLowerCase() == 'distributor') {
				++locationCount;
				var pushpinOptions = {
					icon: '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(0,167,206);"></rect><text font-family="HelveticaBold, Helvetica, sans-serif" font-weight="700" x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + storeCount + '</text></svg>',
					anchor: new Microsoft.Maps.Point(12, 12)
				}
				pin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(currentMeta.Latitude, currentMeta.Longitude), pushpinOptions);

				pin = createHtmlToPin(pin, currentMeta, 'distributor', storeCount);

				/* Mouse over event on pins */
				if (isMobBrowser()) {
					Microsoft.Maps.Events.addHandler(pin, 'click', displayInfobox);
				} else {
					Microsoft.Maps.Events.addHandler(pin, 'mouseover', displayInfobox);
				}
				dataLayer.push(pin);

				leftContent = createLeftHtmlContent(currentMeta, leftContent, 'distributor', storeCount);

				locations.push(val.getLocation());
				map.entities.push(val);
			}
			if (startDealer && currentMeta.ContactClassification.toLowerCase() == 'star dealer') {
				++locationCount;
				var pushpinOptions = {
					icon: '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(0,0,0);"></rect><text font-family="HelveticaBold, Helvetica, sans-serif" font-weight="700" x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + storeCount + '</text></svg>',
					anchor: new Microsoft.Maps.Point(12, 12)
				}
				pin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(currentMeta.Latitude, currentMeta.Longitude), pushpinOptions);

				pin = createHtmlToPin(pin, currentMeta, 'starDealer', storeCount);

				/* Mouse over event on pins */
				if (isMobBrowser()) {
					Microsoft.Maps.Events.addHandler(pin, 'click', displayInfobox);
				} else {
					Microsoft.Maps.Events.addHandler(pin, 'mouseover', displayInfobox);
				}
				dataLayer.push(pin);

				leftContent = createLeftHtmlContent(currentMeta, leftContent, 'starDealer', storeCount);

				locations.push(val.getLocation());
				map.entities.push(val);
			}
			if (dealer && currentMeta.ContactClassification.toLowerCase() == 'dealer') {
				++locationCount;
				var pushpinOptions = {
					icon: '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(157, 157, 157);"></rect><text font-family="HelveticaBold, Helvetica, sans-serif" font-weight="700" x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + storeCount + '</text></svg>',
					anchor: new Microsoft.Maps.Point(12, 12)
				}
				pin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(currentMeta.Latitude, currentMeta.Longitude), pushpinOptions);

				pin = createHtmlToPin(pin, currentMeta, 'dealer', storeCount);

				/* Mouse over event on pins */
				if (isMobBrowser()) {
					Microsoft.Maps.Events.addHandler(pin, 'click', displayInfobox);
				} else {
					Microsoft.Maps.Events.addHandler(pin, 'mouseover', displayInfobox);
				}
				dataLayer.push(pin);

				leftContent = createLeftHtmlContent(currentMeta, leftContent, 'dealer', storeCount);

				locations.push(val.getLocation());
				map.entities.push(val);
			}
		}else{
			if(promoStoreLocatorEntityIds.includes(getEntityID)){
				count = count+1;
				/* Setting SVG images according to category */
				if (kec && currentMeta.ContactClassification.toLowerCase() == 'kec') {
					++locationCount;
					var pushpinOptions = {
						icon: '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(132, 166, 72);"></rect><text font-family="HelveticaBold, Helvetica, sans-serif" font-weight="700" x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>',
						anchor: new Microsoft.Maps.Point(12, 12)
					}
					pin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(currentMeta.Latitude, currentMeta.Longitude), pushpinOptions);
		
					pin = createHtmlToPin(pin, currentMeta, 'expCenter', count);
		
					/* Mouse over event on pins */
					if (isMobBrowser()) {
						Microsoft.Maps.Events.addHandler(pin, 'click', displayInfobox);
					} else {
						Microsoft.Maps.Events.addHandler(pin, 'mouseover', displayInfobox);
					}
		
					dataLayer.push(pin);
		
					leftContent = createLeftHtmlContent(currentMeta, leftContent, 'expCenter', count);
		
					locations.push(val.getLocation());
					map.entities.push(val);
		
				}
				if (distributor && currentMeta.ContactClassification.toLowerCase() == 'distributor') {
					++locationCount;
					var pushpinOptions = {
						icon: '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(0,167,206);"></rect><text font-family="HelveticaBold, Helvetica, sans-serif" font-weight="700" x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>',
						anchor: new Microsoft.Maps.Point(12, 12)
					}
					pin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(currentMeta.Latitude, currentMeta.Longitude), pushpinOptions);
		
					pin = createHtmlToPin(pin, currentMeta, 'distributor', count);
		
					/* Mouse over event on pins */
					if (isMobBrowser()) {
						Microsoft.Maps.Events.addHandler(pin, 'click', displayInfobox);
					} else {
						Microsoft.Maps.Events.addHandler(pin, 'mouseover', displayInfobox);
					}
					dataLayer.push(pin);
		
					leftContent = createLeftHtmlContent(currentMeta, leftContent, 'distributor', count);
		
					locations.push(val.getLocation());
					map.entities.push(val);
				}
				if (startDealer && currentMeta.ContactClassification.toLowerCase() == 'star dealer') {
					++locationCount;
					var pushpinOptions = {
						icon: '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(0,0,0);"></rect><text font-family="HelveticaBold, Helvetica, sans-serif" font-weight="700" x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>',
						anchor: new Microsoft.Maps.Point(12, 12)
					}
					pin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(currentMeta.Latitude, currentMeta.Longitude), pushpinOptions);
		
					pin = createHtmlToPin(pin, currentMeta, 'starDealer', count);
		
					/* Mouse over event on pins */
					if (isMobBrowser()) {
						Microsoft.Maps.Events.addHandler(pin, 'click', displayInfobox);
					} else {
						Microsoft.Maps.Events.addHandler(pin, 'mouseover', displayInfobox);
					}
					dataLayer.push(pin);
		
					leftContent = createLeftHtmlContent(currentMeta, leftContent, 'starDealer', count);
		
					locations.push(val.getLocation());
					map.entities.push(val);
				}
				if (dealer && currentMeta.ContactClassification.toLowerCase() == 'dealer') {
					++locationCount;
					var pushpinOptions = {
						icon: '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(157, 157, 157);"></rect><text font-family="HelveticaBold, Helvetica, sans-serif" font-weight="700" x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>',
						anchor: new Microsoft.Maps.Point(12, 12)
					}
					pin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(currentMeta.Latitude, currentMeta.Longitude), pushpinOptions);
		
					pin = createHtmlToPin(pin, currentMeta, 'dealer', count);
		
					/* Mouse over event on pins */
					if (isMobBrowser()) {
						Microsoft.Maps.Events.addHandler(pin, 'click', displayInfobox);
					} else {
						Microsoft.Maps.Events.addHandler(pin, 'mouseover', displayInfobox);
					}
					dataLayer.push(pin);
		
					leftContent = createLeftHtmlContent(currentMeta, leftContent, 'dealer', count);
		
					locations.push(val.getLocation());
					map.entities.push(val);
				}
			}
		}
	});

	$('#koh-store-locator-results ul').append(leftContent);

	if (locationCount > 0) {
		forResults(locationCount); // Function for results
		map.setView({
			bounds: Microsoft.Maps.LocationRect.fromLocations(locations),
			padding: 80
		});
	} else {
		forNoResults(); // Function for no results
	}
}

function createHtmlToPin(pin, currentMeta, filterType, count) {

	var storeDisplayName = (currentMeta.LocationName) ? currentMeta.LocationName : '';
	var storeDisplayNameAlternate = (currentMeta.LocationNameAlternate) ? currentMeta.LocationNameAlternate : '';
	var storeAddress = (currentMeta.AddressLine) ? currentMeta.AddressLine : '';
	var storeAddressAlternate = (currentMeta.AddressLineAlternate) ? currentMeta.AddressLineAlternate : '';
	var storeLocality = (currentMeta.Locality) ? currentMeta.Locality : '';
	var storeLocalityAlternate = (currentMeta.LocalityAlternate) ? currentMeta.LocalityAlternate : '';
	var storeDistrict = (currentMeta.AdminDistrict) ? currentMeta.AdminDistrict : '';
	var storeDistrictAlternate = (currentMeta.AdminDistrictAlternate) ? currentMeta.AdminDistrictAlternate : '';
	var storePostalCode = (currentMeta.PostalCode) ? currentMeta.PostalCode : '';
	var storePhone = (currentMeta.Phone) ? currentMeta.Phone : '';
	var storeViewDetails = (currentMeta.WebURL) ? currentMeta.WebURL : '';
	var storeOpeningHours = (currentMeta.OpeningHours) ? currentMeta.OpeningHours : '';
		pin.title = '';
		pin.title += '<div class="koh-store-icon-wrapper">';
	
		if (filterType == 'expCenter') {
			pin.title += '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(132, 166, 72);"></rect><text x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>';
		}
		if (filterType == 'distributor') {
			pin.title += '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(0,167,206);"></rect><text x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>';
		}
		if (filterType == 'starDealer') {
			pin.title += '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(0,0,0);"></rect><text x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>';
		}
		if (filterType == 'dealer') {
			pin.title += '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(157, 157, 157);"></rect><text x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>';
		}
	
		if (languageCode == 'en') {
			pin.title += '</div><span class="koh-store-name">' + storeDisplayName + '</span > ';
			pin.description = '<div class="koh-map-details">';
			pin.description += '<div class="koh-store-locator-result-address">' + storeAddress + '</div>';
			pin.description += '<div class="koh-store-locator-result-city">' + storeLocality + ', ' + storeDistrict + ' ' + storePostalCode + '</div><br>';
		} else {
			pin.title += '</div><span class="koh-store-name">' + storeDisplayNameAlternate + '</span > ';
			pin.description = '<div class="koh-map-details">';
			pin.description += '<div class="koh-store-locator-result-address">' + storeAddressAlternate + '</div>';
			pin.description += '<div class="koh-store-locator-result-city">' + storeLocalityAlternate + ', ' + storeDistrictAlternate + ' ' + storePostalCode + '</div><br>';
		}
		pin.description += '<div class="koh-store-locator-result-phone-num">' + storePhone + '</div>';
		if (storeViewDetails) {
			pin.description += '<div class="koh-store-locator-result-view-details"><a href="' + storeViewDetails + '" target="_blank">' + viewDetailsLabelId + '</a></div>';
		}
		pin.description += '<div class="koh-store-locator-result-opening-hours">' + storeOpeningHours + '</div></div>';
	
		/* Setting id for getting pin details with ID on left tab click */
		pin.id = count;
		return pin;
}

function createLeftHtmlContent(currentMeta, leftContent, filterType, count) {
	
	var kecBackgroundClass = (filterType == 'expCenter') ? 'koh-kec-background' : ""
	var storeLogo = (currentMeta.StoreLogo) ? currentMeta.StoreLogo : '';
	var storeLat = (currentMeta.Latitude) ? currentMeta.Latitude : '';
	var storeLong = (currentMeta.Longitude) ? currentMeta.Longitude : '';
	var storePrimaryEmail = (currentMeta.PrimaryContactEmail) ? currentMeta.PrimaryContactEmail : '';
	var storeDisplayName = (currentMeta.LocationName) ? currentMeta.LocationName : '';
	var storeDisplayNameAlternate = (currentMeta.LocationNameAlternate) ? currentMeta.LocationNameAlternate : '';
	var storeAddress = (currentMeta.AddressLine) ? currentMeta.AddressLine : '';
	var storeAddressAlternate = (currentMeta.AddressLineAlternate) ? currentMeta.AddressLineAlternate : '';
	var storeLocality = (currentMeta.Locality) ? currentMeta.Locality : '';
	var storeLocalityAlternate = (currentMeta.LocalityAlternate) ? currentMeta.LocalityAlternate : '';
	var storeDistrict = (currentMeta.AdminDistrict) ? currentMeta.AdminDistrict : '';
	var storeDistrictAlternate = (currentMeta.AdminDistrictAlternate) ? currentMeta.AdminDistrictAlternate : '';
	var storePostalCode = (currentMeta.PostalCode) ? currentMeta.PostalCode : '';
	var storePhone = (currentMeta.Phone) ? currentMeta.Phone : '';
	var storeViewDetails = (currentMeta.WebURL) ? currentMeta.WebURL : '';
	var storeOpeningHours = (currentMeta.OpeningHours) ? currentMeta.OpeningHours : '';
		leftContent += '<li class="koh-store-locator-result row ' + kecBackgroundClass + '"><input class="bingPinId" type="hidden" value="' + count + '"/>';
		if (storeLogo) {
			leftContent += '<img class="koh-store-locator-img" src="' + currentMeta.StoreLogo + '" alt="KohlerExperienceCenter">';
		}
		leftContent += '<input id="currentLatLong" type="hidden" value="' + storeLat + ',' + storeLong + '"/>';
		leftContent += '<input id="currentPhone" type="hidden" value="' + storePhone + '"/>';
		leftContent += '<input id="currentPrimaryEmail" type="hidden" value="' + storePrimaryEmail + '"/>';
		leftContent += '<h3 class="koh-store-name"><div style="display: inline-block; vertical-align: middle">';
	
		if (filterType == 'expCenter') {
			leftContent += '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(132, 166, 72);"></rect><text x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>';
		}
		if (filterType == 'distributor') {
			leftContent += '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(0,167,206);"></rect><text x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>';
		}
		if (filterType == 'starDealer') {
			leftContent += '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(0,0,0);"></rect><text x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>';
		}
		if (filterType == 'dealer') {
			leftContent += '<svg xmlns="https://www.w3.org/2000/svg" width="25" height="25"><rect width="25" height="25" style="fill:rgb(157,157,157);"></rect><text x="50%" y="18" text-anchor="middle" fill="white" font-size="14">' + count + '</text></svg>';
		}
		if (languageCode == 'en') {
			leftContent += '</div><span>' + storeDisplayName + '</span></h3>';
			leftContent += '<div class="koh-map-details">';
			leftContent += '<div class="koh-store-locator-result-address">' + storeAddress + '</div>';
			leftContent += '<div class="koh-store-locator-result-city">' + storeLocality + ', ' + storeDistrict + ' ' + storePostalCode + '</div><br>';
		} else {
			leftContent += '</div><span>' + storeDisplayNameAlternate + '</span></h3>';
			leftContent += '<div class="koh-map-details">';
			leftContent += '<div class="koh-store-locator-result-address">' + storeAddressAlternate + '</div>';
			leftContent += '<div class="koh-store-locator-result-city">' + storeLocalityAlternate + ', ' + storeDistrictAlternate + ' ' + currentMeta.PostalCode + '</div><br>';
		}
		leftContent += '<div class="koh-store-locator-result-phone-num"><span>' + storePhone + '</span></div>';
		if (storeViewDetails) {
			leftContent += '<div class="koh-store-locator-result-view-details"><a href="' + storeViewDetails + '" target="_blank">' + viewDetailsLabelId + '</a></div>';
		}
		leftContent += '<div class="koh-store-locator-result-opening-hours"><span>' + storeOpeningHours + '</span></div></div>';
	
		/* Converting distance to miles */
		var currentMiles = currentMeta.__Distance.toFixed(2);
		leftContent += '<div class="koh-store-locator-result-distance">' + currentMiles + ' KM</div>';
		return leftContent;
}

/* On map pin mouse over invoke function */
function displayInfobox(e) {
	if (e.targetType == 'pushpin') {

		var infoDetails = e.target;
		infobox.setLocation(e.target.getLocation());

		var infoTitle = "<div class='infobox_title'>" + infoDetails.title + "</div>";
		var infoDescription = infoDetails.description;

		infobox.setOptions({
			visible: true,
			offset: new Microsoft.Maps.Point(-102, 15),
			htmlContent: infoboxTemplate.replace('{title}', infoTitle).replace('{description}', infoDescription)
		});

		map.setView({
			center: e.target.getLocation(),
			animate: true,
		});
	}
}

/* Info window close function */
function closeInfobox() {
	infobox.setOptions({
		visible: false,
	});
}

/* Template change for results */
function forResults(locationCount) {
	$('#koh-map-container').prepend($("#koh-map").detach());
	$('#koh-no-result h3').empty();
	var searchText = $('#koh-search-text').val();
	if (searchText != '') {
		var resultMessage = locationCount + ' ' + searchSuccessMessageId + ' ' + searchText + '.';
		$("#koh-no-result h3").html(resultMessage);
	}
	$("#koh-no-result, #koh-legend").show();
	$('#koh-store-locator-results').removeClass('hide');
	$('#koh-map-container').removeClass('col-sm-12').addClass('col-sm-8 pad-left-0');
}

/* Template change for no results */
function forNoResults() {
	jQuery("#koh-map").detach().appendTo('.c-koh-store-locator');
	$('#koh-initial-map').show();
	$('').hide();
	$('#koh-no-result h3').empty();
	var resultMessage = searchErrorMessageId;
	$("#koh-no-result h3").html(resultMessage);
	$("#koh-no-result").show();
	if (!$('#koh-store-locator-results').hasClass('hide')) {
		$('#koh-store-locator-results').addClass('hide');
	}
	if ($('#koh-map-container').hasClass('col-sm-8')) {
		$('#koh-map-container').removeClass('col-sm-8 pad-left-0').addClass('col-sm-12');
	}
	$("#koh-legend").hide();
	$('#koh-store-locator-results').addClass('hide');
}

var callFunctionFromScript = function (inst) {
	inst.open();
	scaleCaptcha();
};
var scaleCaptcha = function () {

	// Width of the reCAPTCHA element, in pixels
	var reCaptchaWidth = 304;

	// Get the containing element's width
	var containerWidth = $('.captcha-container').width();
	var captchaElements = $('.g-recaptcha');

	// Only scale the reCAPTCHA if it won't fit inside the container
	if (containerWidth <= reCaptchaWidth) {
		var scale = 0.82;
		captchaElements.css('transform', 'scale(' + scale + ')').css('-webkit-transform', 'scale(' + scale + ')').css('-ms-transform', 'scale(' + scale + ')').css('-o-transform', 'scale(' + scale + ')').css('transform-origin', '0 0').css('-webkit-transform-origin', '0 0').css('-ms-transform-origin', '0 0').css('-o-transform-origin', '0 0');
		$('.captcha-container').height(68);
	}
}

$(window).on('resize', function () {
	var listContent = $('#koh-store-locator-results ul').html().trim();
	if (listContent.length > 0) {

		/* For responsive screens enabling tabs */
		var screenWidth = $(window).width();
		if (screenWidth < 768) {
			$('#koh-response-tab').show();
			var getActiveTab = $('#koh-response-tab').find('.active');
			if ($(getActiveTab).attr('id') == 'koh-list-map-tab') {
				$('#koh-store-locator-results').show();
				$('#koh-map-container').hide();
			} else {
				$('#koh-store-locator-results').hide();
				$('#koh-map-container').show();
			}
		} else {
			$('#koh-response-tab').hide();
			$('#koh-map-container').show();
			$('#koh-store-locator-results').show();
		}
	}
})

/* On click event triggering on tab click */
$('#koh-response-tab li').on('click', function (e) {
	e.preventDefault();
	e.stopPropagation();
	$('#koh-response-tab li').removeClass('active');
	$(this).addClass('active');
	var clickedId = $(this).attr('id');
	if (clickedId == 'koh-map-tab') {
		$('#koh-store-locator-results').hide();
		$('#koh-map-container').show();
	} else {
		$('#koh-store-locator-results').show();
		$('#koh-map-container').hide();
	}
})

/* ToolTip script starts here */
$('.koh-dotted-span').on('mouseover', function (e) {
	var currentTip = $(this).closest('.koh-tooltip');
	showToolTip(currentTip);
	switchToolTip(currentTip);
})

$('.koh-dotted-span').on('mouseout', function (e) {
	var currentTip = $(this).closest('.koh-tooltip');
	hideToolTip(currentTip);
});

function showToolTip(currentTip) {
	$(currentTip).find('span').css({
		'visibility': 'visible',
		'opacity': '1'
	})
}

function hideToolTip() {
	$('.koh-tooltip span').css({
		'visibility': 'hidden',
		'opacity': '0'
	})
}

$(window).on('scroll', function () {
	var currentTipText = $(".koh-tooltip span");
	$(currentTipText).each(function (index) {
		if ($(this).css("visibility") === "visible") {
			var currentTip = $(this).closest('.koh-tooltip');
			switchToolTip(currentTip);
		}
	})
});

function switchToolTip(currentTip) {
	var currentOffset = $(currentTip).offset();
	var toptipHeight = $(currentTip).find('span').outerHeight();
	var xAxis = currentOffset.top;
	var windowTop = $(window).scrollTop() + toptipHeight + 100;
	if (xAxis < windowTop) {
		$(currentTip).find('span').removeClass('koh-tooltiptop').addClass('koh-tooltipbottom')
	} else {
		$(currentTip).find('span').removeClass('koh-tooltipbottom').addClass('koh-tooltiptop')
	}
}
/* ToolTip script ends here */

/* Allowing only numbers in input tag with a class name */
$(document).on('keyup', '.number-input', function () {
	var start = this.selectionStart - 1,
		end = this.selectionEnd - 1;
	if ($(this).val().match(/[^0-9]/gi)) {
		$(this).val($(this).val().replace(/[^0-9]/gi, ''));
		this.setSelectionRange(start, end);
	}
});

$(document).on('blur', '#koh-search-text', function (e) {
	e.preventDefault();
	$('#as_container').css('visibility', 'hidden');
	$('.asOuterContainer ul').empty();
	e.stopImmediatePropagation();
	return false;
});