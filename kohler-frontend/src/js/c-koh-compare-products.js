/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

// Javascript Namespacing setup
var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};
axKOH.main.componentManager = axKOH.main.componentManager || {};

// Replace "componentName" with something representing which component this is
axKOH.main.componentManager.compareProducts = (function () {
	var componentClass = "c-koh-compare-products";

	// Main init function; contains the main loop through component instances
	var initComponents = function() {
		// Find all instances of the component on the page
		var $components = $("."+componentClass);

		// For each component instance, initialize it
		$components.each( function(componentIndex) {
			var $component = $(this); // the single component instance we're currently operating on

			// Set up tooltips on swatches
			var $swatches = $component.find(".swatch");
			$swatches.each( function(abbrIndex) {
				var $swatch = $(this);
				$swatch.qtip({
					content: { attr: 'alt' },
					position: {
						my: 'bottom center',
						at: 'top center'
					},
					style: {
						classes: 'koh-qtip-tooltip koh-product-search-tooltip qtip-bootstrap',
						tip: {
							mimic: 'center',
							width: 30,
							height: 15
						}
					}
				});
			});

		});
	};


	// Back to top button
	var initBackTopButton = function () {
		//alert("Copy Roger 1");
		var topButton = $(".koh-search-top button");
		if ($(topButton).length ) {
			var topTarget = $(".c-koh-search-top:first");
			$(topButton).click(function(e) {
				e.preventDefault;
				if ($(topTarget).length) {
					$('html, body').animate({scrollTop: $(topTarget).offset().top -80 }, 250);
				}
			});
		}
	};

	// Back to top fadein
	var initBackTopDistance = function () {
		//alert("Copy Roger 2");
		var topButton = $(".koh-search-top button");
		if ($(topButton).length ) {
			var topDistance = $(".c-koh-search-top:first").offset().top;
			$(document).scroll(function() {
				var y = $(this).scrollTop();
				if (y > topDistance) {
					$(topButton).fadeIn();
				} else {
					$(topButton).fadeOut();
				}
			});
		}
	};


	/* Defines functions exposed through manager object */
		return {
				initialize: function () {
			initComponents();

			/* Initialize Back to Top Button */
			initBackTopButton();

			/* Show/Hide Back to Top Button */
			initBackTopDistance();

				},
		onLoad: function () {
			// call whatever needs to happen on load for all component instances here
		}
		};
})();

$(function () {
		$(document).ready(function() {
				// executes when HTML-Document is loaded and DOM is ready
				axKOH.main.componentManager.compareProducts.initialize();
		});

	$(window).load(function() {
		// executes after the window is fully loaded, including all images, etc.
		axKOH.main.componentManager.compareProducts.onLoad();
	});
});
