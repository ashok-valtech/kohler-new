/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

// Javascript Namespacing setup
var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};
axKOH.main.componentManager = axKOH.main.componentManager || {};

// Replace "componentName" with something representing which component this is
axKOH.main.componentManager.pressRelease = (function () {
	var componentClass = "c-koh-press-release";

	// Main init function; contains the main loop through component instances
	var initComponents = function() {
		// Find all instances of the component on the page
		var $components = $("."+componentClass);

		// For each component instance, initialize it
		$components.each( function(componentIndex) {
			var $component = $(this); // the single component instance we're currently operating on

			var $bookmarkButton = $component.find(".koh-release-bookmark .koh-release-button");

			$bookmarkButton.each(function(){
			  $(this).qtip({
			    content: {
			      text: $(this).next('.koh-release-popover')
			    },
			    position: {
			      my: 'top right',
			      at: 'center center',
			      viewport: true
			    },
			    show: { event: 'click' },
			    hide: { event: 'unfocus', fixed: true },
			    style: {
			      classes: 'koh-qtip-popover koh-release-popover qtip-light qtip-shadow',
			      tip: false
			    }
			  });
			});

			var $productShare = $component.find(".koh-release-share");
			var $productShareButton = $productShare.children(".koh-release-button");
			var $productSharePanel = $productShare.children(".koh-release-popover");

			if ( axKOH && axKOH.utils && axKOH.utils.socialShare ) {
			  axKOH.utils.socialShare.initAsTooltip( $productShare, $productShareButton, $productSharePanel );
			}

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
				axKOH.main.componentManager.pressRelease.initialize();
		});

	$(window).load(function() {
		// executes after the window is fully loaded, including all images, etc.
		axKOH.main.componentManager.pressRelease.onLoad();
	});
});
