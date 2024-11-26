$(document).ready(function () {
    $('.koh-customer-carousel').slick({
        autoplay: false,
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        dots: false,
        arrows:true,
        responsive: [
            {
                breakpoint: 1025,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                    infinite: true,
                    dots: false,
                   arrows:true,
                }
            },

            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1,
                    arrows: true,
                    dots: false,
                }
            }
        ]

    });
})