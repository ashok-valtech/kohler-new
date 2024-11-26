/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};
var mobileScreenWidth = 980;
var smallScreenWidth = 768;

axKOH.main = axKOH.main || {};

axKOH.main.headerManager = function() {
    var selectors = {
        // global navigation
        globalNav: ".koh-global-navigation",
        globalNavComponents: ".koh-global-navigation-components",
        globalNavSpacer: ".koh-global-navigation-spacer",
        // branding bar
        brandingBar: ".c-koh-branding-bar",
        brandContent: ".koh-brands",
        // menu bar
        menuBar: ".c-koh-navigation-bar",
        menuBarNav: ".c-koh-navigation",
        menuBarSearch: ".c-koh-site-search",
        menuBarCart: ".c-koh-shopping-cart-menu",
        menuBarNavHeader: ".koh-primary-nav > .koh-primary-nav-header",
        menuBarNavMenu: ".koh-primary-nav > .koh-primary-nav-menu",
        // back to top button
        topButton: ".koh-back-top"
    };

    var $globalNav = null;
    var $globalNavMenu = null;
    var $globalNavComponents = null;
    var $globalNavSpacer = null;
    var $brandingBar = null;
    var $brandContent = null;
    var $brandingBarMobile = null;
    var $menuBar = null;
    var $menuBarNav = null;
    var $menuBarSearch = null;
    var $menuBarSearchMobile = null;
    var $menuBarCart = null;
    var $menuBarCartMobile = null;
    var $menuBarNavHeader = null;
    var $navToggle = null;
    var $searchToggle = null;

    var globalNavHeight = null;
    var menuBarHeight = null;
    var brandingBarHeight = null;
    var promoBannerHeight = null;
    var navMenuHeight = null;

    var initGlobalNav = function() {
        // Find the global nav; if there's more than one, we have issues, so filter to just the first, and hide any extras
        $globalNav = reduceToFirst($(selectors.globalNav));

        // Branding bar
        $brandingBar = reduceToFirst($globalNav.children(selectors.brandingBar));
        $brandContent = $brandingBar.find(".koh-brands");

        // Menu bar
        $menuBar = reduceToFirst($globalNav.children(selectors.menuBar));
        $menuBarNav = reduceToFirst($menuBar.children(selectors.menuBarNav));
        $menuBarSearch = reduceToFirst($menuBar.children(selectors.menuBarSearch));
        $menuBarCart = reduceToFirst($menuBar.children(selectors.menuBarCart));
        $menuBarNavHeader = reduceToFirst($menuBarNav.find(selectors.menuBarNavHeader));
        $navToggle = reduceToFirst($menuBarNavHeader.children(".koh-primary-nav-toggle"));
        $searchToggle = reduceToFirst($menuBarNavHeader.children(".koh-primary-search-toggle"));
        $menuBarCartMobile = reduceToFirst($menuBarNavHeader.children(".koh-shopping-cart-menu-mobile"));

        // Main global nav menu
        $globalNavMenu = reduceToFirst($menuBarNav.find(selectors.menuBarNavMenu));

        // Create mobile nav items in correct order via cloning
        createMobileMenu();

        // Add wrapper around components
        $globalNavComponents = $("<div></div>");
        $globalNavComponents.addClass(selectors.globalNavComponents.replace(".", ""));
        $globalNav.wrapInner($globalNavComponents);
        $globalNavComponents = reduceToFirst($globalNav.children(selectors.globalNavComponents)); // Need this because wrapInner creates a new element

        // Add spacer element to control header spacing
        $globalNavSpacer = $("<div></div>");
        $globalNavSpacer.addClass(selectors.globalNavSpacer.replace(".", ""));
        $globalNav.append($globalNavSpacer);

        // Set up click event handlers
        addClickEvents();
    };

    var resizeNav = function() {
        // Save heights of branding bar and global nav
        if (isMobile()) {
            promoBannerHeight = $(".c-koh-promotion-ribbon").outerHeight();
            menuBarHeight = $menuBarNavHeader.outerHeight();
            navMenuHeight = $(window).innerHeight() - (promoBannerHeight + menuBarHeight);
            $($globalNavMenu).css("height", navMenuHeight);
            $($globalNavMenu).css("background-color", "#FFFFFF");
            $($globalNavComponents).css("background", "rgba(0,0,0,0.5)");
            brandingBarHeight = 0;
            globalNavHeight = menuBarHeight;
        } else {
            //reset navigation menu style for desktop
            $($globalNavMenu ).css("max-width", "100%");
            $($globalNavMenu).css("height", "auto");
            $($globalNavComponents).css("background", "$koh-color-white");
            $($menuBarSearch).css("border-bottom", "none");
            menuBarHeight = $menuBar.outerHeight();
            brandingBarHeight = $brandingBar.outerHeight();
            globalNavHeight = menuBarHeight + brandingBarHeight;

            if ($('.koh-link-company-brand').hasClass('open')) {
                $brandingBar.addClass("collapsed");
                $('.koh-link-company-brand').removeClass('open');
                toggleOpen($brandContent, true);

                menuBarHeight = $menuBar.outerHeight();
                brandingBarHeight = 0;
                globalNavHeight = menuBarHeight + brandingBarHeight;
            }
        }

        //console.log("Nav height: "+globalNavHeight+" = "+menuBarHeight+" + "+brandingBarHeight);

        // Resize the spacer
        setNavSpacerHeight();

        // Reset the scrolling in case the menu is smaller than the window
        if (isMobile()) {
            resetNavMenuScroll();
        }
    };

    var scrollNav = function() {
        /*
        If the window top offset is at or above the branding bar, unfix the header and show the branding bar.
        If the window top offset is below the branding bar, fix the header to the top of the screen and hide the branding bar.
        */

        var scrollHeight = $(window).scrollTop();

        if (scrollHeight > brandingBarHeight) {
            // make the nav fixed and hide the branding bar
            $globalNav.addClass("fixed");
            $brandingBar.addClass("collapsed");
            $('.koh-link-company-brand').removeClass('open');
            $brandContent.removeClass('open').hide();
            $brandingBar.height(brandingBarHeight);
        } else {
            // make the nav not fixed and show the branding bar
            $globalNav.removeClass("fixed");
            removeInlineCSS($globalNav, "display");
            $brandingBar.removeClass("collapsed");
            removeInlineCSS($brandingBar, "display");
            $brandingBar.height(brandingBarHeight);
            $('.koh-link-company-brand').removeClass('open');
            $brandContent.removeClass('open').hide();
        }


        /* If the user hovers over the menu, display the branding bar by sliding it down. */
        $globalNav.hover(function(e) {
            e.stopPropagation();
            e.preventDefault();
            if (!isMobile() && $(this).hasClass("fixed")) {
                if ($brandingBar.hasClass("collapsed")) {
                    $brandingBar.stop().slideDown();
                    $brandingBar.removeClass("collapsed");
                }
            }
        }, function(e) {
            e.stopPropagation();
            e.preventDefault();
            if (!isMobile() && $(this).hasClass("fixed") && !$(".koh-brands").hasClass('open') && (brandingBarHeight < 33)) {
                if (!$brandingBar.hasClass("collapsed")) {
                    $brandingBar.stop().slideUp();
                    $brandingBar.addClass("collapsed");
                }
            }
        });
    };

    var createMobileMenu = function() {
        // Mobile menu is assembled, in order, from the following items: Search, Menu Bar, Branding Bar
        $brandingBarMobile = $brandingBar.clone();
        $brandingBarMobile.removeClass(selectors.brandingBar.replace(".", ""));
        $brandingBarMobile.addClass(selectors.brandingBar.replace(".c-", "") + "-mobile");
        $brandingBarMobile.addClass("koh-cloned").addClass("koh-mobile-nav");
        $brandingBar.addClass("koh-desktop-nav");
        $globalNavMenu.append($brandingBarMobile);        

        $menuBarSearchMobile = $menuBarSearch.clone();
        $menuBarSearchMobile.removeClass(selectors.menuBarSearch.replace(".", ""));
        $menuBarSearchMobile.addClass(selectors.menuBarSearch.replace(".c-", "") + "-mobile");
        $menuBarSearchMobile.addClass("koh-cloned").addClass("koh-mobile-nav");
        $menuBarSearch.addClass("koh-desktop-nav");
        $globalNavMenu.prepend($menuBarSearchMobile);

        $($navToggle).find(".icon").css("display", "block");
        $($navToggle).find(".cross-icon-nav").css("display", "none");
        $($searchToggle).find(".icon").css("display", "block");
        $($searchToggle).find(".cross-icon-search").css("display", "none");

        $menuBarCartMobile.addClass("koh-mobile-nav");
        $menuBarCart.addClass("koh-desktop-nav");
    };

    var addClickEvents = function() {
        // Mobile nav toggle
        $navToggle.click(function() {
            if (isMobile()) {
                //adding style to nav menu for new design
                $($globalNavMenu ).css("max-width", "85%");
                $(".koh-nav-parents").css("display", "block");
                $(".koh-branding-bar-mobile").css("display", "block");
                $($menuBarSearchMobile).find(".koh-site-search-form").css("display", "none");
                $($searchToggle).find(".icon").css("display", "block");
                $($searchToggle).find(".cross-icon-search").css("display", "none");
                if (!$globalNavMenu.hasClass("open")) {
                    $($navToggle).find(".icon").css("display", "none");
                    $($navToggle).find(".cross-icon-nav").css("display", "block");
                    $("body").css("height", "100%");
                    $("body").css("overflow", "hidden");
                    $($globalNavComponents).css("height", "100vh");
                    $globalNavComponents.addClass("scrollable");

                } else {
                    $($navToggle).find(".icon").css("display", "block");
                    $($navToggle).find(".cross-icon-nav").css("display", "none");
                    $("body").css("height", "auto");
                    $("body").css("overflow", "scroll");
                    $($globalNavComponents).css("height", "auto");
                    $globalNavComponents.removeClass("scrollable");
                }
                toggleOpen($globalNavMenu, true, function() {
                    resetNavMenuScroll();
                    if ($menuBarSearchMobile.hasClass("open")) {
                        toggleOpen($menuBarSearchMobile, true);
                    }
                });
            }
        });

        // Mobile search form toggle
        $searchToggle.click(function() {
            if (isMobile()) {
                //add style to search menu for new design
                $($globalNavMenu).css("max-width", "100%");
                $($menuBarSearchMobile).find(".koh-site-search-form").css("display", "block");
                $($menuBarSearchMobile).find(".koh-site-search-form").css("width", "80%");
                $(".koh-nav-parents").css("display", "none");
                $(".koh-branding-bar-mobile").css("display", "none");
                $($navToggle).find(".icon").css("display", "block");
                $($navToggle).find(".cross-icon-nav").css("display", "none");
                $($globalNavComponents).css("height", "auto");
                if (!$globalNavMenu.hasClass("open")) {
                    toggleOpen($globalNavMenu, true);
                }

                toggleOpen($menuBarSearchMobile, true, function() {
                    if (!$menuBarSearchMobile.hasClass("open")) {
                        $($searchToggle).find(".icon").css("display", "block");
                        $($searchToggle).find(".cross-icon-search").css("display", "none");
                        $("body").css("height", "auto");
                        $("body").css("overflow", "scroll");
                        toggleOpen($globalNavMenu, true);
                        $globalNavComponents.removeClass("scrollable");
                    }else {
                        $($searchToggle).find(".icon").css("display", "none");
                        $($searchToggle).find(".cross-icon-search").css("display", "block");
                        $("body").css("height", "100%");
                        $("body").css("overflow", "hidden");
                    }

                });

            }
        });

        // Branding bar toggle (mobile and desktop)
        $brandingBarMobile.find(".koh-link-company-brand").click({
                target: $brandingBarMobile.find(".koh-brands")
            },
            function(e) {
                e.stopPropagation();
                e.preventDefault();
                if (isMobile()) {
                    toggleOpen($(this));
                    toggleOpen(e.data.target, true, function() {
                        resetNavMenuScroll();
                    });
                }
            }
        );

        $brandingBar.find(".koh-link-company-brand").click({
                target: $brandingBar.find(".koh-brands")
            },
            function(e) {
                e.stopPropagation();
                e.preventDefault();
                if (!isMobile()) {
                    toggleOpen(e.data.target, true);
                }
            }
        );
        $(".koh-brands .worldwide-menu-top").click(function(e) {
            if ($(this).hasClass("open")) {
                $(this).removeClass("open")
            } else {
                $(this).addClass("open");
            }
        });

        $(".koh-site-search-form").submit(function(e) {
            for (var searchBox = $(".koh-site-search-form #koh-nav-searchbox"), isEmpty = !0, i = 0; i < searchBox.length; i++)
                searchBox[i].value.length > 0 && (isEmpty = !1);
            isEmpty && e.preventDefault()
        });

        // Main nav parent sub menu toggle
        var $globalNavMenuOpenables = $globalNavMenu.find(".koh-nav-parents > li").children("a,span,.koh-nav-subnav");
        $globalNavMenu.find(".koh-nav-parents > li").each(function() {
            var $navListItem = $(this);
            var $navLink = $navListItem.children("a,span");
            var $navSubmenu = $navListItem.children(".koh-nav-subnav");

            $navLink.click({
                target: $navSubmenu,
                siblings: $globalNavMenuOpenables
            }, function(e) {
                if (!$navLink.hasClass("single")) {
                    e.stopPropagation();
                    e.preventDefault();

                    if ($('.koh-link-company-brand').hasClass('open')) {
                        $brandingBar.addClass("collapsed");
                        $('.koh-link-company-brand').removeClass('open');
                        toggleOpen($brandContent, true);
                    }



                    // Find all the siblings and close them
                    var $siblings = e.data.siblings.not($(this)).not(e.data.target);
                    $siblings.removeClass("open");
                    $siblings.css("display", "");

                    // Open the clicked one
                    toggleOpen($(this));
                    if (isMobile) {
                        toggleOpen(e.data.target, true, function() {
                            resetNavMenuScroll();
                        });
                    } else {
                        toggleOpen(e.data.target, true);
                    }
                }
            });
        });
    };

    var resetNavMenuScroll = function() {
        var windowHeight = $(window).innerHeight();
        var bodyHeight = $("body").innerHeight();
        var navHeight = globalNavHeight;
        if (isMobile()) {
            if ($globalNavMenu.hasClass("open")) {
                navHeight = navHeight + $globalNavMenu.outerHeight();
            }
        } else {
            // if not mobile, need to account for more things open
            if ($globalNavMenu.hasClass("open")) {
                navHeight = navHeight + $globalNavMenu.outerHeight();
            }
            if (!$brandingBar.hasClass("collapsed")) {
                navHeight = navHeight + $brandingBar.outerHeight();
            }
        }

        //console.log("Window ("+windowHeight+") vs Body ("+bodyHeight+") vs Nav ("+navHeight+")");

        if (navHeight >= windowHeight && !$("body").hasClass("unscrollable")) {
            if (bodyHeight <= windowHeight) {
                $("body").css("overflow", "scroll");
            }
            $globalNavComponents.addClass("scrollable");
            $("body").addClass("unscrollable");
            if (bodyHeight <= windowHeight) {
                setTimeout(function() {
                    $("body").css("overflow", "");
                }, 0.5);
            }
        } else if (navHeight < windowHeight && $("body").hasClass("unscrollable")) {
            $globalNavComponents.removeClass("scrollable");
            $("body").removeClass("unscrollable");
        }
    };

    var setNavSpacerHeight = function() {
        $globalNavSpacer.css('height', globalNavHeight);
    };

    var reduceToFirst = function($object) {
        // For the global nav, if there is ever more than one of an object, it will cause issues.
        // Reduce the set to just the first match, and hide any extras
        var $firstObject = $object.first();
        if ($object.length > 1) {
            $object.slice(1).hide();
        }
        return $firstObject;
    };

    var isMobile = function() {
        // Rather than check window size, let's take advantage of the fact that the
        // hamburger toggle is hidden at desktop sizes. If it's displayed, then we're
        // at mobile. Otherwise we're not.
        return ($navToggle.css("display") !== "none");
    };

    var toggleOpen = function($target, animate, callbackAfter) {
        $target.hasClass("open") ? ($target.selector == $brandContent.selector && $brandingBar.animate({
            height: 0
        }, 800), animate && $target.slideUp(800, callbackAfter), $target.removeClass("open"), $($globalNavComponents).css("height", "auto")) : ($target.selector == $brandContent.selector && $brandingBar.animate({
            height: 32 + $brandContent.outerHeight()
        }, 800), animate && $target.slideDown(800, callbackAfter), $target.addClass("open"))
    };

    var removeInlineCSS = function($target, cssProperty) {
        $target.css(cssProperty, "");
        if ($target.attr("style") === "") {
            $target.removeAttr("style");
        }
    };

    topButton = $('body').find('.koh-back-top button');
    sectionTop = $(topButton).closest('section');

    // Back to top button
    var initBackTopButton = function() {
        if ($(topButton).length) {
            var topTarget = sectionTop;
            $(topButton).click(function(e) {
                e.preventDefault;
                if ($(topTarget).length) {
                    $('html, body').animate({
                        scrollTop: $(topTarget).offset().top - 80
                    }, 250);
                }
            });
        }
    };

    // Back to top fadein
    var initBackTopDistance = function() {
        if ($(topButton).length) {
            var topDistance = sectionTop.offset().top;
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
	
    /*Working of promotion ribbon*/
    var pause = true;
    var promotionRibbon = function () {
    	var counter = 0;
        var ribbonItemCount = $('#promotionRibbonItems').val();
        if (ribbonItemCount != 0) {
        	counter = 1;
        }
        var timer = setInterval(function () {
            if (pause) {
                showDiv()
            }
        }, 5000);
        function showDiv() {
            $('.c-koh-promotion-ribbon-item')
                .stop()
                .hide()
                .filter(function () {
                    return this.id.match('c-koh-promotion-ribbon-item' + counter);
                })
                .show();
            $('.c-koh-promotion-ribbon-item').hover(function (ev) {
                pause = false;
            }, function (ev) {
                pause = true
            });
            (counter == ribbonItemCount) ? counter = 0 : counter++;
        }
    }



  //ribbon target link
    $(".c-koh-promotion-ribbon-item").click(function(e) {
        var getRibbonUrl = $(this).find('.koh-ribbon-link').attr('href');
        if(getRibbonUrl.includes("http")){
            window.open(getRibbonUrl,"_blank");
        } else {
            window.location.href = getRibbonUrl;
        }
    	
    });

    var addTopMargin = function() {
        var url = new URL(window.location.href);
        var path = url.pathname;
        var ribbonItemCount = $("#promotionRibbonItems").val();
        if(path === "/" || path === "/en") {
            if (ribbonItemCount == 0) {
                if (window.screen.width < smallScreenWidth) {
	            	$(".koh-page").css("margin-top", "95px");
	            }else if(window.screen.width >= smallScreenWidth && window.screen.width < mobileScreenWidth) {
	            	$(".koh-page").first().css("margin-top", "40px");
	            }else {
                    $(".koh-page").first().css("margin-top", "0px");
                }
            }else {
                if (window.screen.width < smallScreenWidth) {
	            	$(".koh-page").css("margin-top", "55px");
	            }
            }
        }else {
            if (ribbonItemCount == 0) {
                if (window.screen.width < smallScreenWidth) {
	            	$(".koh-page").css("margin-top", "95px");
	            }else if(window.screen.width >= smallScreenWidth && window.screen.width < mobileScreenWidth) {
	            	$(".koh-page").first().css("margin-top", "40px");
	            }else {
                    $(".koh-page").first().css("margin-top", "0px");
                }
            }else {
                if (window.screen.width < smallScreenWidth) {
	            	$(".koh-page").css("margin-top", "55px");
	            }
            }
            var sixDesignerUrl = $("#sixDesignerUrlId").val();
            if(window.location.pathname !== sixDesignerUrl) {
                $(".koh-page").css("border-top", "10px solid #FFFFFF");
                $(".koh-page").css("border-bottom", "10px solid #FFFFFF");
            }
            
        }
    }

    var refreshPageOnResize = function() {
        var windowOldWidth = $(window).width();
        $(window).resize(function () {
            var windowNewWidth = $(window).width();
            if (windowOldWidth != windowNewWidth) {
                window.location.href = window.location.href;
            }
        });
    }
    var hideFromPrintInTW = function() {
        var thankyouPageURLocal = localStorage.getItem("thankyouPageURL");
        if(thankyouPageURLocal) {
            var newThankyouUrl = "";
            var newWindowPath = "";
            if (thankyouPageURLocal.startsWith("/en/")) {
                newThankyouUrl = thankyouPageURLocal.slice(3, -1);
            } else {
                newThankyouUrl = thankyouPageURLocal;
            }

            if (window.location.pathname.startsWith("/en/")) {
                newWindowPath = window.location.pathname.slice(3)
            } else {
                newWindowPath = window.location.pathname;
            }

            var styleElement =  $('<style>').text("").appendTo('head');
            if(newWindowPath.includes(newThankyouUrl)) {
                $(".koh-quick-link-shortcut").addClass("non-printable");
                $(".c-koh-promotion-ribbon-item").addClass("non-printable");
                var pageStyleRule = "@page :header{display: none !important;} @page :footer {display: none !important;}";
                styleELement = $('<style>').text(pageStyleRule).appendTo('head');
            }else {
                styleElement.remove();
            }
        }
    }
    

    
    
    /* var htmlEscape = function (value) {
         return value
             .replace(/&/g, '&amp;')
             .replace(/"/g, '&quot;')
             .replace(/'/g, '&#39;')
             .replace(/</g, '&lt;')
             .replace(/>/g, '&gt;')
             .replace(/\(/g, "&#40;")
             .replace(/\)/g, "&#41;")
             .replace(/\+/g, '&#43;')
             .replace(/\//g, '&#x2F;');
     };

     $(".koh-site-search-form").submit(function (e) {
         var searchBox = $(".koh-site-search-form #koh-nav-searchbox");
         searchBox.val(htmlEscape$(searchBox.value));
     });*/

    /* Defines functions exposed through footerManager object */
    return {
        initialize: function() {
            initGlobalNav();
            /* Initialize Back to Top Button */
            initBackTopButton();
            /* Show/Hide Back to Top Button */
            initBackTopDistance();
			/* To display many ribbon description */
            promotionRibbon();

            addTopMargin();
            /* To refresh the page on viewport size changes */
            refreshPageOnResize();
           /*  To remove items from print in thankyou page of form */
            hideFromPrintInTW();
        },
        onResize: function() {
            resizeNav();
        },
        onScroll: function() {
            scrollNav();
        }
    };
}();

$(function() {
    $(document).ready(function() {
        // executes when HTML-Document is loaded and DOM is ready
        axKOH.main.headerManager.initialize();
        axKOH.main.headerManager.onResize();
    });
    $(window).load(function() {
        axKOH.main.headerManager.onScroll();
    });
    $(window).resize(function() {
        axKOH.main.headerManager.onResize();
    });
    $(window).scroll(function() {
        axKOH.main.headerManager.onScroll();
    });
});
