/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

// Javascript Namespacing setup
var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};
axKOH.main.componentManager = axKOH.main.componentManager || {};

// Replace "componentName" with something representing which component this is
axKOH.main.componentManager.componentName = (function () {
	var componentClass = "c-koh-simple-content";

	// Main init function; contains the main loop through component instances
	var initSnippetVideos = function ($components) {
		if ( $components ) {
			$components.each( function(componentIndex) {
				var $component = $(this);

				var $videoLinks = $component.find(".v-koh-video > a");

				$videoLinks.each( function(videoIndex) {
					var $videoLink = $(this);
					var videoURL = $videoLink.attr("href");
					var apiURL = $videoLink.data("api");

					if ( videoURL ) {
						// Add divs to be video modals
						var $videoPanel = $('<div class="koh-video-panel"><iframe width="560" height="315" src="'+videoURL+'" frameborder="0" allowfullscreen></iframe></div>');
						$videoPanel.insertAfter($videoLink);

						var remodalID = "Video"+componentIndex+videoIndex;
						initAsModal( $videoLink, $videoPanel, remodalID );
					}
					else {
						$videoLink.click( function(e) {
							e.preventDefault();
							e.stopPropagation();
						});
					}

					if ( apiURL ) {
						$.get( apiURL, function( data ) {
							if ( data && data.items && data.items[0] && data.items[0].snippet ) {
								var videoTitle = data.items[0].snippet.title;
								if ( videoTitle ) {
									$videoLink.children(".koh-video-title").text( videoTitle );
								}
							}
						});
					}
				});
			});
		}
	};

	/* Defines functions exposed through manager object */
		return {
			initialize: function () {
				var $components = $("."+componentClass);
				initSnippetVideos($components);
			}
		};
})();

$(function () {
		$(document).ready(function() {
				// executes when HTML-Document is loaded and DOM is ready
				axKOH.main.productDetailsManager.initialize();
		});
});
