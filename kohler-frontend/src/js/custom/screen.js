// Adding script for google captcha
var head = document.getElementsByTagName('head')[0];
var script = document.createElement('script');
script.type = 'text/javascript';
script.onload = function () {
    scaleCaptcha();
}

var scaleCaptcha = function () {

    // Width of the reCAPTCHA element, in pixels
    var reCaptchaWidth = 304;
    // Get the containing element's width
    var containerWidth = $('.captcha-container').width();
    var captchaElements = $('.g-recaptcha');
    // Only scale the reCAPTCHA if it won't fit
    // inside the container
    if (containerWidth <= reCaptchaWidth) {
        var scale = 0.82;
        captchaElements.css('transform', 'scale(' + scale + ')').css('-webkit-transform', 'scale(' + scale + ')').css('-ms-transform', 'scale(' + scale + ')').css('-o-transform', 'scale(' + scale + ')').css('transform-origin', '0 0').css('-webkit-transform-origin', '0 0').css('-ms-transform-origin', '0 0').css('-o-transform-origin', '0 0');
        $('.captcha-container').height(68);
    }
}
script.src = 'https://www.google.com/recaptcha/api.js';
head.appendChild(script);

(function() {
	document.getElementById('hippo-form-submit').addEventListener("click", validateLoginForm);
})();

function showError(ele, errorMessage, errorMiliSeconds) {
    $(ele).find('.koh-error').remove();
    $(ele).append("<div class='koh-error'>" + errorMessage + "</div>");
    $('#g-recaptcha').css({
        'border-color': '#e7102e'
    })
    setTimeout(function () {
    	$(ele).find('.koh-error').remove();
    	$('.g-recaptcha > div').css({
            'border': 'none'
        })
    }, errorMiliSeconds);
}

function removeError(ele) {
    $(ele).find('.koh-error').remove();
    $('#g-recaptcha').css({
        'border-color': '#cccccc'
    })
}

function validateLoginForm(e) {
	e.preventDefault(); 
	var recaptchaResponse = grecaptcha.getResponse();
    var isValid = true;
    
    if (recaptchaResponse.length === 0) {
        isValid = false;
        showError('.form-group', $('#captchaError').val(), $('#captchaErrorHideIn').val());
        $('.g-recaptcha > div').css({
            'border': '1px solid #e7102e'
        })
    } else {
        removeError('.form-group');
        $('.g-recaptcha > div').css({
            'border': 'none'
        })
    }
    if (isValid == true) {
    	document.getElementById('hippo-login-panel-form').submit();
    }
    return isValid
}