/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

function handleAutoSuggest(event) {
    var textInput = event.currentTarget.value;
    if (textInput.length > 2 || 0 == textInput.toLowerCase().indexOf("k-")) {
		 if (0 == textInput.toLowerCase().indexOf("k-")) {
				var length = textInput.length;
				textInput = textInput.substr(2, length);
		}
        $.getJSON($(".koh-primary-nav-suggest-link").val() + "?q=" + textInput, function(data, status) {
            function getBoldValue(searchText, original) {
                if (original.toLowerCase().indexOf(searchText.toLowerCase()) > -1) {
                    var start = original.toLowerCase().indexOf(searchText.toLowerCase());
                    var end = searchText.length;
                    return original.substr(0, start) + "<mark>" + original.substr(start, end) + "</mark>" + original.substr(start + end, original.length);
                }
                return original;
            }
            if ("success" == status) {
                var ul = $("div#search-suggestions-container > div.search-suggestions-scroller > ul");
                ul.empty();
                if (data.suggestions === 'undefined' || data.suggestions.length == 0) {
                    $("div#search-suggestions-container").hide();
                } else {
                    $.each(data.suggestions, function(name, value) {
                    	/*var displayValue = value;
                          displayValue = displayValue.replace("(R)","<sup>&reg;</sup>");
                    	  displayValue = displayValue.replace("(TM)","<sup>&trade;</sup>");*/
                        ul.append($("<li class=\"suggestion-item\"><a  href=\"" + value + "\">" + getBoldValue(textInput, value) + "</a></li>"));
                    });

                    $(".suggestion-item").on("click", function(e) {
                        e.preventDefault();
                        $("#koh-nav-searchbox").val($($(e.currentTarget).children()[0]).attr("href"));
                        $(".koh-site-search-form")[0].submit();
                    });

                    $("div#search-suggestions-container").show();
                }
            }
        })
    } else {
        $("div#search-suggestions-container").hide();
    }
}

$(document).ready(function() {
    $("#koh-nav-searchbox").on("keyup", handleAutoSuggest);
});

$(window).resize(function() {
    $("#koh-nav-searchbox").on("keyup", handleAutoSuggest);
});

$(window).load(function() {
    $("#koh-nav-searchbox").on("keyup", handleAutoSuggest);
    $('body').css('pointer-events','all');
});
