/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

var axKOH = axKOH || {};

axKOH.utils = axKOH.utils || {};

// Handy utility function for pretty-printing an object
axKOH.utils.objToString = function(obj) {
    return JSON.stringify(obj, null, 4);
};

// Function for logging so it can be turned on/off easily
axKOH.utils.debugLog = function(content) {
    /*if (console) {
        console.log(content);
    }*/
};

axKOH.utils.toggleClass = function($element, className, addIt) {
    if (addIt) {
        $element.addClass(className);
    } else {
        $element.removeClass(className);
    }
};

axKOH.utils.hideElement = function($element, hideIt) {
    var hideClass = "hidden";
    axKOH.utils.toggleClass($element, hideClass, hideIt);
};

axKOH.utils.openMenu = function($element, openIt) {
    var openClass = "open";
    axKOH.utils.toggleClass($element, openClass, openIt);
};

axKOH.utils.disableLinks = function(element) {
    element.find('a').click(function(event) {
        event.preventDefault();
    });
};

axKOH.utils.stopVideos = function(element) {
    var iframe = element.querySelector('iframe');
    var video = element.querySelector('video');
    if (iframe !== null) {
        var iframeSrc = iframe.src;
        iframe.src = iframeSrc;
    }
    if (video !== null) {
        video.pause();
    }
};

