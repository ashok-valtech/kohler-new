/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};

axKOH.main = axKOH.main || {};

axKOH.main.productDetailsManager = function () {
    var componentClass = "c-koh-product-details";
    var productContentOrigHeight = 0;
    var pdpLocaleValue = $('#pdpLocaleValue').val();
    
    var initCarousels = function ($components) {
        $components && $components.each(function (componentIndex) {
            if ($(this).hasClass("c-koh-product-details-ap")) {
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
                            slidesToShow: 3,
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
                            slidesToShow: 3,
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
                    var price = $(this).parent().data('koh-price');
                    if(price) {
                      var $skuDiv = $(".koh-product-skus-colors");
                      if($skuDiv.find(".koh-product-price").length === 0) {
                        var $priceDiv = $("<div>", {"class": "koh-product-price"});
                        $priceDiv.text(price); 
                        $(".koh-product-sku").after($priceDiv);
                      }
                    }else {
                        if($(".koh-product-price").length > 0) {
                            $('.koh-product-price').remove();
                        }
                    }
                    $(".koh-product-iso-image").siblings(".iv-image-view").find(".iv-large-image").attr("src", $(e.target).closest(".koh-product-variant").attr("data-koh-image-zoom"));
                })
        })
    };

    var initDataSwitcher = function ($components) {
        $components && $components.each(function () {
            var $component = $(this);
            var isAP = $component.hasClass("c-koh-product-details-ap");
            var $productDetails = $component.find(".koh-product-details").first();
            var $productBannerSlider = $component.children(".koh-product-banner").children(".koh-product-image-set").first();
            var $primaryBanner = $productBannerSlider.find(".koh-product-image.koh-primary-image").find(".koh-background");
            var $productSKU = $productDetails.find(".koh-product-sku.koh-sku-k");
            var $productSKUData = $productDetails.find(".koh-product-sku.koh-sku-k").data('product-sku');
            var $defaultCategory = $productDetails.find(".koh-product-sku.koh-sku-k").data('default-category');
            var $productInriverPrice = $productDetails.find(".koh-product-price");
            var urlPath = window.location.pathname;
            $productDetails.children(".koh-product-artist-editions, .koh-product-colors");
            $(".koh-modal-product-colors").each(function () {
                var $modalColorCollection = $(this);
                $modalColorCollection.find(".koh-modal-product-color").click(function () {
                    var colorText = $(this).text();
                    $modalColorCollection.find(".koh-modal-product-color-selected").text(colorText)
                })
            });
            urlPath = urlPath + "?skuid=" + $productSKUData;
            if ($defaultCategory && $defaultCategory !== "") {
                urlPath = urlPath + "&defaultCategory=" + $defaultCategory;
            }
            window.history.replaceState({}, $productSKU.text(), urlPath);
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
                            retailer: $colorVariant.data("koh-retailer"),
                            bigcommerceURL: $colorVariant.data("koh-bigcommerce")
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
                    }, function (e) {
                        //$('.product-images__navigation .slick-slide:first-child').trigger('click');
                        //$('.product-images__navigation-mobile .slick-slide:first-child').trigger('click');
                        var display = $(".koh-product-expand-container").css("display");
                        var $variant = e.data.variant,
                            $variantSet = e.data.variantSet,
                            productData = e.data.product,
                            productFields = e.data.fields,
                            urlPath = window.location.pathname;
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
                            $productInriverPrice.text($colorVariant.attr("data-koh-price"));
                        urlPath = urlPath + "?skuid=" + productData.sku;
                        if (productData.defaultCategory !== "") {
                            urlPath = urlPath + "&defaultCategory=" + $defaultCategory;
                        }
                        window.history.replaceState({}, productData.sku, urlPath);

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
                                        } else {
                                            $countryDiv = $("<div class='koh-available-sku'><a href=\"" + siteURL + "productDetails/" + productData.itemNo + "?skuid=" + firstSku + "\" target=\"_blank\">" + item + "</a></div>");
                                        }
                                        $countryAvailableIn.append($countryDiv);
                                    }
                                });
                            }
                        }
                        if(pdpLocaleValue === "TH"){
                        	var $bigCommerceContactFormURL = $('#bigCommerceContactFormURL');
                        	$bigCommerceContactFormURL.empty();
                        	if ((productData.bigcommerceURL === undefined) || (productData.bigcommerceURL === "")) {
	                            	var pdpshareContactFormLink = $('#shareOnContactFormLink').val();
	                            	var pdpEnquiryWithUsLable = $('#EnquiryWithUsLable').val();
	                            	var $enquiryWithUsDiv = "<div class='koh-product-locator'>" + 
	                            								"<a href='"+ pdpshareContactFormLink +"' target='_self' rel='noopener noreferrer'>" + pdpEnquiryWithUsLable + "</a>"
                            								"</div>";
	                            	$bigCommerceContactFormURL.append($enquiryWithUsDiv);
	                        } else {
	                        		var buyNowLabel = $("#buyNowLabel").val();
                                    var retailerURL = productData.bigcommerceURL;
                                    var $retailerDiv = "<div class='koh-product-locator'>" + 
			            									"<a href='"+ retailerURL +"' target='_blank' rel='noopener noreferrer'>" + buyNowLabel + "</a>"
			            								"</div>";
                                    $bigCommerceContactFormURL.append($retailerDiv);
	                        		}
                        }else{
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
    	if ($(window).width() > 979) {
    		var pdpPromoBanner = $('.koh-pdp-promo-banner');
    		var pdpPromoBannerHeight = pdpPromoBanner.outerHeight(true);
    		var kohProductTopRow = $('.koh-product-top-row').outerHeight(true);
    		if (kohProductTopRow === 0) {
    			kohProductTopRow = $('.koh-product-image-sliders').outerHeight(true);
    		}
    		sectionHeight = kohProductTopRow;
    		var kohProductContent = $('.koh-product-content');
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
	        	$('.c-koh-product-details').height((productDetailsHeight + productBreadcrumbHeight + pairsWithHeight + pdpPromoBannerHeight));
	        } else {
	        	$('.c-koh-product-details').height((sectionHeight + productBreadcrumbHeight + pairsWithHeight + pdpPromoBannerHeight));
	        }
    	}
    };
    
