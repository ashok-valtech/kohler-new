var desktopMainVideoSrc = $("#desktopMainVideo").val();
var mobileMainVideoSrc = $("#mobileMainVideo").val();

var axKOH = axKOH || {};

axKOH.main = axKOH.main || {};

axKOH.main.socialManager = (function () {

    var config = {
        breakpoint: 768,
        masonry: {
            itemSelector: '.koh-social-tile',
            columnWidth: '.koh-social-tile-sizer',
            percentPosition: true
        }
    };

    var toggleMasonry = function ($tileCollection, activate) {
        if (activate) {
            // Add sizer div that masonry needs to size things properly
            if ($tileCollection.children(".koh-social-tile-sizer").length < 1) {
                $tileCollection.prepend('<div class="koh-social-tile-sizer"></div>');
            }
            $tileCollection.imagesLoaded(function () {
                $tileCollection.masonry(config.masonry);
            });
        } else {
            $tileCollection.masonry('destroy');
            // Remove sizer when we're not doing masonry
            $tileCollection.remove(".koh-social-tile-sizer");
        }
    };

    function initPromos() {
        // Locate all instances of the component
        var $component = $(".v-koh-social-scattered");
        var $container = $component.children(".koh-social-content");
        var $tileCollection = $container.children(".koh-social-tiles");
        toggleMasonry($tileCollection, true);
    }

    function getInstagramPost(callback) {
    	
    	var date = new Date();
    	var uniqueTimestamp = 
    	  date.getFullYear().toString() +
    	  ('0' + (date.getMonth() + 1)).slice(-2) +  // Months are zero-based, so add 1
    	  ('0' + date.getDate()).slice(-2) +
    	  ('0' + date.getHours()).slice(-2) +
    	  ('0' + date.getMinutes()).slice(-2) +
    	  ('0' + date.getSeconds()).slice(-2);
    	
    	var sociableInstagramAPIValue = $("#sociableInstagramAPIId").val();
    	var sociableInstagramAPI = sociableInstagramAPIValue+"?no_cache={timeStamp}";
    	var sociableInstagramAPIWithTimestamp = sociableInstagramAPI.replace("{timeStamp}", uniqueTimestamp);
    	
        fetch(sociableInstagramAPIWithTimestamp)
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(function (data) {
                var postsArray = data.posts;
               // Filter posts to only include pictures
                var picturePostsArray = postsArray.filter(function (post) {
                    return post.pic_type === "image";
                    //return post.pic_type !== "video" && post.pic_type !== "carousel";
                });
                
                  // Sort the filtered array based on likes count
               /* picturePostsArray.sort(function (a, b) {
                    return b.pic_like_count_formatted - a.pic_like_count_formatted;
                });*/
                
                var socialTilesContainer = document.querySelector('.koh-social-tiles');
                picturePostsArray.slice(0, 10).forEach(function (instaGramDetails) {
                	//console.log("postsArray", instaGramDetails);
                    var socialTileDiv = document.createElement('div');
                    socialTileDiv.className = 'koh-social-tile';
                    var linkElement = document.createElement('a');
                    linkElement.href = 'https://www.instagram.com/kohlerid/';
                    linkElement.target = '_blank';
                    var imageContainer = document.createElement('div');
                    imageContainer.className = 'koh-social-image';
                    var imgElement = document.createElement('img');
                    imgElement.src = instaGramDetails.image_url;
                    imageContainer.appendChild(imgElement);
                    linkElement.appendChild(imageContainer);
                    socialTileDiv.appendChild(linkElement);
                    socialTilesContainer.appendChild(socialTileDiv);
                });
                
             // Ensure Masonry is initialized properly after new elements and images are loaded
                var $tileCollection = $(socialTilesContainer);
                $tileCollection.imagesLoaded(function () {
                    $tileCollection.masonry('appended', $tileCollection.children('.koh-social-tile'));
                });
                
                if (typeof callback === 'function') {
                    callback();  // Call the callback function to initialize Masonry properly
                }
            })
            .catch(function (error) {
                console.error('There was a problem with the fetch operation:', error);
            });
    }

    return {
        initialize: function () {
            initPromos();
            getInstagramPost(initPromos);
        }
    };
})();

