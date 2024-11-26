/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};

var noUiSlider = noUiSlider || null;

axKOH.main.productSearch = (function () {
	var componentClass = "c-koh-product-search";

	// Optional search navigation
	var initSearchNav = function () {

		var $searchControls = $(".koh-search-controls").first();
		var $searchSet = $searchControls.children(".koh-search-set").first();

		var $menuButton = $searchControls.children(".koh-search-select").first();
		$menuButton.click( { list: $searchSet }, function(e) {
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

		$(document).click( { target: $menuButton, list: $searchSet }, function(e) {
			var $button = e.data.target;
			var $list = e.data.list;

			if( !$(e.target).closest($button).length && !$(e.target).is($button) ) {
				if ( $button.hasClass("open") ) {
					$button.removeClass("open");
					$list.removeClass("open");
				}
			}
		});

	};

	// Filter expand/collapse
	var initFilterExpandCollapse = function ($components) {
		if ( $components ) {
			$components.each( function() {
				var $component = $(this);

				var filterGroupClass = ".koh-filter-group.koh-filters";
				var filterClass = ".koh-filter";

				var $filters = $component.find(filterGroupClass).children(filterClass);
				$filters.each( function() {
					var $filterName = $(this).children(".koh-filter-name").first();
					var $filterContent = $(this).children(".koh-filter-content").first();

					$filterName.click( { target: $filterContent }, function(e) {
						var $target = e.data.target;
						if ( $target.hasClass("open") ) {
							$(this).removeClass("open");
							$target.removeClass("open");
						}
						else {
							$(this).addClass("open");
							$target.addClass("open");
						}
					});
				});

				// Also, the main button needs one
				var $filterButton = $component.find(".koh-search-filters-title");
				var $filterContent = $component.find(".koh-search-filters-content");
				$filterButton.click( { target: $filterContent }, function(e) {
					var $target = e.data.target;
					if ( $target.hasClass("open") ) {
						$target.removeClass("open");
					}
					else {
						$target.addClass("open");
					}
				});
			});
		}
	};

	// Range filter sliders
	var initRangeSliders = function ($components) {
		if ( $components ) {
			$components.each( function() {
				var $component = $(this);
				var $rangeFilters = $component.find(".koh-filter.koh-filter-type-range");

				$rangeFilters.each( function() {
					var $rangeFilter = $(this);

					var $sliderContainer = $rangeFilter.find(".koh-range-filter").first();

					var $rangeLabels = $sliderContainer.find(".koh-range-slider-labels");
					var $rangeMinLabel = $sliderContainer.find(".koh-range-slider-labels > .koh-range-slider-min");
					var $rangeMaxLabel = $sliderContainer.find(".koh-range-slider-labels > .koh-range-slider-max");

					var $slider = $sliderContainer.find(".koh-range-slider").first();
					var sliderElement = $slider[0];

					var rangeLabelPrefix = $rangeLabels.data("label-prefix");
					var rangeLabelSuffix = $rangeLabels.data("label-suffix");
					var rangeCurrentMin = $rangeMinLabel.data("min-value");
					var rangeCurrentMax = $rangeMaxLabel.data("max-value");
					var rangeMin = $slider.data("range-min");
					var rangeMax = $slider.data("range-max");

					/* noUISlider does not like having rangeMin and rangeMax the same, so check for that */
					if ( noUiSlider && !isNaN(rangeMin) && !isNaN(rangeMax) && (rangeMin < rangeMax) ) {
						noUiSlider.create(sliderElement, {
							start: [rangeCurrentMin, rangeCurrentMax],
							connect: true,
							range: {
								'min': rangeMin,
								'max': rangeMax
							},
							format: {
								to: function ( value ) {
									return Math.round(value);
								},
								from: function ( value ) {
									return value;
								}
							}
						});

						sliderElement.noUiSlider.on('update', function(values, handleIndex){
							if ( handleIndex === 0 ) {
								$rangeMinLabel.data("min-value",values[handleIndex]);
								$rangeMinLabel.text(rangeLabelPrefix+values[handleIndex]+rangeLabelSuffix);
							}
							else if ( handleIndex === 1 ) {
								$rangeMaxLabel.data("max-value",values[handleIndex]);
								$rangeMaxLabel.text(rangeLabelPrefix+values[handleIndex]+rangeLabelSuffix);
							}
						});
					} // end checks before initializing slider
					else {
						// Let's hide the slider if it can't be initialized for some reason
						sliderElement.hide();
					}
				});
			});
		}
	};

	// Quick View
	var initQuickView = function ($components) {
		if ( $components ) {
			$components.each( function(componentIndex) {
				var $component = $(this);

				// Find the quick view blocks, which have the buttons and panels
				var $quickViewContainers = $component.find(".koh-product-quick-view");

				// For each quick view, make the panel modal and hook it to the button
				$quickViewContainers.each( function(quickViewIndex) {
					var $button = $(this).children(".koh-product-quick-view-button");
					var $panel = $(this).children(".koh-product-quick-view-panel");

					// Calculate the remodal ID value and hook things up
					var remodalID = "QuickView"+componentIndex+quickViewIndex;

					initAsModal( $button, $panel, remodalID );

					$panel.each( function() {
						initQuickViewData( $(this) );
					});
				});
			});
		}
	};

	// Tooltips
	var initTooltips = function ($components) {
		if ( $components ) {
			// Need to initialize tooltips in modals as well
			var $componentModals = $('.remodal-wrapper > div[data-koh-component="'+componentClass+'"]');

			// Merge lists of components and component-related modals
			var $targets = $components.add( $componentModals );

			// Loops through components and component-related modals
			$targets.each( function(componentIndex) {
				var $component = $(this);

				// Set up tooltips on abbreviations
				var $abbreviations = $component.find("abbr[title]");
				$abbreviations.each( function(abbrIndex) {
					var $abbr = $(this);
					$abbr.qtip({
						id: "abbr"+componentIndex+abbrIndex,
						content: { attr: 'title' },
						position: {
							my: 'bottom center',  // Position my top left...
							at: 'top center' // at the bottom right of...
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

				// Set up tooltips on color swatches
				var $colorSwatches = $component.find("button.koh-product-color, .koh-available-filters.v-koh-colors > li > a");
				$colorSwatches.each( function(swatchIndex) {
					var $colorSwatch = $(this);
					var $colorLabel = $colorSwatch.children(".label");

					$colorSwatch.qtip({
						id: "swatch"+componentIndex+swatchIndex,
						content: { text: $colorLabel.clone() },
						position: {
							my: 'top left',  // Position my top left...
							at: 'bottom left', // at the bottom right of...
							adjust: {
								y: 6
							}
						},
						style: {
							classes: 'koh-qtip-tooltip koh-product-search-tooltip qtip-bootstrap',
							tip: false
						}
					});
				});

				// Set up special facet tooltips
				var $facetsWithTooltips = $component.find(".koh-filter").has(".koh-filter-tooltips");
				$facetsWithTooltips.each( function(facetIndex) {
					var $facet = $(this);
					var $facetName = $facet.find(".koh-filter-name");
					var $facetTooltip = $facet.find(".koh-filter-tooltips");

					$facetName.append("<span class=\"koh-tooltip\"></span>");
					var $tooltipIcon = $facetName.children(".koh-tooltip");

					$tooltipIcon.qtip({
						id: "facetTooltip"+componentIndex+facetIndex,
						content: { text: $facetTooltip },
						show: 'click',
						hide: { event: 'click unfocus' },	// Hide on click anywhere
						position: {
							my: 'center left',  // Position my top left...
							at: 'center right', // at the bottom right of...
							adjust: {
								x: 6
							}
						},
						style: {
							classes: 'koh-qtip-tooltip koh-product-search-facet-tooltip qtip-bootstrap',
							tip: {
								mimic: 'center',
								width: 30,
								height: 15
							}
						}
					});

					// Need this to stop the tooltip icon from also triggering the open/close of the filter
					$tooltipIcon.click( function(e) {
						e.stopPropagation();
						e.preventDefault();
					});
				});
			});
		}
	};

	// Dropdowns
	var initDropdowns = function ($components) {
		if ( $components ) {
			$components.each( function() {
				var $component = $(this);

				var $options = $component.find(".koh-search-sorting");
				$options.each( function() {
					var $optionsButton = $(this).children(".koh-sort-by");
					var $optionsList = $(this).children(".koh-sort-options");

					$optionsButton.click( { list: $optionsList }, function(e) {
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
				});
			});
		}
	};

	// Cookie handling to support Simiilar Products
	var initSimilarProducts = function ($components) {
		if ( $components ) {
			$components.each( function() {
				var $component = $(this);

				var $productTiles = $component.find(".koh-product-tile");

				$productTiles.each( function() {
					var $productLink = $(this).find(".koh-product-tile-content > a");

					// On click, save the similar products id list from the data attribute into a cookie
					$productLink.on("click", function() {
						var similarProducts = $(this).data("similar");
						if ( similarProducts ) {
							var minutes = 5;
							var expires = new Date();
							expires.setTime(expires.getTime() + (minutes * 60 * 1000));
							document.cookie = "kohsimprod" + '=' + similarProducts + ';expires=' + expires.toUTCString() + ';path=/';
						}
					});
				});
			});
		}
	};

	/* Filtering/Sorting Functionality */
	var initSearchFacets = function ($components) {
		$components.each( function() {
			var $component = $(this);

			// Set up click events for Sorting
			var $sortButton = $component.find(".koh-search-sorting > .koh-sort-by");
			var $sortOptions = $component.find(".koh-search-sorting > .koh-sort-options");
			var $sortOptionItems = $sortOptions.children("li");
			$sortOptionItems.each( function() {
				var $sortOptionItem = $(this);
				$sortOptionItem.click( { button: $sortButton, collection: $sortOptions, choices: $sortOptionItems }, function(e) {
					// Save the selected sort
					setActiveSorting( e.data.button, e.data.choices, $(this) );
					// Close the drop down
					e.data.button.removeClass("open");
					e.data.collection.removeClass("open");
					// Display the new search results
					displayNewResults($component);
				});
			});

			// Set up click events for Categories
			var $categories = $component.find(".koh-search-filters .koh-filter-group.koh-category");
			$categories.each( function() {
				var $availableFilters = $categories.find(".koh-available-filters > li > a");
				var $selectedFilters = $categories.find(".koh-selected-filters");

				// Remove filter when selected filter is clicked
				var $selectedFilterItems = $selectedFilters.children("li");
				$selectedFilterItems.each( function() {
					var $filterItem = $(this);
					var $filterItemButton = $filterItem.children("button.koh-selected-filter");
					$filterItemButton.click( { filterCount: $selectedFilterItems.length, filterSet: $selectedFilters, filterItem: $filterItem }, function(e) {
						if ( e.data.filterCount <= 1 ) {
							e.data.filterSet.remove();
						}
						else {
							e.data.filterItem.remove();
						}
						displayNewResults($component);
					});
				});
			});

			var $showAllCats = $component.find(".koh-search-filters .koh-filter-group.koh-category .koh-filter-group-all-link");
			$showAllCats.click( function(e) {
				e.stopPropagation();
				e.preventDefault();
				displayNewResults($component);
			});

			// Set up click events for Filtering
			var $filters = $component.find(".koh-search-filters .koh-filter-group.koh-filters .koh-filter");
			$filters.each( function() {
				var $filter = $(this);
				var $selectedFilters = null;
				var $selectedFilterItems = null;
				var $availableFilters = null;

				if ( $filter.hasClass("koh-filter-type-checkbox") ) {
					var $checkbox = $filter.find(".koh-checkbox-filter > a");
					$checkbox.click( function(e) {
						e.stopPropagation();
						e.preventDefault();
						if ( $(this).hasClass("koh-checkbox-checked") ) {
							$(this).removeClass("koh-checkbox-checked").addClass("koh-checkbox-unchecked");
						}
						else {
							$(this).removeClass("koh-checkbox-unchecked").addClass("koh-checkbox-checked");
						}
						displayNewResults($component);
					});
				}
				else if ( $filter.hasClass("koh-filter-type-range") ) {
					// Add filter when slider changes
					var $sliderContainer = $filter.find(".koh-range-filter").first();
					var $rangeMinLabel = $sliderContainer.find(".koh-range-slider-labels > .koh-range-slider-min");
					var $rangeMaxLabel = $sliderContainer.find(".koh-range-slider-labels > .koh-range-slider-max");
					var $slider = $sliderContainer.find(".koh-range-slider");
					var sliderElement = $slider[0];
					if ( sliderElement.noUiSlider ) {
						sliderElement.noUiSlider.on('change', function(){
							var facetName = $sliderContainer.data("facet-name");
							var minValue = $rangeMinLabel.data("min-value");
							var maxValue = $rangeMaxLabel.data("max-value");
							var facetValue = (minValue && maxValue) ? (minValue + "-" + maxValue): "";
							addActiveFilter( $filter, facetName, facetValue, facetValue, "0" );
							displayNewResults($component);
						});
					}

					// Remove filter when selected filter is clicked
					$selectedFilters = $filter.find(".koh-selected-filters");
					$selectedFilterItems = $selectedFilters.children("li");
					$selectedFilterItems.each( function() {
						var $filterItem = $(this);
						var $filterItemButton = $filterItem.children("button.koh-selected-filter");
						$filterItemButton.click( { filterCount: $selectedFilterItems.length, filterSet: $selectedFilters, filterItem: $filterItem }, function(e) {
							if ( e.data.filterCount <= 1 ) {
								e.data.filterSet.remove();
							}
							else {
								e.data.filterItem.remove();
							}
							displayNewResults($component);
						});
					});
				}
				else if ( $filter.hasClass("koh-filter-type-colors") ) {
					$availableFilters = $filter.find(".koh-available-filters > li > a");
					$selectedFilters = $filter.find(".koh-selected-filters");

					// Add color filter
					$availableFilters.each( function() {
						var $filterLink = $(this);
						$($filterLink).on('click touchend', function(e) {
							e.stopPropagation();
							e.preventDefault();
							var facetName = $filterLink.data("facet-name");
							var facetValue = $filterLink.data("facet-value");
							var facetLabel = $filterLink.children("span.label").text();
							var facetCount = "0";
							addActiveFilter( $filter, facetName, facetValue, facetLabel, facetCount );
							displayNewResults($component);
						});
					});

					// Remove filter when selected filter is clicked
					$selectedFilters = $filter.find(".koh-selected-filters");
					$selectedFilterItems = $selectedFilters.children("li");
					$selectedFilterItems.each( function() {
						var $filterItem = $(this);
						var $filterItemButton = $filterItem.children("button.koh-selected-filter");
						$filterItemButton.click( { filterCount: $selectedFilterItems.length, filterSet: $selectedFilters, filterItem: $filterItem }, function(e) {
							if ( e.data.filterCount <= 1 ) {
								e.data.filterSet.remove();
							}
							else {
								e.data.filterItem.remove();
							}
							displayNewResults($component);
						});
					});
				}
				else {
					$availableFilters = $filter.find(".koh-available-filters > li > a");
					$selectedFilters = $filter.find(".koh-selected-filters");

					// Add list filter
					$availableFilters.each( function() {
						var $filterLink = $(this);
						$filterLink.click( function(e) {
							e.stopPropagation();
							e.preventDefault();
							var facetName = $filterLink.data("facet-name");
							var facetValue = $filterLink.data("facet-value");
							var facetLabel = $filterLink.children("span.name").first().text();
							var facetCount = $filterLink.children("span.count").first().text();
							addActiveFilter( $filter, facetName, facetValue, facetLabel, facetCount );
							displayNewResults($component);
						});
					});

					// Remove filter when selected filter is clicked
					$selectedFilterItems = $selectedFilters.children("li");
					$selectedFilterItems.each( function() {
						var $filterItem = $(this);
						var $filterItemButton = $filterItem.children("button.koh-selected-filter");
						$filterItemButton.click( { filterCount: $selectedFilterItems.length, filterSet: $selectedFilters, filterItem: $filterItem }, function(e) {
							if ( e.data.filterCount <= 1 ) {
								e.data.filterSet.remove();
							}
							else {
								e.data.filterItem.remove();
							}
							displayNewResults($component);
						});
					});
				}
			});

			var $clearAllFilters = $component.find(".koh-search-filters .koh-filter-group.koh-filters .koh-filter-group-all-link");
			$clearAllFilters.click( function(e) {
				e.stopPropagation();
				e.preventDefault();
				displayNewResults($component, false, true);
			});
		});
	};

	/* Filtering/Sorting - Get Functions */
	var getActiveSection = function ($component) {
		var sectionName = "";
		sectionName = ($component.find(".koh-data-attributes").data("koh-section") || "");
		return sectionName;
	};

	var getType = function ($component) {
		var type = "";
		type = ($component.find(".koh-data-attributes").data("koh-type") || "");
		return type;
	};

	var getSearchTerm = function ($component) {
		var searchTerm = "";
		searchTerm = ($component.find(".koh-data-attributes").data("koh-searchterm") || "");
		return searchTerm;
	};

	var getCurrentPage = function ($component) {
		var currentPage = "";
		currentPage = ($component.find(".koh-data-attributes").data("koh-currentpage") || "");
		return currentPage;
	};

	var getActiveCategory = function ($component) {
		var categoryName = "";
		var $activeCategory = $component.find(".koh-search-filters .koh-filter-group.koh-category .koh-selected-filter").first();
		categoryName = ($activeCategory.data("facet-value") || "");
		return categoryName;
	};

	var getActiveFilters = function ($component) {
		var facetNameField = "facet-name";
		var facetValueField = "facet-value";

		var activeFilters = {};

		var $filters = $component.find(".koh-search-filters .koh-filter-group.koh-filters .koh-filter");
		$filters.each( function() {
			var $filter = $(this);
			var $selectedFilters = null;
			var facetName = "";
			var facetValue = "";

			if ( $filter.hasClass("koh-filter-type-checkbox") ) {
				var $checkbox = $filter.find(".koh-checkbox-filter > a");
				if ( $checkbox.hasClass("koh-checkbox-checked") ) {
					facetName = $checkbox.data(facetNameField);
					facetValue = $checkbox.data(facetValueField);
					if ( facetName && facetValue ) {
						activeFilters[facetName] = facetValue;
					}
				}
			}
			else if ( $filter.hasClass("koh-filter-type-range") ) {
				$selectedFilters = $filter.find(".koh-selected-filter");
				$selectedFilters.each( function() {
					facetName = $(this).data(facetNameField);
					facetValue = $(this).data(facetValueField);
					if ( facetName && facetValue ) {
						var values = facetValue.split(/-/);

						activeFilters[facetName] = activeFilters[facetName] || {};
						activeFilters[facetName].Min = values[0];
						activeFilters[facetName].Max = values[1];
					}
				});
			}
			else if ( $filter.hasClass("koh-filter-type-colors") ) {
				$selectedFilters = $filter.find(".koh-selected-filter");
				$selectedFilters.each( function(index) {
					facetName = $(this).data(facetNameField);
					facetValue = $(this).data(facetValueField);
					if ( facetName && facetValue ) {
						activeFilters[facetName] = activeFilters[facetName] || {};
						activeFilters[facetName][index] = facetValue;
					}
				});
			}
			else {
				$selectedFilters = $filter.find(".koh-selected-filter");
				$selectedFilters.each( function(index) {
					facetName = $(this).data(facetNameField);
					facetValue = $(this).data(facetValueField);
					if ( facetName && facetValue ) {
						activeFilters[facetName] = activeFilters[facetName] || {};
						activeFilters[facetName][index] = facetValue;
					}
				});
			}
		});

		return activeFilters;
	};

	var getUrlfromActiveFilters = function ($component) {

		var url="";
		var activeFilters = getActiveFilters($component);
		$.each( activeFilters, function(facetName, facetValues) {
			if ( facetName && facetValues ) {
				if ( typeof facetValues === 'object' ) {
					$.each( facetValues, function(index, facetValue) {
						// if the key is Min or Max, treat as range slider
						if ( index === "Min" || index === "Max" ) {
							var paramName = facetName + index;
							queryParams[paramName] = facetValue;
							queryParamCount = queryParamCount + 1;
						}
						// otherwise it's a normal filter
						else {
							url = url + "/" + facetName + "/" + facetValue;
						}
					});
				}
				else {
					url = url + "/" + facetName + "/" + facetValues;
				}
			}
		});

		return url;
	}

	var getActiveSorting = function ($component) {
		var sortOptions = {
			orderBy: "",
			direction: "",
			labels: {
				orderBy: "orderBy",
				direction: "sort"
			}
		};

		var $sortOptions = $component.find(".koh-search-sorting > .koh-sort-options");
		sortOptions.labels.orderBy = ($sortOptions.data("sort-orderby-label") || sortOptions.labels.orderBy);
		sortOptions.labels.direction = ($sortOptions.data("sort-direction-label") || sortOptions.labels.direction);

		var $activeSort = $sortOptions.find(".koh-sort-selected > span").first();
		sortOptions.orderBy = $activeSort.data("sort-orderby");
		sortOptions.direction = $activeSort.data("sort-direction");

		return sortOptions;
	};

	var setActiveSorting = function ($sortButton, $sortOptions, $targetOption) {
		$sortButton.text( $targetOption.children("span").text() );
		$sortOptions.removeClass("koh-sort-selected");
		$targetOption.addClass("koh-sort-selected");
	};

	var addActiveCategory = function ($filter, facetName, facetValue, facetLabel, facetCount) {
		var $filterList = $filter.children(".koh-selected-filters");
		if ( !$filterList || !$filterList.length ) {
			$filterList = $('<ul class="koh-selected-filters"></ul>');
			$filterList.insertBefore( $filter.children(".koh-available-filters") );
		}
		else {
			$filterList.empty();
		}

		var $filterItemButton = $('<button class="koh-selected-filter"></button>');
		$filterItemButton.data("facet-name",facetName);
		$filterItemButton.data("facet-value",facetValue);
		$filterItemButton.append($('<span class="remove">Remove Filter</span>'));
		$filterItemButton.append($('<span class="name">'+facetLabel+'</span>'));
		$filterItemButton.append($('<span class="count">'+facetCount+'</span>'));

		$filterList.append( $("<li></li>").wrapInner($filterItemButton) );
	};

	var addActiveFilter = function ($filter, facetName, facetValue, facetLabel, facetCount) {
		var $filterList = $filter.children(".koh-selected-filters");
		var $filterItemButton = $('<button class="koh-selected-filter"></button>');

		if ( $filter.hasClass("koh-filter-type-checkbox") ) {
			$(this).removeClass("koh-checkbox-unchecked").addClass("koh-checkbox-checked");
		}
		else if ( $filter.hasClass("koh-filter-type-range") ) {
			if ( !$filterList || !$filterList.length ) {
				$filterList = $('<ul class="koh-selected-filters"></ul>');
				$filterList.insertBefore( $filter.find(".koh-range-filter") );
			}

			$filterItemButton.data("facet-name",facetName);
			$filterItemButton.data("facet-value",facetValue);
			$filterItemButton.append($('<span class="remove">Remove Filter</span>'));
			$filterItemButton.append($('<span class="name">'+facetLabel+'</span>'));
			$filterItemButton.append($('<span class="count">'+facetCount+'</span>'));

			$filterList.append( $("<li></li>").wrapInner($filterItemButton) );
		}
		else { // Colors and List are the same for this at the moment
			if ( !$filterList || !$filterList.length ) {
				$filterList = $('<ul class="koh-selected-filters"></ul>');
				$filterList.insertBefore( $filter.find(".koh-available-filters") );
			}

			$filterItemButton.data("facet-name",facetName);
			$filterItemButton.data("facet-value",facetValue);
			$filterItemButton.append($('<span class="remove">Remove Filter</span>'));
			$filterItemButton.append($('<span class="name">'+facetLabel+'</span>'));
			$filterItemButton.append($('<span class="count">'+facetCount+'</span>'));

			$filterList.append( $("<li></li>").wrapInner($filterItemButton) );
		}
	};

	/* Filtering/Sorting - URL functions */
	var getURL = function ($component, excludeCategory, excludeFilters) {
		/* URLs will be in the form of results?type=product&search=shower&currentpage=1
		 &orderBy={name|price}&sort={asc|desc} */
		var url = "";
		var queryParams = {};
		var queryParamCount = 0;

		var urlRoot = $component.find(".koh-data-attributes").data("koh-url-root");
		url = urlRoot;

		/* If the category is excluded, then the filters and query string parameters and sort should also be excluded */
		if ( !excludeCategory ) {
			var categoryName = getActiveCategory($component);
			if ( categoryName ) {
				url = url + "/Category/" + categoryName;
			}

			if ( !excludeFilters ) {
				var urlFilters = getUrlfromActiveFilters($component);
				url = url + urlFilters;
			}

			var sortOptions = getActiveSorting($component);
			if ( sortOptions && sortOptions.orderBy && sortOptions.direction ) {
				queryParams[sortOptions.labels.orderBy] = sortOptions.orderBy;
				queryParamCount = queryParamCount + 1;

				queryParams[sortOptions.labels.direction] = sortOptions.direction;
				queryParamCount = queryParamCount + 1;
			}
		}

		var type = getType($component);
		if ( type ) {
			url = url + "?" +"type="+ type;
		}

		var searchTerm = getSearchTerm($component);
		if ( searchTerm ) {
			url = url + "&" +"search="+ searchTerm;
		}

		var currentPage = getCurrentPage($component);
		if ( currentPage ) {
			url = url + "&" +"currentpage="+ currentPage;
		}

		if ( queryParamCount > 0) {
			url = url + "&";

			$.each( queryParams, function(key, value) {
				url = url + key + "=" + value + "&";
			});

			// remove the trailing & if we added one
			url = url.replace(/&$/, "");
		}

		return url;
	};

	var displayNewResults = function ($component, excludeCategory, excludeFilters) {
		var currentUrl = window.location.href;

		var targetUrl = getURL($component, excludeCategory, excludeFilters);

		if ( targetUrl !== currentUrl ) {
			saveScrollPosition();
			window.location.href = targetUrl;
		}
	};

	/* Helper Functions */
	var initAsModal = function ( $button, $panel, remodalID ) {
		$panel.data( "remodal-id", remodalID );
		$panel.prepend('<button data-remodal-action="close" class="remodal-close"></button>');
		$panel.attr("data-koh-component",componentClass);
		$panel.addClass(componentClass+"-modal");

		var $remodalPanel = $panel.remodal({
			hashTracking: true,
			closeOnCancel: true,
			closeOnEscape: true,
			closeOnOutsideClick: true,
			modifier: ''
		});

		// Make the button all button-y and click-y
		$button.data( "remodal-target", remodalID );
		$button.click( { target: $remodalPanel }, function(e) {
			e.preventDefault();
			e.stopPropagation();
			var $target = e.data.target;
			$target.open();
		});
	};

	var initQuickViewData = function ( $panel ) {

		var $primaryBanner = $panel.children(".koh-product-image");

		var $productContent = $panel.children(".koh-product-content");
		var $productSKU = $productContent.children(".koh-product-sku");
		var $productPrices = $productContent.children(".koh-product-prices");

		var $colorCollections = $productContent.children(".koh-product-colors");
		$colorCollections.each( function() {
			var $colorCollection = $(this);

			var $selectedColorLabel = $colorCollection.children(".koh-selected-color").find(".value");

			var $colorVariants = $colorCollection.find(".koh-product-variant");
			$colorVariants.each( function() {
				var $colorVariant = $(this);

				var productData = {
					imageURL: $colorVariant.data("koh-image"),
					sku: $colorVariant.data("koh-sku"),
					prices: $colorVariant.data("koh-price"),
					colorName: $colorVariant.data("koh-color")
				};

				var productFields = {
					banner: $primaryBanner,
					sku: $productSKU,
					prices: $productPrices,
					color: $selectedColorLabel
				};

				$colorVariant.children(".koh-product-color").click( { variantSet: $colorVariants, variant: $colorVariant, product: productData, fields: productFields }, function(e) {
					var $variant = e.data.variant;
					var $variantSet = e.data.variantSet;

					var productData = e.data.product;
					var productFields = e.data.fields;

					productFields.banner.find("img").attr("src", productData.imageURL);
					productFields.sku.text(productData.sku);
					productFields.prices.find(".koh-current-price > .value").text(productData.prices.current);
					productFields.prices.find(".koh-previous-price > .value").text(productData.prices.previous);
					productFields.prices.find(".koh-discounted-price > .value").text(productData.prices.discounted);
					productFields.color.text(productData.colorName);

					$variantSet.removeClass("koh-selected-variant");
					$variant.addClass("koh-selected-variant");
				});
			});
		});
	};

	var loadScrollPosition = function () {
		var scrollPosition = sessionStorage.getItem("kohPageScroll");
		axKOH.utils.debugLog("Scroll set to "+scrollPosition);
		$(window).scrollTop(scrollPosition);
		sessionStorage.removeItem("kohPageScroll");
	};

	var saveScrollPosition = function () {
		var scrollPosition = $(window).scrollTop();
		sessionStorage.setItem("kohPageScroll", scrollPosition);
	};

	// Back to top button
	var initBackTopButton = function () {
		var topButton = $(".koh-search-top button");
		if ($(topButton).length ) {
			var topTarget = $(".c-koh-product-search:first");
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
		var topButton = $(".koh-search-top button");
		if ($(topButton).length ) {
			var topDistance = $(".c-koh-product-search:first").offset().top;
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

	// Category link generation
	var initCatgoryLinks = function ($components) {
		// determine current search parameters
		var $searchTerm = window.location.search.slice(1);
		// find category links
		var $categoryLinks = $(".koh-category .koh-available-filters li a");

		var $component = $($components[0]);
		var urlFilters = getUrlfromActiveFilters($component);

		$categoryLinks.each(function() {
			var $categoryName  		= $(this).data("facet-name");
			var $categoryValue 		= $(this).data("facet-value");
			var $categoryURL = '/results/' + $categoryName + '/' + $categoryValue + urlFilters + '?' + $searchTerm;
			$(this).attr('href',$categoryURL);
		});
	};

	// Spec Toggle
	var initSpecToggle = function () {
		var specToggle = $(".koh-spec-toggle");
		if ($(specToggle).length ) {
			specToggle.each( function() {
				$(this).click(function(e) {
					e.preventDefault;
					$(this).next().toggle();
					$(this).toggleClass('active');
					return false;
				});
			});
		}
	};

	// Trigger article videos
	var initArticleVideos = function ($components) {
		if ( $components ) {
			$components.each( function(componentIndex) {
				var $component = $(this);

				var $videoLinks = $component.find(".koh-product-article.v-koh-video a");

				$videoLinks.each( function(videoIndex) {
					var $videoLink = $(this);
					var videoURL = $videoLink.attr("href");
					var apiURL = $videoLink.data("api");

					if ( videoURL ) {
						// Add divs to be video modals
						var $videoPanel = $('<div class="koh-video-panel"><iframe width="560" height="315" src="'+videoURL+'" frameborder="0" allowfullscreen></iframe></div>');
						$videoPanel.insertAfter($videoLink);

						var remodalID = "Video"+componentIndex+videoIndex;
						var $videoPanel = $component.find(".koh-video-panel");
						initAsModal( $videoLink, $videoPanel, remodalID );
						$videoPanel.on('opened', function() {
							var $embeddedVideo = $(this).find("iframe");
							resizeResponsiveVideo($embeddedVideo);
						});
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

	var resizeResponsiveVideo = function ( $videoElement ) {
		var embedTags = "iframe, object, embed";
		var aspectRatio = $videoElement.data('aspectratio');
		var maxWidth = $videoElement.data('maxwidth');

		if ( !aspectRatio || !maxWidth ) {
			makeVideoResponsive( $videoElement );
			aspectRatio = $videoElement.data('aspectratio');
			maxWidth = $videoElement.data('maxwidth');
		}

		var $parent = $videoElement.closest(':not('+embedTags+')');

		var responsiveWidth = 320; // in case we can't find a parent, set to smallest possible screen width
		if ( $parent ) {
			responsiveWidth = $parent.width();
		}
		// Don't exceed the original width (i.e. don't scale up, only down)
		responsiveWidth = Math.min(responsiveWidth, maxWidth);

		$videoElement.width( responsiveWidth );
		$videoElement.height( responsiveWidth * aspectRatio );
	};

	/* Defines functions exposed through footerManager object */
	return {
		initialize: function () {
			var $components = $("."+componentClass);

			/* Initialize Search Navigation */
			initSearchNav($components);

			/* Initialize Filters Expand/Collapse */
			initFilterExpandCollapse($components);

			/* Initialize Range Sliders */
			initRangeSliders($components);

			/* Initialize Quick View */
			initQuickView($components);

			/* Initialize Tooltips */
			initTooltips($components);

			/* Initialize Dropdowns */
			initDropdowns($components);

			/* Set up all the filters and sorting and such */
			initSearchFacets($components);

			/* Set up Click Events for Product Tiles */
			initSimilarProducts($components);

			/* Initialize Back to Top Button */
			initBackTopButton($components);

			/* Show/Hide Back to Top Button */
			initBackTopDistance($components);

			// Category link generation
			initCatgoryLinks($components);

			// Spec Toggle
			initSpecToggle($components);

			// Article videos
			initArticleVideos($components);

		},
		onLoad: function () {
			loadScrollPosition();
		}
	};
})();

$(function () {
	$(document).ready(function() {
		// executes when HTML-Document is loaded and DOM is ready
		axKOH.main.productSearch.initialize();
	});

	$(window).on("load", function() {
		axKOH.main.productSearch.onLoad();
	});
});
