$(function() {
	$(document).ready(function () {
		if ($(window).width() < 760) { 
			var componentClass = "koh-page";
			var $components = $("." + componentClass);
			var $shoppingGuideCarousel = $components.find(".koh-shopping-guide-container");
        	$shoppingGuideCarousel.each(function (){
	            $(this).addClass('koh-shopping-guide-carousel');
	            $(this).slick({
	                responsive: [
	                    {
	                        breakpoint: 760,
	                        settings: {
								slidesToShow: 1,
								slidesToScroll: 1,
								arrows: false,
								dots: true,
								autoplay: true,
								infinite: true,
								autoplaySpeed: 5000,
	                            
	                        }
	                    }
	                ]
	            });
			});
		} 
	});
});	