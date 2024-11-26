/*
* Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

/* Getting checked filters */
function getFilterInputs() {
    var values = '';
    $('#myDiv .source:checked').each(function () {
        if (values != '') {
            values += ',' + $(this).val();
        } else {
            values += $(this).val();
        }
    });
    return values; //Returning in comma separated format
}

/* On change function for filters */
$(document).on('change', '#myDiv .source', function () {
	$("#koh-fifthFilterSeeAllCountry").prop("checked", false);
	$('#seeCountryProject').remove();
    var countries = $('#country').find('option');
    var taxonomyInDetailPage = '';
    for (var i = 0; i <= countries.length; i++) {
        if (countries.eq(i).attr('selected') === 'selected') {
            taxonomyInDetailPage = countries.eq(i).val(); //Getting selected country or taxonomy
        }
    }
    ajaxForFilter(taxonomyInDetailPage);
});
$(document).on('change', '#koh-fifthFilterSeeAllCountry', function () {
	$(".source").prop("checked", false);
    var countries = $('#country').find('option');
    var taxonomyInDetailPage = '';
    for (var i = 0; i <= countries.length; i++) {
        if (countries.eq(i).attr('selected') === 'selected') {
            taxonomyInDetailPage = countries.eq(i).val(); //Getting selected country or taxonomy
        }
    }
    ajaxForFilter(taxonomyInDetailPage);
});

/* Ajax call for filter change */
function ajaxForFilter(isTaxonomyKey) {

    var getValues = getFilterInputs();
    if(getValues == '') {
    	$('#koh-fifthFilterSeeAllCountry').prop("checked", true);
    }
    var globalprojectLazyLink = $('#globalprojectLazyLink').val();
    $.ajax({
        url: globalprojectLazyLink,
        cache: !1,
        data: { 'firstCheck': getValues, 'getTaxonomyVal': isTaxonomyKey },
        success: function (data) {
            console.log('json data for list page--------', data);

            if (data.success == true) {
                var globalProjectListSection = $("#globalProjectListSection");
                var checkTaxonomy = data.taxonomy;

                if (checkTaxonomy) {
                    var globalProjectmainList = $("#globalProjectmainList");
                    withTaxonomyListing(data, globalProjectmainList)
                } else {
                    withOutTaxonomyListing(data, globalProjectListSection)
                }
            }
        }
    })
}

/* On click function for taxonomy list page */
$(document).on('click', '.seeAllCountry', function () {

    var getTaxonomyVal = $(this).data('taxonomy');
    var getValues = getFilterInputs();
    if(getValues == '') {
    	if($('#koh-fifthFilterSeeAllCountry').prop("checked") == true){
    		$('#koh-fifthFilterSeeAllCountry').prop('disabled', false);
        } else {
        	$('#koh-fifthFilterSeeAllCountry').prop('disabled', true);
        }
    } else {
    	$('#koh-fifthFilterSeeAllCountry').prop('disabled', false);
    }
    var globalprojectLazyLink = $('#globalprojectLazyLink').val();

    // Ajax call
    $.ajax({
        url: globalprojectLazyLink,
        cache: !1,
        data: { 'firstCheck': getValues, 'getTaxonomyVal': getTaxonomyVal },
        success: function (data) {

            console.log('json data for List Page--------', data);

            if (data.success == true) {
            	
                var globalProjectmainList = $("#globalProjectmainList");

                $('#globalProjectCountryListing').removeClass('hide');
                $("#globalProjectListSection").addClass('hide');

                var countries = $('#country').find('option')
                for (var i = 0; i <= countries.length; i++) {
                    if (countries.eq(i).val() === getTaxonomyVal) {
                        countries.eq(i).attr('selected', 'selected')
                    }
                }
                withTaxonomyListing(data, globalProjectmainList); // Function for listing projects with taxonomy
            }
        }
    })
});

