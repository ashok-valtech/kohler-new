/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};

var noUiSlider = noUiSlider || null;
axKOH.main.isLazyLoader = true;
axKOH.main.quickViewLastIndex = -1;
var localeValue = $('#localeValue').val();
var priceVat = $('#price-vat').val();
var multipleValue = $('#multiply-value').val();
axKOH.main.productFacetedSearch = function() {
    var componentClass = "c-koh-product-faceted-search";

    // Optional search navigation
    var initSearchNav = function() {

        var $searchControls = $(".koh-search-controls").first();
        var $searchSet = $searchControls.children(".koh-search-set").first();

        var $menuButton = $searchControls.children(".koh-search-select").first();
        $menuButton.click({
            list: $searchSet
        }, function(e) {
            var $button = $(this);
            var $list = e.data.list;

            if ($button.hasClass("open")) {
                $button.removeClass("open");
                $list.removeClass("open");
            } else {
                $button.addClass("open");
                $list.addClass("open");
            }
        });

        $(document).click({
            target: $menuButton,
            list: $searchSet
        }, function(e) {
            var $button = e.data.target;
            var $list = e.data.list;

            if (!$(e.target).closest($button).length && !$(e.target).is($button)) {
                if ($button.hasClass("open")) {
                    $button.removeClass("open");
                    $list.removeClass("open");
                }
            }
        });

    };
  //Init click event on PDF link
    var initPLPProductClick = function($components) {
    	if ($components) {
    		var $productTiles = $components.find(".koh-product-tile");
            $productTiles.each(function() {
                var $productLink = $(this).find(".koh-product-tile-content > a");
                $productLink.on("click", function() {
                    // Session storage for tracking Product listing page click
                    sessionStorage.setItem("prodListClickTrack", true);
                });
            });
            $(".remodal-wrapper").each(function() {
                var $productLink = $(this).find("a");
                $productLink.each (function (){
                	$(this).on("click", function() {
                        // Session storage for tracking Product listing page click
                        sessionStorage.setItem("prodListClickTrack", true);
                    });
                })
            });
    	}
    } 
    
  //Init Market availabilty top
    var initMarketAvailabilityTop = function($components) { 
    	if ($components) {
    		var marketListClass = ".market-list";
    		var $marketList = $components.find(marketListClass);
    		if ($marketList) {
    			$marketList.find('a').each(function(e) {
    				$(this).click({
    					target: $components
                    }, function(e) {
                    	e.preventDefault ();
                    	var $variable = $(this).text();
                        $(".koh-market-values label").text($variable);
                        var $components = e.data.target;
                    	var $hiddenCountryFilter = '.koh-filter-type-hidden';
                    	var $filter = $components.find($hiddenCountryFilter);
                    	if ($filter.length) {
	                    	var facetName = $(this).data("facet-name"),
	                        facetValue = $(this).data("facet-value"),
	                        facetCount = $(this).data("facet-count"),
	                        facetLabel = $(this).data("facet-label");
	                    	var reMarketElement = $('#cookie-info-re-market');
	                    	if (reMarketElement !== 'undefined') {
	                    		var cookieName = reMarketElement.data("cookie-name");
	                    		var durationMinutes = reMarketElement.data("cookie-duration");
	                    		createCookie (cookieName, "/" +facetName+"/" + facetValue, durationMinutes);
	                    	}
	                    	var $filterContent = $filter.find(".koh-filter-content");
	                    	if ($filterContent.length) {
		                    	var $selectedFilters = $filterContent.find(".koh-selected-filters");
		                    	if ($selectedFilters.length) {
		                    		$selectedFilters.empty();
		                    		$filter.append ("<ul class='koh-available-filters'></ul>");
		                    	} 
	                    	}
	                    	addActiveFilter($filter, facetName, facetValue, facetLabel, facetCount);
                    	}
                    	displayNewResults($components);
                    });
    			});
    		}
    		
    		$(".koh-market-values-click").click(function(){
    			$('.market-list').toggle();
    			$('.koh-market-availability').addClass('koh-market-availabiltiy-mobile-wrap')
    		});

    		$("#koh-allproduct-values").click(function(){
    			$('.market-list').hide();
                localStorage.setItem("allProdClickedMa", true);
    		});

    		$('input[name="market"]').change({
					target: $components
	            }, function(e){
	    		    if($('#koh-allproduct-values').prop('checked')){
	    		    	var $variable = $('#select-market-label').val();
	    		        $(".koh-market-values label").text($variable);
	    		        var $components = e.data.target;
	    		        var $hiddenCountryFilter = '.koh-filter-type-hidden';
                    	var $filter = $components.find($hiddenCountryFilter);
                    	if ($filter.length) {
                    		var $filterContent = $filter.find(".koh-filter-content");
                    		if ($filterContent.length) {
                    			var $selectedFilters = $filterContent.find(".koh-selected-filters");
                    			if ($selectedFilters.length) {
	                    			$selectedFilters.empty();
	                    			$filter.append ("<ul class='koh-available-filters'></ul>");
	                    			var reMarketElement = $('#cookie-info-re-market');
	                    			var cookieName = '';
	    	                    	if (reMarketElement !== 'undefined') {
	    	                    		cookieName = reMarketElement.data("cookie-name");
	    	                    	}
	                    			deleteCookie (cookieName);
	                    			displayNewResults($components);
                    			}
                    		}
                    	}
	    		    }
	    		    else if ($('#koh-market-values').prop('checked')){
	    		    	$('.market-list').show();
	    		    }
	    		});

    		$(".koh-market-list-close-button").click(function(){
    		        $('.market-list').hide();
    		        $("#koh-market-values").prop("checked", false);
    		        $("#koh-allproduct-values").prop("checked", true);
    		});
    		
    		}
    };

    /*var createCookie = function(name, value, minutes) {
        var expires = "",
           cookie = "";
       if (minutes) {
           var date = new Date;
           date.setTime(date.getTime() + 60 * minutes * 1e3),
               expires = "expires=" + date.toUTCString()
       }
       cookie = name + "=" + value + "; " + expires + "; path=/",
           document.cookie = cookie
   }*/
   var deleteCookie = function(name) {
        document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/';
    };
    
    // Filter expand/collapse
    var initFilterExpandCollapse = function($components) {
        if ($components) {
            $components.each(function() {
                var $component = $(this);

                var filterGroupClass = ".koh-filter-group.koh-filters";
                var filterClass = ".koh-filter";

                var $filters = $component.find(filterGroupClass).children(filterClass);
                $filters.each(function() {
                    var $filterName = $(this).children(".koh-filter-name").first();
                    var $filterContent = $(this).children(".koh-filter-content").first();

                    $filterName.click({
                        target: $filterContent
                    }, function(e) {
                        var $target = e.data.target;
                        if ($target.hasClass("open")) {
                            $(this).removeClass("open");
                            $target.removeClass("open");
                        } else {
                            $(this).addClass("open");
                            $target.addClass("open");
                        }
                    });
                });

                // Also, the main button needs one
                var $filterButton = $component.find(".koh-search-filters-title");
                var $filterContent = $component.find(".koh-search-filters-content");
                $filterButton.click({
                    target: $filterContent
                }, function(e) {
                    var $target = e.data.target;
                    if ($target.hasClass("open")) {
                        $target.removeClass("open");
                    } else {
                        $target.addClass("open");
                    }
                });
            });
        }
    };

    // Range filter sliders
    var initRangeSliders = function($components) {
        if ($components) {
            $components.each(function() {
                var $component = $(this);
                var $rangeFilters = $component.find(".koh-filter.koh-filter-type-range");

                $rangeFilters.each(function() {
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
                    if (noUiSlider && !isNaN(rangeMin) && !isNaN(rangeMax) && (rangeMin < rangeMax)) {
                        noUiSlider.create(sliderElement, {
                            start: [rangeCurrentMin, rangeCurrentMax],
                            connect: true,
                            range: {
                                'min': rangeMin,
                                'max': rangeMax
                            },
                            format: {
                                to: function(value) {
                                    return Math.round(value);
                                },
                                from: function(value) {
                                    return value;
                                }
                            }
                        });

                        sliderElement.noUiSlider.on('update', function(values, handleIndex) {
                            if (handleIndex === 0) {
                                $rangeMinLabel.data("min-value", values[handleIndex]);
                                $rangeMinLabel.text(rangeLabelPrefix + values[handleIndex] + rangeLabelSuffix);
                            } else if (handleIndex === 1) {
                                $rangeMaxLabel.data("max-value", values[handleIndex]);
                                $rangeMaxLabel.text(rangeLabelPrefix + values[handleIndex] + rangeLabelSuffix);
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
    
    var initCompareButtons = function($components) {
        $components && $components.each(function(componentIndex) {
            var $component = $(this),
                $tiles = $component.find(".koh-product-tile"),
                compareIds = [];
            $(".c-koh-compare-panel").find(".koh-compare-remove").each(function() {
                compareIds.push($(this).attr("data-product-itemno"))
            }), $tiles.each(function() {
                var $tile = $(this),
                    itemno = $tile.find(".koh-compare-add").first().attr("data-product-itemno");
                $.inArray(itemno, compareIds) > -1 && $tile.addClass("product-added")
            })
        })
    };

    // Quick View
    var initQuickView = function($components) {
        $components && $components.each(function(componentIndex) {
            var $component = $(this);

            // Find the quick view blocks, which have the buttons and panels
            var $quickViewContainers = $component.find(".koh-product-quick-view");

            // For each quick view, make the panel modal and hook it to the button
            $quickViewContainers.each(function(quickViewIndex) {
            	if(quickViewIndex > axKOH.main.quickViewLastIndex){
	                var $button = $(this).children(".koh-product-quick-view-button"),
	                    $panel = $(this).children(".koh-product-quick-view-panel"),
	                    sku = $(this).attr("data-sku"),
	                    remodalID = "QuickView" + sku,
	                    currentCompareProducts = [];
	                $(".c-koh-compare-panel").find(".koh-compare-remove").each(function() {
	                    currentCompareProducts.push($(this).attr("data-product-itemno"))
	                    $(".add_" + $(this).attr("data-product-itemno")).closest(".koh-product-tools").addClass("product-added");
	                }), $(this).closest(".koh-product-tile").attr("data-modal-link", remodalID), initAsModal($button, $panel, remodalID), $panel.each(function() {
	                    initQuickViewData($(this), currentCompareProducts)
	                })
	                axKOH.main.quickViewLastIndex = quickViewIndex;
            	}
            })
        })
    };

    // Tooltips
    var initTooltips = function($components) {
        if ($components) {
            // Need to initialize tooltips in modals as well
            var $componentModals = $('.remodal-wrapper > div[data-koh-component="' + componentClass + '"]');

            // Merge lists of components and component-related modals
            var $targets = $components.add($componentModals);

            // Loops through components and component-related modals
            $targets.each(function(componentIndex) {
                var $component = $(this);

                // Set up tooltips on abbreviations
                var $abbreviations = $component.find("abbr[title]");
                $abbreviations.each(function(abbrIndex) {
                    var $abbr = $(this);
                    $abbr.qtip({
                        id: "abbr" + componentIndex + abbrIndex,
                        content: {
                            attr: 'title'
                        },
                        position: {
                            my: 'bottom center', // Position my top left...
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
                $colorSwatches.each(function(swatchIndex) {
                    var $colorSwatch = $(this);
                    var $colorLabel = $colorSwatch.children(".label");

                    $colorSwatch.qtip({
                        id: "swatch" + componentIndex + swatchIndex,
                        content: {
                            text: $colorLabel.clone()
                        },
                        position: {
                            my: 'top left', // Position my top left...
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
                $facetsWithTooltips.each(function(facetIndex) {
                    var $facet = $(this);
                    var $facetName = $facet.find(".koh-filter-name");
                    var $facetTooltip = $facet.find(".koh-filter-tooltips");

                    $facetName.append("<span class=\"koh-tooltip\"></span>");
                    var $tooltipIcon = $facetName.children(".koh-tooltip");

                    $tooltipIcon.qtip({
                        id: "facetTooltip" + componentIndex + facetIndex,
                        content: {
                            text: $facetTooltip
                        },
                        show: 'click',
                        hide: {
                            event: 'click unfocus'
                        }, // Hide on click anywhere
                        position: {
                            my: 'center left', // Position my top left...
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
                    $tooltipIcon.click(function(e) {
                        e.stopPropagation();
                        e.preventDefault();
                    });
                });
            });
        }
    };

    // Dropdowns
    var initDropdowns = function($components) {
        if ($components) {
            $components.each(function() {
                var $component = $(this);

                var $options = $component.find(".koh-search-sorting");
                $options.each(function() {
                    var $optionsButton = $(this).children(".koh-sort-by");
                    var $optionsList = $(this).children(".koh-sort-options");

                    $optionsButton.click({
                        list: $optionsList
                    }, function(e) {
                        var $button = $(this);
                        var $list = e.data.list;

                        if ($button.hasClass("open")) {
                            $button.removeClass("open");
                            $list.removeClass("open");
                        } else {
                            $button.addClass("open");
                            $list.addClass("open");
                        }
                    });
                });
            });
        }
    };

    // Cookie handling to support Simiilar Products
    var initSimilarProducts = function($components) {
        if ($components) {
            $components.each(function() {
                var $component = $(this);

                var $productTiles = $component.find(".koh-product-tile");

                $productTiles.each(function() {
                    var $productLink = $(this).find(".koh-product-tile-content > a");

                    // On click, save the similar products id list from the data attribute into a cookie
                    $productLink.on("click", function() {
                        var similarProducts = $(this).data("similar");
                        if (similarProducts) {
                            var minutes = 5;
                            var expires = new Date();
                            expires.setTime(expires.getTime() + (minutes * 60 * 1000));
                            document.cookie = "kohsimprod" + '=' + similarProducts + ';expires=' + expires.toUTCString() + ';path=/';
                        }
                        // Session storage for tracking Product listing page click
                        sessionStorage.setItem("prodListClickTrack", true);
                    });
                });
            });
        }
    };

    var initSpecToggle = function() {
        var specToggle = $(".koh-spec-toggle");
        $(specToggle).length && specToggle.each(function() {
            $(this).click(function(e) {
                return e.preventDefault, $(this).next().toggle(), $(this).toggleClass("active"), !1
            })
        })
    };

    var initSearchFacets = function($components) {
        $components.each(function() {
            var $component = $(this),
                $sortButton = $component.find(".koh-search-sorting > .koh-sort-by"),
                $sortOptions = $component.find(".koh-search-sorting > .koh-sort-options"),
                $sortOptionItems = $sortOptions.children("li");
            $sortOptionItems.each(function() {
                var $sortOptionItem = $(this);
                $sortOptionItem.click({
                    button: $sortButton,
                    collection: $sortOptions,
                    choices: $sortOptionItems
                }, function(e) {
                    setActiveSorting(e.data.button, e.data.choices, $(this)), e.data.button.removeClass("open"), e.data.collection.removeClass("open"), displayNewResults($component)
                })
            });
            var $categories = $component.find(".koh-search-filters .koh-filter-group.koh-category");
            $categories.each(function() {
                var $availableFilters = $categories.find(".koh-available-filters > li > a"),
                    $selectedFilters = $categories.find(".koh-selected-filters");
                $availableFilters.each(function() {
                    var $filterLink = $(this);
                    $filterLink.click(function(e) {
                        e.stopPropagation(), e.preventDefault();
                        var facetName = $filterLink.data("facet-name"),
                            facetValue = $filterLink.data("facet-value"),
                            facetLabel = $filterLink.children("span.name").first().text(),
                            facetCount = $filterLink.data("facet-count");
                        addActiveCategory($categories, facetName, facetValue, facetLabel, facetCount), displayNewResults($component)
                    })
                });
                var $selectedFilterItems = $selectedFilters.children("li");
                $selectedFilterItems.each(function() {
                    var $filterItem = $(this),
                        $filterItemButton = $filterItem.children("button.koh-selected-filter");
                    $filterItemButton.click({
                        filterCount: $selectedFilterItems.length,
                        filterSet: $selectedFilters,
                        filterItem: $filterItem
                    }, function(e) {
                        e.data.filterCount <= 1 ? e.data.filterSet.remove() : e.data.filterItem.remove();
                        (getActiveSection($component) === "SearchPage")? displayNewResults($component, true, false) : displayNewResults($component, true, true);
                    })
                })
            });
            var $pagination = $component.find(".pagination");
            var $paginationChild = $pagination.children("li");
            $paginationChild.each(function() {
                var $paginationChildLi = $(this);
                $paginationChildLi.click({
                    component: $component
                }, function(e) {
                    e.stopPropagation(), e.preventDefault();
                    $(window).scrollTop(0);
                    var page = $(this).find("a:first").data("page");
                    var currentPage = $component.find(".koh-facet-data-attributes").data("koh-currentpage") || "";
                    $component.find(".koh-facet-data-attributes").data('koh-currentpage', page);
                    displayNewResults($component, 'paging')
                });
            });

            var $showAllCats = $component.find(".koh-search-filters .koh-filter-group.koh-category .koh-filter-group-all-link");
            $showAllCats.click(function(e) {
                e.stopPropagation(); 
                e.preventDefault();
                (getActiveSection($component) === "SearchPage")? displayNewResults($component, true, false) : displayNewResults($component, true, true);
            });
            var $filters = $component.find(".koh-search-filters .koh-filter-group.koh-filters .koh-filter");
            $filters.each(function() {
                var $filter = $(this),
                    $selectedFilters = null,
                    $selectedFilterItems = null,
                    $availableFilters = null;
                if ($filter.hasClass("koh-filter-type-checkbox")) {
                    var $checkbox = $filter.find(".koh-checkbox-filter > a");
                    $checkbox.click(function(e) {
                        e.stopPropagation(), e.preventDefault(), $(this).hasClass("koh-checkbox-checked") ? $(this).removeClass("koh-checkbox-checked").addClass("koh-checkbox-unchecked") : $(this).removeClass("koh-checkbox-unchecked").addClass("koh-checkbox-checked"), displayNewResults($component)
                    })
                } else if ($filter.hasClass("koh-filter-type-range")) {
                    var $sliderContainer = $filter.find(".koh-range-filter").first(),
                        $rangeMinLabel = $sliderContainer.find(".koh-range-slider-labels > .koh-range-slider-min"),
                        $rangeMaxLabel = $sliderContainer.find(".koh-range-slider-labels > .koh-range-slider-max"),
                        $slider = $sliderContainer.find(".koh-range-slider"),
                        sliderElement = $slider[0];
                    	var suffix = $sliderContainer.find(".koh-range-slider-labels").data('label-suffix');
                    sliderElement.noUiSlider && sliderElement.noUiSlider.on("change", function() {
                        var facetName = $sliderContainer.data("facet-name"),
                            minValue = $rangeMinLabel.data("min-value"),
                            maxValue = $rangeMaxLabel.data("max-value"),
                            facetNameLabel = minValue && maxValue ? minValue + suffix + " - " + maxValue + suffix : "",
                            facetValue = minValue && maxValue ? minValue + "-" + maxValue : "";
                        
                        var $sliderSelectedFilter = $filter.find(".koh-selected-filters");
                        if($sliderSelectedFilter.length > 0){
                        	$selectedFilterItems = $selectedFilters.children("li")
                        	$selectedFilterItems.each(function() {
                        		var $filterItem = $(this);
                        		$filterItemButton = $filterItem.find("button.koh-selected-filter");
                        		$filterItemButton.data('facet-value',facetValue);
                        		$filterItemButtonName = $filterItemButton.find('.name');
                        		$filterItemButtonName.html(facetNameLabel);
                        		displayNewResults($component);
                        	});
                        	$sliderSelectedFilter.children("button.koh-selected-filter")
                        }else{
                        	addActiveFilter($filter, facetName, facetValue, facetNameLabel, ""), displayNewResults($component)	
                        }
                    }), $selectedFilters = $filter.find(".koh-selected-filters"), $selectedFilterItems = $selectedFilters.children("li"), $selectedFilterItems.each(function() {
                        var $filterItem = $(this),
                            $filterItemButton = $filterItem.children("button.koh-selected-filter");
                        $filterItemButton.click({
                            filterCount: $selectedFilterItems.length,
                            filterSet: $selectedFilters,
                            filterItem: $filterItem
                        }, function(e) {
                            e.data.filterCount <= 1 ? e.data.filterSet.remove() : e.data.filterItem.remove(), displayNewResults($component)
                        })
                    })
                } else $filter.hasClass("koh-filter-type-colors") ? ($availableFilters = $filter.find(".koh-available-filters > li > a"), $selectedFilters = $filter.find(".koh-selected-filters"), $availableFilters.each(function() {
                    var $filterLink = $(this);
                    $($filterLink).on("click touchend", function(e) {
                        e.stopPropagation(), e.preventDefault();
                        var selectedFiltersNow = $filter.find(".koh-selected-filters");
                        var selectedFlag = false;
                        for (i = 0; i < selectedFiltersNow.length; ++i) {
                            selectedFlag = selectedFiltersNow[i].hasClass("koh-filter-type-colors") ? true : false;
                            if (selectedFlag == true) {
                                break;
                            }
                        }
                        if (selectedFlag == false) {
                            var facetName = $filterLink.data("facet-name"),
                                facetValue = $filterLink.data("facet-value"),
                                facetLabel = $filterLink.children("span.label").text(),
                                facetCount = $filterLink.data("facet-count");
                            addActiveFilter($filter, facetName, facetValue, facetLabel, facetCount), displayNewResults($component)
                        }
                    })
                }), $selectedFilters = $filter.find(".koh-selected-filters"), $selectedFilterItems = $selectedFilters.children("li"), $selectedFilterItems.each(function() {
                    var $filterItem = $(this),
                        $filterItemButton = $filterItem.children("button.koh-selected-filter");
                    $filterItemButton.click({
                        filterCount: $selectedFilterItems.length,
                        filterSet: $selectedFilters,
                        filterItem: $filterItem
                    }, function(e) {
                        e.data.filterCount <= 1 ? e.data.filterSet.remove() : e.data.filterItem.remove(), displayNewResults($component)
                    })
                })) : ($availableFilters = $filter.find(".koh-available-filters > li > a"), $selectedFilters = $filter.find(".koh-selected-filters"), $availableFilters.each(function() {
                    var $filterLink = $(this);
                    $filterLink.click(function(e) {
                        e.stopPropagation(), e.preventDefault();
                        var facetName = $filterLink.data("facet-name"),
                            facetValue = $filterLink.data("facet-value"),
                            facetLabel = $filterLink.children("span.name").first().text(),
                            facetCount = $filterLink.data("facet-count");
                        var selectedFiltersNow = $filter.find(".koh-selected-filters");
                        var selectedFlag = false;
                        for (i = 0; i < selectedFiltersNow.length; ++i) {
                            var facetValueSelected = selectedFiltersNow[i].data("facet-value");
                            if (facetValue == facetValueSelected) {
                                break;
                            }
                        }
                        if (selectedFlag == false) {
                            addActiveFilter($filter, facetName, facetValue, facetLabel, facetCount), displayNewResults($component)
                        }
                    })
                }), $selectedFilterItems = $selectedFilters.children("li"), $selectedFilterItems.each(function() {
                    var $filterItem = $(this),
                        $filterItemButton = $filterItem.children("button.koh-selected-filter");
                    $filterItemButton.click({
                        filterCount: $selectedFilterItems.length,
                        filterSet: $selectedFilters,
                        filterItem: $filterItem
                    }, function(e) {
                        e.data.filterCount <= 1 ? e.data.filterSet.remove() : e.data.filterItem.remove(), displayNewResults($component)
                    })
                }))
            });
            var $clearAllFilters = $component.find(".koh-search-filters .koh-filter-group.koh-filters .koh-filter-group-all-link");
            $clearAllFilters.click(function(e) {
                e.stopPropagation(), e.preventDefault(), displayNewResults($component, false, true)
            })
        })
    };

    /* Filtering/Sorting - Get Functions */
    var getActiveSection = function($component) {
        var sectionName = "";

        sectionName = ($component.find(".koh-data-attributes").data("koh-section") || "");

        return sectionName;
    };

    var getActiveCategory = function($component) {
        var categoryName = "";

        var $activeCategory = $component.find(".koh-search-filters .koh-filter-group.koh-category .koh-selected-filter").first();
        categoryName = ($activeCategory.data("facet-value") || "");

        return categoryName;
    };

    var getActiveFilters = function($component) {
        var facetNameField = "facet-name",
            facetValueField = "facet-value",
            activeFilters = {},
            $filters = $component.find(".koh-search-filters .koh-filter-group.koh-filters .koh-filter");
        return $filters.each(function() {
            var $filter = $(this),
                $selectedFilters = null,
                facetName = "",
                facetValue = "";
            if ($filter.hasClass("koh-filter-type-checkbox")) {
                var $checkbox = $filter.find(".koh-checkbox-filter > a");
                $checkbox.hasClass("koh-checkbox-checked") && (facetName = $checkbox.data(facetNameField), facetValue = $checkbox.data(facetValueField), facetName && facetValue && (activeFilters[facetName] = facetValue))
            } else $filter.hasClass("koh-filter-type-range") ? ($selectedFilters = $filter.find(".koh-selected-filter"), $selectedFilters.each(function() {
                if (facetName = $(this).data(facetNameField), facetValue = $(this).data(facetValueField), facetName && facetValue) {
                    var values = facetValue.split(/-/);
                    activeFilters[facetName] = activeFilters[facetName] || {}, activeFilters[facetName].Min = values[0], activeFilters[facetName].Max = values[1]
                }
            })) : $filter.hasClass("koh-filter-type-colors") ? ($selectedFilters = $filter.find(".koh-selected-filter"), $selectedFilters.each(function(index) {
                facetName = $(this).data(facetNameField), facetValue = $(this).data(facetValueField), facetName && facetValue && (activeFilters[facetName] = activeFilters[facetName] || {}, activeFilters[facetName][index] = facetValue)
            })) : ($selectedFilters = $filter.find(".koh-selected-filter"), $selectedFilters.each(function(index) {
                facetName = $(this).data(facetNameField), facetValue = $(this).data(facetValueField), facetName && (facetValue != undefined && facetValue !== '') && (activeFilters[facetName] = activeFilters[facetName] || {}, activeFilters[facetName][index] = facetValue)
            }))
        }), activeFilters
    };

    var getActiveSorting = function($component) {
        var sortOptions = {
                orderBy: "",
                direction: "",
                labels: {
                    orderBy: "orderBy",
                    direction: "sort"
                }
            },
            $sortOptions = $component.find(".koh-search-sorting > .koh-sort-options");
        sortOptions.labels.orderBy = $sortOptions.data("sort-orderby-label") || sortOptions.labels.orderBy, sortOptions.labels.direction = $sortOptions.data("sort-direction-label") || sortOptions.labels.direction;
        var $activeSort = $sortOptions.find(".koh-sort-selected > span").first();
        return sortOptions.orderBy = $activeSort.data("sort-orderby"), sortOptions.direction = $activeSort.data("sort-direction"), sortOptions
    };

    var setActiveSorting = function($sortButton, $sortOptions, $targetOption) {
        $sortButton.text($targetOption.children("span").text()), $sortOptions.removeClass("koh-sort-selected"), $targetOption.addClass("koh-sort-selected")
    };

    var addActiveCategory = function($filter, facetName, facetValue, facetLabel, facetCount) {
        var $filterList = $filter.children(".koh-selected-filters");
        $filterList && $filterList.length ? $filterList.empty() : ($filterList = $('<ul class="koh-selected-filters"></ul>'), $filterList.insertBefore($filter.children(".koh-available-filters")));
        var $filterItemButton = $('<button class="koh-selected-filter"></button>');
        $filterItemButton.data("facet-name", facetName), $filterItemButton.data("facet-value", facetValue), $filterItemButton.append($('<span class="remove">Remove Filter</span>')), $filterItemButton.append($('<span class="name">' + facetLabel + "</span>")), $filterItemButton.append($('<span class="count"> (' + facetCount + ")</span>")), $filterList.append($("<li></li>").wrapInner($filterItemButton))
    };

    var addActiveFilter = function($filter, facetName, facetValue, facetLabel, facetCount) {
        var $filterList = $filter.children(".koh-selected-filters"),
            $filterItemButton = $('<button class="koh-selected-filter"></button>');
        $filter.hasClass("koh-filter-type-checkbox") ? $(this).removeClass("koh-checkbox-unchecked").addClass("koh-checkbox-checked") : $filter.hasClass("koh-filter-type-range") ? ($filterList && $filterList.length || ($filterList = $('<ul class="koh-selected-filters"></ul>'), $filterList.insertBefore($filter.find(".koh-range-filter"))), $filterItemButton.data("facet-name", facetName), $filterItemButton.data("facet-value", facetValue), $filterItemButton.append($('<span class="remove">Remove Filter</span>')), $filterItemButton.append($('<span class="name">' + facetLabel + "</span>")), $filterItemButton.append($('<span class="count">' + facetCount + "</span>")), $filterList.append($("<li></li>").wrapInner($filterItemButton))) : $filter.hasClass("koh-filter-type-colors") ? ($filterList && $filterList.length || ($filterList = $('<ul class="koh-selected-filters"></ul>'), $filterList.insertBefore($filter.find(".koh-available-filters"))), $filterItemButton.data("facet-name", facetName), $filterItemButton.data("facet-value", facetValue), $filterItemButton.append($('<span class="remove">Remove Filter</span>')), $filterItemButton.append($('<span class="name">' + facetLabel + "</span>")), $filterList.append($("<li></li>").wrapInner($filterItemButton))) : ($filterList && $filterList.length || ($filterList = $('<ul class="koh-selected-filters"></ul>'), $filterList.insertBefore($filter.find(".koh-available-filters"))), $filterItemButton.data("facet-name", facetName), $filterItemButton.data("facet-value", facetValue), $filterItemButton.append($('<span class="remove">Remove Filter</span>')), $filterItemButton.append($('<span class="name">' + facetLabel + "</span>")), $filterItemButton.append($('<span class="count">(' + facetCount + ")</span>")), $filterList.append($("<li></li>").wrapInner($filterItemButton)))
    };

    var getType = function($component) {
        var type = "";
        return type = $component.find(".koh-facet-data-attributes").data("koh-type") || ""
    };

    var getSearchTerm = function($component) {
    	var searchTerm = $component.find(".koh-facet-data-attributes").data("koh-searchterm");
        if(searchTerm !== undefined){
        	return searchTerm 
        }else{
        	return searchTerm = ""
        }
    };

    var getCurrentPage = function($component) {
        var currentPage = "";
        return currentPage = $component.find(".koh-facet-data-attributes").data("koh-currentpage") || ""
    };

    var getSortBy = function($component) {
        var sortBy = "";
        return sortBy = $component.find(".koh-facet-data-attributes").data("koh-sortby") || ""
    };

    var getOrderBy = function($component) {
        var orderBy = "";
        return orderBy = $component.find(".koh-facet-data-attributes").data("koh-orderby") || ""
    };

    var getURL = function($component, excludeCategory, excludeFilters) {
        var url = "",
            queryParams = {},
            queryParamCount = 0,
            //urlRoot = $component.find(".koh-data-attributes").data("koh-url-root");
            urlRoot = window.location.href;
        
         var isSearch = false;
         var urlSearch = $component.find(".koh-data-attributes").data("koh-search-url");
         var resIndex = urlRoot.indexOf("/" + urlSearch);
          var urlCollections = $component.find(".koh-data-attributes").data("koh-collections-url");
         var collectionsIndex = urlRoot.indexOf("/" + urlCollections);
         if (resIndex && resIndex > 0) {
        	 isSearch = true;
        	 url = urlRoot.substring(0, resIndex);
             url = url + "/" +urlSearch;
         }else if(collectionsIndex && collectionsIndex > 0){
        	 isSearch = true;
        	 url = urlRoot.substring(0, collectionsIndex);
             url = url + "/" + urlCollections;
         }else{
        	 var urlRootComponent = $component.find(".koh-data-attributes").data("koh-url-root");
        	 var urlRoot = decodeURIComponent(urlRoot);
        	 var catIndex = urlRoot.indexOf("/" + urlRootComponent);
        	 if (catIndex && catIndex > 0) {
                 url = urlRoot.substring(0, catIndex);
             }else {
            	 url = urlRoot;
             }
         }
        //url = urlRoot;
        var sectionName = getActiveSection($component);
        //if (sectionName && (url = url + "/" + sectionName), !excludeCategory) {
        if(isSearch){
        	if (!excludeCategory || excludeCategory == 'paging') {
                var categoryName = getActiveCategory($component);
                if (categoryName){
                	url = url + "/" + categoryName;
                }
        	}    
            if (!excludeFilters) {
                var activeFilters = getActiveFilters($component);
                $.each(activeFilters, function(facetName, facetValues) {
                    facetName && facetValues && ("object" == typeof facetValues ? $.each(facetValues, function(index, facetValue) {
                        if ("Min" === index || "Max" === index) {
                            var paramName = facetName + index;
                            queryParams[paramName] = facetValue, queryParamCount += 1
                        } else url = url + "/" + facetName + "/" + facetValue
                    }) : url = url + "/" + facetName + "/" + facetValues)
                })
            }    
            var sortOptions = getActiveSorting($component);
            sortOptions && sortOptions.orderBy && sortOptions.direction && (queryParams[sortOptions.labels.orderBy] = sortOptions.orderBy, queryParamCount += 1, queryParams[sortOptions.labels.direction] = sortOptions.direction, queryParamCount += 1)
        }
        else{
	        if (!excludeCategory) {
	            var categoryName = getActiveCategory($component);
	            if (categoryName && (url = url + "/" + categoryName), !excludeFilters) {
	                var activeFilters = getActiveFilters($component);
	                $.each(activeFilters, function(facetName, facetValues) {
	                    facetName && facetValues && ("object" == typeof facetValues ? $.each(facetValues, function(index, facetValue) {
	                        if ("Min" === index || "Max" === index) {
	                            var paramName = facetName + index;
	                            queryParams[paramName] = facetValue, queryParamCount += 1
	                        } else url = url + "/" + facetName + "/" + facetValue
	                    }) : url = url + "/" + facetName + "/" + facetValues)
	                })
	            }
	            var sortOptions = getActiveSorting($component);
	            sortOptions && sortOptions.orderBy && sortOptions.direction && (queryParams[sortOptions.labels.orderBy] = sortOptions.orderBy, queryParamCount += 1, queryParams[sortOptions.labels.direction] = sortOptions.direction, queryParamCount += 1)
	        } 
        }    
        var searchTerm = getSearchTerm($component);
        if (searchTerm !== "") {
            queryParams["search"] = searchTerm, queryParamCount += 1;
        }
        var type = getType($component);
        if (type !== "") {
            queryParams["type"] = type, queryParamCount += 1;
        }

        if (excludeCategory == 'paging') {
            var currentPage = getCurrentPage($component);
            if (currentPage !== "") {
                queryParams["currentpage"] = currentPage, queryParamCount += 1;
            }
            var orderBy = getOrderBy($component);
            if (orderBy !== "") {
                queryParams["orderBy"] = orderBy, queryParamCount += 1;
            }

            var sortBy = getSortBy($component);
            if (sortBy !== "") {
                queryParams["sort"] = sortBy, queryParamCount += 1;
            }
        }
        return queryParamCount > 0 && (url += "?", $.each(queryParams, function(key, value) {
            url = url + key + "=" + value + "&"
        }), url = url.replace(/&$/, "")), url
    };

    var displayNewResults = function($component, excludeCategory, excludeFilters) {
        var currentUrl = window.location.href,
            targetUrl = getURL($component, excludeCategory, excludeFilters);
        targetUrl !== currentUrl && (saveScrollPosition(), window.location.href = targetUrl)
    };

    var initAsModal = function($button, $panel, remodalID) {
        $panel.data("remodal-id", remodalID), $panel.prepend('<button data-remodal-action="close" class="remodal-close"></button>'), $panel.attr("data-koh-component", componentClass), $panel.addClass(componentClass + "-modal"), $panel.attr("id", remodalID);
        var $remodalPanel = $panel.remodal({
            hashTracking: true,
            closeOnCancel: true,
            closeOnEscape: true,
            closeOnOutsideClick: true,
            modifier: ""
        });
        $button.data("remodal-target", remodalID), $button.click({
            target: $remodalPanel
        }, function(e) {
            e.preventDefault(), e.stopPropagation();
            var $target = e.data.target;
            $target.open()
        })
    };

    var initQuickViewData = function($panel, compareIds) {
        var $primaryBanner = $panel.children(".koh-product-image"),
            $productContent = $panel.children(".koh-product-content"),
            $productSKU = $productContent.children(".koh-product-sku"),
            $colorCollections = $productContent.children(".koh-product-colors"),
            $productInriverPrice = $productContent.find(".koh-product-price");
        $colorCollections.each(function() {
            var $colorCollection = $(this),
                $selectedColorLabel = $colorCollection.children(".koh-selected-color").find(".value"),
                itemno = $colorCollection.find(".koh-compare-add").first().attr("data-product-itemno");
            $.inArray(itemno, compareIds) > -1 && $panel.addClass("product-added");
            var $colorVariants = $colorCollection.find(".koh-product-variant");
            $colorVariants.each(function() {
                var $colorVariant = $(this),
                    productData = {
                        imageURL: $colorVariant.data("koh-image"),
                        sku: $colorVariant.data("koh-sku"),
                        skuk: $colorVariant.data("koh-sku-k"),
                        prices: $colorVariant.data("koh-price"),
                        colorName: $colorVariant.data("koh-color"),
                        defaultCategory: $colorVariant.data("default-category")
                    },
                    productFields = {
                        banner: $primaryBanner,
                        sku: $productSKU,
                        prices: $productInriverPrice,
                        color: $selectedColorLabel
                    };
                $colorVariant.children(".koh-product-color").click({
                    variantSet: $colorVariants,
                    variant: $colorVariant,
                    product: productData,
                    fields: productFields
                }, function(e) {
                    var $variant = e.data.variant,
                        $variantSet = e.data.variantSet,
                        productData = e.data.product,
                        productFields = e.data.fields;
                    var allOpened = $('.remodal-wrapper.remodal-is-opened');
                    $.each(allOpened, function() {
                        $(this).find('a').each(function() {
                            var href = $(this).attr('href');
                            if (href !== undefined) {
                                var index = href.indexOf('?skuid=');
                                if (index > 0) {
                                	var defaultCategoryURL = "";
                                	if(productData.defaultCategory !== ""){
                                		defaultCategoryURL = "&defaultCategory=" + productData.defaultCategory;
                                	}
                                    href = $(this).attr('href').substr(0, index) + "?skuid=" + productData.sku + defaultCategoryURL;
                                    $(this).attr('href', href);
                                }
                            }
                        });
                    });
                    productFields.banner.find("img").attr("src", productData.imageURL), productFields.sku.text(productData.skuk), productFields.prices.find(".koh-current-price > .value").text(productData.prices.current), productFields.prices.find(".koh-previous-price > .value").text(productData.prices.previous), productFields.prices.find(".koh-discounted-price > .value").text(productData.prices.discounted), productFields.color.text(productData.colorName), productFields.prices.text(productData.prices), $variantSet.removeClass("koh-selected-variant"), $variant.addClass("koh-selected-variant")
                })
            })
        });
        var $productShare = $productContent.find(".koh-product-share");
        $productShare.addClass("test");
        var $productShareButton = $productShare.children(".koh-product-button"),
            $productSharePanel = $productShare.children(".koh-product-popover");
        axKOH && axKOH.utils && axKOH.utils.socialShare && axKOH.utils.socialShare.initAsTooltip($productShare, $productShareButton, $productSharePanel)
    };

    var loadScrollPosition = function() {
        var scrollPosition = sessionStorage.getItem("kohPageScroll");
        axKOH.utils.debugLog("Scroll set to " + scrollPosition), $(window).scrollTop(scrollPosition), sessionStorage.removeItem("kohPageScroll")
    };

    var saveScrollPosition = function() {
        var scrollPosition = $(window).scrollTop();
        sessionStorage.setItem("kohPageScroll", scrollPosition)
    };

    var initProductVideos = function($components) {
        $components && $components.each(function(componentIndex) {
            var $component = $(this),
                $videoLinks = $component.find(".koh-article-video a");
            $videoLinks.each(function(videoIndex) {
                var $videoLink = $(this),
                    videoURL = $videoLink.attr("href"),
                    apiURL = $videoLink.data("api");
                if (videoURL) {
                    var $videoPanel = $('<div class="koh-video-panel"><iframe width="630" height="354" src="' + videoURL + '" frameborder="0" allowfullscreen></iframe></div>');
                    $videoPanel.insertAfter($videoLink);
                    var remodalID = "Video" + componentIndex + videoIndex,
                        $videoPanel = $component.find(".koh-video-panel");
                    initAsModal($videoLink, $videoPanel, remodalID),
                        $videoPanel.on("opened", function() {
                            var $embeddedVideo = $(this).find("iframe");
                            resizeResponsiveVideo($embeddedVideo)
                        })
                } else
                    $videoLink.click(function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                    });
                apiURL && $.get(apiURL, function(data) {
                    if (data && data.items && data.items[0] && data.items[0].snippet) {
                        var videoTitle = data.items[0].snippet.title;
                        videoTitle && $videoLink.children(".koh-video-title").text(videoTitle);
                    }
                })
            })
        })
    };

    var makeVideoResponsive = function($videoElement) {
        var aspectRatio = $videoElement.data("aspectratio");
        var maxWidth = $videoElement.data("maxwidth");
        if (!aspectRatio || !maxWidth) {
            var width = $videoElement.width();
            var height = $videoElement.height();
            var aspectRatio = height / width;
            $videoElement.data("aspectratio", aspectRatio);
            $videoElement.data("maxwidth", width);
            $videoElement.removeAttr("width");
            $videoElement.removeAttr("height");
        }
    };

    var resizeResponsiveVideo = function($videoElement) {
        var embedTags = "iframe, object, embed";
        var aspectRatio = $videoElement.data("aspectratio");
        var maxWidth = $videoElement.data("maxwidth");
        aspectRatio && maxWidth || (makeVideoResponsive($videoElement), aspectRatio = $videoElement.data("aspectratio"), maxWidth = $videoElement.data("maxwidth"));
        var $parent = $videoElement.closest(":not(" + embedTags + ")");
        var responsiveWidth = 320;
        $parent && (responsiveWidth = $parent.width()),
            responsiveWidth = Math.min(responsiveWidth, maxWidth),
            $videoElement.width(responsiveWidth),
            $videoElement.height(responsiveWidth * aspectRatio)
    };

    var changeUrlDam = function(domainUrl, lastSlashIndex, imageUrl, damName) {
        var defImgDomainWithoutDam = domainUrl.slice(0, lastSlashIndex);
        var newDomainUrl = defImgDomainWithoutDam + "/"+ damName;    
        imageUrl = imageUrl.replace("{domain}", newDomainUrl);
        imageUrl = imageUrl.replaceAll("{dam}", damName);
        imageUrl = imageUrl.replace("product_src=is{PAWEB", "product_src=is{kohlerchina");
        return imageUrl;
    }

    var doLazyLoading = function($components) {
    	try{
    		var sortBy = getSortBy($components);
    		var orderBy = getOrderBy($components);
    		
    		var activeFilters = getActiveFilters($components);
    		var queryParams = {};
    		var	queryParamCount = 0;
            $.each(activeFilters, function(facetName, facetValues) {
                facetName && facetValues && ("object" == typeof facetValues ? $.each(facetValues, function(index, facetValue) {
                    if ("Min" === index || "Max" === index) {
                        var paramName = facetName + index;
                        queryParams[paramName] = facetValue, queryParamCount += 1
                    } 
                }) : "")
            });
            var url = "";
            $.each(queryParams, function(key, value) {
            	url += key + ":" + value + ","
            })
    		if(url !== ""){
    			url = url.substring(0, url.length - 1);
    		}
	    	var productListLazyLink = $('#productListLazyLink').val();
	    	var totalProducts = $('#totalProducts').val();
	    	var pageSize = $('#pageSize').val();
	    	var currentPage = $('#currentPage').val();
	    	var currentSwatch = $('#currentSwatch').val();
	    	var addToCompareLink = $('#addToCompareLink').val();
	    	var contextBase = $("#compare-cookie-info").data("cookie-name")
	    	var gridImageProductUrl = $('#gridImageProductUrl').val();
	    	var gridImageNewProductUrl = $('#gridImageNewProductUrl').val();
	    	var common_galleryNewProductUrl = $('#common_galleryNewProductUrl').val();
	    	var common_gridImageUrl = $('#common_gridImageUrl').val();
	    	var common_galleryQuickViewNewProductUrl = $('#common_galleryQuickViewNewProductUrl').val();
	    	var common_productListQuickViewUrl = $('#common_productListQuickViewUrl').val();
	    	var productDetailslinkMain = $('#productDetailslink').val();
	    	var productListQuickViewUrl = $('#productListQuickViewUrl').val();
	    	var quickViewImageNewProductUrl = $('#quickViewImageNewProductUrl').val();
	    	var quickview = $('#quickview').val();
	    	var selectColor = $('#selectColor').val();
	    	var imgswatch = $('#imgswatch').val();
	    	var compareButtonLable = $('#compareButtonLable').val();
	    	var removeProduct = $('#removeProduct').val();
	    	var addCompare = $('#addCompare').val();
	    	var relativeContentPath = $('#relativeContentPath').val();
	    	var bannerFlag = $('#bannerFlag').val();
	    	var bannerProductCount = $('#bannerProductCount').val();
	    	var discontinuedDateLabel = $('#discontinuedLableWithSku').val();
	    	var defaultCategoryKey = $('#defaultCategoryKey').val();
	    	var lastVisitedPage = 0;
	    	var windowScrollTop = 0;
	    	if(typeof(Storage) !== undefined) {
	    		lastVisitedPage = localStorage.getItem("lastVisitedPage")
				windowScrollTop = localStorage.getItem("facetPageLastScroll");
	    	}
        	$.ajax({
                url: productListLazyLink,
                cache: !1,
                data: {'relativeContentPath': relativeContentPath,'totalProducts': totalProducts, 'pageSize':pageSize, 'currentPage': currentPage,  'lastVisitedPage' : lastVisitedPage, 'sort' : sortBy, 'orderBy' : orderBy, 'url': url},
                success: function (data) { 
                	if(data.success == true){
                		currentPage = data.currentPage;
                		var currentPageClone = $('#currentPage').val();
                		$('#currentPage').val(currentPage);
                		if(typeof(Storage) !== undefined) {
                			localStorage.setItem("lastVisitedPage", currentPage);
                		}
                		if((currentPage * pageSize) >= totalProducts || data.items===""){
                			$("#koh-load-more").val(false);
                		}
                		var mainList=$(".koh-search-results");
                		for(var i=0; i < data.items.length; i++){
                			bannerProductCount++;
                			var defaultSkuId = data.items[i].DEFAULT_SKU;
                			var defaultImage  = data.items[i].IMG_ISO;
                			var defaultSkuColorFinishName = data.items[i].COLOR_FINISH_NAME;
                			var defaultSwatch = data.items[i].IMG_SWATCH ;
                            var productPrice = data.items[i].PRODUCT_PRICE;
                			if(defaultSkuId === undefined){
                				for(var j=0; j < data.items[i].SKUS.length; j++){
                					if(data.items[i].SKUS[j].sku !== undefined){
                						defaultImage = data.items[i].SKUS[j].IMG_ITEM_ISO;
                						defaultSkuId = data.items[i].SKUS[j].sku;
                						defaultSkuColorFinishName = data.items[i].SKUS[j].COLOR_FINISH_NAME;
                						defaultSwatch = data.items[i].SKUS[j].IMG_SWATCH 
                						break;
                					}
                				}
                			}
                			if(currentSwatch !== ''){
                				for(var j=0; j < data.items[i].SKUS.length; j++){
                					if(currentSwatch === data.items[i].SKUS[j].IMG_SWATCH){
                						defaultImage = data.items[i].SKUS[j].IMG_ITEM_ISO;
                						defaultSkuId = data.items[i].SKUS[j].sku;
                						defaultSkuColorFinishName = data.items[i].SKUS[j].COLOR_FINISH_NAME;
                						defaultSwatch = data.items[i].SKUS[j].IMG_SWATCH 
                					}
                				}
                			}
                			var imageURL = "";
                			var quickViewImg = "";
                			if(defaultImage === undefined){
                                if(defaultSkuId === undefined) {
                                    defaultImage = "blank";
                                }else {
                                    for(var j=0; j < data.items[i].SKUS.length; j++){
                                        if(data.items[i].SKUS[j].sku === defaultSkuId){
                                            defaultImage = data.items[i].SKUS[j].IMG_ITEM_ISO || "blank";
                                            break;
                                        }
                                    }
                                }   
                			}
                            if(productPrice === undefined) {
                                for(var j=0; j < data.items[i].SKUS.length; j++){
                					if(data.items[i].SKUS[j].sku === defaultSkuId){
                						productPrice = data.items[i].SKUS[j].PRODUCT_PRICE;
                						break;
                					}
                				}
                            }
                            var roundPrice = Math.round(productPrice * priceVat);
                            productPrice = (roundPrice * multipleValue).toLocaleString('en-US');
                            
                			if (defaultImage !== undefined && defaultImage.startsWith('http')) {
								var lastSlashIndex = defaultImage.lastIndexOf('/');
								const defImgFromUrl = defaultImage.slice(lastSlashIndex + 1);
								const domainStartIndex = defaultImage.indexOf("//") + 2;
								const defImgDomainFromUrl = defaultImage.slice(domainStartIndex, lastSlashIndex)
								lastSlashIndex = defImgDomainFromUrl.lastIndexOf('/');
								const defImgDamNameFromUrl = defImgDomainFromUrl.slice(lastSlashIndex + 1);
								if(data.items[i].isNew == true) {
									imageURL = common_galleryNewProductUrl.replace("{0}", defImgFromUrl);
                                    if(defImgDamNameFromUrl === "kohlerchina") {
                                        imageURL = changeUrlDam(defImgDomainFromUrl, lastSlashIndex, imageURL, "PAWEB");
                                    }else {
                                        imageURL = imageURL.replace("{domain}", defImgDomainFromUrl);
                                        imageURL = imageURL.replaceAll("{dam}", defImgDamNameFromUrl);
                                    }
									
									quickViewImg = common_galleryQuickViewNewProductUrl.replace("{0}", defImgFromUrl);
                                    if(defImgDamNameFromUrl === "kohlerchina") {
                                        quickViewImg = changeUrlDam(defImgDomainFromUrl, lastSlashIndex, quickViewImg, "PAWEB");
                                    }else {
                                        quickViewImg = quickViewImg.replace("{domain}", defImgDomainFromUrl);
                                        quickViewImg = quickViewImg.replaceAll("{dam}", defImgDamNameFromUrl);
                                    }
									
								} else {
									imageURL = common_gridImageUrl.replace("{0}", defImgFromUrl);
									if(defImgDamNameFromUrl === "kohlerchina") {
                                        imageURL = changeUrlDam(defImgDomainFromUrl, lastSlashIndex, imageURL, "PAWEB");
                                    }else {
                                        imageURL = imageURL.replace("{domain}", defImgDomainFromUrl);
                                        imageURL = imageURL.replaceAll("{dam}", defImgDamNameFromUrl);
                                    }
									
									quickViewImg = common_productListQuickViewUrl.replace("{0}", defImgFromUrl);
									if(defImgDamNameFromUrl === "kohlerchina") {
                                        quickViewImg = changeUrlDam(defImgDomainFromUrl, lastSlashIndex, quickViewImg, "PAWEB");
                                    }else {
                                        quickViewImg = quickViewImg.replace("{domain}", defImgDomainFromUrl);
                                        quickViewImg = quickViewImg.replaceAll("{dam}", defImgDamNameFromUrl);
                                    }
								}
							} else {
	                			if(data.items[i].isNew == true){
	                				imageURL = gridImageNewProductUrl.replace("{0}", defaultImage);
	                				quickViewImg = quickViewImageNewProductUrl.replace("{0}", defaultImage);
	                			}else{
	                				imageURL = gridImageProductUrl.replace("{0}", defaultImage);
	                				quickViewImg = productListQuickViewUrl.replace("{0}", defaultImage);
	                			}
	                		}
                			
                			var productDetailLink = "";
                			var multiAncestorefaultCategoryData = "";
                			var defaultCategoryIfMultiple = "";
                			var productCategoryKey = defaultCategoryKey;
                			if (defaultCategoryKey === '') {
                				productCategoryKey = data.items[i].category;
                			}
                			if(data.items[i].isMultipleAncestor == true)
                			{
                				productDetailLink = productDetailslinkMain + "/" + data.items[i].itemNo + "?skuid=" + defaultSkuId + "&defaultCategory=" + productCategoryKey;
                				multiAncestorefaultCategoryData = " data-product-itemno-multianscestors=\""+ data.items[i].itemNo + "!_"+ productCategoryKey + "\"";
                				defaultCategoryIfMultiple = productCategoryKey;
                			}else{
                				productDetailLink = productDetailslinkMain + "/" + data.items[i].itemNo + "?skuid=" + defaultSkuId;
                				multiAncestorefaultCategoryData = " data-product-itemno-multianscestors=\""+ data.items[i].itemNo+ "\"";
                			}
                			
                			var descriptionShort = data.items[i].DESCRIPTION_PRODUCT_SHORT;
                			var brandName = data.items[i].BRAND_NAME;
                			if(descriptionShort === undefined){
                				descriptionShort = "";
                			}
                			if(brandName === undefined){
                                brandName = "";
                			}
                            var currencyCode = "";
                            var productPriceSpan = "";
                            if(productPrice !== undefined && !isNaN(roundPrice)) {
                               currencyCode = $("#currencyCode").val();
                               if(currencyCode) {
                                   productPriceSpan = "<span class=\"koh-product-price\" data-koh-price='" + productPrice + "'>"+ currencyCode + " " + productPrice + "</span>"
                               }
                            }
                            
            			if(localeValue === "TH"){
                			var html="<div class=\"koh-product-tile\">" +
										"<div class=\"koh-product-tile-inner\">" +
											"<div class=\"koh-product-tile-content\">" + 
												"<a href='" + productDetailLink +"' data-id='" + data.items[i].itemNo + "' data-default-category='" + defaultCategoryIfMultiple +"' data-productlink='" + productDetailslinkMain + "' data-skuid='"+ defaultSkuId +"'>" +
													"<div class=\"koh-product-image\">	" +
					                     	              "<img src='"+ imageURL +"' width=\"300\" height=\"232\" alt='"+brandName+""+ descriptionShort +"'>" +
													"</div>" +
													"<span class=\"koh-product-description\">" +
														"<span class=\"koh-product-family\">" +  brandName +"</span>&nbsp" +
														descriptionShort +
													"&nbsp </span>"+
													"<span class=\"koh-product-sku\" data-product-sku='" + defaultSkuId + "'>K-" + defaultSkuId + "</span>" +
													productPriceSpan +
												"</a>" +
											"</div>" +
											
											"<div class=\"koh-product-tile-actions\">" +
												"<div class=\"koh-product-quick-view\" data-sku=\""+ data.items[i].itemNo +"\">" +
													"<button class=\"koh-product-quick-view-button\"><span class=\"label\">" + quickview + "</span></button>" +
													"<div class=\"koh-product-quick-view-panel\">" +
														"<div class=\"koh-product-image\">" +
															"<a href='" + productDetailLink + "' id='" +  data.items[i].itemNo + "' data-id='" + data.items[i].itemNo + "' data-default-category='" + defaultCategoryIfMultiple + "' data-productlink='" + productDetailslinkMain + "' data-skuid='" + defaultSkuId +"'>" +
					                                         "<img src='" + quickViewImg + "' width=\"570\" height=\"413\" alt='" + defaultSkuId + "'>" +
			                                               "</a>" +
														"</div>" +
														"<div class=\"koh-product-content\">" +
															"<span class=\"koh-product-description\">" +
															  "<a href='" + productDetailLink + "' id='" + data.items[i].itemNo  + "' data-id='" + data.items[i].itemNo  + "'  data-default-category='" + defaultCategoryIfMultiple + "' data-productlink='" + productDetailslinkMain + "' data-skuid='" + defaultSkuId +"'><span class=\"koh-product-family\">" + brandName + "</span>"+ descriptionShort + "</a>" +
															"</span>" +
															"<span class=\"koh-product-sku\" data-product-sku='" + defaultSkuId+ "'>K-"+ defaultSkuId+ "</span>"+
															productPriceSpan +
															"<div class=\"koh-product-colors\">" + 
														      "<span class=\"koh-selected-color\">" + 
														          "<span class=\"label\">" + selectColor + "</span>"+
														          "<span class=\"value\">" + defaultSkuColorFinishName + "</span>" +
														          "<ul>";
            													}else{
            														var html="<div class=\"koh-product-tile\">" +
            														"<div class=\"koh-product-tile-inner\">" +
            															"<div class=\"koh-product-tile-content\">" + 
            																"<a href='" + productDetailLink +"' data-id='" + data.items[i].itemNo + "' data-default-category='" + defaultCategoryIfMultiple +"' data-productlink='" + productDetailslinkMain + "' data-skuid='"+ defaultSkuId +"'>" +
            																	"<div class=\"koh-product-image\">	" +
            									                     	              "<img src='"+ imageURL +"' width=\"300\" height=\"232\" alt='" + defaultSkuId + "'>" +
            																	"</div>" +
            																	"<span class=\"koh-product-description\">" +
            																		"<span class=\"koh-product-family\">" +  brandName +"</span>&nbsp" +
            																		descriptionShort +
            																	"&nbsp </span>"+
            																	"<span class=\"koh-product-sku\" data-product-sku='" + defaultSkuId + "'>K-" + defaultSkuId + "</span>" +
            																	productPriceSpan +
            																"</a>" +
            															"</div>" +
            															
            															"<div class=\"koh-product-tile-actions\">" +
            																"<div class=\"koh-product-quick-view\" data-sku=\""+ data.items[i].itemNo +"\">" +
            																	"<button class=\"koh-product-quick-view-button\"><span class=\"label\">" + quickview + "</span></button>" +
            																	"<div class=\"koh-product-quick-view-panel\">" +
            																		"<div class=\"koh-product-image\">" +
            																			"<a href='" + productDetailLink + "' id='" +  data.items[i].itemNo + "' data-id='" + data.items[i].itemNo + "' data-default-category='" + defaultCategoryIfMultiple + "' data-productlink='" + productDetailslinkMain + "' data-skuid='" + defaultSkuId +"'>" +
            									                                         "<img src='" + quickViewImg + "' width=\"570\" height=\"413\" alt='" + defaultSkuId + "'>" +
            							                                               "</a>" +
            																		"</div>" +
            																		"<div class=\"koh-product-content\">" +
            																			"<span class=\"koh-product-description\">" +
            																			  "<a href='" + productDetailLink + "' id='" + data.items[i].itemNo  + "' data-id='" + data.items[i].itemNo  + "'  data-default-category='" + defaultCategoryIfMultiple + "' data-productlink='" + productDetailslinkMain + "' data-skuid='" + defaultSkuId +"'><span class=\"koh-product-family\">" + brandName + "</span>"+ descriptionShort + "</a>" +
            																			"</span>" +
            																			"<span class=\"koh-product-sku\" data-product-sku='" + defaultSkuId+ "'>K-"+ defaultSkuId+ "</span>"+
            																			productPriceSpan +
            																			"<div class=\"koh-product-colors\">" + 
            																		      "<span class=\"koh-selected-color\">" + 
            																		          "<span class=\"label\">" + selectColor + "</span>"+
            																		          "<span class=\"value\">" + defaultSkuColorFinishName + "</span>" +
            																		          "<ul>";
            														}
                													var  htmlSub = "";
										                			for(var j=0; j < data.items[i].SKUS.length; j++){
									                						var image = data.items[i].SKUS[j].IMG_ITEM_ISO;
									                						var skuId = data.items[i].SKUS[j].sku;
									                						var skuColorFinishName = data.items[i].SKUS[j].COLOR_FINISH_NAME;
									                						var skuImgSwatch = data.items[i].SKUS[j].IMG_SWATCH
									                						var discontinuedDate = "";
									                						var discontinuedDateClass = "";
                                                                            var skuItemPrice = data.items[i].SKUS[j].PRODUCT_PRICE;
									                						try{
									                							discontinuedDate = data.items[i].SKUS[j].DISCONTINUED_DATE;
									                							if(discontinuedDate != undefined && discontinuedDate !== ""){
									                								skuColorFinishName = skuColorFinishName + " ("+ discontinuedDateLabel + ")";
									                								discontinuedDateClass = "koh-discontinued";
									                							}
									                						}catch(ex){
									                						}
									                						if(image === undefined){
									                							image = "blank";
									                            			}
									                						if(skuId === undefined){
									                							skuId = "";
									                            			}
									                						if(skuColorFinishName === undefined){
									                							skuColorFinishName = "";
									                            			}
									                						if(skuImgSwatch === undefined){
									                							skuImgSwatch = "";
									                            			}
									                						
									                						var selectedSwatchClass = "" ; 
									                						if(defaultSwatch === data.items[i].SKUS[j].IMG_SWATCH){
									                							selectedSwatchClass = "koh-selected-variant";
									                						}
									                						var quickViewImgSub = "";
									                						
									                						if (image.startsWith('http')) {
																				var lastSlashIndex = image.lastIndexOf('/');
																				const defImgFromUrl = image.slice(lastSlashIndex + 1);
																				const domainStartIndex = image.indexOf("//") + 2;
																				const defImgDomainFromUrl = image.slice(domainStartIndex, lastSlashIndex)
																				lastSlashIndex = defImgDomainFromUrl.lastIndexOf('/');
																				const defImgDamNameFromUrl = defImgDomainFromUrl.slice(lastSlashIndex + 1);
																				if(data.items[i].isNew == true){
										                							quickViewImgSub = common_galleryQuickViewNewProductUrl.replace("{0}", defImgFromUrl);
                                                                                    if(defImgDamNameFromUrl === "kohlerchina") {
                                                                                        quickViewImgSub = changeUrlDam(defImgDomainFromUrl, lastSlashIndex, quickViewImgSub, "PAWEB");
                                                                                    }else {
                                                                                        quickViewImgSub = quickViewImgSub.replace("{domain}", defImgDomainFromUrl);
                                                                                        quickViewImgSub = quickViewImgSub.replaceAll("{dam}", defImgDamNameFromUrl);
                                                                                    }
										                						}else {
										                							quickViewImgSub = common_productListQuickViewUrl.replace("{0}", defImgFromUrl);
                                                                                    if(defImgDamNameFromUrl === "kohlerchina") {
                                                                                        quickViewImgSub = changeUrlDam(defImgDomainFromUrl, lastSlashIndex, quickViewImgSub, "PAWEB");
                                                                                    }else {
                                                                                        quickViewImgSub = quickViewImgSub.replace("{domain}", defImgDomainFromUrl);
                                                                                        quickViewImgSub = quickViewImgSub.replaceAll("{dam}", defImgDamNameFromUrl);
                                                                                    }
										                						}
																			} else {
										                						if(data.items[i].isNew == true){
										                							quickViewImgSub = quickViewImageNewProductUrl.replace("{0}", image);
										                						}else {
										                							quickViewImgSub = productListQuickViewUrl.replace("{0}", image);
										                						}
									                						}

                                                                            var roundPrice = Math.round(skuItemPrice * priceVat);
                                                                            skuItemPrice = (roundPrice * multipleValue).toLocaleString('en-US');
                                                                            var swatchImageURL = imgswatch.replace("{0}", skuImgSwatch);
									                						htmlSub += "<li>"+   
																		                    "<span class=\"koh-product-variant " + selectedSwatchClass + discontinuedDateClass + "\" data-koh-image=\"" + quickViewImgSub + "\" data-koh-sku=\""+ skuId +"\" data-koh-sku-k=\"K-" + skuId + "\" data-koh-color=\""+ skuColorFinishName + "\" + data-koh-price=\""+currencyCode+" "+ skuItemPrice + "\" + data-default-category=\"" + defaultCategoryIfMultiple + "\">"+
																		                        "<button class=\"koh-product-color\" data-hasqtip=\""+ skuImgSwatch +"\">"+
																			                            "<img src=\""+ swatchImageURL + "\" width=\"28\" height=\"28\" alt=\""+ skuId+ "\"> "+
																		                            "<span class=\"label\">" + skuColorFinishName +"</span>"+
																		                        "</button>"+
																		                    "</span>"+
																		                "</li>";
									                				}
                										 
										        html +=	htmlSub + "</ul>" +
										        				"</div>" +
														        "<div class=\"koh-product-tools\">" +
																	"<a data-href=\"" + addToCompareLink + "\" class=\"koh-compare-add koh-compare-button-tile add_" + data.items[i].itemNo +"\" data-product-itemno=\"" + data.items[i].itemNo +"\" data-category=\"" + productCategoryKey +"\""+ multiAncestorefaultCategoryData + ">"+ compareButtonLable +"</a>" +
				            										"<a data-href=\"" + addToCompareLink + "\" class=\"koh-compare-removes koh-compare-button-tile remove_" + data.items[i].itemNo +"\" data-product-itemno=\"" + data.items[i].itemNo +"\" data-category=\"" + productCategoryKey +"\""+ multiAncestorefaultCategoryData + ">"+ compareButtonLable +"</a>" +
																"</div>" +
															"</div>" +
														"</div>" +
													"</div>" +		
													"<a data-href='"+ addToCompareLink + "' class=\"koh-compare-add koh-compare-button-tile add_" + data.items[i].itemNo +"\" data-product-itemno='" + data.items[i].itemNo + "'  data-category='" + productCategoryKey + "'"+ multiAncestorefaultCategoryData + ">"+ addCompare +"</a>"+ 
												    "<a data-href='" + addToCompareLink + "' class=\"koh-compare-removes koh-compare-button-tile remove_" + data.items[i].itemNo +"\" data-product-itemno='" + data.items[i].itemNo + "'  data-category='" + productCategoryKey + "'"+ multiAncestorefaultCategoryData + ">"+ removeProduct +"</a> "+
											"</div>" +
										"</div>" +
									"</div>";	
                            mainList.append(html);
                            if((bannerProductCount % 11 == 0) && (bannerFlag === 'true'))
                            {
                            	var promoBanner = $(".koh-product-tile.koh-grid-promo-banner").eq(0).clone();
                            	mainList.append(promoBanner);
                            	bannerProductCount++;
                            }
                		}
                		try {
	                		if ((currentPage - currentPageClone) > 1 ) {
	                			if(typeof(Storage) !== undefined) {
	                            	$("html, body").animate({ scrollTop: windowScrollTop}, 1000, function(){
		                             });
	                            }
	                		}
                		}catch (ex) {
                			
                		}
                		axKOH.main.productFacetedSearch.initializeLazy();
                        axKOH.main.isLazyLoader = true;
                        $('#bannerProductCount').val(bannerProductCount);
                	}
                }
    	        }).done(function () {
    	        
    	        }).fail(function () {
    	        }) 
    	}catch(ex){
        }
    };
    
    var showHideFilters = function($components) {
    	if ($components) {
    		var $lessFilterButton = $components.find(".categories-and-filters__less-filters-link");
    		var $moreFilterButton = $components.find(".categories-and-filters__more-filters-link");
    		if ($lessFilterButton && $moreFilterButton) {
    			var $showMoreDropDown = $components.find(".showMoreDropdowns");
    			if ($showMoreDropDown) {
	    			$lessFilterButton.on("click", function(e) {
	    				e.preventDefault ();
	    				$lessFilterButton.toggle();
	    				var length = $showMoreDropDown.length;
	    				$showMoreDropDown.each(function(index) {
	    					$(this).slideUp("slow", "swing", function(){
	    						if (index === (length - 1)) {
		    						$moreFilterButton.toggle();
		    					}
	    					});
	    			    });
	    	        });
	    			
	    			$moreFilterButton.on("click", function(e) {
	    				e.preventDefault ();
	    				$moreFilterButton.toggle();
	    				var length = $showMoreDropDown.length;
	    				$showMoreDropDown.each(function(index) {
	    					$(this).slideDown("slow", "swing", function(){
	    						if (index === (length - 1)) {
	    							$lessFilterButton.toggle();
		    					}
	    					});
	    			    });
	    	        });
    			}	
    		}
    	}
    };
    
    return {
        initialize: function() {
            var $components = $("." + componentClass);
            initSearchNav($components), initFilterExpandCollapse($components), initRangeSliders($components), initCompareButtons($components), initQuickView($components), initTooltips($components), initDropdowns($components), initSearchFacets($components), initSpecToggle(), initProductVideos($components), initMarketAvailabilityTop ($components), initPLPProductClick ($components), showHideFilters($components)
        },
        onLoad: function() {
            //loadScrollPosition()
        },
        initializeLazy: function() {
            var $components = $("." + componentClass);
            initCompareButtons($components), initQuickView($components), initTooltips($components), initSpecToggle(),  initPLPProductClick ($components)
        },
        scrollPage: function(windowLastScroll) {
        	if (windowLastScroll !== 'undefined' && windowLastScroll > 0) {
            	localStorage.setItem("facetPageLastScroll", windowLastScroll);
        	} else {
        		if(typeof(Storage) !== undefined) {
                	localStorage.setItem("facetPageLastScroll", $(window).scrollTop());
                }
        	}
        	
        	var $components = $("." + componentClass);
        	var $lastElement = $('.koh-product-tile').last();
        	if($lastElement.length !== 0){
	        	var $lastElementHeight = $lastElement.height();
	            var windowHeight = $(window).height();
	            var lastElementTop = ($lastElement.offset().top);
	            var isLoadMore = $("#koh-load-more").val();
	           
	            var lastVisitedPage = $('#currentPage').val();
	            var currentPageVal = lastVisitedPage;
	            if(typeof(Storage) !== undefined) {
	            	lastVisitedPage = localStorage.getItem("lastVisitedPage");
	            }
            	
	            if ((((lastElementTop - $(window).height() + $lastElementHeight + ($lastElementHeight / 4)) <= $(window).scrollTop()) || ((currentPageVal !== undefined) && (currentPageVal !== lastVisitedPage))) && (isLoadMore == 'true')  && axKOH.main.isLazyLoader) {	
	            	doLazyLoading($components);
	            	axKOH.main.isLazyLoader = false;
	            }
        	}
        }
    };

}();

$(function() {
	var lastVisitedPage = 0;
	var productDetailPage = false;
    $(document).ready(function() {
    	if(typeof(Storage) !== undefined) {
        	lastVisitedPage = localStorage.getItem("lastVisitedPage");
        	var currentPageVal = $('#currentPage').val();
        	if (currentPageVal !== undefined) {
        		localStorage.setItem("lastVisitedPage", currentPageVal)
        	}
        	productDetailPage = localStorage.getItem("productDetailPage");
        	if (productDetailPage === 'true') {
        		 if($('#backFromProductDetailPage').length)
        		 {
        			 $('#backFromProductDetailPage').val(productDetailPage);
        		 }
        	}
        	var productDetailPageVal = $('#productDetailPage').val();
        	if (productDetailPageVal !== undefined) {
        		localStorage.setItem("productDetailPage", productDetailPageVal)
        	}else {
        		localStorage.setItem("productDetailPage", false)
        	}
    	}
        axKOH.main.productFacetedSearch.initialize();
    }), $(window).on("load", function() {
        axKOH.main.productFacetedSearch.onLoad();
        $(".koh-video-panel .remodal-close, div:not(.koh-video-panel)").on("click", function() {
            var $vURL= $(".remodal-is-opened .koh-video-panel iframe").attr("src");
            $(".remodal-is-opened .koh-video-panel iframe").attr("src",$vURL); 
        });
		//local storage addded and removed for all product values
		var allProdClickedMa = localStorage.getItem("allProdClickedMa");
        if(allProdClickedMa) {
            var windowScrollTop = localStorage.getItem("facetPageLastScroll");
            if(windowScrollTop != undefined) {
                $("html, body").animate({ scrollTop: windowScrollTop}, 1000, function() {
                    localStorage.removeItem('allProdClickedMa');                        
                });
            }
        }
    }),
    $(window).on("scroll",axKOH.main.productFacetedSearch.scrollPage),
    $(window).unload(function()
	{
	}); 
    
    window.addEventListener("pageshow", function (event) {
    	try {
	    	if (event.persisted || ( typeof window.performance != "undefined" && window.performance.navigation.type === 2 )) {
	    		var windowLastScroll = 'undefined' ;
	    		if(typeof(Storage) !== undefined) {
		    		localStorage.setItem("lastVisitedPage", lastVisitedPage)
		    		windowLastScroll = localStorage.getItem("facetPageLastScroll");
	    		}
	    		if (productDetailPage === 'false') {
	    			window.location.reload();
	    		} else {
	    			axKOH.main.productFacetedSearch.scrollPage(windowLastScroll);
	    		}
	    	}
    	}catch (ex) {
    	}
    });
});

$(window).on("resize", function () {
    /*market search list*/
    if ($(window).width() < 768) {
        $(".koh-market-values-click").click(function(){
            $('.koh-market-availability').addClass('koh-market-availabiltiy-mobile-wrap');
            $('.koh-market-list-close-button').show();
            
            $('.market-list').css('height', $(window).height());
        });
        $("a.koh-country-name").click(function (event) {
            $('.koh-market-availability').removeClass('koh-market-availabiltiy-mobile-wrap');
        });
        $(".koh-market-list-close-button").click(function(){
            $('.koh-market-availability').removeClass('koh-market-availabiltiy-mobile-wrap');
            $('.koh-market-list-close-button').hide();
        });
    }
}).resize();
