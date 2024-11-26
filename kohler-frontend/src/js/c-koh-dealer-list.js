/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

// Javascript Namespacing setup
var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};
axKOH.main.componentManager = axKOH.main.componentManager || {};

// Replace "componentName" with something representing which component this is
axKOH.main.componentManager.dealerList = (function () {
	var componentClass = "c-koh-dealer-list";

	// Main init function; contains the main loop through component instances
	var initComponents = function() {
		// Find all instances of the component on the page
		var $components = $("."+componentClass);

		// For each component instance, initialize it
		$components.each( function(componentIndex) {
			var $component = $(this); // the single component instance we're currently operating on

			var $dealerForm 			= $component.find("form");
			var $dealerSubmit 		= $component.find("input[type=submit]");

			var $dealerFilters 		= $component.find(".koh-dealer-filters");
			var $filterToggle 		= $component.find(".koh-dealer-filter").children("input");

			var $countrySelector 	= $component.find(".country").children("select");
			var $stateSelector 		= $component.find(".state").children("select");
			var $citySelector 		= $component.find(".city").children("select");

			$countrySelector.change(function(){
				var selectedCountry = $countrySelector.find("option:selected").text();
				$stateSelector.removeAttr("disabled").parent().removeClass('disabled');
				$citySelector.attr("disabled");
				$dealerSubmit.attr("disabled","disabled");
				$citySelector.html('').parent().addClass('disabled');
				$.get( "/api/dealerlookup/"+selectedCountry, function( data ) {
					$stateSelector.html(data);
				});
			});

			$stateSelector.change(function(){
				var selectedCountry = $countrySelector.find("option:selected").text();
				var selectedState = $stateSelector.find("option:selected").text();
				$citySelector.removeAttr("disabled").parent().removeClass('disabled');
				$.get( "/api/dealerlookup/"+selectedCountry+"/"+selectedState, function( data ) {
					$citySelector.html(data);
				});
			});

			$citySelector.change(function(){
				$dealerSubmit.removeAttr("disabled");
			});

			if(document.location.search.length) {
				$stateSelector.removeAttr("disabled").parent().removeClass('disabled');
			  $citySelector.removeAttr("disabled").parent().removeClass('disabled');
				$dealerFilters.show();
			};

			$filterToggle.change(function(){
				$dealerForm.submit();
			});

		});
	};

	/* Defines functions exposed through manager object */
		return {
				initialize: function () {
			initComponents();
				},
		onLoad: function () {
			// call whatever needs to happen on load for all component instances here
		}
		};
})();

$(function () {
		$(document).ready(function() {
				// executes when HTML-Document is loaded and DOM is ready
				axKOH.main.componentManager.dealerList.initialize();
		});

	$(window).load(function() {
		// executes after the window is fully loaded, including all images, etc.
		axKOH.main.componentManager.dealerList.onLoad();
	});
});