var carouselConfig = function() {
	$('.koh-img-carousel').slick({
        autoplay: false,
        infinite: true,
        slidesToShow: 7,
        slidesToScroll: 1,
        dots: false,
        arrows:true,
        responsive: [
           
            {
                breakpoint: 480,
                settings: {
					slidesToShow: 2,
					slidesToScroll: 2,
                }
            },
			{
				breakpoint: 1200,
                settings: {
					slidesToShow: 5,
					slidesToScroll: 1,
                }
			},
			{
				breakpoint: 980,
                settings: {
					slidesToShow: 4,
					slidesToScroll: 1,
                }
			},
			{
				breakpoint: 2200,
                settings: {
					slidesToShow: 4,
					slidesToScroll: 1,
                }
			}
        ]

    });
}

var videoModalFunc = function(src) {
	var currentUrl = removeEndingSlash(window.location.href);
	var videoPagePath = currentUrl+src;
	window.location.href = videoPagePath;
}

function loadContent(page, contentId) {
	$.ajax({
		url: page,
		method: 'GET',
		success: function(data) {
			var parsedHTML = $(data);
			var desiredDiv = parsedHTML.find('.koh-page');
			if (desiredDiv.length) {
				$(contentId).html(desiredDiv.html());
				inspirationPageSetup(page);
				carouselConfig();
				axKOH.main.carouselManager.init();
				if (url === "/social"){
					axKOH.main.socialManager.initialize();
				}
				$('.slick-list').css("height", "100%");
				var ele = $('.koh-carousel-controls');
				var currentSlick = $('.slick-current');
				var height = currentSlick.height() + "px";
				$(ele).css("height",height);
			} else {
				$(contentId).html('Desired content not found.');
			}
		},
		error: function() {
			$(contentId).html('Failed to load content.');
		}
	});
}

function inspirationPageSetup(url) {
	$(".play-icon").on("click", function() {
        var videoSrc = $(this).data("source");
        videoModalFunc(videoSrc);
    });
    $(".koh-horizontalrule-block").css("background", "#000000");
    $(".koh-horizontalrule-block").css("padding-top", "6.5rem");
	$(".koh-horizontalrule-block hr").css("background", "#CCCCCC");
	$(".c-koh-banner-carousel").css("background", "#E5E5E5");
	$(".container-bg-cta").last().css("padding-bottom", "70px");
	var firstPath =  $('.tablinks').first().data('url');
	if(url.includes(firstPath)) {
		$(".title-container").removeClass("background-grey");
		$(".title-container").addClass("background-light-grey");
	}
	if($(window).width() < 768) {
		$(".koh-banner-thumbnail-comp").css({
			"padding-top": "2rem",
			"padding-bottom": "5rem"
		})
		$(".embed-responsive-16by9").css({
			"padding-bottom": "0",
			"height": "50rem"
		})
		//mobilevideo
		var video = '<video class="hero-video-mobile" src="'+mobileMainVideoSrc+'" autoplay muted loop type="video/mp4"></video>';
		$(".embed-responsive-16by9").html(video);
		
		var tiles = document.querySelectorAll('.tile');
		tiles.forEach(function(tileEle, index) {
			if(index !== tiles.length - 1) {
				var hr = document.createElement('hr');
				tileEle.insertAdjacentElement('afterend', hr);
			}
		});
	}else {
		$(".koh-banner-thumbnail-comp").css({
			"padding-top": "3rem",
			"padding-bottom": "10rem"
		})
		//desktopvideo
		var video = '<video class="hero-video-mobile" src="'+desktopMainVideoSrc+'" autoplay muted loop type="video/mp4"></video>';
		$(".embed-responsive-16by9").html(video);
	}

	$(".koh-page").css({
		"border-top": "0",
		"border-bottom": "0"
	});

	$(".embed-responsive-16by9").prepend('<div class="gradient-container"></div>');
}

function removeEndingSlash(url) {
    if (url.endsWith('/')) {
        url = url.slice(0, -1);
    }
    return url;
}

var url = "";

$(document).ready(function () {
	var currentUrl = removeEndingSlash(window.location.href);
	var buttons = $('.tablinks');
	buttons.first().addClass('active');
	$('.tablinks').on('click', function(evt) {
		$('.tablinks').removeClass('active');
		var element = $(evt.currentTarget);
		$(element).addClass('active');
        var dataUrl = element.data('url');
        url = dataUrl;
		var contentUrl = currentUrl + dataUrl;
        loadContent(contentUrl, '#content1');
		window.scrollTo({
			top: 737,
			left: 0,
			behavior: 'smooth'
		});
    });

	var inspirationPath = buttons.first().data('url');
	var defaultPageUrl = currentUrl + inspirationPath
	loadContent(defaultPageUrl, '#content1');
});