

var firstNameErrorMessageLink = $('#firstNameErrorMessageLink').val();
var scoreErrorMessageLink=$('#scoreErrorMessageLink').val();
var interestedErrorMessageLink=$('#interestedErrorMessageLink').val();
var emailErrorMessageLink = $('#emailErrorMessageLink').val();
var mobileErrorMessageLink = $('#mobileErrorMessageLink').val();
var captchaErrorMessageLink = $('#captchaErrorMessageLink').val();
var emailErrorMessageLink2 = $('#emailErrorMessageLink2').val();
var mobileErrorMessageLink2 = $('#mobileErrorMessageLink2').val();
var checkboxErrorMessageLink = $('#checkboxerrorlink').val();


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

$(document).ready(function () {
    $('#mucontactUsForm').submit(function (e) {
       var valid= validateContactForm();
       if(!valid)
    	   e.preventDefault(); 
    })
});

function validateContactForm() {
    var firstName = $('#koh-contact-first-name').val();
    var emailAddress = $('#koh-contact-email-address').val();
    var mobileNumber = $('#koh-contact-mobile-number').val();
    var score = $('#koh-contact-score').val();
    var products = $('#koh-contact-interestedproducts').val();
    var thankyouPage = $('#koh-contact-ThankyouPage').val();
    var recaptchaResponse = grecaptcha.getResponse();
    var isValid = true;
    
    if (firstName == '' || firstName == null) {
        isValid = false;
        showError('#koh-contact-first-name', firstNameErrorMessageLink);
    }else {
    	$('#namefirst').val()=encodeURIComponent(firstName);
    }
    if (emailAddress == '' || emailAddress == null) {
        isValid = false;
        showError('#koh-contact-email-address', emailErrorMessageLink);
    } else {
    		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    	    if (reg.test(emailAddress) == false) {
    	    	isValid = false;
    	    	showError('#koh-contact-email-address', emailErrorMessageLink2);
    	    }
    	
    }
    
    if (mobileNumber == '' || mobileNumber == null ) {
        isValid = false;
        showError('#koh-contact-mobile-number', mobileErrorMessageLink);
    }
   
    if (score == '') {
        isValid = false;
        showError('#koh-contact-score', scoreErrorMessageLink);
    }
    if (products == '') {
        isValid = false;
        showError('#koh-contact-interestedproducts', interestedErrorMessageLink);
    }
    if (recaptchaResponse.length === 0) {
        isValid = false;
        showError('.g-recaptcha', captchaErrorMessageLink);
        $('.g-recaptcha > div').css({
            'border': '1px solid #e7102e'
        })
    } else {
        removeError('#g-recaptcha');
        $('.g-recaptcha > div').css({
            'border': 'none'
        })
    }
    if (isValid == true) {
    	return true;
     }
}
var showError = function (ele, errorMessage) {
    $(ele).parent().find('.koh-error').remove();
    $(ele).after("<div class='koh-error'>" + errorMessage + "</div>");
    $(ele).css({
        'border-color': '#e7102e'
    })
    $(ele).closest('.form-group').css({
        'margin-bottom': '6px'
    })
}
var removeError = function (ele) {
    $(ele).parent().find('.koh-error').remove();
    $(ele).css({
        'border-color': '#cccccc'
    })
    $(ele).closest('.form-group').css({
        'margin-bottom': '15px'
    })
}
var validateEmail = function (ele) {
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var currentVal = $(ele).val();
    if (reg.test(currentVal) == false) {
        return false;
    }
    return true;
}

$(document).on('keyup change', '#mucontactUsForm input, #mucontactUsForm select, #mucontactUsForm textarea', function (e) {
    var currentIdName = $(this).attr('id');
    var currentId = '#' + currentIdName;
    if (currentIdName == 'koh-contact-first-name') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, firstNameErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentIdName == 'koh-contact-email-address') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, emailErrorMessageLink);
        } else if (!validateEmail(currentId)) {
            isValid = false;
            showError(currentId, emailErrorMessageLink2);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentIdName == 'koh-contact-mobile-number') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, mobileErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentIdName == 'koh-contact-score') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, scoreErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentIdName == 'koh-contact-interestedproducts') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, interestedErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
})

