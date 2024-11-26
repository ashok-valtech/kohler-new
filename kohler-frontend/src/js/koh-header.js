/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */
function createCookie(name, value, minutes, locale) {
    var expires = "",
        cookie = "";
    if (minutes) {
        var date = new Date;
        date.setTime(date.getTime() + 60 * minutes * 1e3),
            expires = "expires=" + date.toUTCString()
    }
    cookie = name + "=" + value + "; " + expires + "; path=/" + ";";
    document.cookie = cookie
}

function switchLanguage (locale, isParent, currentSiteUrl, targetSiteUrl) {
	// Code for keeping the same url while switching the language
    if (locale != '' && locale != undefined) {
        var getLanguageCode = locale.split('_')[0];
        var getCurrentUrl = window.location.href;
        var parameters = '';
        if (getCurrentUrl.indexOf('?') > 0) {
        	parameters = getCurrentUrl.substring (getCurrentUrl.indexOf("?"), getCurrentUrl.length)
        }
        if (isParent) {
        	var finalUrl = window.location.protocol + "//" + window.location.host;
        	var currentUrl = window.location.pathname;
        	if (currentUrl === currentSiteUrl) {
        		finalUrl = finalUrl + currentUrl.replace(currentSiteUrl, targetSiteUrl);
        	} else {
        		finalUrl = finalUrl + currentUrl.replace(currentSiteUrl + "/", targetSiteUrl);
        	}
        	finalUrl = finalUrl + parameters;
        	location.assign(finalUrl);
        } else {
        	var finalUrl = window.location.protocol + "//" + window.location.host;
        	var currentUrl = window.location.pathname;
        	if (currentUrl === currentSiteUrl) {
        		finalUrl = finalUrl + currentUrl.replace(currentSiteUrl, targetSiteUrl);
        	} else if (currentUrl === currentSiteUrl + "/") {
        		finalUrl = finalUrl + currentUrl.replace(currentSiteUrl + "/", targetSiteUrl);
        	} else if (currentUrl.indexOf (currentSiteUrl) === 0) {
        		if (targetSiteUrl === "/") {
        			finalUrl = finalUrl + currentUrl.replace(currentSiteUrl + "/", targetSiteUrl);
        		} else {
        			finalUrl = finalUrl + currentUrl.replace(currentSiteUrl, targetSiteUrl + "/");
        		}
        	}
        	finalUrl = finalUrl + parameters;
        	location.assign(finalUrl);
        }
    }
}
function addRemeCookie(remeElement, locale) {
    var cookieName = remeElement.data("cookie-name");
    var cookieValue = remeElement.data("cookie-value");
    if (locale !== '') {
        cookieValue = locale;
    }
    var durationMinutes = remeElement.data("cookie-duration");
    var expires = "",
        cookie = "";
    if (durationMinutes === null || durationMinutes === '') {
        var date = new Date;
        date.setTime(date.getTime() + 60 * durationMinutes * 1e3),
            expires = "expires=" + date.toUTCString()
    }
    createCookie(cookieName, cookieValue, durationMinutes, locale);
}

function addAlternateFilterCookie (remeElement, locale) {
    //alternate_filter_cookie_name
    var alternateFilterCookieName = remeElement.data("alternate_filter_cookie_name");
    createCookie(alternateFilterCookieName, "YES", 2, locale);
}

$(document).on('click','.koh-link-language-left', function(e) {
    e.preventDefault();
    var url = window.location.href;
    var splitedUrl = url.split('/');
    var ori = window.location.origin; // Getting root URL
    var remeElement = $('#compare-cookie-info-reme');
    var locale = this.getAttribute('data-locale');
    addRemeCookie(remeElement, locale);
    addAlternateFilterCookie (remeElement, locale);
    var isParent = $(this).hasClass("parent-mount");
    var currentSiteUrl = $(this).data("current-site-url");
    var targetSiteUrl = $(this).attr('href');
    if ($(this).hasClass("koh-link-language-active")) {
    	window.location.href = url;
    } else {
    	switchLanguage (locale, isParent, currentSiteUrl, targetSiteUrl);
    }
})
jQuery(document).ready(function($) {
    var remeElement = $('#compare-cookie-info-reme');
    if (remeElement !== 'undefined') {
        if (remeElement.data("multisite") === 'yes') {
            addRemeCookie(remeElement, '');
        }
    }
});
