$(document).ready(function () {
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

    $(".back-button").click(function() {
        window.history.back();
    });
})