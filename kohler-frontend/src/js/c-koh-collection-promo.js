/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};

axKOH.main = axKOH.main || {};

axKOH.main.promoCollection = (function () {
	var componentClass = "c-koh-collection-promo";

	var initCarousels = function ($components) {
		if ( $components ) {
			$components.each( function(componentIndex) {
				var $component = $(this);

				var $carousels = $component.find(".koh-collection-carousel");
				$carousels.each( function(carouselIndex) {
					var $carousel = $(this);

					// Adding a div to the carousel so we can group the controls
					var $carouselControls = $('<div class="koh-carousel-controls"></div>');
					$carousel.prepend($carouselControls);

					var $movePrevious = $('<button class="koh-carousel-control previous"><span class="icon"></span><span class="label">Previous</span></button>');
					var $moveNext = $('<button class="koh-carousel-control next"><span class="icon"></span><span class="label">Next</span></button>');
					$carouselControls.append($movePrevious);
					$carouselControls.append($moveNext);

					// Find the slides
					var $carouselSlideCollection = $carousel.find(".koh-collection-image-set");

					// For each slide collection, initialize the slides
					$carouselSlideCollection.each( function() {
						initSlides( $(this), componentIndex+carouselIndex );
					});

					// Initialize the carousel
					$carouselSlideCollection.slick({
						autoplay: false,
						autoplaySpeed: 5000,
						adaptiveHeight: false,
						dots: true,
						dotsClass: 'koh-carousel-indicators',
						arrows: true,
						appendArrows: $carouselControls,
						appendDots: $carouselControls,
						prevArrow: $movePrevious,
						nextArrow: $moveNext,
						pauseOnHover: true,
						pauseOnDotsHover: true,
						draggable: false // has to be not draggable to allow the zoom to come through
					});	// end init slick
				}); // end $carousels.each
			}); // end $components.each
		}
	};

	var initSlides = function ( $slideCollection, containerID ) {
		var $slides = $slideCollection.children(".koh-product-image");
	};	

	/* Defines functions exposed through manager object */
		return {
				initialize: function () {
			var $components = $("."+componentClass);

			/* Initialize Carousels */
			initCarousels($components);

				}
		};
})();

$(function () {
		$(document).ready(function() {
				// executes when HTML-Document is loaded and DOM is ready
				axKOH.main.promoCollection.initialize();
		});
});
