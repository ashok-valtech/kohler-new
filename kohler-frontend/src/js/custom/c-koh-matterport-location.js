$(document).ready(function () {
    var $carousel = $('.koh-carousel-showroom');

    var $carouselControls = $('<div class="koh-carousel-controls-showroom"></div>');
    $carousel.prepend($carouselControls);

    var config = {
        autoplay: false,
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        dots: true,
        dotsClass: "koh-carousel-indicators-showroom",
        arrows:false,
        appendDots: $carouselControls
    };

    $('.koh-slide-collection-showroom').slick(config);

    var $showroomDiv= $('.koh-showroom-lists ul');
    var $showroomArray = $showroomDiv.find('.koh-showroom-list');
    var $showroomFirstDiv = $showroomDiv.find('.koh-showroom-list:first-child');
    
    $.each($showroomArray, function (index, element) {
        $(element).on('click', function () {
            $showroomArray.removeClass('selected');
            $('.koh-slide-collection-showroom').slick('slickGoTo', index);
            $(this).addClass('selected');
        })
    });

    $($showroomFirstDiv).addClass('selected');

});


