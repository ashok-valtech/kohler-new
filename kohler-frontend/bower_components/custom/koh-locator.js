$(function() {
    // URL to the data source that powers the locator. 
    //var dataSourceUrl = 'https://spatial.virtualearth.net/REST/v1/data/515d38d4d4e348d9a61c615f59704174/CoffeeShops/CoffeeShop'; 

    var dataSourceUrl = 'https://spatial.virtualearth.net/REST/v1/data/f5df4be94f7045e89b00567fe992f6e6/sampledata/FourthCoffeeShops';

    // A setting for specifying the distance units displayed. Possible values are 'km' and 'mi'. 
    var distanceUnits = 'km';

    // Load the map. 
    var map = new Microsoft.Maps.Map(document.getElementById('myMap'), {
        credentials: 'Av8by0e-sNM9wb5lf8ipY0d0iGc14lZvoBN4kVU25-xr1wYtt_Z5hM7mTXoHSg4H',
        zoom: 1
    });



    // Create a layer to load pushpins to. 
    var dataLayer = new Microsoft.Maps.EntityCollection();
    map.entities.push(dataLayer);

    // Add a layer for the infobox. 
    var infoboxLayer = new Microsoft.Maps.EntityCollection();
    map.entities.push(infoboxLayer);

    // Create a global infobox control. 
    var infobox = new Microsoft.Maps.Infobox(new Microsoft.Maps.Location(0, 0), {
        visible: false,
        offset: new Microsoft.Maps.Point(0, 20),
        height: 170,
        width: 230
    });
    infoboxLayer.push(infobox);

    // Create a session key from the map to use with data source service requests. 
    var sessionKey;
    map.getCredentials(function(c) {
        sessionKey = c;
    });

    // Load the Search Module for Bing Maps for doing geocoding. 
    var searchManager;
    Microsoft.Maps.loadModule('Microsoft.Maps.Search', {
        callback: function() {
            searchManager = new Microsoft.Maps.Search.SearchManager(map);
        }
    });

    // Resize the height of the results panel based on the available space. 
    $(window).resize(function() {
        $('.resultsPanel').height($(window).height() - $('.searchBar').height() - 100);
        $('.mapPanel').height($(window).height() - 100);
        $('.sidePanel').height($(window).height() - 100);
    });
    $(window).resize();

    $(window).load(function() {
        var url = (window.location.href)
        var lat = getUrlVars(url)[2];
        var lan = getUrlVars(url)[1];
        var query = lat + "," + lan;
        clearMap();
        var geocodeRequest = {
            where: query,
            count: 1,
            callback: function(r) {
                if (r && r.results &&
                    r.results.length > 0 &&
                    r.results[0].location) {
                    findNearbyLocations(r.results[0].location);
                } else {
                    showErrorMsg('Unable to geocode query');
                }
            },
            errorCallback: function() {
                showErrorMsg('Unable to geocode query');
            }
        };
        searchManager.geocode(geocodeRequest);
    });
    
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
    
    

    // Add a key press event to the search box that triggers the search when the user presses Enter key. 
    $('#searchBox').keypress(function(e) {
        if (e.which == 13) {
            $('#searchBtn').click();
        }
    });

    // A simple function for displaying error messages in the app. 
    function showErrorMsg(msg) {
        $('.resultsPanel').html('<span class="errorMsg">' + msg + '</span>');
    }

    // A simple function for clearing the map and results panel. 
    function clearMap() {
        dataLayer.clear();
        infobox.setOptions({
            visible: false
        });
        $('.resultsPanel').html('');
    }

    // A function that searches for nearby locations against the data source. 
    function findNearbyLocations(location) {
        // Create the URL request to do a nearby search against the data source. 
        // Have it search within a radius of 20KM and return the top 10 results. 
        var request = dataSourceUrl + '?spatialFilter=nearby(' + location.latitude +
            ',' + location.longitude + ',20)&$format=json&$top=1&key=' + sessionKey;

        $.ajax({
            url: request,
            dataType: 'jsonp',
            jsonp: 'jsonp',
            success: function(data) {
                var results = data.d.results;

                if (results.length > 0) {
                    // Create an array to store the coordinates of all the location results. 
                    var locs = [];

                    // Create an array to store the HTML used to generate the list of results. 
                    // By using an array to concatenate strings is much more efficient than using +. 
                    var listItems = [];

                    //Loop through results and add to map 
                    for (var i = 0; i < results.length; i++) {
                        var loc = new Microsoft.Maps.Location(results[i].Latitude, results[i].Longitude);

                        // Create pushpin 
                        var pin = new Microsoft.Maps.Pushpin(loc, {
                            icon: $('#redpin').val(),
                            text: (i + 1) + ''
                        });

                        // Store the location result info as a property of the pushpin so we can use it later. 
                        pin.Metadata = results[i];

                        // Add a click event to the pushpin to display an infobox. 
                        Microsoft.Maps.Events.addHandler(pin, 'click', function(e) {
                            displayInfobox(e.target);
                        });

                        // Add the pushpin to the map. 
                        dataLayer.push(pin);

                        // Add the location coordinate to the array of locations 
                        locs.push(loc);

                        // Create the HTML for a single list item for the result.                         
                        listItems.push('<table class="listItem"><tr><td rowspan="3"><span>', (i + 1), '.</span></td>');

                        // Store the result ID as a property of the name. This will allow us to relate the list item to the pushpin on the map. 
                        listItems.push('<td><a class="title" href="javascript:void(0);" rel="', results[i].ID, '">', results[i].Name, '</a></td>');


                        listItems.push('<tr><td colspan="2" class="listItem-address">', results[i].AddressLine, '<br/>', results[i].Locality, ', ');
                        listItems.push(results[i].AdminDistrict, '<br/>', results[i].PostalCode, '</td></tr>');



                        listItems.push('</table>');
                    }

                    // Use the array of locations from the results to set the map view to show all locations. 
                    if (locs.length > 1) {
                        map.setView({
                            bounds: Microsoft.Maps.LocationRect.fromLocations(locs),
                            padding: 80
                        });
                    } else {
                        map.setView({
                            center: locs[0],
                            zoom: 15
                        });
                    }

                    // Add the list items to the results panel. 
                    $('.resultsPanel').html(listItems.join(''));

                    // Add a click event to the title of each list item. 
                    $('.title').click(function() {
                        // Get the ID of the selected location 
                        var id = $(this).attr('rel');

                        //Loop through all the pins in the data layer and find the pushpin for the location. 
                        var pin;
                        for (var i = 0; i < dataLayer.getLength(); i++) {
                            pin = dataLayer.get(i);

                            if (pin.Metadata.ID != id) {
                                pin = null;
                            } else {
                                break;
                            }
                        }

                        // If a pin is found with a matching ID, then center the map on it and show it's infobox. 
                        if (pin) {
                            // Offset the centering to account for the infobox. 
                            map.setView({
                                center: pin.getLocation(),
                                centerOffset: new Microsoft.Maps.Point(-70, 150),
                                zoom: 17
                            });
                            displayInfobox(pin);
                        }
                    });
                }
            },
            error: function(e) {
                showErrorMsg(e.statusText);
            }
        });
    }

    // Takes a pushpin and generates the content for the infobox from the Metadata and displays the infobox. 
    function displayInfobox(pin) {
        infobox.setLocation(pin.getLocation());

        var desc = ['<table>'];

        desc.push('<tr><td colspan="2">', pin.Metadata.AddressLine, '<br/>', pin.Metadata.Locality, ', ');
        desc.push(pin.Metadata.AdminDistrict, '<br/>', pin.Metadata.PostalCode, '</td></tr>');


        desc.push('<tr><td><b>Store Type:</b></td><td>', pin.Metadata.AddressLine, '</td></tr>');


        desc.push('</table>');

        infobox.setOptions({
            visible: true,
            title: pin.Metadata.Name,
            description: desc.join('')
        });
    }


});