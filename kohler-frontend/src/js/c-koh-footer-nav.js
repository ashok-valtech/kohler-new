/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};
var smallScreenWidth = 768;

axKOH.main = axKOH.main || {};

axKOH.main.footerManager = (function() {

var selectors = {
    //hamburger icon
    navToggle: ".koh-primary-nav-toggle",
    //footer ul
    footerList: ".koh-nav-items",
    socialIcons: ".koh-social-links",
    findStore: ".koh-fixed-find-store-btn",
    chatWithUs: ".koh-chat-with-us",
    whatsappChatBtn: ".koh-whatsApp-btn"
};  

var $navToggle = null;
var $footerList = null;
var $footerNavItem = null;
var $socialLinkIcons = null;
var $findStoreBtn = null;
var $chatBtn = null;
var $whatsappBtn = null;

    var initFooterNav = function() {
        // Locate all instances of the Footer Nav component
        var $components = $(".c-koh-footer-nav");
        $navToggle = reduceToFirst($(selectors.navToggle));
        $footerList = reduceToFirst($(selectors.footerList));
        $footerNavItem = $footerList.find(".koh-nav-section");
        $socialLinkIcons = reduceToFirst($(selectors.socialIcons));
        $findStoreBtn = reduceToFirst($(selectors.findStore));
        $chatWithUs = reduceToFirst($(selectors.chatWithUs));
        $whatsappBtn = reduceToFirst($(selectors.whatsappChatBtn));


        $components.each(function() {
            // Locate the master nav items collection
            var $navItemsContainer = $(this).find(".koh-nav-items");

            $navItemsContainer.each(function() {
                // Locate all the nav sections and add click handling to each
                var $navSections = $(this).children(".koh-nav-section");

                // What we're looking for is all of the koh-nav-section-items elements that
                // live inside sections in our main container. These are the siblings that 
                // should collapse when one of them is opened.
                var $navSectionTitlesCollection = $navSections.children(".koh-nav-section-title");
                var $navSectionItemsCollection = $navSections.children(".koh-nav-section-items");

                // For each nav section, add a click handler
                $navSections.each(function() {
                    var $navSection = $(this);

                    // Find the nav section title element
                    var $navSectionTitle = $navSection.children(".koh-nav-section-title");

                    // Find the nav section items element
                    var $navSectionItems = $navSectionTitle.next(".koh-nav-section-items");

                    // We get the variables outside the click handler and pass them in, rather
                    // than getting them in the click handler, to reduce page processing (structure stays
                    // the same, so no reason to grab the elements on every click
                    $navSectionTitle.click({
                            navTarget: $navSectionItems,
                            navSiblings: $navSectionItemsCollection,
                            navSiblingTitles: $navSectionTitlesCollection
                        },
                        function(e) {
                            // Check whether we're at mobile size. If so, toggle the active class. If not, do nothing.
                            e.stopPropagation();
                            e.preventDefault();

                            // Don't really like hard-coding the width breakpoint here, but for now this will do
                            if ($(window).width() < 980) {
                                var $navTargetTitle = $(this);
                                var $navTarget = e.data.navTarget;
                                var $navSiblings = e.data.navSiblings;
                                var $navSiblingTitles = e.data.navSiblingTitles;

                                if ($navTargetTitle.hasClass('active')) {
                                    // If already open, close it
                                    $navTargetTitle.removeClass('active');
                                    $navTarget.slideUp();
                                } else {
                                    // If not open, close all siblings, and open it
                                    $navSiblingTitles.removeClass('active');
                                    $navSiblings.slideUp();

                                    $navTargetTitle.addClass('active');
                                    $navTarget.slideDown();
                                }
                            }
                        }
                    ); // end click
                }); // end navSections.each
            }); // end navItemsContainer.each
        }); // end components.each        
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

    var styleFooter = function() {
        if($(window).width() < smallScreenWidth) {
            $($footerNavItem).last().css("display", "none");
            $($socialLinkIcons).css("display", "block");
            $($findStoreBtn).css("display", "none");
            $($chatBtn).css("bottom", "20px");
            $($whatsappBtn).css("bottom", "20px");
        }else {
            $($footerNavItem).last().css("display", "block");
            $($socialLinkIcons).css("display", "none");
            if($chatWithUs.length === 0 && $whatsappBtn.length === 0) {
                $($findStoreBtn).css("bottom", "12px");
            }
        }
    };


    /* Defines functions exposed through footerManager object */
    return {
        init: function() {
            initFooterNav();
        },
        setSocialStyle: function() {
            styleFooter();
        }
    };
})();

$(function() {
    $(document).ready(function() {
        // executes when HTML-Document is loaded and DOM is ready
        axKOH.main.footerManager.init();
        axKOH.main.footerManager.setSocialStyle();

        // Back to top common button
        var throttleScroll = function() {
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
        };
        throttleScroll();
    });
});