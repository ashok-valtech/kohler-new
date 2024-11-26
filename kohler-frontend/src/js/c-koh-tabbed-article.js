/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};

axKOH.main = axKOH.main || {};
axKOH.main.componentManager = axKOH.main.componentManager || {};

axKOH.main.componentManager.tabbedArticle = (function () {
    var componentClass = "c-koh-tabbed-article";

    var initComponents = function () {
        // Find all instances of the component on the page
        var $components = $("." + componentClass);

        // For each component instance, initialize it
        $components.each(function (componentIndex) {
            var $component = $(this);

            var $articleControls = $(".koh-article-controls").first();
            var $articleSet = $articleControls.children(".koh-article-set").first();
            var $controlsHeight = $articleControls.outerHeight();
            var $setHeight = $articleSet.outerHeight();

            var $menuButton = $articleControls.children(".koh-article-select").first();
            $menuButton.click({
                list: $articleSet
            }, function (e) {
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

            if ($setHeight > $controlsHeight) {
                $articleControls.addClass('contained');
            }

            $(document).click({
                target: $menuButton,
                list: $articleSet
            }, function (e) {
                var $button = e.data.target;
                var $list = e.data.list;

                if (!$(e.target).closest($button).length && !$(e.target).is($button)) {
                    if ($button.hasClass("open")) {
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
    $(document).ready(function () {
        // executes when HTML-Document is loaded and DOM is ready
        axKOH.main.componentManager.tabbedArticle.initialize();

        $(".koh-article-set a").each(function () {
            var href = $(this).attr("href");
            if ((window.location.href == href) || (window.location.href == href + '/')) {
                $(this).attr('class', 'active');
            }
        });
    });
});
