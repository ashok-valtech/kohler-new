/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};

axKOH.main = axKOH.main || {};
axKOH.main.componentManager = axKOH.main.componentManager || {};

axKOH.main.componentManager.pressList = (function () {
	var componentClass = "c-koh-press-list";

	var initComponents = function() {
		// Find all instances of the component on the page
		var $components = $("."+componentClass);

		// For each component instance, initialize it
		$components.each( function(componentIndex) {
			var $component = $(this);

			var $tabTitle = $component.find(".koh-press-title");
			var $tabControls = $component.find(".koh-tab-controls").first();
			var $tabSet = $tabControls.children(".koh-tab-set").first();
			var $tabs = $tabSet.children("li");
			var $tabBodies = $component.find(".koh-tab-content").first().find(".koh-tab-contents");

			if ( $tabs.length !== $tabBodies.length ) {
				axKOH.utils.debugLog("Error: Tab count does not match tab body count");
			}
			else {
				$tabBodies.each( function(tabIndex) {
					var $tabBody = $(this);
					var $tab = $tabs.eq(tabIndex); // find the corresponding tab in the tab set
					var tabID = "tab"+componentIndex+tabIndex;

					// Add a unique ID to the tab body
					$tabBody.attr("id",tabID);

					// Add a reference to the same ID to the corresponding tab and set up onclick
					if ( $tab ) {
						$tab.attr("data-target-id",tabID);
						$tab.click( { tabs: $tabs, tabBodies: $tabBodies, target: $tabBody, title: $tabTitle }, function(e) {
							e.data.tabs.not($(this)).removeClass('open');
							$(this).addClass('open');

							e.data.tabBodies.not(e.data.target).removeClass('open');
							e.data.target.addClass('open');

							var title = $(this).data('title');
							e.data.title.html(title);
						});
					}

				});
			}

			// Load first title
			var $firstMenu = $tabSet.find('li.open');
			var title = $firstMenu.data('title');
			$tabTitle.html(title);

			var $menuButton = $tabControls.children(".koh-tab-select").first();
			$menuButton.click( { list: $tabSet }, function(e) {
				var $button = $(this);
				var $list = e.data.list;

				if ( $button.hasClass("open") ) {
					$button.removeClass("open");
					$list.removeClass("open");
				}
				else {
					$button.addClass("open");
					$list.addClass("open");
				}
			});

			$(document).click( { target: $menuButton, list: $tabSet }, function(e) {
				var $button = e.data.target;
				var $list = e.data.list;

				if( !$(e.target).closest($button).length && !$(e.target).is($button) ) {
					if ( $button.hasClass("open") ) {
						$button.removeClass("open");
						$list.removeClass("open");
					}
				}
			});

		});
	};

	/* Defines functions exposed through manager object */
	return {
		initialize: function () {
			initComponents();
		},
	};
})();

$(function () {
	$(document).ready(function() {
			// executes when HTML-Document is loaded and DOM is ready
			axKOH.main.componentManager.pressList.initialize();
	});
});