/* Filter function for taxonomy based */
function withTaxonomyListing(data, projectOuterDiv) {

    projectOuterDiv.html('');
    $('#globalProjectListSection').addClass('hide');

    var countryName = data.taxonomy;
    var requiredFilters = data.availableCountryFilterList;
    var projectCounries = data.newCountryMap;

    Object.size = function (obj) {
        var size = 0, key;
        for (key in obj) {
            if (obj.hasOwnProperty(key)) size++;
        }
        return size;
    };

    // Get the size of an object
    var size = Object.size(projectCounries);
    $('#myDiv input.source').prop('disabled', false);
    var allAvailableFilters = [];
    $('div#myDiv input[type=checkbox]').each(function() {
		allAvailableFilters.push($(this).val());
	});
    
    
    for (var i = 0; i < allAvailableFilters.length; i++) {
        if (jQuery.inArray(allAvailableFilters[i], requiredFilters) === -1) {
            $('#koh-fifthFilter' + allAvailableFilters[i]).prop('disabled', true).prop("checked", false);
        }
    }
    if (size > 0) {
    	var selectedVal = $('#country').val();
        $('#country').find('option').remove();
        for (var k in projectCounries) {
            $('#country').append($('<option>', {
                value: k,
                text: projectCounries[k]
            }));
        }
    }

    $('#country option').removeAttr('selected').filter('[value=' + selectedVal + ']').attr('selected', 'selected').prop('selected', true);
    $('#globalProjectCountry').find('.koh-project-country-title h3').text(countryName);

    var html = '', i, itemTitle, itemImage, itemPlace, itemHstLink, itemImageAlt, itemImageTitle;
    var items = data.globalprojectJson;

    for (i = 0; i < items.length; i++) {
        itemTitle = items[i].title;
        itemImage = items[i].image;
        itemPlace = items[i].place;
        itemHstLink = items[i].hstLink;
        itemImageAlt = items[i].imageAlt;
        itemImageTitle = items[i].imageTitle;
        html += '<div class="koh-project-display-items col-lg-4 col-md-4 col-sm-4 col-xs-12">';
        html += '<a href="' + itemHstLink + '">';
        html += '<div class="koh-project-display-items-inner">';
        if ((itemImageTitle !== undefined) && (itemImageTitle !== "")) {
        	html += '<img class="featured-box-image" src="' + itemImage + '"alt="' + itemImageAlt + '" title="' + itemImageTitle + '">';
        } else {
        	html += '<img class="featured-box-image" src="' + itemImage + '"alt="' + itemImageAlt + '">';
        }
        html += '<div class="koh-project-display-content">';
        html += '<p>' + itemTitle + '</p><span>' + itemPlace + '</span></div>';
        html += '</div></a></div>';
    }
    projectOuterDiv.append(html);
}

/* Filter function for all countries */
function withOutTaxonomyListing(data, projectOuterDiv) {

    var html = '', k, country;
    var projects = data.globalprojectListJson;
    var projectCounries = data.allcountriesInProjects;
    var seeAllProjects = data.seeAllProjectLink;

    $('#country').find('option').remove();
    $('#country').append($('<option>', {
        value: '',
        text: 'Select'
    }));

    for (k in projectCounries) {
        $('#country').append($('<option>', {
            value: k,
            text: projectCounries[k]
        }));
    }

    $('#globalProjectListSection').html('');
    for (country in projects) {
        html += '<div class="koh-project-display">';
        html += '<div class="koh-project-country-title"><h3 class="col-lg-6 col-md-6 col-sm-6 col-xs-12">' + projectCounries[country] + '</h3>';
        if (seeAllProjects.hasOwnProperty(country)) {
            html += '<div class="koh-project-country-more-link col-lg-6 col-md-6 col-sm-6 col-xs-12"><span class="seeAllCountry" data-taxonomy="' + country + '">See All In ' + projectCounries[country] + '</span></div>';
        }
        html += '</div>'
        if (projects.hasOwnProperty(country)) {
            $(projects[country]).each(function (i, value) {
                html += '<div class="koh-project-display-items col-lg-4 col-md-4 col-sm-4 col-xs-12">';
                html += '<a href="' + value.hstLink + '">'
                html += '<div class="koh-project-display-items-inner">';
                html += '<img class="featured-box-image" src="' + value.image + '" alt="' + value.title + '">';
                html += '<div class="koh-project-display-content"><p>' + value.title + '</p><span>' + value.place + '</span></div>';
                html += '</div></a>';
                html += '</div>';
            });
        }
        html += '</div>';
    }
    projectOuterDiv.append(html);
    console.log("countryHtml........" + globalProjectCountry);
    $("#globalProjectCountryListing").addClass('hide');
    $("#globalProjectListSection").removeClass('hide');
}

/* On change function for country dropdown */
$(document).on('change', '#country', function (e) {
    var selectedCountry = $(this).val();
    $('#country option').removeAttr('selected').filter('[value=' + selectedCountry + ']').attr('selected', 'selected').prop('selected', true);
    $('#seeCountryProject').remove();
    ajaxForFilter(selectedCountry);
})

$(document).on('ready', function() {
	var baseUrl = window.location.pathname;
	var currentTile = document.title;
	window.history.pushState({}, currentTile, baseUrl);
})