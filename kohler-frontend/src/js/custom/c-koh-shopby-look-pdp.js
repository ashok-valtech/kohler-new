/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};

axKOH.main = axKOH.main || {};

axKOH.main.productDetailsManager = function () {
    var componentClass = "c-koh-shop-by-look-pdp";
    var productContentOrigHeight = 0;
    
    var initCarousels = function ($components) {
        $components && $components.each(function (componentIndex) {
            if ($(this).hasClass("c-koh-shop-by-look-details-ap")) {
                var zoom;
                var $component = $(this);
                var setZoom = function () {
                    return $(".product-zoom img").magnify()
                };
                var $carousels = $component.find(".koh-product-image-sliders");
                $carousels.each(function (carouselIndex) {
                    var $carousel = $(this);
                    var $carouselSlideCollection = $carousel.find(".product-images__primary");
                    var $carouselSlideControls = $carousel.find(".product-images__navigation");
                    var $carouselSlideControlsMobile = $carousel.find(".product-images__navigation-mobile");
                    var imageWidth = $carouselSlideCollection.find(".koh-product-iso-image").first().width();
                    var imageHeight = $carouselSlideCollection.find(".koh-product-iso-image").first().height();
                    $carouselSlideCollection.each(function () {
                        initSlides($(this), componentIndex + carouselIndex);
                        $(this).find("iframe").width(imageWidth).height(imageHeight);
                    });
                    $carouselSlideCollection.on({
                        init: function (event) {
                            $carouselSlideCollection.find(".slick-current .product-zoom-contract img");
                            "none" == $carouselSlideControlsMobile.css("display") && setTimeout(function () {
                                zoom = $(".product-zoom img").magnify()
                            }, 1000)
                        },
                        afterChange: function (event) {
                            "none" == $carouselSlideControlsMobile.css("display") && (zoom && zoom.destroy(), zoom = setZoom())
                        }
                    }).slick({
                        slidesToShow: 1,
                        slidesToScroll: 1,
                        arrows: false,
                        fade: true,
                        asNavFor: ".product-images__navigation",
                        respondTo: "slider"
                    }),
                        $carouselSlideControls.slick({
                            slidesToShow: 5,
                            slidesToScroll: 1,
                            asNavFor: ".product-images__primary",
                            dots: false,
                            centerMode: true,
                            vertical: true,
                            focusOnSelect: true,
                            arrow: true,
                            respondTo: "slider"
                        }),
                        $carouselSlideControlsMobile.slick({
                            slidesToShow: 5,
                            slidesToScroll: 1,
                            asNavFor: ".product-images__primary",
                            dots: false,
                            centerMode: true,
                            focusOnSelect: true,
                            arrows: true,
                            respondTo: "slider"
                        }),
                        $carouselSlideControls.find(".slick-next").first().text("").append('<svg version="1.1" xmlns="https://www.w3.org/2000/svg" xmlns:xlink="http://www.w3' +
                            '.org/1999/xlink" x="0px" y="0px" viewBox="4.1 -3.6 23.9 12.9" style="enable-back' +
                            'ground: new 4.1 -3.6 23.9 12.9;" xml:space="preserve" aria-label="Next"><path fi' +
                            'll="#d1d1d1" d="M4.1-2.7L5-3.6L16,7.6L27.2-3.5L28-2.7l-12,12L4.1-2.7z"></path></' +
                            'svg>'),
                        $carouselSlideControlsMobile.find(".slick-next").first().text("").append('<svg version="1.1" xmlns="https://www.w3.org/2000/svg" xmlns:xlink="http://www.w3' +
                            '.org/1999/xlink" x="0px" y="0px" viewBox="4.1 -3.6 23.9 12.9" style="enable-back' +
                            'ground: new 4.1 -3.6 23.9 12.9;" xml:space="preserve" aria-label="Next"><path fi' +
                            'll="#d1d1d1" d="M4.1-2.7L5-3.6L16,7.6L27.2-3.5L28-2.7l-12,12L4.1-2.7z"></path></' +
                            'svg>'),
                        $carouselSlideCollection.find("img").width($carouselSlideCollection.find(".product-slide").width())
                })
            } else
                $components.each(function (componentIndex) {
                    var $component = $(this);

                    // There are two sections that need to carousel: koh-product-banner and koh-product-collection-images
                    var $carousels = $component.find(".koh-product-banner, .koh-product-collection-banner");
                    $carousels.each(function (carouselIndex) {
                        var $carousel = $(this);

                        // Adding a div to the carousel so we can group the controls
                        var $carouselControls = $('<div class="koh-carousel-controls"></div>');
                        $carousel.prepend($carouselControls);

                        var $movePrevious = $('<button class="koh-carousel-control previous"><span class="icon"></span><span class="label">Previous</span></button>');
                        var $moveNext = $('<button class="koh-carousel-control next"><span class="icon"></span><span class="label">Next</span></button>');
                        $carouselControls.append($movePrevious);
                        $carouselControls.append($moveNext);

                        // Find the slides
                        var $carouselSlideCollection = $carousel.find(".koh-product-image-set");

                        // For each slide collection, initialize the slides
                        $carouselSlideCollection.each(function () {
                            initSlides($(this), componentIndex + carouselIndex);
                        });

                        // Initialize the carousel
                        $carouselSlideCollection.slick({
                            autoplay: true,
                            autoplaySpeed: 5000,
                            adaptiveHeight: true, // adjust for varying height images
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
                        }); // end init slick
                    }); // end $carousels.each
                }); // end $components.each
        })
    };

    var initSlides = function ($slideCollection, containerID) {
        var $slides = $slideCollection.children(".koh-product-image");
        $slides.each(function (slideIndex) {
            var $slide = $(this),
                $hotspotsContainer = $slide.children(".koh-banner-hotspots");
            if ($hotspotsContainer && $hotspotsContainer.length > 0) {
                $hotspotsContainer = $hotspotsContainer.first();
                var $hotspotHeader = $hotspotsContainer.children(".koh-banner-hotspots-header");
                $hotspotHeader.click({
                    target: $hotspotsContainer,
                    slideCollection: $slideCollection
                }, function (e) {
                    toggleOpen(e.data.target);
                    var $slideSet = e.data.slideCollection,
                        $activeSlide = $slideSet.find(".slick-active");
                    $slideSet
                        .children(".slick-list")
                        .height($activeSlide.height())
                });
                var $hotspots = $hotspotsContainer
                    .children(".koh-banner-hotspots-collection")
                    .children(".koh-banner-hotspot");
                $hotspots.each(function (hotspotIndex) {
                    var $hotspot = $(this),
                        $hotspotButton = $hotspot.children(".koh-banner-hotspot-icon"),
                        $hotspotPanel = $hotspot.children(".koh-banner-hotspot-tile"),
                        tipID = "hotspotPopover" + containerID + slideIndex + hotspotIndex;
                    initOverlayHotspot($slide, $hotspotButton, $hotspotPanel, tipID),
                        $(window).resize(function () {
                            waitForFinalEvent(function () {
                                initOverlayHotspot($slide, $hotspotButton, $hotspotPanel, tipID)
                            }, 100, "carouselTimer" + tipID)
                        })
                })
            }
        })
    };

    var initRequiredItems = function ($components) {
        $components && $components.each(function (componentIndex) {
            var $component = $(this),
                $button = ($component.find(".koh-product-required"), $(".koh-product-required-button")),
                $panel = $(".koh-required-products-panel"),
                remodalID = "RequiredItems" + componentIndex;
            initAsModal($button, $panel, remodalID)
        })
    };

    var initProductVideos = function ($components) {
        $components && $components.each(function (componentIndex) {
            var $component = $(this),
                $videoLinks = $component.find(".koh-product-resources-videos .v-koh-video a");
            $videoLinks.each(function (videoIndex) {
                var $videoLink = $(this),
                    videoURL = $videoLink.attr("href"),
                    apiURL = $videoLink.data("api");
                if (videoURL) {
                    var $videoPanel = $('<div class="koh-video-panel"><iframe width="628" height="354" src="' + videoURL + '" frameborder="0"></iframe></div>');
                    $videoPanel.insertAfter($videoLink);
                    var remodalID = "Video" + componentIndex + videoIndex,
                        $videoPanel = $component.find(".koh-video-panel");
                    initAsModal($videoLink, $videoPanel, remodalID),
                        $videoPanel.on("opened", function () {
                            var $embeddedVideo = $(this).find("iframe");
                            resizeResponsiveVideo($embeddedVideo)
                        })
                } else
                    $videoLink.click(function (e) {
                        e.preventDefault();
                        e.stopPropagation();
                    });
                apiURL && $.get(apiURL, function (data) {
                    if (data && data.items && data.items[0] && data.items[0].snippet) {
                        var videoTitle = data.items[0].snippet.title;
                        videoTitle && $videoLink.children(".koh-video-title").text(videoTitle);
                    }
                })
            })
        })
    };

    var initDropdowns = function ($components) {
        $components && $components.each(function () {
            var $component = $(this),
                $options = $component.find(".koh-product-options");
            $options.each(function () {
                var $optionsButton = $(this).children(".koh-product-options-button"),
                    $optionsList = $(this).children(".koh-product-options-list");
                $optionsButton.click({
                    list: $optionsList
                }, function (e) {
                    var $button = $(this);
                    var $list = e.data.list;
                    $button.hasClass("open") ? ($button.removeClass("open"), $list.removeClass("open")) : ($button.addClass("open"), $list.addClass("open"))
                })
            })
        })
    };

    var initTooltips = function ($components) {
        if ($components) {
            var $componentModals = $('.remodal-wrapper > div[data-koh-component="' + componentClass + '"]');
            var $targets = $components.add($componentModals);
            $targets.each(function (componentIndex) {
                var $component = $(this);
                var $abbreviations = $component.find("abbr[title]");
                $abbreviations.each(function (abbrIndex) {
                    var $abbr = $(this);
                    $abbr.qtip({
                        id: "abbr" + componentIndex + abbrIndex,
                        content: {
                            attr: "title"
                        },
                        position: {
                            my: "bottom center",
                            at: "top center"
                        },
                        style: {
                            classes: "koh-qtip-tooltip koh-product-detail-tooltip qtip-bootstrap",
                            tip: {
                                mimic: "center",
                                width: 30,
                                height: 15
                            }
                        }
                    })
                });
                var $colorSwatches = $component.find("button.koh-product-color");
                $colorSwatches.each(function (swatchIndex) {

                    var $colorSwatch = $(this),
                        $colorLabel = $colorSwatch.children(".label");
                    var iOsDevices = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
                    if (!iOsDevices) {
                        $colorSwatch.qtip({
                            id: "swatch" + componentIndex + swatchIndex,
                            content: {
                                text: $colorLabel.clone()
                            },
                            position: {
                                my: "top left",
                                at: "bottom left",
                                adjust: {
                                    y: 6
                                }
                            },
                            style: {
                                classes: "koh-qtip-tooltip koh-product-detail-tooltip qtip-bootstrap",
                                tip: false
                            }
                        })
                    } else {
                        $(".koh-product-variant").find("button span").css("display", "none");
                        /*$(".koh-product-variant").on("mouseover", function(event) {
                            $(this).find("button span").css("display", "none");
                        });*/
                        /*$(".koh-product-variant").on("mouseout", function(event) {
                            $(this).find("button span").css("display", "block");
                        });*/
                    }
                })
            })
        }
    };

    var initZoomAndExpand = function ($components) {
        $components && $components.each(function () {
            var expandViewer,
                viewerIndex,
                $component = $(this),
                viewers = [],
                $expander = $component.find(".product-slide-expand--open"),
                $contractor = $component.find(".product-slide-expand--close"),
                $slider = $component.find(".product-images__primary"),
                $productImages = ($(".koh-product-top-row"), $component.find(".koh-product-banner .koh-product-image"));

            var colorSelectedSku = '';

            $productImages.each(function () {
                var $productImage = $(this),
                    $imageLink = $productImage.find(".koh-background");
                $imageLink.imagesLoaded(function () {
                    $imageLink.zoom({
                        on: "grab"
                    })
                })
            }),
                "block" == $(".product-images__navigation-mobile").css("display") && $(".product-images__primary .slick-slide").each(function () {
                    var viewer = ImageViewer($(this).find("img"), {
                        maxZoom: 400,
                        snapView: false
                    });
                    $(this).find(".iv-large-image").attr("src", $(this).find(".koh-product-img").attr("data-magnify-src"));
                    viewers.push(viewer);
                }),
                $contractor.on("click", function () {
                    //$(".koh-product-content").attr('style', 'min-height:inherit;');
                    $(".koh-product-expand-container").css("display", "none"),
                        $(".koh-product-top-row").height(0),
                        $(".koh-product-details").css("top", ""),
                        $(".koh-product-image-sliders").css("margin-top", "0"),
                        "block" === $(".product-images__navigation-mobile").css("display") ? (viewerIndex = $(".product-images__primary .slick-slide").index($(".product-images__primary .slick-current")), viewers[viewerIndex].refresh(), expandViewer = expandViewer.destroy()) : $("#koh-product-expand").remove();
                        resizeForBuyNow();
                }),
                $expander.on("click", function () {
                    var kohProductDetailsSyno = parseInt($(".koh-product-details").height());
                    //$(".koh-product-content").css("min-height", kohProductDetailsSyno - 65);
                    var viewerIndex = $(".product-images__primary .slick-slide").index($(".product-images__primary .slick-current"));
                    if (viewerIndex == 0) {
                        var kohProductTopRowWidth = $(".koh-product-top-row").width();
                        var kohProductTopRowHeight = kohProductTopRowWidth * (75 / 100);

                        var kohProductExpand = 'koh-product-expand-' + viewerIndex + '-' + colorSelectedSku;
                        //console.log(kohProductExpand);
                    } else {
                        var kohProductExpand = 'koh-product-expand-' + viewerIndex;
                        var kohProductTopRowWidth = $(".koh-product-top-row").width();
                        var kohProductTopRowHeight = kohProductTopRowWidth * (75 / 100);
                    }

                    if ($("#" + kohProductExpand).length == 0) {
                        $currentSlide = $(".product-images__primary .slick-slide:eq(" + viewerIndex + ")");
                        var productSrc = $currentSlide.find(".product-zoom-expand").find(".koh-product-img").attr("src");
                        var productSrcVideo = $currentSlide.find("iframe").attr("src");
                        var zoomSrc = $currentSlide.find(".product-zoom-expand").find(".koh-product-img").attr("data-magnify-src");
                        if ($currentSlide.find(".product-zoom-expand").find(".koh-product-img").length > 0) {
                            $(".koh-product-expand-inner").append('<img id="' + kohProductExpand + '" class="koh-img-product-expand" src="' + productSrc + '" data-magnify-src="' + zoomSrc + '"/>').find('img.koh-img-product-expand').animate({
                                opacity: 1
                            }, 500),
                                $("#" + kohProductExpand).width($(".koh-product-top-row").width()),
                                $(".koh-product-expand-container").css("display", "block"),
                                $('.koh-img-product-expand').css('display', 'block'),
                                $(".koh-product-top-row").height(kohProductTopRowHeight),
                                $(".koh-product-details").css("top", kohProductTopRowHeight + 20),
                                "block" === $(".product-images__navigation-mobile").css("display") ? ($(".koh-product-image-sliders").css("margin-top", kohProductTopRowHeight - $(".koh-product-image-sliders").height() + 100), expandViewer = ImageViewer("#" + kohProductExpand, {
                                    maxZoom: 400,
                                    snapView: false
                                }),
                                    $(".koh-product-expand-container").find(".iv-large-image").attr("src", $("#" + kohProductExpand).attr("data-magnify-src"))) : $("#" + kohProductExpand).magnify();

                        } else {
                            $(".koh-product-expand-inner").append('<embed id="' + kohProductExpand + '" class="koh-img-product-expand" style="width:' + kohProductTopRowWidth + 'px; height:' + kohProductTopRowHeight + 'px; position:relative; z-index:99" src="' + productSrcVideo + '" />');
                            $(".product-images__navigation .product-slide").on("click", function () {
                                if (!($(this).hasClass("product-slide-video-thumb"))) {
                                    $(".koh-product-expand-inner").find('embed').remove();
                                }
                            });
                        }
                        //$('.magnify img').animate({ opacity: 1 }, 1000);
                    } else {
                        var len = $(".product-images__primary .slick-slide").length;
                        for (var i = 0; i < len; i++) {
                            if (i == 0) {
                                /*$(".koh-product-colors").each(function() {
                                      var $colorVariant = $(this);
                                      var sku = $colorVariant.data("koh-sku");
                                      $('#koh-product-expand-'+ i + '-' + sku).css('display', 'none');
                                });*/
                                $('.koh-img-product-expand').css('display', 'none');
                            } else {
                                $('#koh-product-expand-' + i).css('display', 'none');
                            }
                        }
                        $("#" + kohProductExpand).css('display', 'block');
                        $(".koh-product-expand-container").css("display", "block");
                        $(".koh-product-top-row").height(kohProductTopRowHeight);
                        $(".koh-product-details").css("top", kohProductTopRowHeight + 20);
                        if ($(".product-images__navigation-mobile").css("display") === "block") {
                            $(".koh-product-image-sliders").css("margin-top", kohProductTopRowHeight - $(".koh-product-image-sliders").height() + 100), expandViewer = ImageViewer("#" + kohProductExpand, {
                                maxZoom: 400,
                                snapView: false
                            }), $(".koh-product-expand-container").find(".iv-large-image").attr("src", $("#" + kohProductExpand).attr("data-magnify-src"))
                        }
                    }
                    resizeForBuyNow();
                }),
                $(".product-images__navigation-mobile").on("click", function () {
                    $(".koh-product-expand-container").css("display", "none"),
                        $(".koh-product-top-row").height(0),
                        $(".koh-product-details").css("top", ""),
                        $(".koh-product-image-sliders").css("margin-top", "0")
                    // viewerIndex = $(".product-images__primary .slick-slide").index($(".product-images__primary .slick-current")),
                    // viewers[viewerIndex].refresh(),
                    // expandViewer = expandViewer.destroy()
                }),
                $(".product-images__navigation").on("click", function () {
                    var display = $(".koh-product-expand-container").css("display");
                    if (display === "block") {
                        $('.product-slide-expand--open').trigger('click');
                    } else {
                        $(".koh-product-expand-container").css("display", "none"),
                            $("#koh-product-expand").remove(),
                            $(".koh-product-top-row").height(0),
                            $(".koh-product-details").css("top", ""),
                            $(".koh-product-image-sliders").css("margin-top", "0");
                    }
                }),
                $(".product-images__navigation .product-slide").on("click", function () {
                    if (!($(this).hasClass("product-slide-video-thumb"))) {
                        var $vURL = $(".product-images-video-wrapper iframe").attr("src");
                        $(".product-images-video-wrapper iframe").attr("src", $vURL);
                    }

                }),
                $(".koh-product-color").on("click", function (e) {
                    colorSelectedSku = $(this).parent().data('koh-sku');
                    $(".koh-product-iso-image").siblings(".iv-image-view").find(".iv-large-image").attr("src", $(e.target).closest(".koh-product-variant").attr("data-koh-image-zoom"));
                })
        })
    };

    var initDataSwitcher = function ($components) {
        $components && $components.each(function () {
            var $component = $(this);
            var isAP = $component.hasClass("c-koh-shop-by-look-details-ap");
            var $productDetails = $component.find(".koh-product-details").first();
            var $productBannerSlider = $component.children(".koh-product-banner").children(".koh-product-image-set").first();
            var $primaryBanner = $productBannerSlider.find(".koh-product-image.koh-primary-image").find(".koh-background");
            var $productSKU = $productDetails.find(".koh-product-sku.koh-sku-k");
            var $productSKUData = $productDetails.find(".koh-product-sku.koh-sku-k").data('product-sku');
            var $defaultCategory = $productDetails.find(".koh-product-sku.koh-sku-k").data('default-category');
            var $productPrices = $productDetails.children(".koh-product-prices");
            $productDetails.children(".koh-product-artist-editions, .koh-product-colors");
            $(".koh-modal-product-colors").each(function () {
                var $modalColorCollection = $(this);
                $modalColorCollection.find(".koh-modal-product-color").click(function () {
                    var colorText = $(this).text();
                    $modalColorCollection.find(".koh-modal-product-color-selected").text(colorText)
                })
            });
            window.history.replaceState({}, $productSKU.text());
            $(".koh-product-colors").each(function () {
                var $colorCollection = $(this),
                    $selectedColorLabel = $colorCollection.children(".koh-product-artist-editions-selected, .koh-product-colors-selected"),
                    $colorVariants = $colorCollection.find(".koh-product-variant");
                $colorVariants.each(function () {
                    var $colorVariant = $(this),
                        productData = {
                            imageURL: $colorVariant.data("koh-image"),
                            imageZoomURL: $colorVariant.data("koh-image-zoom"),
                            imageExpandURL: $colorVariant.data("koh-image-expand"),
                            imageExpandZoomURL: $colorVariant.data("koh-image-expand-zoom"),
                            sku: $colorVariant.data("koh-sku"),
                            skuk: $colorVariant.data("koh-sku-k"),
                            prices: $colorVariant.data("koh-price"),
                            colorName: $colorVariant.data("koh-color"),
                            countries: $colorVariant.data("koh-countries"),
                            itemNo: $colorVariant.data("koh-itemno"),
                            defaultCategory: $colorVariant.data("default-category"),
                            retailer: $colorVariant.data("koh-retailer")
                        },
                        productFields = {
                            banner: $primaryBanner,
                            sku: $productSKU,
                            prices: $productPrices,
                            color: $selectedColorLabel
                        };
                    $colorVariant.children(".koh-product-color").click({
                        variantSet: $colorVariants,
                        variant: $colorVariant,
                        product: productData,
                        fields: productFields
                    }, function (e) {
                        //$('.product-images__navigation .slick-slide:first-child').trigger('click');
                        //$('.product-images__navigation-mobile .slick-slide:first-child').trigger('click');
                        var display = $(".koh-product-expand-container").css("display");
                        var $variant = e.data.variant,
                            $variantSet = e.data.variantSet,
                            productData = e.data.product,
                            productFields = e.data.fields;
                        productFields
                            .sku
                            .text(productData.skuk),
                            isAP ? ($(".koh-product-iso-image").attr("src", productData.imageURL).attr("data-magnify-src", productData.imageZoomURL), $("#koh-product-expand").attr("src", productData.imageExpandURL).attr("data-magnify-src", productData.imageExpandZoomURL), "none" == $(".product-slider-mobile").css("display") && $(".product-zoom img").magnify()) : (productFields.banner.children("img").attr("src", productData.imageURL), productFields.prices.find(".koh-current-price > .value").text(productData.prices.current), productFields.prices.find(".koh-previous-price > .value").text(productData.prices.previous), productFields.prices.find(".koh-discounted-price > .value").text(productData.prices.discounted)),
                            productFields
                                .color
                                .text(productData.colorName),
                            $variantSet.removeClass("koh-selected-variant"),
                            $variant.addClass("koh-selected-variant"),
                            $selectedColorLabel.text($colorVariant.text());
                        window.history.replaceState({}, productData.sku);

                        $('.product-images__navigation .slick-slide.product-slide-first').trigger('click');
                        $('.product-images__navigation-mobile .slick-slide.product-slide-first').trigger('click');

                        if (display === "block") {
                            $('.product-slide-expand--open').trigger('click');
                        }
                        var $kohAvailableSku = $('.koh-country-available-in .koh-available-sku');
                        if ((typeof $kohAvailableSku !== 'undefined')) {
                            $kohAvailableSku.remove();
                        }
                        var $countryAvailableIn = $('.koh-country-available-in');
                        if ($countryAvailableIn != undefined) {
                            if ((productData.countries != undefined) && (productData.countries !== "")) {
                                productData.countries.split("|,").forEach(function (item) {
                                    if (item !== '') {
                                        var itemArray = item.split("__");
                                        var countryId = itemArray[1];
                                        var firstSku = itemArray[2];
                                        item = itemArray[3];
                                        var siteURL = $("#" + countryId + "_SiteUrl").val();
                                        var $countryDiv = "";
                                        if (siteURL === '') {
                                            $countryDiv = $("<div class='koh-available-sku'>" + item + "</div>");
                                        } 
                                        $countryAvailableIn.append($countryDiv);
                                    }
                                });
                            }
                        }
                        if ((productData.retailer === undefined) || (productData.retailer === "")) {
                        	var $kohRetailer = $('#exclusiveAt');
                            if ((typeof $kohRetailer !== 'undefined')) {
                            	$kohRetailer.empty();
                            }
                        } else {
                        	var $kohRetailer = $('#exclusiveAt');
                        	if ((typeof $kohRetailer !== 'undefined')) {
                        		$kohRetailer.empty();
                        		var featureRetailersLabel = $('#featureRetailersLabel').val();
                        		$kohRetailer.append('<span>' + featureRetailersLabel + ':</span>');
                        		var retailerImgUrlBase = $("#retailerImgUrl").val();
                        		var buyNowLabel = $("#buyNowLabel").val();
                        		productData.retailer.split("|,").forEach(function (item) {
                                    if (item !== '') {
                                        var itemArray = item.split("__");
                                        var image = itemArray[0];
                                        var retailerURL = itemArray[1];
                                        var retailerImgUrl = retailerImgUrlBase.replace('{0}',image);
                                        var $retailerDiv = "<div class='koh-retail-buy-content sc7'>" +
                                        						"<div class='koh-flex-image-button'>" +
                                        							"<div class='koh-img-div-wrap-one'>" +
                        												"<img src='"+ retailerImgUrl + "' class='koh-retail-logo display-none-md-only'>" + 
                        												"<img src='"+ retailerImgUrl + "'  class='retail-logo display-block-md-only display-none-sm'>" +
                        											"</div>" +
                        											"<a href='"+ retailerURL +"' target='_blank' rel='noopener noreferrer'>" +
						        										"<div id='product-detail__retail-buy-now' class='koh-btn-buy-now-green buttonBuyNow'>" + 
						        											buyNowLabel +
						        										"</div>" +
						        									"</a>" +	
                                        						"</div>" +
                                        					"</div>";
                                    	$kohRetailer.append($retailerDiv);
                                    }
                                });
                        	}
                        }
                        setTimeout(function(){ resizeForBuyNow() }, 200);
                    });
                });

                var $bookmarkButton = $component.find(".koh-product-bookmark button");
                $bookmarkButton.each(function () {
                    $(this).qtip({
                        content: {
                            text: $(this).next(".koh-product-popover")
                        },
                        position: {
                            my: "top right",
                            at: "center center",
                            viewport: true
                        },
                        show: {
                            event: "click"
                        },
                        hide: {
                            event: "unfocus",
                            fixed: true
                        },
                        style: {
                            classes: "koh-qtip-popover koh-release-popover qtip-light qtip-shadow",
                            tip: false
                        }
                    })
                });

                var $productShare = $component.find(".koh-product-share"),
                    $productShareButton = $productShare.children(".koh-product-button"),
                    $productSharePanel = $productShare.children(".koh-product-popover");
                axKOH && axKOH.utils && axKOH.utils.socialShare && axKOH.utils.socialShare.initAsTooltip($productShare, $productShareButton, $productSharePanel)
            })
        })
    };

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
            modifier: ""
        });
        $button.data("remodal-target", remodalID),
            $button.click({
                target: $remodalPanel
            }, function (e) {
                e.preventDefault();
                e.stopPropagation();
                var $target = e.data.target;
                $target.open();
            }),
            $(document).on("closed", $remodalPanel, function (e) {
                e.target && axKOH.utils.stopVideos(e.target)
            })
    };

    var initOverlayHotspot = function ($slide, $button, $content, tooltipID) {
        axKOH.utils.debugLog("Handling hotspots");
        $(window).width() < 980 ? (axKOH.utils.debugLog("destroyOverlayHotspot"), null !== $button.data("qtip") && $button.qtip("destroy", true)) : (axKOH.utils.debugLog("initOverlayHotspot"), $button.qtip({
            id: tooltipID,
            content: {
                text: $content.clone()
            },
            position: {
                my: "right top",
                at: "left top",
                container: $slide,
                viewport: true,
                adjust: {
                    method: "flip shift"
                }
            },
            show: {
                event: "click"
            },
            hide: {
                event: "unfocus",
                fixed: true
            },
            style: {
                classes: "koh-qtip-popover koh-carousel-popover qtip-light qtip-shadow",
                tip: {
                    mimic: "center",
                    offset: 50,
                    width: 30,
                    height: 15
                }
            }
        }))
    };

    var initCompare = function ($components) {
        $components && $components.each(function () {
            var $component = $(this);
            var currentCompareProducts = [];
            var itemno = $component.find(".koh-compare-add").first().attr("data-product-itemno");
            $(".c-koh-compare-panel").find(".koh-compare-remove").each(function () {
                currentCompareProducts.push($(this).attr("data-product-itemno"))
            }),
                $.inArray(itemno, currentCompareProducts) > -1 && $(".koh-product-tools").addClass("product-added")
            /* ,
	            $component.find(".koh-compare-add").on("click", function(event) {
	                $(".koh-product-tools").addClass("product-added")
	            }),
	            $component.find(".koh-compare-removes").on("click", function(event) {
	                $(".koh-product-tools").removeClass("product-added")
	            })*/
        })
    };

    var toggleOpen = function ($target) {
        $target.hasClass("open") ? $target.removeClass("open") : $target.addClass("open")
    };

    var makeVideoResponsive = function ($videoElement) {
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

    var resizeResponsiveVideo = function ($videoElement) {
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

    /*var throttleScroll = function() {
        window.addEventListener("scroll", _.throttle(scrollToTop, 1e3)),
            $("#back-to-top").click(function() {
                $("body,html").animate({
                    scrollTop: 0
                }, 500)
            })
    };

    var scrollToTop = function() {
        $(window).scroll(function() {
            $(this).scrollTop() >= 500 ? $("#back-to-top").css("opacity", 1) : $("#back-to-top").css("opacity", 0)
        })
    };*/

    var timers = {};
    var waitForFinalEvent = function (callback, ms, uniqueId) {
        uniqueId || (uniqueId = "UNIQUEID"),
            timers[uniqueId] && clearTimeout(timers[uniqueId]),
            timers[uniqueId] = setTimeout(callback, ms)
    };

    var resizeCarouselVideos = function () {
        var isoImage = $(".koh-product-iso-image").first();
        $(".product-images__primary").find("embed").width(isoImage.width()).height(isoImage.height());
    };

    // Checking parameter exist in the URL    
    var checkURLParam = function (parameter) {
        var reg = new RegExp('[?&]' + parameter + '=([^&#]*)', 'i');
        var string = reg.exec(window.location.href);
        return string ? string[1] : undefined;
    };


    var initBackToResult = function () {
        // History state adding to back to result link
        $(".koh-back-to-results a").on("click", function (e) {
            e.preventDefault();
            if ((history.length) >= 2) {
                window.history.back(-1);
            }
        });

    };
    
    var setProductContentOrigHeight = function () {
    	productContentOrigHeight = $('.koh-product-content').outerHeight(true);
    };
    var resizeForBuyNow =  function () {
    	var sectionHeight = 0;
    	if ($(window).width() > 767) {
    		var kohProductTopRow = $('.koh-product-top-row').outerHeight(true);
    		if (kohProductTopRow === 0) {
    			kohProductTopRow = $('.koh-product-image-sliders').outerHeight(true);
    		}
    		sectionHeight = kohProductTopRow;
    		var kohProductContent = $('.koh-product-content')
    		sectionHeight = sectionHeight + productContentOrigHeight;
    		var kohProductDetails = $('.koh-product-details');
	    	var productDetailsHeight = kohProductDetails.outerHeight(true);
	    	var productBreadcrumbHeight = $('.koh-product-breadcrumb').outerHeight(true);
	    	var productDetailsHeight =  productDetailsHeight + kohProductDetails.position().top;
	    	var pairsWithHeight = 0;
	    	$('.koh-product-pairs-with').each(function() {
	    		pairsWithHeight = pairsWithHeight + $(this).outerHeight(true);
	    	});
	    	var productDetailsHeightForContent = (productDetailsHeight - kohProductTopRow);
	        if (productDetailsHeightForContent > productContentOrigHeight) {
	        	kohProductContent.outerHeight(productDetailsHeightForContent);
        	} else {
        		kohProductContent.outerHeight(productContentOrigHeight);
        	}
	        if (productDetailsHeight >= (sectionHeight)) {
	        	$('.c-koh-shop-by-look-pdp').height((productDetailsHeight + productBreadcrumbHeight + pairsWithHeight));
	        } else {
	        	$('.c-koh-shop-by-look-pdp').height((sectionHeight + productBreadcrumbHeight + pairsWithHeight));
	        }
    	}
    };
    
    return {
        initialize: function () {
            initBackToResult();
            var $components = $("." + componentClass);
            initCarousels($components);
            initRequiredItems($components);
            initProductVideos($components);
            initDropdowns($components);
            initCompare($components);
            initTooltips($components);
            initZoomAndExpand($components);
            initDataSwitcher($components);
            //throttleScroll();
            //initShareMail($components);
        },
        onLoad: function () {
            resizeCarouselVideos();
            setProductContentOrigHeight();
            resizeForBuyNow();
        }
    };
}();


$(function () {
    $(document).ready(function () {
        // executes when HTML-Document is loaded and DOM is ready
        axKOH.main.productDetailsManager.initialize();
        $(document).on('click', '.koh-share-email', function (e) {
            $('.koh-qtip-popover').hide();
            emailSharePopUp();
        });
        $(document).on('click', '#koh-share-email-btn', function () {
            validateShareEmailForm();
        });
    });

    $(window).on("load", function () {
        // Refresh tracking for PDP
        if (window.performance.navigation.type === 1) {
            if (typeof (Storage) !== undefined) {
                var lastVisitedPageTrackRefresh = sessionStorage.getItem("prodListClickTrackRefresh");
                if (lastVisitedPageTrackRefresh !== undefined && lastVisitedPageTrackRefresh !== null) {
                    sessionStorage.setItem("prodListClickTrack", true);
                }
            }
        }
        // Removing local storage variable of Product list page track
        if (typeof (Storage) !== undefined) {
            var lastVisitedPageTrack = sessionStorage.getItem("prodListClickTrack");
            if ((navigator.appName == 'Microsoft Internet Explorer' || !!(navigator.userAgent.match(/Trident/) || navigator.userAgent.match(/rv:11/)) || (typeof $.browser !== "undefined" && $.browser.msie == 1)) && history.length === 1) {
                var getClass = $(".koh-product-breadcrumb").find('.koh-back-to-results');
                if (getClass.hasClass('koh-back-to-results')) {
                    getClass.removeClass('koh-back-to-results').addClass('koh-back-to-results-default');
                } else {
                    getClass.addClass('koh-back-to-results-default');
                }
            }
            else if (lastVisitedPageTrack !== undefined && lastVisitedPageTrack !== null) {
                sessionStorage.removeItem('prodListClickTrack');
                // Back to result breadcrumb
                var getClass = $(".koh-product-breadcrumb").find('.koh-back-to-results-default');
                if (getClass.hasClass('koh-back-to-results-default')) {
                    getClass.removeClass('koh-back-to-results-default').addClass('koh-back-to-results');
                } else {
                    getClass.addClass('koh-back-to-results');
                }
                sessionStorage.setItem("prodListClickTrackRefresh", true);
            } else {
                var lastVisitedPageTrackRefresh = sessionStorage.getItem("prodListClickTrackRefresh");
                if (lastVisitedPageTrackRefresh !== undefined && lastVisitedPageTrackRefresh !== null) {
                    sessionStorage.removeItem('prodListClickTrackRefresh');
                }
                var getClass = $(".koh-product-breadcrumb").find('.koh-back-to-results');
                if (getClass.hasClass('koh-back-to-results')) {
                    getClass.removeClass('koh-back-to-results').addClass('koh-back-to-results-default');
                } else {
                    getClass.addClass('koh-back-to-results-default');
                }
            }
        }
        axKOH.main.productDetailsManager.onLoad();
    });
});

$(document).on("click", ".koh-back-to-results-default a", function (e) {
    e.preventDefault();
    var listPageUrl = $(".koh-product-breadcrumb ul > li:last-child a").attr('href');
    document.location.href = listPageUrl;
});

// Android Ios Expand Button is Hidden
var isMobile = {
    Android: function () {
        return navigator.userAgent.match(/Android/i);
    },
    iOS: function () {
        return navigator.userAgent.match(/iPad/i);
    },
    Windows: function () {
        return navigator.userAgent.match(/IEMobile/i);
    },
    any: function () {
        return (isMobile.Android() || isMobile.iOS() || isMobile.Windows());
    }
};

if (isMobile.any()) {
    $('button.product-slide-expand--open').css("display", "none")
}

var windowOldWidth = $(window).width();
$(window).resize(function () {
    var windowNewWidth = $(window).width();
    if (windowOldWidth != windowNewWidth) {
        window.location.href = window.location.href;
    }
});
