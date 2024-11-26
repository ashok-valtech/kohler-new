/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

// JavaScript Namespacing
var axKOH = axKOH || {};
axKOH.utils = axKOH.utils || {};

axKOH.utils.socialShare = (function () {
    var houzzID = "28925";

    // Get functions for share link inserts
    var getURL = function ($target) {
        return encodeURI($target.data("share-url") || window.location.href);
    };

    var getTitle = function ($target) {
        var shareTitle = document.getElementById("shareTitle");
        if(shareTitle !== null && shareTitle != 'undefined'){
    		var shareTitleValue = shareTitle.value;
	        if (shareTitleValue.length != 0) {
	            return shareTitleValue
	        }
        }else{
         return $target.data("share-title") || ""
    	}  
    };

    var getImage = function ($target) {
    	var shareImage = document.getElementById("shareImage");
    	if(shareImage !== null && shareImage != 'undefined'){
    		var shareImageValue = shareImage.value;
            if (shareImageValue.length != 0) {
                return shareImageValue
            }else{
            	return $target.data("share-image") || ""
            }
    	}else{
         return $target.data("share-image") || ""
    	}
     };

    var getDescription = function ($target) {
        var shareDescription = document.getElementById("shareDescription");
        if(shareDescription !== null && shareDescription != 'undefined'){
        	var shareDescriptionValue = shareDescription.value.replace(/(<[^>]+?>|<>|<\/>)/img, "");
	        if (shareDescriptionValue.length != 0) {
	            return shareDescriptionValue
	        }
        }else{
         return $target.data("share-description") || ""
    	}    
    };

    var getSource = function ($target) {
        return $target.data("share-source") || "KOHLER"
    };

    var getHashtags = function ($target) {
        return $target.data("share-hashtags") || ""
    };

    var isVideoShare = function ($target) {
        return $target.data("share-video") || "false"
    };

    var getCategory = function ($target) {
        return $target.data("share-category") || ""
    };

    var formatShareLink = function ($target, linkTemplate) {
        var formattedLink = linkTemplate;

        formattedLink = formattedLink.replace(/{url}/g, getURL($target));
        formattedLink = formattedLink.replace(/{title}/g, getTitle($target));
        formattedLink = formattedLink.replace(/{img}/g, getImage($target));
        formattedLink = formattedLink.replace(/{via}/g, getSource($target));
        formattedLink = formattedLink.replace(/{hashtags}/g, getHashtags($target));
        formattedLink = formattedLink.replace(/{is_video}/g, isVideoShare($target));
        formattedLink = formattedLink.replace(/{category}/g, getCategory($target));
        formattedLink = formattedLink.replace(/{description}/g, getDescription($target));

        return formattedLink;
    };

    var showWindow = function (targetURL, targetProperties) {
        var windowProps = 'menubar=no,location=yes,resizable=yes,scrollbars=yes,status=no,width={width},height={height},top={top},left={left}';

        if (targetProperties && targetProperties.width && targetProperties.height && screen && screen.height) {
            windowProps = windowProps.replace(/{width}/g, targetProperties.width);
            windowProps = windowProps.replace(/{height}/g, targetProperties.height);
            windowProps = windowProps.replace(/{top}/g, (screen.height / 2) - (targetProperties.height / 2));
            windowProps = windowProps.replace(/{left}/g, (screen.width / 2) - (targetProperties.width / 2));
            window.open(targetURL, 'shareWindow', windowProps).focus();
        } else {
            window.open(targetURL);
        }
    };

    // Share functions
    var shareOnPinterest = function ($target) {
        var shareLink = formatShareLink($target, 'https://pinterest.com/pin/create/bookmarklet/?media={img}&url={url}&is_video={is_video}&description={title}');
        showWindow(shareLink, {
            width: 750,
            height: 331
        });
    };


    var shareOnHouzz = function ($target) {
        // Because Houzz provides a very specific script tag in an iframe for their share link,
        // this share option requires special handling. We replace the existing button with the
        // Houzz-provided code snippet, and then the user has to manually click the new Houzz button

        $target.addClass("clickedHouzz");
        // $target is <button class="koh-share-houzz">Label</button>
        var $houzzButton = $target;
        var $houzzButtonContainer = $target.parent();
        var $houzzFrameContainer = $houzzButtonContainer.find(".koh-houzz-button-target");

        // If the container for the special Houzz code doesn't exist, add it
        if ($houzzFrameContainer.length <= 0) {
            $houzzButtonContainer.append('<span class="koh-houzz-button-target"></span>');
            $houzzFrameContainer = $houzzButtonContainer.find(".koh-houzz-button-target");
        }

        // Now that the container exists (assuming nothing failed)
        if ($houzzFrameContainer.length > 0) {
            $houzzFrameContainer.html("Hello World");

            // If the iframe doesn't already exist, add the special Houzz code
            //if ( $houzzFrameContainer.find("iframe") <= 0 ) {

            // HTML snippet for this is provided by Houzz, and must match exactly
            var htmlSnippet = '<a class="houzz-share-button" data-url="{url}" data-hzid="{houzzID}" data-title="{title}" data-img="{img}" data-desc="{description}" data-category="{category}" data-showcount="0" href="http://www.houzz.com"></a>';
            htmlSnippet = formatShareLink($target, htmlSnippet);
            htmlSnippet = htmlSnippet.replace(/{houzzID/g, houzzID);
            htmlSnippet += '<script>(function(d,s,id){if(!d.getElementById(id)){var js=d.createElement(s);js.id=id;js.async=true;js.src="//platform.houzz.com/js/widgets.js?"+(new Date().getTime());var ss=d.getElementsByTagName(s)[0];ss.parentNode.insertBefore(js,ss);}})(document,"script","houzzwidget-js");</script>';

            $houzzFrameContainer.html(htmlSnippet);
            //}
            // Hide the pretty button
            $houzzButton.hide();
        }
    };

    var shareOnFacebook = function ($target) {
    	var image = getImage($target);
    	var shareLink = '';
    	if(image === ''){
    		shareLink = formatShareLink($target, "https://www.facebook.com/sharer/sharer.php?u={url}&title={title}&description={description}");
    	}else{
    		shareLink = formatShareLink($target, "https://www.facebook.com/sharer/sharer.php?u={url}&title={title}&description={description}&picture={img}");
    	}
        showWindow(shareLink, {
            width: 600,
            height: 270
        });
    };

    var shareOnTwitter = function ($target) {
        var shareLink = formatShareLink($target, "https://twitter.com/share?url={url}&text={title}");
        showWindow(shareLink, {
            width: 600,
            height: 440
        });
    };

    var shareOnGoogle = function ($target) {
        var shareLink = formatShareLink($target, 'https://plus.google.com/share?url={url}');
        showWindow(shareLink, {
            width: 600,
            height: 270
        });
    };

    var shareOnEmail = function ($target) {
        // Need to figure out what the handling for email is
    };

    // Initialize entire panel
    var initSharePanel = function ($sharePanel) {

        /* Assumes the following structure, where the ul is passed in as $sharePanel:

        <ul>
        	<li><button class="koh-share-pinterest">Label</button></li>
        	<li><button class="koh-share-houzz">Label</button></li>
        	<li><button class="koh-share-facebook">Label</button></li>
        	<li><button class="koh-share-twitter">Label</button></li>
        	<li><button class="koh-share-google">Label</button></li>
        	<li><button class="koh-share-email">Label</button></li>
        	<li><button class="koh-share-showroom">Label</button></li>
        </ul>
        */

        var $shareButtons = $sharePanel.find("li > button");
        $shareButtons.each(function () {
            var $button = $(this);

            $button.on("click", function () {
                if ($button.hasClass("koh-share-pinterest")) {
                    shareOnPinterest($button);
                } else if ($button.hasClass("koh-share-houzz")) {
                    shareOnHouzz($button);
                } else if ($button.hasClass("koh-share-facebook")) {
                    shareOnFacebook($button);
                } else if ($button.hasClass("koh-share-twitter")) {
                    shareOnTwitter($button);
                } else if ($button.hasClass("koh-share-google")) {
                    shareOnGoogle($button);
                } else if ($button.hasClass("koh-share-email")) {
                    shareOnEmail($button);
                } else if ($button.hasClass("koh-share-showroom")) {
                    // Intentionally left blank; showroom feature not implemented yet
                }
            });
        });

    };

    var makeTooltip = function ($container, $button, $panel, tooltipID, tooltipClass) {
        // Preserve original panel HTML so we can put it back when the tooltip closes
        var panelHTML = $panel.html();

        if (typeof tooltipID != 'undefined') {
            $button.qtip({
                id: tooltipID,
                content: {
                    text: $panel
                },
                position: {
                    my: 'bottom right', // Position my top left...
                    at: 'center center', // at the bottom right of...
                    container: $container,
                    viewport: true
                },
                show: {
                    event: 'click'
                },
                hide: {
                    event: 'unfocus',
                    fixed: true
                },
                style: {
                    classes: 'koh-qtip-popover ' + tooltipClass + ' qtip-light qtip-shadow',
                    tip: false
                },
                events: {
                    hide: function () {
                        // special clean up of Houzz button stuff
                        if ($('#houzzwidget-js')) {
                            delete window.houzz;
                            delete window.hzmr;
                            $('#houzzwidget-js').remove();
                        }

                        // reset the html and reinitialize onclick handlers
                        $panel.html(panelHTML);
                        initSharePanel($panel);
                    }
                }
            });
        } else {
            $button.qtip({
                id: tooltipID,
                content: {
                    text: $panel
                },
                position: {
                    my: 'top right',
                    at: 'center center',
                    viewport: true
                },
                show: {
                    event: 'click'
                },
                hide: {
                    event: 'unfocus',
                    fixed: true
                },
                style: {
                    classes: 'koh-qtip-popover ' + tooltipClass + ' qtip-light qtip-shadow',
                    tip: false
                },
                events: {
                    hide: function () {
                        // special clean up of Houzz button stuff
                        if ($('#houzzwidget-js')) {
                            delete window.houzz;
                            delete window.hzmr;
                            $('#houzzwidget-js').remove();
                        }

                        // reset the html and reinitialize onclick handlers
                        $panel.html(panelHTML);
                        initSharePanel($panel);
                    }
                }
            });
        }

    };

    /* Defines functions exposed through manager object */
    return {
        initialize: function ($panel) {
            initSharePanel($panel);
        },
        initAsTooltip: function ($container, $button, $panel, tooltipID, tooltipClass) {
            initSharePanel($panel);
            makeTooltip($container, $button, $panel, tooltipID, tooltipClass);
        },
        pinterest: function ($target) {
            shareOnPinterest($target);
        },
        houzz: function ($target) {
            shareOnHouzz($target);
        },
        facebook: function ($target) {
            shareOnFacebook($target);
        },
        twitter: function ($target) {
            shareOnTwitter($target);
        },
        google: function ($target) {
            shareOnGoogle($target);
        },
        email: function ($target) {
            shareOnEmail($target);
        }
    };

})();


/* Defines common code for share button */
var $socialPageShare = $(".koh-page-share");
var $socialPageShareButton = $socialPageShare.children(".koh-page-share-button");
var $socialPageSharePanel = $socialPageShare.children(".koh-page-share-popover");
axKOH && axKOH.utils && axKOH.utils.socialShare && axKOH
    .utils
    .socialShare
    .initAsTooltip($socialPageShare, $socialPageShareButton, $socialPageSharePanel);
