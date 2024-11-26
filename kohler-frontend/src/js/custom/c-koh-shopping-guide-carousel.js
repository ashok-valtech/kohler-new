/* /*
* Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

var axKOH = axKOH || {};

axKOH.main = axKOH.main || {};

axKOH.main.carouselManager = (function () {

    var initCarousel = function () {
        // Locate all instances of the component
        var $components = $(".c-koh-shopping-guide-carousel"), slidesToShow;

        $components.each(function (componentIndex, element) {
            var isthumbnail = $(element).hasClass('koh-banner-thumbnail-comp');
            var isHero = $(element).hasClass('v-koh-hero');
            var isThumbnailRequired = false;
            if (isthumbnail) {
                isThumbnailRequired = true;
            }

            // Find the main carousel element
            var $carousels = $(this).children(".koh-carousel");

            $carousels.each(function (carouselIndex, ele) {

                var sliderIdName = 'slider_' + componentIndex;
                var sliderNavIdName = 'sliderNav_' + componentIndex;

                var $carousel = $(this);

                $carousel.find('.koh-thumbnail-slide-collection').attr('id', sliderIdName);
                $carousel.find('.koh-thumbnail-slide-navigation').attr('id', sliderNavIdName);

                var sliderId = '#' + sliderIdName;
                var sliderNavId = '#' + sliderNavIdName;

                // Adding a div to the carousel so we can group the controls
                var $carouselControls = $('<div class="koh-carousel-controls"></div>');
                $carousel.prepend($carouselControls);

                var $movePrevious = $('<button class="koh-carousel-control previous"><span class="icon"></span><span class="label">Previous</span></button>');
                var $moveNext = $('<button class="koh-carousel-control next"><span class="icon"></span><span class="label">Next</span></button>');
                $carouselControls.append($movePrevious);
                $carouselControls.append($moveNext);

                // Find the slides
                var $slideCollection = $carousel.find(".koh-slide-collection");
                var slideCount = $slideCollection.find(".koh-banner-slide").length;
                if (slideCount < 6) {
                    if (slideCount < 2) {
                        slidesToShow = 1;
                    } else {
                        slidesToShow = slideCount - 1;
                    }
                } else {
                    slidesToShow = 5;
                }
                var $firstSlide = $slideCollection.find(".koh-banner-slide").first();

                // For each slide collection, initialize the slides
                $slideCollection.each(function () {
                    initSlides($(this), componentIndex + carouselIndex);
                });

                var config = {
                    autoplay: false, // never autoplay on "mobile"
                    autoplaySpeed: 5e3,
                    adaptiveHeight: true,
                    dots: true,
                    dotsClass: "koh-carousel-indicators",
                    arrows: true,
                    appendArrows: $carouselControls,
                    appendDots: $carouselControls,
                    prevArrow: $movePrevious,
                    nextArrow: $moveNext,
                    pauseOnHover: true,
                    pauseOnDotsHover: true,
                    mobileFirst: true
                };
                if (isThumbnailRequired) {
                    config['asNavFor'] = sliderNavId;
                    config['fade'] = true;
                }
                if (isHero) {
                    config['autoplay'] = true;
                }

                // Initialize the carousel
                $slideCollection
                    .slick(config).on('afterChange', function (event, slick, slideIndex) {
                        var $slide = $(slick.$slides.get(slideIndex));
                        repositionControls($carouselControls, $slide);
                    });

                if (isThumbnailRequired) {
                    $(sliderNavId).slick({
                        asNavFor: sliderId,
                        slidesToShow: Math.min(5, slidesToShow),
                        slidesToScroll: 1,
                        arrows: false,
                        focusOnSelect: true,
                        centerMode: true,
                        variableWidth: true,
                        responsive: [
                            {
                                breakpoint: 550,
                                settings: {
                                    slidesToShow: Math.min(3, slidesToShow),
                                }
                            }
                        ],
                        responsive: [
                            {
                                breakpoint: 480,
                                settings: {
                                    slidesToShow: Math.min(2, slidesToShow),
                                }
                            }
                        ]
                    });
                }
                var slick = $slideCollection.slick('getSlick');
                if (slick) {
                    var $indicators = slick.$dots;
                    if ($indicators) {
                        $indicators.on('click', 'li', function () {
                            var slick = $slideCollection.slick('getSlick');
                            if (slick) {
                                slick.slickSetOption("autoplay", false, false);
                                slick.autoPlay = $.noop;
                            }
                        });
                    }
                }
                // In order to make the controls appear where Kohler wants them at all screen sizes,
                // we need to size the controls container to match the banner background, and then we
                // can position the controls accordingly.
                $(window).load(function () {
                    repositionControls($carouselControls, $firstSlide);
                });
                $(window).resize(function () {
                    waitForFinalEvent(function () {
                        repositionControls($carouselControls, $firstSlide);
                    }, 100, "carouselTimer" + componentIndex + carouselIndex);
                });

            }); // end carousel.each
        }); // end components.each
    };

    var repositionControls = function ($controlsContainer, $referenceSlide) {
        var bgWidth = "";
        var bgHeight = "";

        var $bannerBg = $referenceSlide.children(".koh-banner-background").first();
        if ($bannerBg) {
            bgWidth = $bannerBg.width();
            bgHeight = $bannerBg.height();
        } else {
            bgWidth = $referenceSlide.width();
            bgHeight = $referenceSlide.height();
        }

        $controlsContainer.width(bgWidth).height(bgHeight);
    };

    var initSlides = function ($slideCollection, containerID) {
        // Locate all instances of the Footer Nav component
        var $slides = $slideCollection.children(".koh-banner-slide");

        $slides.each(function (slideIndex) {
            var $slide = $(this);

            // For slides with an overlay, add click events for bookmark and share buttons
            var $overlayBookmark = $slide.find(".koh-overlay-bookmark");
            var $overlayBookmarkButton = $overlayBookmark.children(".koh-overlay-bookmark-button");
            var $overlayBookmarkPanel = $overlayBookmark.children(".koh-overlay-bookmark-form");
            initOverlayPopover($slide, $overlayBookmarkButton, $overlayBookmarkPanel, "bookmarkPopover" + containerID + slideIndex);

            var $overlayShare = $slide.find(".koh-overlay-social");
            var $overlayShareButton = $overlayShare.children(".koh-overlay-share-button");
            var $overlaySharePanel = $overlayShare.children(".koh-overlay-share-menu");
            if (axKOH && axKOH.utils && axKOH.utils.socialShare) {
                axKOH.utils.socialShare.initAsTooltip($slide, $overlayShareButton, $overlaySharePanel, "sharePopover" + containerID + slideIndex, "koh-carousel-popover");
            }

            // For any hotspot slides, add click event to view featured products
            if ($slide.hasClass("v-koh-hotspot")) {
                var $hotspotsContainer = $slide.children(".koh-banner-hotspots").first();

                var $hotspotHeader = $hotspotsContainer.children(".koh-banner-hotspots-header");
                $hotspotHeader.click({
                    target: $hotspotsContainer,
                    slideCollection: $slideCollection
                }, function (e) {
                    toggleOpen(e.data.target);

                    // Find the .slick-list that contains this toggle and recalculate the size
                    var $slideSet = e.data.slideCollection;
                    var $activeSlide = $slideSet.find('.slick-active');
                    $slideSet.children('.slick-list').height($activeSlide.height());
                });

                var $hotspots = $hotspotsContainer.children(".koh-banner-hotspots-collection").children(".koh-banner-hotspot");
                $hotspots.each(function (hotspotIndex) {
                    var $hotspot = $(this);
                    var $hotspotButton = $hotspot.children(".koh-banner-hotspot-icon");
                    var $hotspotPanel = $hotspot.children(".koh-banner-hotspot-tile");
                    var tipID = "hotspotPopover" + containerID + slideIndex + hotspotIndex;
                    initOverlayHotspot($slide, $hotspotButton, $hotspotPanel, tipID);
                    $(window).resize(function () {
                        waitForFinalEvent(function () {
                           initOverlayHotspot($slide, $hotspotButton, $hotspotPanel, tipID);
                        }, 100, "carouselTimer" + tipID);
                    });
                });
            }

            if ($slide.hasClass("v-koh-video")) {
                // Video slides get extra handling for their modal window
                var $backgroundLink = $slide.children(".koh-banner-background").children("a");
                var $videoPanel = $slide.children(".koh-banner-video-panel");

                initAsModal($backgroundLink, $videoPanel, "CarouselVideo" + containerID + slideIndex);
                $videoPanel.on('opened', function () {
                    var $embeddedVideo = $(this).find("iframe");
                    resizeResponsiveVideo($embeddedVideo);
                });
            }

            // At desktop sizes, the koh-banner-content completely covers the koh-banner-background, so the click
            // can't get through. So let's make the click get through?
            var $bannerBackground = $slide.children(".koh-banner-background");
            var $bannerContent = $slide.children(".koh-banner-content");
            $bannerContent.click({
                background: $bannerBackground
            }, function (e) {
                var $bannerContent = $(this);
                var $bannerBackground = e.data.background;

                if (($bannerContent.offset().top === $bannerBackground.offset().top) &&
                    ($bannerContent.offset().left === $bannerBackground.offset().left) &&
                    ($bannerContent.outerWidth() === $bannerBackground.outerWidth()) &&
                    ($bannerContent.outerHeight() === $bannerBackground.outerHeight())) {
                    var $links = $bannerBackground.children("a").first();
                    // Note: Have to do this with a native Javascript click because
                    // jQuery click does not trigger default browser behavior
                    $links[0].click();
                }
            });
        });
    };

    var initOverlayPopover = function ($slide, $button, $content, tooltipID) {
        $button.qtip({
            id: tooltipID,
            content: {
                text: $content
            },
            position: {
                my: 'bottom right', // Position my top left...
                at: 'center center', // at the bottom right of...
                container: $slide,
                viewport: true
            },
            hide: {
                when: {
                    event: 'mouseout unfocus'
                },
                fixed: true,
                delay: 100
            },
            style: {
                classes: 'koh-qtip-popover koh-carousel-popover qtip-light qtip-shadow',
                tip: false
            }
        });
    };

    var initOverlayHotspot = function ($slide, $button, $content, tooltipID) {
        // if mobile
        if ($(window).width() < 980) {
            if ($button.data('qtip') !== null) {
                $button.qtip('destroy', true);
            }
        }
        // else if desktop
        else {
            $button.qtip({
                id: tooltipID,
                content: {
                    text: $content.clone()
                },
                position: {
                    my: 'right top', // Position my top left...
                    at: 'left top', // at the bottom right of...
                    container: $slide,
                    viewport: true,
                    adjust: {
                        method: 'flip shift'
                    }
                },
                hide: {
                    when: {
                        event: 'mouseout unfocus'
                    },
                    fixed: true,
                    delay: 100
                },
                style: {
                    classes: 'koh-qtip-popover koh-carousel-popover qtip-light qtip-shadow',
                    tip: {
                        mimic: 'center',
                        offset: 50,
                        width: 30,
                        height: 15
                    }
                }
            });
        }
    };

    /* Helper Functions */
    var initAsModal = function ($button, $panel, remodalID) {
        $panel.data("remodal-id", remodalID);
        $panel.prepend('<button data-remodal-action="close" class="remodal-close"></button>');
        $panel.attr("data-koh-component", "c-koh-banner-carousel");
        $panel.addClass("c-koh-banner-carousel-modal");

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

        // When modal is closed, reset src on any YouTube iframes to stop videos
        $(document).on('closed', $remodalPanel, function (e) {
            if (e.target) {
                axKOH.utils.stopVideos(e.target);
            }
        });
    };

    var toggleOpen = function ($target) {
        if ($target.hasClass("open")) {
            $target.removeClass("open");
        } else {
            $target.addClass("open");
        }
    };

    var makeVideoResponsive = function ($videoElement) {
        var aspectRatio = $videoElement.data('aspectratio');
        var maxWidth = $videoElement.data('maxwidth');

        if (!aspectRatio || !maxWidth) {
            var width = $videoElement.width();
            var height = $videoElement.height();

            aspectRatio = height / width;

            // Find and save the aspect ratio, and the original width
            $videoElement.data('aspectratio', aspectRatio);
            $videoElement.data('maxwidth', width);
            $videoElement.removeAttr('width');
            $videoElement.removeAttr('height');
        }
    };

    var resizeResponsiveVideo = function ($videoElement) {
        var embedTags = "iframe, object, embed";
        var aspectRatio = $videoElement.data('aspectratio');
        var maxWidth = $videoElement.data('maxwidth');

        if (!aspectRatio || !maxWidth) {
            makeVideoResponsive($videoElement);
            aspectRatio = $videoElement.data('aspectratio');
            maxWidth = $videoElement.data('maxwidth');
        }

        var $parent = $videoElement.closest(':not(' + embedTags + ')');

        var responsiveWidth = 320; // in case we can't find a parent, set to smallest possible screen width
        if ($parent) {
            responsiveWidth = $parent.width();
        }
        // Don't exceed the original width (i.e. don't scale up, only down)
        responsiveWidth = Math.min(responsiveWidth, maxWidth);

        $videoElement.width(responsiveWidth);
        $videoElement.height(responsiveWidth * aspectRatio);
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

    /* Defines functions exposed through object */
    return {
        init: function () {
            initCarousel();
        }
    };
})();

$(function () {
    $(document).ready(function () {
        // executes when HTML-Document is loaded and DOM is ready
        axKOH.main.carouselManager.init();
    });
});
