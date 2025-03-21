

var firstNameErrorMessageLink = $('#firstNameErrorMessageLink').val();
var emailErrorMessageLink = $('#emailErrorMessageLink').val();
var mobileErrorMessageLink = $('#mobileErrorMessageLink').val();
var captchaErrorMessageLink = $('#captchaErrorMessageLink').val();
var emailErrorMessageLink2 = $('#emailErrorMessageLink2').val();
var mobileErrorMessageLink2 = $('#mobileErrorMessageLink2').val();
var messageTimingLink = $('#messageTimingLink').val();
var messageFadingTimingLink = $('#messageFadingTimingLink').val();
var selectStoreErrorMessage = $('#selectStoreErrorMessage').val();
var selectRamadanPackageErrorMessage = $('#selectRamadanPackageErrorMessage').val();


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
    $('#bathtubcontactform').submit(function (e) {
        e.preventDefault();
        var contactEmailLink = $(this).attr('action');
        validateContactForm(contactEmailLink);
    })
});

function validateContactForm(contactEmailLink) {
    var districtTypeVal = $('#koh-districts-request-type').val();
    var firstName = $('#koh-contact-first-name').val();
    var emailAddress = $('#koh-contact-email-address').val();
    var mobileNumber = $('#koh-contact-mobile-number').val();
    var thankyouPage = $('#koh-contact-ThankyouPage').val();
    var recaptchaResponse = grecaptcha.getResponse();
    var isValid = true;
    if (firstName == '' || firstName == null) {
        isValid = false;
        showError('#koh-contact-first-name', firstNameErrorMessageLink);
    }
   
    if (emailAddress == '' || emailAddress == null) {
        isValid = false;
        showError('#koh-contact-email-address', emailErrorMessageLink);
    }
    if (mobileNumber == '') {
        isValid = false;
        showError('#koh-contact-mobile-number', mobileErrorMessageLink);
    }
    if (districtTypeVal == '') {
        isValid = false;
        showError('#koh-districts-request-type', selectStoreErrorMessage);
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
        var fdata = new FormData();
        fdata.append("koh-districts-request-type", districtTypeVal);
        fdata.append("koh-contact-first-name", firstName);
        fdata.append("koh-contact-email-address", emailAddress);
        fdata.append("koh-contact-mobile-number", mobileNumber);
        fdata.append("koh-contact-google-captcha", recaptchaResponse);
        $.ajax({
            url: contactEmailLink,
            cache: false,
            contentType: false,
            processData: false,
            data: fdata,
            type: 'POST',
            beforeSend: function () {
                $('#koh-contact-form-btn').addClass('koh-form-btn-loader');
            },
            success: function (data) {
            	 if (data.success == 'true') {
            		 window.open(thankyouPage.concat('/?name=').concat(firstName), '_self');
            	 } else {
                	grecaptcha.reset();
                    $('#koh-contact-form-btn').removeClass('koh-form-btn-loader');
                    $('.koh-upload-wrapper').show();
                    $("html, body").animate({ scrollTop: 0 }, "fast");
                    $('form').find("input[type=text], input[type=file], textarea, select").val("");
                	
                    $('#failureMessage').removeClass('hide').fadeIn('fast');
                    setTimeout(function () {
                        $('#failureMessage').fadeOut(messageFadingTimingLink, function () {
                            $(this).addClass('hide');
                        });
                    }, messageTimingLink); // <-- time in milliseconds
                }
            }
        })
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
$('#koh-contact-mobile-number').on('input', function (event) {
    var start = this.selectionStart - 1,
        end = this.selectionEnd - 1;
    if ($(this).val().match(/[^0-9-+()]/gi)) {
        $(this).val($(this).val().replace(/[^0-9-+()]/gi, ''));
        this.setSelectionRange(start, end);
    }
});

$(document).on('keyup change', '#bathtubcontactform input, #bathtubcontactform select', function (e) {
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
    if (currentIdName == 'koh-districts-request-type') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, selectStoreErrorMessage);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
})