if(pdpLocaleValue === "TH"){
	
    //product video carousel
    var initProductVideoCarousel = function () {
        // Locate all instances of the component
        var $components = $(".c-koh-product-video-carousel"), slidesToShow;

        $components.each(function (componentIndex, element) {
            var isthumbnail = $(element).hasClass('koh-banner-thumbnail-comp');
            var isThumbnailRequired = false;
            if (isthumbnail) {
                isThumbnailRequired = true;
            }

            // Find the main carousel element
            var $carousels = $(this).children(".koh-carousel");

            $carousels.each(function (carouselIndex, ele) {
                var $carousel = $(this);

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
                    config['autoplay'] = true;
                }

                // Initialize the carousel
                $slideCollection
                    .slick(config).on('afterChange', function (event, slick, slideIndex) {
                        var $slide = $(slick.$slides.get(slideIndex));
                        repositionControls($carouselControls, $slide);
                    });

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
    
    //similar product carousel
    var similarProductCarousel = function () {
        $('.koh-product-tiles').slick({
            autoplay: false,
            infinite: true,
            slidesToShow: 4,
            slidesToScroll: 1,
            dots: false,
            arrows:true,
            responsive: [
               
                {
                    breakpoint: 480,
                    settings: {
                        slidesToShow: 1,
                    }
                },
                
                {
                	breakpoint: 840,
                    settings: {
                        slidesToShow: 2,
                        slidesToScroll: 2,
                    }
                },
                
            ]

        });
    };
}
    
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
            if(pdpLocaleValue === "TH"){
	            initProductVideoCarousel();
	            similarProductCarousel();
            }
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

var callFunctionFromScript = function (inst) {
    //setTimeout(function(){ inst.open(); }, 400);
    inst.open();
    scaleCaptcha();
};
var scaleCaptcha = function () {

    // Width of the reCAPTCHA element, in pixels
    var reCaptchaWidth = 304;
    // Get the containing element's width
    var containerWidth = $('.captcha-container').width();
    var captchaElements = $('.g-recaptcha');
    // Only scale the reCAPTCHA if it won't fit
    // inside the container
    if (containerWidth <= reCaptchaWidth) {
        var scale = 0.82;
        captchaElements.css('transform', 'scale(' + scale + ')').css('-webkit-transform', 'scale(' + scale + ')').css('-ms-transform', 'scale(' + scale + ')').css('-o-transform', 'scale(' + scale + ')').css('transform-origin', '0 0').css('-webkit-transform-origin', '0 0').css('-ms-transform-origin', '0 0').css('-o-transform-origin', '0 0');
        $('.captcha-container').height(68);
    }
}

/* Email Popup Functions Starts Here */

var pdpShareEmailPopupSuccessTitle = $('#pdpEmailPopupSuccessTitle').val();
var pdpShareEmailPopupSuccessMessage = $('#pdpEmailPopupSuccessMessage').val();
var pdpShareEmailPopupFailureMessage = $('#pdpEmailPopupFailureMessage').val();
var pdpShareEmailPopupNameValidationMessage = $('#pdpEmailPopupNameValidationMessage').val();
var pdpShareEmailPopupEamilValidationMessage1 = $('#pdpEmailPopupEamilValidationMessage1').val();
var pdpShareEmailPopupEamilValidationMessage2 = $('#pdpEmailPopupEamilValidationMessage2').val();
var pdpShareEmailPopupRecipientsEamilValidationMessage1 = $('#pdpEmailPopupRecipientsEamilValidationMessage1').val();
var pdpShareEmailPopupRecipientsEamilValidationMessage2 = $('#pdpEmailPopupRecipientsEamilValidationMessage2').val();
var pdpShareEmailPopupCaptchaValidationMessage = $('#pdpEmailPopupCaptchaValidationMessage').val();
var brandNameAndShortdescriptionValue = $('#brandNameAndShortdescription').val();
var googleCapthcaClientSecretKey = $('#capthcaClientSecretKey').val();
var privacyPolicyShareEmailURL = $('#privacyPolicyShareEmailURLPDP').val();
var pdpShareEmailAFriendTitle = $('#pdpEmailAFriendTitle').val();
var pdpShareEmailPopupPrivacyPolicy = $('#pdpEmailTemplatePrivacyPolicy').val();
var pdpShareEmailPopupHelpText1 = $('#pdpEmailPopupHelpText1').val();
var pdpShareEmailPopupHelpText2 = $('#pdpEmailPopupHelpText2').val();
var pdpShareEmailPopupHelpText3 = $('#pdpEmailPopupHelpText3').val();
var pdpShareEmailPopupShareTitle = $('#pdpEmailPopupShareTitle').val();
var pdpShareEmailPopupInputBoxTitle1 = $('#pdpEmailPopupInputBoxTitle1').val();
var pdpShareEmailPopupInputBoxTitle2 = $('#pdpEmailPopupInputBoxTitle2').val();
var pdpShareEmailPopupInputBoxTitle3 = $('#pdpEmailPopupInputBoxTitle3').val();
var pdpShareEmailPopupInputBoxTitle4 = $('#pdpEmailPopupInputBoxTitle4').val();
var pdpShareEmailPopupSendButtonText = $('#pdpEmailPopupSendButtonText').val();
var pdpShareEmailPopupCancelButtonText = $('#pdpEmailPopupCancelButtonText').val();
var pdpShareEmailPopupPlaceHolderMessage = $('#pdpEmailPopupPlaceHolderMessage').val();

var pdpShareEmailTemplateCopyRight = $('#pdpEmailTemplateCopyRight').val();
var pdpShareEmailTemplateCurrentURL = $('#pdpEmailTemplateCurrentURL').val();

var emailSharePopUp = function () {
    var componentClass = "c-koh-product-details";
    var shareBtn = $(document).find(".koh-share-email");
    var sharePopUpTargetID = shareBtn.data('remodal-id');
    var parentPopDiv = $(document).find("#koh-share-email-remodal");
    parentPopDiv.data("remodal-id", sharePopUpTargetID);
    var inst = $(parentPopDiv).remodal();

    // Adding script for google captcha
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.onload = function () {
        callFunctionFromScript(inst);
    }
    script.src = 'https://www.google.com/recaptcha/api.js';
    head.appendChild(script);

    var popupBody = '';
    popupBody += '<button data-remodal-action="close" class="remodal-close"></button>';
    popupBody += '<h4 class="modal-title">' + pdpShareEmailAFriendTitle + '</h4>';
    popupBody += '<span class="koh-product-share-description"><span>' + pdpShareEmailPopupShareTitle + '</span> ' + brandNameAndShortdescriptionValue + '</span>';
    popupBody += '<div class="koh-share-email-form">'
    popupBody += '<div class="col-md-12 koh-share-note-head"><span class="koh-req-head">' + pdpShareEmailPopupHelpText1 + '</span> <a href="' + privacyPolicyShareEmailURL + '" class="koh-privacy-policy" target="_blank" >' + pdpShareEmailPopupPrivacyPolicy + '</a></div>';
    popupBody += '<form id="koh-share-email-form" class="form-horizontal" role="form">';
    popupBody += '<div class="form-group row"><label class="control-label col-sm-4" for="pwd">' + pdpShareEmailPopupInputBoxTitle1 + '</label><div class="col-sm-8"><input type="text" class="form-control koh-mar-bot-5" id="name" name="name"/></div></div>';
    popupBody += '<div class="form-group row"><label class="control-label col-sm-4" for="email">' + pdpShareEmailPopupInputBoxTitle2 + '</label><div class="col-sm-8"><input type="email" class="form-control" id="email" name="email"/><span class="form-instructions koh-mar-top-5">' + pdpShareEmailPopupHelpText2 + '</span></div></div>';
    popupBody += '<div class="form-group row"><label class="control-label col-sm-4" for="email">' + pdpShareEmailPopupInputBoxTitle3 + '</label><div class="col-sm-8"><input type="email" class="form-control" id="recipient-email" name="recipient-email"/><span class="form-instructions koh-mar-top-5">' + pdpShareEmailPopupHelpText3 + '</div></div>';
    popupBody += '<div class="form-group row"><label class="control-label col-sm-4" for="comment">' + pdpShareEmailPopupInputBoxTitle4 + '</label><div class="col-sm-8"><textarea class="form-control koh-mar-bot-5" rows="3" id="message" name="message" placeholder="' + pdpShareEmailPopupPlaceHolderMessage + '"></textarea></div></div>';
    popupBody += '<div class="form-group row"><div class="col-sm-offset-4 col-sm-8 captcha-container"><div class="g-recaptcha" data-sitekey="' + googleCapthcaClientSecretKey + '" name="g-recaptcha-response"></div></div></div>';
    popupBody += '<div class="form-group row"><div class="col-sm-offset-4 col-sm-8"><div class="row"><button id="koh-share-email-btn" type="button" class="col-xs-6 col-sm-6 btn btn-default koh-primary-btn">' + pdpShareEmailPopupSendButtonText + '</button><button type="button" class="col-xs-6 col-sm-6 btn btn-default koh-cancel-btn" data-remodal-action="close">' + pdpShareEmailPopupCancelButtonText + '</button></div></div></div></div>';
    popupBody += '</div>';
    $(parentPopDiv).html(popupBody);
}

var validateShareEmailForm = function () {
    if ($('.koh-failure-message').length > 0) {
        $('.koh-failure-message').remove();
    }
    var isValid = true;
    var formDataArray = $('#koh-share-email-form').serializeArray();
    var dataObj = {};
    $(formDataArray).each(function (index, field) {
        dataObj[field.name] = field.value;
    });
    var shareName = dataObj['name'];
    var shareEmail = dataObj['email'];
    var shareRecipientEmail = dataObj['recipient-email'];
    var shareMessage = dataObj['message'];
    var recaptcha7 = dataObj['g-recaptcha-response'];
    if (shareName == '' || shareName == null) {
        isValid = false;
        showError('#name', pdpShareEmailPopupNameValidationMessage);
    }
    if (shareEmail == '' || shareEmail == null) {
        isValid = false;
        showError('#email', pdpShareEmailPopupEamilValidationMessage1);
    }
    if (shareRecipientEmail == '' || shareRecipientEmail == null) {
        isValid = false;
        showError('#recipient-email', pdpShareEmailPopupRecipientsEamilValidationMessage1);
    } else {
        var getAllEmails = shareRecipientEmail.split(',');
        var validEmailStatus = true;
        for (var i = 0; i < getAllEmails.length; i++) {
            var eachEmail;
            // Trim the excess whitespace.
            eachEmail = getAllEmails[i].trim();
            // Add additional code here, such as:
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            if (reg.test(eachEmail) == false) {
                validEmailStatus = false;
            }
        }
        if (validEmailStatus == false) {
            isValid = false;
            showError('#recipient-email', pdpShareEmailPopupRecipientsEamilValidationMessage2);
        } else {
            removeError('#recipient-email');
        }
    }
    if (recaptcha7 == '' || recaptcha7 == null) {
        isValid = false;
        showError('.g-recaptcha', pdpShareEmailPopupCaptchaValidationMessage);
        $('.g-recaptcha > div').css({
            'border': '1px solid #e7102e'
        })
    }
    if (isValid == true) {
        var shareOnEmailLink = $('#shareOnEmailLink').val();
        $.ajax({
            url: shareOnEmailLink,
            type: "POST",
            cache: !1,
            data: {
                'name': shareName,
                'email': shareEmail,
                'recipientsemail': shareRecipientEmail,
                'message': shareMessage,
                'g-recaptcha-response': recaptcha7,
                'currentURL': pdpShareEmailTemplateCurrentURL,
                'copyRight': pdpShareEmailTemplateCopyRight,
                'brandName': brandNameAndShortdescriptionValue
            },
            success: function (data) {
                //console.log('data------', data);
                if (data.success == true) {
                    $('.modal-title').html(pdpShareEmailPopupSuccessTitle);
                    $('.koh-share-email-form').html(pdpShareEmailPopupSuccessMessage);
                    $('.koh-share-email-form').prev().remove();
                    $('.koh-share-email-form').addClass('koh-success-message');
                } else {
                    var errorMessage = '<div class="koh-failure-message">' + pdpShareEmailPopupFailureMessage + '</div>';
                    $(".modal-title").after(errorMessage); //data.message
                }
            }
        })
    }
}
var showError = function (ele, errorMessage) {
    $(ele).parent().find('.koh-error').remove();
    $(ele).after("<div class='koh-error'>" + errorMessage + "</div>");
    $(ele).css({
        'border-color': '#e7102e'
    })
    $(ele).closest('.form-group').css({
        'margin-bottom': '6px'
    })
}
var removeError = function (ele) {
    $(ele).parent().find('.koh-error').remove();
    $(ele).css({
        'border-color': '#cccccc'
    })
    $(ele).closest('.form-group').css({
        'margin-bottom': '15px'
    })
}
var validateEmail = function (ele) {
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var currentVal = $(ele).val();
    if (reg.test(currentVal) == false) {
        return false;
    }
    return true;
}
$(document).on('keyup change', '#koh-share-email-form input', function (e) {
    var currentName = $(this).attr('name');
    var currentId = '#' + $(this).attr('id');
    if (currentName == 'name') {
        var valLength = $(this).val().length;
        if (valLength == 0) {
            isValid = false;
            showError(currentId, pdpShareEmailPopupNameValidationMessage);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentName == 'email') {
        var valLength = $(this).val().length;
        if (valLength == 0) {
            isValid = false;
            showError(currentId, pdpShareEmailPopupEamilValidationMessage1);
        } else if (!validateEmail(currentId)) {
            isValid = false;
            showError(currentId, pdpShareEmailPopupEamilValidationMessage2);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentName == 'recipient-email') {
        var currentVal = $(this).val();
        var valLength = currentVal.length;
        if (valLength == 0) {
            isValid = false;
            showError(currentId, pdpShareEmailPopupRecipientsEamilValidationMessage1);
        } else {
            var getAllEmails = currentVal.split(',');
            var validEmailStatus = true;
            for (var i = 0; i < getAllEmails.length; i++) {
                var eachEmail;
                // Trim the excess whitespace.
                eachEmail = getAllEmails[i].trim();
                var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if (reg.test(eachEmail) == false) {
                    validEmailStatus = false;
                }
            }
            if (validEmailStatus == false) {
                isValid = false;
                showError(currentId, pdpShareEmailPopupRecipientsEamilValidationMessage2);
            } else {
                isValid = true;
                removeError(currentId);
            }
        }
    }
})
/* Email Popup Functions ends Here */

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

function handleSimilarProduct(currentItemNo) {
    if (currentItemNo != undefined) {
        if (localStorage.getItem("similarProducts_" + currentItemNo) == null) {
            $(".koh-similar-products-title").hide();
        }
        if (localStorage.getItem("similarProducts_" + currentItemNo) != null) {
            var similarProducts = JSON.parse(localStorage.getItem("similarProducts_" + currentItemNo));
            if (similarProducts.length == 1) {
                if (similarProducts.length == 1 && similarProducts[0].id == currentItemNo) {
                    $(".koh-similar-products-title").hide();
                }
            } else if (similarProducts.length == 0) {
                $(".koh-similar-products-title").hide();
            }
            for (var i = 0; i < similarProducts.length; i++) {
                if (similarProducts[i].id != currentItemNo) {
                    var productObject = similarProducts[i];
                    var divtile = $("<div></div>", {
                        class: "koh-product-tile"
                    });
                    var div = $("<div></div>", {
                        class: "koh-product-tile-content"
                    });
                    var hrefcontent = productObject.content;
                    productObject.content = hrefcontent.replace('onclick', 'data-onclick');
                    div.html(productObject.content);
                    div.appendTo(divtile);
                    divtile.appendTo("div#koh-product-tiles")
                }
            }
        }
    }
}

function getNextProduct(object) {
    if (null != object) {
        var i = 0;
        var productData = [];
        var similarProduct = [];
        var currentProductCategory = $(object).data('category');
        var productId = $(object).data('id');
        $(".koh-product-tile-content").each(function () {
            var product = {};
            var html = $(this).html();
            var obj = $(this).children("a");
            var objId = obj.attr('id');
            var objCategory = obj.data('category');
            if (objCategory === currentProductCategory) {
                objId = obj.data('id');
                product.id = objId;
                product.content = html;
                productData.push(product);
            }
        });
        for (var i = 0; i < productData.length; i++) {
            var product = productData[i];
            if (product.id == productId) {
                var product1 = productData[i + 1];
                var product2 = productData[i + 2];
                var product3 = productData[i + 3];
                var product4 = productData[i + 4];
                if (product1 != undefined) {
                    similarProduct.push(product1);
                }
                if (product2 != undefined) {
                    similarProduct.push(product2);
                }
                if (product3 != undefined) {
                    similarProduct.push(product3);
                }
                if (product4 != undefined) {
                    similarProduct.push(product4);
                }
                try {
                    localStorage.removeItem("similarProducts_" + productId);
                } catch (e) {

                }
                localStorage.setItem("similarProducts_" + productId, JSON.stringify(similarProduct));
            }
        }
        var productlink = $(object).data("productlink");
        if ("undefined" != productlink) {
            var skuId = $(object).data("skuid"),
                url = productlink + "/" + productId + "?skuid=" + skuId;
            object.href = url
        }
    }
}

// var windowOldWidth = $(window).width();
// $(window).resize(function () {
//     var windowNewWidth = $(window).width();
//     if (windowOldWidth != windowNewWidth) {
//         window.location.href = window.location.href;
//     }
// });
