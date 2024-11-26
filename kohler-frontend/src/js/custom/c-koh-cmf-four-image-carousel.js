$(document).ready(function () {
    $('.koh-img-carousel').slick({
        autoplay: false,
        infinite: true,
        slidesToShow: 5,
        slidesToScroll: 1,
        dots: false,
        arrows:true,
        responsive: [
           
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1,
                }
            },
            
            {
            	breakpoint: 840,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2,
                }
            },
            
        ]

    });
})