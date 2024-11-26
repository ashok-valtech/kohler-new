var firstNameErrorMessageLink = $('#firstNameErrorMessageLink').val();
var lastNameErrorMessageLink = $('#lastNameErrorMessageLink').val();
var emailErrorMessageLink = $('#emailErrorMessageLink').val();
var mobileErrorMessageLink = $('#mobileErrorMessageLink').val();
var captchaErrorMessageLink = $('#captchaErrorMessageLink').val();
var emailErrorMessageLink2 = $('#emailErrorMessageLink2').val();
var mobileErrorMessageLink2 = $('#mobileErrorMessageLink2').val();
var messageTimingLink = $('#messageTimingLink').val();
var messageFadingTimingLink = $('#messageFadingTimingLink').val();
var homeAddressErrorMessageLink = $('#homeAddressErrorMessageLink').val();
var sourcePurchaseErrorMessageLink = $('#sourcePurchaseErrorMessageLink').val();
var skuErrorMessageLink = $('#skuErrorMessage').val();

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

var disableSubmit = function(errorCount) {
	if(errorCount > 0) {
		$("#koh-contact-form-btn").attr("disabled", true);
	}else {
		$("#koh-contact-form-btn").removeAttr("disabled")

	}
}

$(document).ready(function () {
    $('#afterSalesServiceForm').submit(function (e) {
        e.preventDefault();
        var afterSalesServiceLink = $(this).attr('action');
        validateAfterSalesServiceForm(afterSalesServiceLink);
    })
});

function onAjaxError(customMsg) {
	if(customMsg.length > 0) {
		$("#failureMessage").html(customMsg);
	}
	grecaptcha.reset();
	$('#koh-contact-form-btn').removeClass('koh-form-btn-loader');
	$('#koh-contact-upload-file-name').hide();
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

function validateAfterSalesServiceForm(afterSalesServiceLink) {
    var firstName = $('#koh-contact-first-name').val();
    var lastName = $('#koh-contact-last-name').val();
    var emailAddress = $('#koh-contact-email-address').val();
    var mobileNumber = $('#koh-contact-mobile-number').val();
    var comment = $('#koh-contact-request').val();
    var thankyouPage = $('#koh-contact-ThankyouPage').val();
    var homeAddress = $('#koh-contact-address').val();
    var prodSku = $('#koh-contact-sku').val();

	var sourcePurchaseSelected = $('input[name="koh-source-of-purchasing"]:checked').val();
    var sourcePurchaseText = $('#koh-source-purchase-name').val();
    var sourcePurchaseStr = sourcePurchaseSelected + ": "+sourcePurchaseText;
    var recaptchaResponse = grecaptcha.getResponse();
    var isValid = true;

    if(sourcePurchaseText.length === 0) {
		isValid = false;
		showError("#koh-source-purchase-name", sourcePurchaseErrorMessageLink)
	}
    
    if (firstName == '' || firstName == null) {
        isValid = false;
        showError('#koh-contact-first-name', firstNameErrorMessageLink);
    }
    if (lastName == '' || lastName == null) {
        isValid = false;
        showError('#koh-contact-last-name', lastNameErrorMessageLink);
    }
    if (emailAddress == '' || emailAddress == null) {
        isValid = false;
        showError('#koh-contact-email-address', emailErrorMessageLink);
    }
    if (mobileNumber == '') {
        isValid = false;
        showError('#koh-contact-mobile-number', mobileErrorMessageLink);
    }
    if (homeAddress == '') {
        isValid = false;
        showError('#koh-contact-address', homeAddressErrorMessageLink);
    }

    if(prodSku == '') {
        isValid = false;
        showError('#koh-contact-sku', skuErrorMessageLink)
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
        fdata.append("koh-contact-first-name", firstName);
        fdata.append("koh-contact-last-name", lastName);
        fdata.append("koh-contact-email-address", emailAddress);
        fdata.append("koh-contact-mobile-number", mobileNumber);
        fdata.append("koh-source-of-purchasing", sourcePurchaseStr);
        fdata.append("koh-contact-address", homeAddress);
        fdata.append("koh-contact-request", comment);
        fdata.append("koh-contact-sku", prodSku);
        fdata.append("koh-contact-google-captcha", recaptchaResponse);
        $.ajax({
            url: afterSalesServiceLink,
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
            		 window.open(thankyouPage, '_self');
            	 } else {
                	var errorMessage = $("#formErrorMessage").val();
					onAjaxError(errorMessage);
                }
            },
            error: function(error) {
				var errorMessage = $("#formErrorMessage").val();
				onAjaxError(errorMessage);
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
    disableSubmit(1);
}
var removeError = function (ele) {
    $(ele).parent().find('.koh-error').remove();
    $(ele).css({
        'border-color': '#cccccc'
    })
    $(ele).closest('.form-group').css({
        'margin-bottom': '15px'
    })
    var sfmcErrorCount = $(document).find('.koh-error').size();
	disableSubmit(sfmcErrorCount);
}

var showCheckboxError = function(ele,errorMessage) {
	$(ele).find('.koh-sfmc-error').remove();
	$(ele).append("<div class='koh-sfmc-error'>" + errorMessage + "</div>");
	disableSubmit(1);
};

var removeCheckboxError = function(ele) {
	$(ele).find('.koh-sfmc-error').remove();
	var sfmcErrorCount = $(document).find('.koh-sfmc-error').size();
    var uploadFileError = $(document).find('.koh-file-upload-error').size();
    var totalErrorCount = sfmcErrorCount + uploadFileError;
    disableSubmit(totalErrorCount);
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


$(document).on('keyup change', '#afterSalesServiceForm input, #afterSalesServiceForm select, #afterSalesServiceForm textarea', function (e) {
    var currentIdName = $(this).attr('id');
    var currentId = '#' + currentIdName;
    if (currentIdName == 'koh-contact-request-type') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, requestTypeErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
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
    if (currentIdName == 'koh-contact-last-name') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, lastNameErrorMessageLink);
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
    if (currentIdName == 'koh-contact-address') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, homeAddressErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentIdName == 'koh-contact-sku') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, skuErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }

    if (currentIdName == 'koh-source-purchase-name') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, sourcePurchaseErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }


})

function recaptchaCallback() {
	var captchaRes = grecaptcha.getResponse();
	if(captchaRes.length !== 0) {
		removeError(".captcha-container");
		$("#g-recaptcha").css("border", "none");
		$("#g-recaptcha").children('div:first-child').css("border", "none");
	}
};

