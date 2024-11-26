/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

// JavaScript Document

var axKOH = axKOH || {};

axKOH.main = axKOH.main || {};

axKOH.main.promoManager = (function () {
    var componentClass = "c-koh-promo-grid";

    var config = {
        breakpoint: 768,
        masonry: {
            itemSelector: '.koh-promo-tile',
            columnWidth: '.koh-promo-tile-sizer',
            percentPosition: true
        },
        slick: {
            autoplay: false,
            adaptiveHeight: true,
            dots: true,
            dotsClass: 'koh-slider-indicators',
            arrows: true,
            pauseOnHover: true,
            pauseOnDotsHover: true,
            mobileFirst: true,
            slide: '.koh-promo-tile'
        }
    };

    var toggleMasonry = function ($tileCollection, activate) {
        if (activate) {
            // add sizer div that masonry needs to size things properly
            if ($tileCollection.children(".koh-promo-tile-sizer").length < 1) {
                $tileCollection.prepend('<div class="koh-promo-tile-sizer"></div>');
            }

            $tileCollection.imagesLoaded(function () {
                $tileCollection.masonry(config.masonry);
            });
        } else {
            $tileCollection.masonry('destroy');
            // remove sizer when we're not doing masonry
            $tileCollection.remove(".koh-promo-tile-sizer");
        }
    };

    var timers = {};

    var waitForFinalEvent = function (callback, ms, uniqueId) {
        if (!uniqueId) {
            uniqueId = "UNIQUEID";
        }
        if (timers[uniqueId]) {
            clearTimeout(timers[uniqueId]);
        }
        timers[uniqueId] = setTimeout(callback, ms);
    };

    var createSliderControls = function ($tileCollection) {
        var $sliderControls = $('<div class="koh-slider-controls"></div>');
        $sliderControls.insertBefore($tileCollection);

        var $movePrevious = $('<button class="koh-slider-control previous"><span class="icon"></span><span class="label">Previous</span></button>');
        var $moveNext = $('<button class="koh-slider-control next"><span class="icon"></span><span class="label">Next</span></button>');
        $sliderControls.append($movePrevious);
        $sliderControls.append($moveNext);

        return $sliderControls;
    };

    var destroySliderControls = function ($tileCollection) {
        $tileCollection.prev(".koh-slider-controls").remove();
    };

    var onResize = function ($target, useMasonry, slickConfig) {
        var isMobile = ($(window).width() < config.breakpoint);
        var prevSize = $target.attr("data-size");

        if ((isMobile && prevSize === "mobile") || (!isMobile && prevSize === "desktop")) {
            // if we're still on the same side of the breakpoint that we were 
            // before, and this is not first load, don't re-init
            return;
        }

        if (isMobile) {
            // turn off masonry if we're using it
            if (useMasonry && prevSize) {
                toggleMasonry($target, false);
            }
            // turn on slick
            var $sliderControls = createSliderControls($target);
            slickConfig.appendArrows = $sliderControls;
            slickConfig.appendDots = $sliderControls;
            slickConfig.prevArrow = $sliderControls.children(".koh-slider-control.previous");
            slickConfig.nextArrow = $sliderControls.children(".koh-slider-control.next");

            $target.slick(slickConfig);
            // save what size we initialized at last
            $target.attr("data-size", "mobile");
        } else {
            // turn off slick
            if (prevSize) {
                if ($target.hasClass('slick-initialized')) {
                    $target.slick('unslick');
                    destroySliderControls($target);
                }
            }
            // turn on masonry if we're using it
            if (useMasonry) {
                toggleMasonry($target, true);
            }
            $target.attr("data-size", "desktop");
        }
    };

    var initPromos = function () {
        // Locate all instances of the component
        var $components = $("." + componentClass);

        // Filter lists for sliders vs stacked
        var $stackedComponents = $components.not(".m-koh-slideshow");
        var $sliderComponents = $components.filter(".m-koh-slideshow");

        var isMobile = ($(window).width() < config.breakpoint);
        // If the component settings indicate that it should be stacked at mobile, keep it simple:
        // Just activate the Masonry for Scattered and be done
        $stackedComponents.each(function () {
            var $component = $(this);
            if ($component.hasClass("v-koh-scattered")) {
                var $container = $component.children(".koh-promo-content");
                var $tileCollection = $container.children(".koh-promo-tiles");
                toggleMasonry($tileCollection, true);
            }
        });

        // If the component settings indicate that it should be slider at mobile, this needs more steps:
        $sliderComponents.each(function (index) {
            var $component = $(this);
            var $container = $component.children(".koh-promo-content");
            var $tileCollection = $container.children(".koh-promo-tiles");

            var useMasonry = $component.hasClass("v-koh-scattered");
            onResize($tileCollection, useMasonry, config.slick);

            $(window).resize({
                target: $tileCollection,
                masonry: useMasonry,
                config: config.slick
            }, function (e) {
                var $target = e.data.target;
                var masonry = e.data.masonry;
                var config = e.data.config;

                waitForFinalEvent(function () {
                    onResize($target, masonry, config);
                }, 500, "promo" + index);
            });
        });
    };

    var initPromoVideos = function () {
        var $components = $("." + componentClass);

        $components.each(function (componentIndex) {
            var $component = $(this);
            var $tiles = $component.find(".koh-promo-tile");

            $tiles.each(function (tileIndex) {
                var $tile = $(this);

                // Need to determine if this tile is a video tile
                var $videoIcons = $tile.find(".koh-video-icon");
                if ($videoIcons.length > 0) {
                    var $videoLink = $tile.children("a").first();
                    var videoURL = $videoLink.attr("href");

                    if (videoURL) {
                        // Add divs to be video modals 
                        var $videoPanel = $('<div class="koh-video-panel"><iframe width="560" height="315" src="' + videoURL + '" frameborder="0" allowfullscreen></iframe></div>');
                        $videoPanel.insertAfter($videoLink);

                        var remodalID = "PromoVideo" + componentIndex + tileIndex;
                        initAsModal($videoLink, $videoPanel, remodalID);
                    }
                }
            });
        });
    };

    /* Helper Functions */
    var initAsModal = function ($button, $panel, remodalID) {
        $panel.data("remodal-id", remodalID);
        $panel.prepend('<button data-remodal-action="close" class="remodal-close"></button>');
        $panel.attr("data-koh-component", componentClass);
        $panel.addClass(componentClass + "-modal");

        var $remodalPanel = $panel.remodal({
            hashTracking: true,
            closeOnCancel: true,
            closeOnEscape: true,
            closeOnOutsideClick: true,
            modifier: ''
        });

        // Make the button all button-y and click-y
        $button.data("remodal-target", remodalID);
        $button.click({
            target: $remodalPanel
        }, function (e) {
            e.preventDefault();
            e.stopPropagation();
            var $target = e.data.target;
            $target.open();
        });
    };

    // Tiles description trimmer function
    var tileDescStyler = function () {
        var $component = $("."+componentClass);
        var $tiles = $($component).find(".koh-promo-tile");
        $tiles.each(function() {
            var $currentTile = $(this);
            var $curTileDesc = $($currentTile).find(".koh-promo-description");
            var charArr = $curTileDesc.text().split("");
            var CHARACTER_LIMIT = 48;
            if(charArr.length > CHARACTER_LIMIT) {
                var delCount = charArr.length - CHARACTER_LIMIT;
                charArr.splice(CHARACTER_LIMIT,delCount);
                charArr.push("...");
            }
            var trimmedStr = charArr.join("");
            $curTileDesc.text(trimmedStr);
        });
    }

    /* Defines functions exposed through manager object */
    return {
        initialize: function () {
            initPromos();
            initPromoVideos();
        },
        descriptionStyler: function() {
            tileDescStyler();
        }
    };
})();

$(function () {
    $(window).load(function () {
        // executes when HTML-Document is loaded and DOM is ready
        axKOH.main.promoManager.initialize();
        var BREAKPOINT_WIDTH = 768; 
        
        if($(window).width() < BREAKPOINT_WIDTH) {
            axKOH.main.promoManager.descriptionStyler();
        }
    });
});
