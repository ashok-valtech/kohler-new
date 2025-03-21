/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

"use strict";

var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};

axKOH.main.walkinBath = function () {
    var componentClass = "c-koh-walking-bath";
    var walkinBathScroll = (function ($components) {
        var $scrollSpeed = 1000;
        var $names = $components.find('.koh-anchor-scroll');
		if ($(window).width() >= 760) {
            if ($names) {
                $names.each(function () {
                    $(this).click(function (e) {
                        var getCurrentEle = $(this)[0];
                        var getCurrentId = $(getCurrentEle).attr('id');
                        var getCurrentIdVal = getCurrentId.split('-')[1];
                        $('html, body').animate({
                            scrollTop: $('#' + getCurrentIdVal).offset().top - 150
                        }, $scrollSpeed);
                    });
                });
            }
        }

    });


    return {
        initialize: function () {
            var $components = $("." + componentClass);
            walkinBathScroll($components);
        }
    }
}();

$(function () {
    $(document).ready(function () {
        axKOH.main.walkinBath.initialize();
    });
});
